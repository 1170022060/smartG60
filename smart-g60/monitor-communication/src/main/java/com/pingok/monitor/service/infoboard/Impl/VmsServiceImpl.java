package com.pingok.monitor.service.infoboard.Impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.monitor.domain.common.MbsAttribute;
import com.pingok.monitor.domain.device.TblDeviceInfo;
import com.pingok.monitor.domain.infoboard.DevInfo;
import com.pingok.monitor.domain.infoboard.VmsPubInfo;
import com.pingok.monitor.mapper.device.TblDeviceInfoMapper;
import com.pingok.monitor.service.common.IModbusService;
import com.pingok.monitor.service.common.ISocketService;
import com.pingok.monitor.service.infoboard.IVmsService;
import com.pingok.monitor.utils.ByteUtils;
import com.pingok.monitor.utils.config.InfoBoardConfig;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import com.serotonin.modbus4j.code.DataType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author
 * @time 2022/5/2 8:59
 */
@Slf4j
@Service
public class VmsServiceImpl implements IVmsService {

    @Autowired
    private IModbusService iModbusService;
    @Autowired
    private ISocketService iSocketService;

    @Override
    public int publish(String pubInfo) {

        int retCode = 200;

        JSONObject jo = JSONObject.parseObject(pubInfo);
//        JSONArray devInfoList = jo.getJSONArray("devInfo");
        List<DevInfo> devInfoList = JSON.parseArray(JSONObject.toJSONString(jo.get("devInfo")), DevInfo.class);
        JSONArray dataList = jo.getJSONArray("data");
        List<List<VmsPubInfo>> pubList = new ArrayList<>();
        for(int i = 0; i < dataList.size(); ++i) {
            List<VmsPubInfo> vmsInfoList = JSON.parseArray(JSONObject.toJSONString(dataList.get(i)), VmsPubInfo.class);
            pubList.add(vmsInfoList);
        }

        int ret = 0;
        JSONArray result = new JSONArray(); // 反馈结果
        String playlst = genPlaylst(devInfoList, pubList);
        for(int i = 0 ; i < devInfoList.size(); ++i) {
            DevInfo dev = devInfoList.get(i);
//            TblDeviceInfo dev = tblDeviceInfoMapper.findByDeviceId(devInfoList.getString(i));
            switch (dev.getProtocol())
            {
                case InfoBoardConfig.SWARCO_CMS_V1d5: ret = publishSwarcoCMSV1d5(dev, pubList.get(0)); break;
                case InfoBoardConfig.SWARCO_FMS_V1d5: ret = publishSwarcoFMSV1d5(dev, pubList.get(0)); break;
                case InfoBoardConfig.DONGHAI_F: ret = publishDonghaiF(dev, pubList.get(0)); break;
                case InfoBoardConfig.SANSI_PLIST_MULTI: ret = publishSansiPlistMulti(dev, playlst); break;
                default: retCode = 500; break;
            }
            JSONObject joRet = new JSONObject();
            joRet.put(dev.getId(), ret);
            result.add(joRet);
        }

        //通知
        notifyResult(result);

        return retCode;
    }

    /* 世博翰 CMS
    谨慎驾驶 注意安全（BD-F7-C9-F7-BC-DD-CA-BB-20-D7-A2-D2-E2-B0-B2-C8-AB）
    夜间行车 避免疲劳驾驶（D2-B9-BC-E4-D0-D0-B3-B5-20-B1-DC-C3-E2-C6-A3-C0-CD-BC-DD-CA-BB）
    整体报文：
    01 01 FF FF FF FF FF FF 前缀
    1b 37 31 1b 38 30 30 35 1b 39 30 1b 3a 30 1b 22 1b 34 1b 31 控制
    BD F7 C9 F7 BC DD CA BB 20 D7 A2 D2 E2 B0 B2 C8 AB 文字1
    1b 0d 换屏
    1b 37 31 1b 38 30 30 35 1b 39 30 1b 3a 30 1b 21 1b 34 1b 31 控制
    D2 B9 BC E4 D0 D0 B3 B5 20 B1 DC C3 E2 C6 A3 C0 CD BC DD CA BB 文字2
    00 后缀
     */
    private int publishSwarcoCMSV1d5(DevInfo dev, List<VmsPubInfo> vmsInfoList) {
        int retCode = 200;
        String protocol = InfoBoardConfig.SWARCO_CMS_V1d5;
        String newline = "|";

        //组织报文
        StringBuilder sb = new StringBuilder();
        sb.append(" 01 01 FF FF FF FF FF FF ");
        for (VmsPubInfo info : vmsInfoList) {
            //图片、图片类型（默认64点阵 全屏）
            if(info.getPicId() != null && !info.getPicId().equals("0")) {
                sb.append( " 1b 36 "+ picCvt(protocol, info.getPicId()) + " 33 ");
            } else {
                //出字方式、间隔（默认：立即显示 + 5s）
                sb.append(" 1b 37 31 ");
                sb.append(" 1b 38 30 30 35 ");
                //字体、字体大小
                sb.append(" 1b 39 " + fontCvt(protocol, info.getTypeface()));
                sb.append(" 1b 3a " + fontSizeCvt(protocol, info.getTextSize()));
                //字符颜色
                sb.append(" 1b " + fontColorCvt(protocol, info.getTextColor()));
                //水平垂直对齐（默认居中）
                sb.append(" 1b 34 1b 31 ");
                //文字
                //解析<br>换行，替换为 1B 0A
                int sPos = 0, ePos = 0;
                String line = "";
                String text = StringUtils.isEmpty(info.getContent()) ? "" : info.getContent();
                ePos = text.indexOf(newline, sPos);
                try {
                    while (ePos != -1) {
                        line = text.substring(sPos, ePos);
                        sb.append(StringUtils.bytesToHexStr(line.getBytes("gb2312")));
                        sb.append(" 1B 0A ");
                        sPos = ePos + newline.length();
                        ePos = text.indexOf(newline, sPos);
                    }
                    line = text.substring(sPos);
                    sb.append(StringUtils.bytesToHexStr(line.getBytes("gb2312")));
                }catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            //换屏
            sb.append("1B0D");
        }
        //去掉最后的 1B0D
        sb.delete(sb.length()-4, sb.length());
        sb.append(" 0000000000000000000000000000000000000000000000000000000000000000000000 ");

        //发送（modbus tcp）
        MbsAttribute mbsAttribute = new MbsAttribute(dev.getIp(), dev.getPort(), dev.getSlave(),
                3, Integer.valueOf("1500", 16), 0, DataType.TWO_BYTE_INT_SIGNED);
        try {
            byte[] bytes = StringUtils.hexStrToBytes(sb.toString());
            short[] shorts = StringUtils.bytesToShorts(bytes);
            iModbusService.writeMultiRegister(mbsAttribute, shorts);
        } catch (Exception e) {
            log.error("情报板发布失败：" + e.getMessage());
            retCode = 500;
        }

        return retCode;
    }

    /* 世博翰 FMS
     */
    private int publishSwarcoFMSV1d5(DevInfo dev, List<VmsPubInfo> vmsInfoList) {
        int retCode = 200;
        String protocol = InfoBoardConfig.SWARCO_FMS_V1d5;

        for(VmsPubInfo info : vmsInfoList) {
            String fmsValue = fmsCvt(protocol, info.getPicId());
            //发送（modbus tcp）
            MbsAttribute mbsAttribute = new MbsAttribute(dev.getIp(), dev.getPort(), dev.getSlave(),
                    3, Integer.valueOf("1801", 16), 0, DataType.TWO_BYTE_INT_SIGNED);
            try {
                iModbusService.writeRegister(mbsAttribute, Short.parseShort(fmsValue));
            } catch (Exception e) {
                retCode = 500;
            }
        }

        return retCode;
    }

    /* 东海 F板
     */
    private int publishDonghaiF(DevInfo dev, List<VmsPubInfo> vmsInfoList) {
        int retCode = 200;
        String protocol = InfoBoardConfig.DONGHAI_F;
        String newline = "|";
        Socket socket = iSocketService.clientSocket(dev.getIp(), dev.getPort());

        //组织报文
        StringBuilder sb = new StringBuilder();
        sb.append("F2")
            .append(dev.getSlave())
            .append("F5020105")
            .append(DonghaiCheckNum(dev.getSlave() + "F5020105"))
            .append("F0");
        retCode = iSocketService.writeAndResult(StringUtils.hexStrTobytes(sb.toString()), socket);
        if(retCode == 200) {
            for (VmsPubInfo info : vmsInfoList) {
                sb.setLength(0);
                sb.append("F2")
                    .append(dev.getSlave())
                    .append("F5")
                    .append(fontSizeCvt(protocol, info.getTypeface()));
                //解析<br>换行，替换为 0a（换行）（0d回车？）
                int sPos = 0, ePos = 0;
                float len = 0.0f, lenAfter = 0.0f;
                String line = "", gb2312 = "";
                String text = info.getContent();
                ePos = text.indexOf(newline, sPos);
                StringBuilder sb2 = new StringBuilder();
                try {
                    while (ePos != -1) {
                        sb2.setLength(0);
                        line = text.substring(sPos, ePos);
                        len = StringUtils.getStrLength(line) / 2.0f;
                        lenAfter = (int)(len + 0.5f);
                        if(len != lenAfter) {
                            //需要补空格
                            text += " ";
                        }
                        gb2312 = StringUtils.bytesToHexStr(text.getBytes("gb2312"));
                        //拆开加前缀0（比如文字为“谨慎驾驶”的hex是 uvwx，则需要转成 0u0v0w0x
                        for(int i = 0; i < gb2312.length(); ++i) {
                            sb2.append("0").append(gb2312.charAt(i));
                        }
                        //文字两端补空格（为了对齐）
//                        if(lenAfter <= 5) {
//                            //一行最多5个字
//                        }
                        sb.append(sb2.toString()).append("0A");
                        sPos = ePos + newline.length();
                    }
                    line = text.substring(sPos, ePos);
                    sb2.setLength(0);
                    gb2312 = StringUtils.bytesToHexStr(line.getBytes("gb2312"));
                    for(int i = 0; i < gb2312.length(); ++i) {
                        sb2.append("0").append(gb2312.charAt(i));
                    }
                    sb.append(sb2.toString());
                }catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                sb.append(DonghaiCheckNum(dev.getSlave() + "F5"
                        + fontSizeCvt(protocol, info.getTypeface())
                        + sb2.toString())
                ).append("F0");

                iSocketService.writeAndResult(StringUtils.hexStrTobytes(sb.toString()), socket);
            }
            sb.setLength(0);
            sb.append("F2")
                .append(dev.getSlave())
                .append("F5020405")
                .append(DonghaiCheckNum(dev.getSlave() + "F5020405"))
                .append("F0");
            iSocketService.writeAndResult(StringUtils.hexStrTobytes(sb.toString()), socket);
        }

        return retCode;
    }

    /* 三思 播放列表 多分区
     */
    private int publishSansiPlistMulti(DevInfo dev, String playlst) {
        int retCode = 1;

        String fn = "play.lst";
        byte[] btFile = playlst.getBytes(StandardCharsets.UTF_8);
        int sendTimes = btFile.length / 2048 + 1;
        int sendLen = 0;
        int pos = fn.length();
        for (int i = 0; i < sendTimes; ++i)
        {
            sendLen = (sendTimes - i - 1 > 0) ? 2048 : btFile.length % 2048;
            byte[] data = new byte[fn.length() + 1 + 4 + sendLen];
            System.arraycopy(ByteUtils.ASCIIToBytes(fn), 0, data, 0, fn.length());
            data[pos] = 0x2b;
            //小端返回，转大端
//            byte[] btOffset = BitConverter.GetBytes(System.Net.IPAddress.HostToNetworkOrder(2048 * i));
            byte[] btOffset = ByteBuffer.allocate(4).putInt(2048*i).array();
            System.arraycopy(btOffset, 0, data, pos + 1, 4);
            System.arraycopy(btFile, i * 2048, data, pos + 5, sendLen);

            try {
                if(-1 == ULOneFile10(dev.getSlave(), dev.getIp(), dev.getPort(), data))
                {
                    return -1;
                }

                if (i + 1 < sendTimes)
                {
                    Thread.sleep(200);
                }
            } catch (Exception ex) {
                log.error("情报板发布异常-上传playlst文件：" + ex.getMessage());
            }
        }

        return retCode;
    }

    // 生成playlst
    private String genPlaylst(List<DevInfo> devInfoList, List<List<VmsPubInfo>> pubList) {
        String playlst = "";
        if(devInfoList.size() > 0) {
            DevInfo dev = devInfoList.get(0);
            String newline = System.getProperty("line.separator");
            if(dev.getProtocol() == InfoBoardConfig.SANSI_PLIST_MULTI) {
                StringBuilder playlst1 = new StringBuilder();
                playlst1.append("[playlist]" + newline);
                playlst1.append("nwindows=" + pubList.size());
                StringBuilder playlst2 = new StringBuilder();

                for (int i = 0; i < pubList.size(); ++i)
                {
                    String pref = "";
                    if(i > 0) pref = "window" + i + "_";
                    List<VmsPubInfo> editList = pubList.get(i);
                    playlst1.append("windows" + i + "_x=" + i * dev.getWidth() + newline);
                    playlst1.append("windows" + i + "_y=0" + newline);
                    playlst1.append("windows" + i + "_w="  + dev.getWidth() + newline);
                    playlst1.append("windows" + i + "_y="  + dev.getHeight() + newline);
                    playlst1.append(pref + "item_no=" + editList.size() + newline);
                    for(int j = 0; j < editList.size(); ++j) {
                        playlst2.setLength(0); //清空
                        int itemNo = 0;
                        VmsPubInfo edit = editList.get(i);
                        if (!StringUtils.isEmpty(edit.getPicId()))
                        {
                            playlst2.append("item" + itemNo + "=500,1,0,");
                            playlst2.append("\\B" + edit.getPicId() + newline);
                        }
                        else
                        {
                            //text <br>替换为\n
                            String text = edit.getContent();
                            String xy = "000000";
                            //int fontSize = Tools.ToolFunc.GetFontSizeAndXY(text, ref xy);
                            text = edit.getContent().replace("<br>", "\\n");
                            playlst2.append("item" + itemNo + "=500,1,0,");
                            playlst2.append("\\fs" + edit.getTypeface() + edit.getTypeface());
                            playlst2.append("\\C" + xy + "\\c" + edit.getTextColor() + text);
                        }
                        itemNo++;
                    }

                    playlst1.append(newline);
                }

                if (playlst2.toString() == "") return "";

                playlst1.append(playlst2);
                playlst = playlst1.toString();
            }
        }
        return playlst;
    }

    // playlst上传文件
    private int ULOneFile10(Integer slaveID, String ip, Integer port, byte[] data)
    {
        byte[] sendPkg = PacketPlaylst(slaveID, "10", data);
//        byte[] recvPkg = null;
        int recv = 1;
        try
        {
            Socket skt = iSocketService.clientSocket(ip, port);
            recv = iSocketService.writeAndResult(sendPkg, skt);
        }
        catch (Exception ex)
        {
            log.error("情报板发布：上传文件异常！" + ex.getMessage());
        }
        return recv;
    }

    // playlst 组包
    private byte[] PacketPlaylst(Integer slaveID, String pkgType, byte[] data)
    {
        int len = data == null ? 0 : data.length;
        int pos = 0;
        byte[] ret = new byte[len + 8];
        ret[pos++] = 0x02;
        String sid = String.format("%2d", slaveID);
        ret[pos++] = (byte)(sid.charAt(0));
        ret[pos++] = (byte)(sid.charAt(1));
        ret[pos++] = (byte)(pkgType.charAt(0));
        ret[pos++] = (byte)(pkgType.charAt(1));

        if (len > 0)
        {
            System.arraycopy(data, 0, ret, 5, len);
        }
        pos += len;

        //校验
//        byte[] crc2 = ByteUtils.GetCrc(ret.Skip(1).Take(pos - 1).ToArray());
        byte[] crcCheck = Arrays.copyOfRange(ret, 1, ret.length - 1);
        byte[] crc2 = ByteUtils.GetCrc(crcCheck, crcCheck.length);
        ret[pos++] = crc2[1];
        ret[pos++] = crc2[0];

        ret[pos] = 0x03;

        //转义
        byte[] sendPkg = ByteUtils.TransPkg(ret);

        return sendPkg;
    }

    // 字体转换，[in]=字体名，[return]=字体编码
    private String fontCvt(String protocol, String font) {
        String fontCode = "30";
        if(protocol == InfoBoardConfig.SWARCO_CMS_V1d5) {
            switch (font) { //+0x30
                case "黑体": fontCode = "30"; break;
                case "楷体": fontCode = "31"; break;
                case "宋体": fontCode = "32"; break;
                case "仿宋体": fontCode = "33"; break;
            }
        }
        return fontCode;
    }

    // 字体大小转换，[in]=字体大小，[return]=字体大小编码
    private String fontSizeCvt(String protocol, String fontSize) {
        String fontSizeCode = "33";
        if(protocol == InfoBoardConfig.SWARCO_CMS_V1d5) {
            switch (fontSize) { //+0x30
                case "16": fontSizeCode = "31"; break;
                case "24": fontSizeCode = "32"; break;
                case "32": fontSizeCode = "33"; break;
                case "48": fontSizeCode = "34"; break;
            }
        }
        else if(protocol == InfoBoardConfig.DONGHAI_F) {
            switch (fontSize) {
                case "16":
                case "24":
                case "32": fontSizeCode = "7C"; break;
                case "36":
                case "48": fontSizeCode = "78"; break;
            }
        }
        return fontSizeCode;
    }

    // 字体颜色转换，[in]=字体颜色，[return]=字体颜色编码
    private String fontColorCvt(String protocol, String fontColor) {
        String fontColorCode = "21";
        if(protocol == InfoBoardConfig.SWARCO_CMS_V1d5) {
            switch (fontColor) {
                case "红": fontColorCode = "20"; break;
                case "绿": fontColorCode = "21"; break;
                case "黄": fontColorCode = "22"; break;
            }
        }
        return fontColorCode;
    }

    // 图片转换，[in]=图片名，[return]=图片编码
    private String picCvt(String protocol, String picId) {
        String picCode = ""; //无图片
        if(protocol == InfoBoardConfig.SWARCO_CMS_V1d5) {
            if(StringUtils.isEmpty(picId)) return "30";
            switch (picId) { //+0x30
                case "0": picCode = "30"; break; //无图片
                case "D7": picCode = "67"; break; //20
                case "D8": picCode = "68"; break; //25
                case "D9": picCode = "69"; break; //30
                case "C1": picCode = "51"; break; //35
                case "C2": picCode = "52"; break; //40
                case "DA": picCode = "6A"; break; //45
                case "DB": picCode = "6B"; break; //50
                case "DC": picCode = "6C"; break; //55
                case "C3": picCode = "53"; break; //60
                case "DD": picCode = "6D"; break; //65
                case "DE": picCode = "6E"; break; //70
                case "C4": picCode = "54"; break; //80
                case "DF": picCode = "6F"; break; //90
                case "C5": picCode = "55"; break; //100
                case "E0": picCode = "70"; break; //110
                case "C6": picCode = "56"; break; //120
            }
        }
        return picCode;
    }

    // 限速转换，[in]=限速值，[return]=限速编码
    private String fmsCvt(String protocol, String fmsValue) {
        String fmsCode = "16"; //限速80
        if(protocol == InfoBoardConfig.SWARCO_FMS_V1d5) {
            switch (fmsValue) {
                case "40": fmsCode = "12"; break;
                case "50": fmsCode = "13"; break;
                case "60": fmsCode = "14"; break;
                case "70": fmsCode = "15"; break;
                case "80": fmsCode = "16"; break;
                case "90": fmsCode = "17"; break;
                case "100": fmsCode = "18"; break;
                case "110": fmsCode = "19"; break;
                case "120": fmsCode = "1a"; break;
            }
        }
        return fmsCode;
    }

    //东海校验码
    private byte DonghaiCheckNum(String value) {
        byte[] bytes = StringUtils.hexStrTobytes(value);
        byte VerifyByte = bytes[0];
        for (int i = 1; i < bytes.length; i++)
        {
            VerifyByte = (byte)(VerifyByte ^ bytes[i]);
        }
        VerifyByte = (byte)(VerifyByte & 0x7F);
        return VerifyByte;
    }

    //通知
    private void notifyResult(JSONArray result) {
        String post;
        R ret;
        int time = 1;
        while (true) {
            try {
                post = HttpUtil.post("localhost:9308" + "/infoBoard", JSON.toJSONString(result));
                if (!StringUtils.isEmpty(post)) {
                    if (post.startsWith("{")) {
                        ret = JSON.parseObject(post, R.class);
                        if (R.SUCCESS == ret.getCode()) {
                            break;
                        } else {
                            log.error(JSON.toJSONString(result) + "转发发布通知失败：" + ret.getMsg());
                        }
                    } else {
                        log.error(JSON.toJSONString(result) + "转发发布通知状态未知");
                    }
                    Thread.sleep(time * 1000);
                }
            } catch (InterruptedException e) {
                log.error(JSON.toJSONString(result) + "转发发布通知异常：" + e.getMessage());
            }
            time += 2;
        }
    }
}
