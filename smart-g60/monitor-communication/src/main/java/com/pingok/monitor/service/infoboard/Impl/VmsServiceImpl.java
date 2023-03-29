package com.pingok.monitor.service.infoboard.Impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.monitor.config.HostConfig;
import com.pingok.monitor.domain.common.MbsAttribute;
import com.pingok.monitor.domain.device.TblDeviceInfo;
import com.pingok.monitor.domain.infoboard.DevInfo;
import com.pingok.monitor.domain.infoboard.PlaylstWndInfo;
import com.pingok.monitor.domain.infoboard.SansiParkingPubInfo;
import com.pingok.monitor.domain.infoboard.VmsPubInfo;
import com.pingok.monitor.service.common.IModbusService;
import com.pingok.monitor.service.common.ISocketService;
import com.pingok.monitor.service.infoboard.IVmsService;
import com.pingok.monitor.utils.ByteUtils;
import com.pingok.monitor.utils.config.InfoBoardConfig;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.ip.IpUtils;
import com.serotonin.modbus4j.code.DataType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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


    static String[] A7pngFileName = {"0", "120", "110", "100", "90", "80", "70", "65", "60", "55", "50", "45", "40", "35", "30", "25", "20", "叉", "上箭头", "右上箭头", "左上箭头"};

    @Override
    public JSONObject publish(String pubInfo) {

        JSONObject jo = JSONObject.parseObject(pubInfo);
//        JSONArray devInfoList = jo.getJSONArray("devInfo");
        List<DevInfo> devInfoList = JSON.parseArray(JSONObject.toJSONString(jo.get("devInfo")), DevInfo.class);
        JSONArray dataList = jo.getJSONArray("data");
        int model = 0;
        if(jo.containsKey("model")) {
            model = jo.getInteger("model");
        }
        List<List<VmsPubInfo>> pubList = new ArrayList<>();
        for (int i = 0; i < dataList.size(); ++i) {
            List<VmsPubInfo> vmsInfoList = JSON.parseArray(JSONObject.toJSONString(dataList.get(i)), VmsPubInfo.class);
            pubList.add(vmsInfoList);
        }

        int ret = 0;
        // 反馈结果
        JSONObject result = new JSONObject();
        result.put("pubContent", JSON.toJSONString(dataList));
        JSONArray jaResult = new JSONArray();
        String playlst = getPublishModel(model, devInfoList, pubList);
        for (int i = 0; i < devInfoList.size(); ++i) {
            DevInfo dev = devInfoList.get(i);
            switch (dev.getProtocol()) {
                case InfoBoardConfig.SWARCO:
                    ret = publishSwarcoCMSV1d5(dev, pubList.get(0));
                    break;
                case InfoBoardConfig.SANSI_XS:
                    ret = publishSansiXS(dev, pubList.get(0));
                    break;
                case InfoBoardConfig.DONGHAI_F:
                    ret = publishDonghaiF(dev, pubList.get(0));
                    break;
                case InfoBoardConfig.SANSI_PLIST:
                case InfoBoardConfig.SANSI_PLIST_MULTI:
                    ret = publishSansiPlistMulti(dev, playlst);
//                    ret = 200;
                    break;
                default:
                    ret = 500;
                    break;
            }
            JSONObject joRet = new JSONObject();
            joRet.put("devId", dev.getDevId());
            joRet.put("recordId", dev.getRecordId());
            joRet.put("ret", ret);
            jaResult.add(joRet);
        }
        result.put("devList", jaResult);

        return result;
    }

    @Override
    public boolean publish(SansiParkingPubInfo parkInfo) {
        boolean ret = true;
        byte[] sendPkg = null;
//        String infoType = parkInfo.getInfoType() == 1 ? " 01 " : " 0A ";
        switch (parkInfo.getDevPos()){
            case 1: {
                byte[] data = new byte[6];
                data[0] = (byte)parkInfo.getColor1_ke().intValue();
                int num = Integer.parseInt(parkInfo.getText1_ke());
                data[1] = (byte)(num / 256);
                data[2] = (byte)(num % 256);
                data[3] = (byte)parkInfo.getColor1_huo().intValue();
                num = Integer.parseInt(parkInfo.getText1_huo());
                data[4] = (byte)(num / 256);
                data[5] = (byte)(num % 256);
                sendPkg = PacketParking(1, (byte) 0x01, data);
                break;
            }
            case 2: {
                byte[] data = new byte[9];
                data[0] = (byte)parkInfo.getColor2A_huoA().intValue();
                int num = Integer.parseInt(parkInfo.getText2A_huoA());
                data[1] = (byte)(num / 256);
                data[2] = (byte)(num % 256);
                data[3] = (byte)parkInfo.getColor2A_huoB().intValue();
                num = Integer.parseInt(parkInfo.getText2A_huoB());
                data[4] = (byte)(num / 256);
                data[5] = (byte)(num % 256);
                data[6] = (byte)parkInfo.getColor2A_ke().intValue();
                num = Integer.parseInt(parkInfo.getText2A_ke());
                data[7] = (byte)(num / 256);
                data[8] = (byte)(num % 256);
                sendPkg = PacketParking(1, (byte) 0x01, data);
                break;
            }
            case 3: {
                byte[] data = new byte[6];
                data[0] = (byte)parkInfo.getColor2BC_huoB().intValue();
                int num = Integer.parseInt(parkInfo.getText2BC_huoB());
                data[1] = (byte)(num / 256);
                data[2] = (byte)(num % 256);
                data[3] = (byte)parkInfo.getColor2BC_ke().intValue();
                num = Integer.parseInt(parkInfo.getText2BC_ke());
                data[4] = (byte)(num / 256);
                data[5] = (byte)(num % 256);
                sendPkg = PacketParking(1, (byte) 0x01, data);
                break;
            }
            default: ret = false; break;
        }

        int recv = 1;
        Socket skt = null;
        try {
            skt = iSocketService.clientSocket(parkInfo.getDevIp(), 3434);
            recv = iSocketService.writeAndResult(sendPkg, skt);
            skt.close();
        } catch (Exception ex) {
            log.error("情报板发布：上传文件异常！" + ex.getMessage());
            recv = -1;
        }
        ret = recv != -1;
        return ret;
    }

    @Override
    public void collect(String devInfo) {
        List<TblDeviceInfo> devList = JSON.parseArray(devInfo, TblDeviceInfo.class);
        boolean ping = true;
        JSONArray ret = new JSONArray();
        try {
            for (int i = 0; i < devList.size(); ++i) {
                TblDeviceInfo dev = devList.get(i);
                ping = IpUtils.ping(dev.getDeviceIp());
                JSONObject jo = new JSONObject();
                jo.put("devId", dev.getId());
                jo.put("devCode", dev.getDeviceId());
                jo.put("ping", ping);
                ret.add(jo);
            }
            notifyPing(ret);
        } catch (Exception ex) {
            log.error("情报板采集异常：", ex.getMessage());
        }
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
        String protocol = InfoBoardConfig.SWARCO;
        String newline = "<br>";

        //组织报文
        StringBuilder sb = new StringBuilder();
        sb.append(" 01 01 FF FF FF FF FF FF ");
        for (VmsPubInfo info : vmsInfoList) {
            //图片、图片类型（默认64点阵 全屏）
            if (!StringUtils.isEmpty(info.getPicId())) {
                sb.append(" 1b 36 " + picCvt(protocol, info.getPicId()) + " 32 ");
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
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            //换屏
            sb.append("1B0D");
        }
        //去掉最后的 1B0D
        sb.delete(sb.length() - 4, sb.length());
        sb.append(" 0000000000000000000000000000000000000000000000000000000000000000000000 ");

        //发送（modbus tcp）
        MbsAttribute mbsAttribute = new MbsAttribute(dev.getIp(), dev.getPort(), dev.getSlave(),
                3, Integer.valueOf("1500", 16), 0, DataType.TWO_BYTE_INT_SIGNED);
        try {
            byte[] bytes = StringUtils.hexStrToBytes(sb.toString());
            short[] shorts = StringUtils.bytesToShorts(bytes);
            if (dev.getDevId().equals("F1")) {
                retCode = iModbusService.writeMultiRegister("COM22", 1, 0x1500, shorts);
            } else if (dev.getDevId().equals("F2")) {
                retCode = iModbusService.writeMultiRegister("COM23", 1, 0x1500, shorts);
            } else {
                retCode = iModbusService.writeMultiRegister(mbsAttribute, shorts);
            }
        } catch (Exception e) {
            log.error("情报板发布失败：" + e.getMessage());
            retCode = 500;
        }

        return retCode;
    }


    /* 三思限速板（串口）
     * */
//    private int publishSansiXS_S1(DevInfo dev, List<VmsPubInfo> vmsInfoList) {
//        int retCode = 200;
//        String protocol = InfoBoardConfig.SANSI_XS;
//        for(VmsPubInfo info : vmsInfoList) {
//            String fmsValue = fmsCvt(protocol, info.getPicId());
//            //发送（modbus rtu）
//            try {
//                retCode = iModbusService.writeRegister("COM12", dev.getSlave(),
//                        0x1801, Short.parseShort(fmsValue, 16));
//            } catch (Exception e) {
//                retCode = -1;
//            }
//        }
//        return retCode;
//    }

    /* 三思限速板（tcp）
     */
    private int publishSansiXS(DevInfo dev, List<VmsPubInfo> vmsInfoList) {
        int retCode = 200;
        String protocol = InfoBoardConfig.SANSI_XS;

        for (VmsPubInfo info : vmsInfoList) {
            String fmsValue = fmsCvt(protocol, info.getPicId());
            //发送（modbus tcp）
            MbsAttribute mbsAttribute = new MbsAttribute(dev.getIp(), dev.getPort(), dev.getSlave(),
                    3, Integer.valueOf("1801", 16), 0, DataType.TWO_BYTE_INT_SIGNED);
            try {
                if (dev.getDevId().equals("S1")) {
                    retCode = iModbusService.writeRegister("COM12", dev.getSlave(),
                            0x1801, Short.parseShort(fmsValue, 16));
                } else {
                    retCode = iModbusService.writeRegister(mbsAttribute, Short.parseShort(fmsValue, 16));
                }
            } catch (Exception e) {
                retCode = -1;
            }
        }

        return retCode;
    }

    /* 东海 F板
       正常用7C：24*24 最多12个字； 78：32*32
     */
    private int publishDonghaiF(DevInfo dev, List<VmsPubInfo> vmsInfoList) {
        int retCode = 200;
        String protocol = InfoBoardConfig.DONGHAI_F;
        String newline = "<br>";
        Socket socket = iSocketService.clientSocket(dev.getIp(), dev.getPort());

        //组织报文
        StringBuilder sb = new StringBuilder();
        String sid = String.format("%02d", dev.getSlave());
        //DonghaiCheckNum返回 114，还需要转成16进制
        sb.append("F2")
                .append(sid)
                .append("F5020105")
                .append(DonghaiCheckNum(sid + "F5020105"))
                .append("F0");
        retCode = iSocketService.writeAndResult(StringUtils.hexStrTobytes(sb.toString()), socket);
        if (retCode == 200) {
            for (VmsPubInfo info : vmsInfoList) {
                sb.setLength(0);
                sb.append("F2")
                        .append(sid)
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
                        lenAfter = (int) (len + 0.5f);
                        if (len != lenAfter) {
                            //需要补空格
                            text += " ";
                        }
                        gb2312 = StringUtils.bytesToHexStr(text.getBytes("gb2312"));
                        //拆开加前缀0（比如文字为“谨慎驾驶”的hex是 uvwx，则需要转成 0u0v0w0x
                        for (int i = 0; i < gb2312.length(); ++i) {
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
                    for (int i = 0; i < gb2312.length(); ++i) {
                        sb2.append("0").append(gb2312.charAt(i));
                    }
                    sb.append(sb2.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                sb.append(DonghaiCheckNum(sid + "F5"
                        + fontSizeCvt(protocol, info.getTypeface())
                        + sb2.toString())
                ).append("F0");

                iSocketService.writeAndResult(StringUtils.hexStrTobytes(sb.toString()), socket);
            }
            sb.setLength(0);
            sb.append("F2")
                    .append(sid)
                    .append("F5020405")
                    .append(DonghaiCheckNum(sid + "F5020405"))
                    .append("F0");
            iSocketService.writeAndResult(StringUtils.hexStrTobytes(sb.toString()), socket);
        }

        return retCode;
    }

    /* 三思 播放列表 多分区 & v4.20（V3全彩屏）
     */
    private int publishSansiPlistMulti(DevInfo dev, String playlst) {
        int retCode = 1;

        String fn = "play.lst";
        byte[] btFile = null;
        try {
            btFile = playlst.getBytes("GBK");
        } catch (Exception ex) {
            log.error("情报板获取GBK字符编码异常：" + ex.getMessage());
            return -1;
        }

        System.out.println("长度：" + btFile.length + "，btFile[]：" + ByteUtils.bytes2hex(btFile));
        int sendTimes = btFile.length / 2048 + 1;
        int sendLen = 0;
        int pos = fn.length();
        for (int i = 0; i < sendTimes; ++i) {
            sendLen = (sendTimes - i - 1 > 0) ? 2048 : btFile.length % 2048;
            byte[] data = new byte[fn.length() + 1 + 4 + sendLen];
            System.arraycopy(ByteUtils.ASCIIToBytes(fn), 0, data, 0, fn.length());
            data[pos] = 0x2b;
            //小端返回，转大端
            byte[] btOffset = ByteUtils.intToByte4B(2048 * i);
//            byte[] btOffset = BitConverter.GetBytes(System.Net.IPAddress.HostToNetworkOrder(2048 * i));
//            byte[] btOffset = ByteBuffer.allocate(4).putInt(2048 * i).array();
            System.arraycopy(btOffset, 0, data, pos + 1, 4);
            System.arraycopy(btFile, i * 2048, data, pos + 5, sendLen);

            try {
                System.out.println("长度：" + data.length + "，data[]：" + ByteUtils.bytes2hex(data));
                if (-1 == ULOneFile10(dev.getSlave(), dev.getIp(), dev.getPort(), data)) {
                    return -1;
                }

                if (i + 1 < sendTimes) {
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
        if (devInfoList.size() > 0) {
            DevInfo dev = devInfoList.get(0);
            String newline = System.getProperty("line.separator");
            if (dev.getProtocol().equals(InfoBoardConfig.SANSI_PLIST_MULTI)) {
                StringBuilder playlstAll = new StringBuilder();
                playlstAll.append("[playlist]" + newline);
                int winNum = 0;   //窗口数
                int picSize = 48; //图片尺寸

                StringBuilder playlstItem = new StringBuilder();
                StringBuilder playlstAllItem = new StringBuilder();

                int regionNum = pubList.size(); //分几个区域
                //计算区域的 宽，高 (regionNum - 1) * 8 是间隔
                int regionWidth = dev.getWidth() / regionNum;
                int regionHeight = dev.getHeight();

                for (int i = 0; i < regionNum; ++i) {
                    String pref = ""; //前缀
                    int xPos = 0, yPos = 0;      //图片/文字的坐标
                    int winX = 0, winY = 0;     //窗口坐标
                    //计算窗口的x，y
                    winX = regionWidth * i;
                    winY = 0;
                    List<VmsPubInfo> editList = pubList.get(i);
                    for (int j = 0; j < editList.size(); ++j) {
                        playlstItem.setLength(0); //清空
                        VmsPubInfo edit = editList.get(j);
                        //一个区域里如果既有图片，又有文字，则 windows 要增加
                        boolean bPic = !StringUtils.isEmpty(edit.getPicId()) && !edit.getPicId().equals("0");
                        if (bPic) {
                            playlstItem.append("windows" + winNum + "_x=" + winX + newline);
                            playlstItem.append("windows" + winNum + "_y=" + winY + newline);
                            playlstItem.append("windows" + winNum + "_w=" + regionWidth + newline);
                            playlstItem.append("windows" + winNum + "_h=" + picSize + newline);
                            playlstItem.append(pref + "item_no=" + editList.size() + newline);
                            playlstItem.append(pref + "item" + j + "=500,1,0,");
                            xPos = (regionWidth - picSize) / 2;
                            String xyPic = String.format("%03d%03d", xPos, yPos);
                            playlstItem.append("\\C" + xyPic);
                            playlstItem.append("\\P" + edit.getPicId() + newline + newline);
                            winNum++;
                            regionHeight -= picSize;
                            winY += picSize;
                        }
                        if (!StringUtils.isEmpty(edit.getContent())) {
                            if (winNum > 0) pref = "windows" + winNum + "_";
                            playlstItem.append("windows" + winNum + "_x=" + winX + newline);
                            playlstItem.append("windows" + winNum + "_y=" + winY + newline);
                            playlstItem.append("windows" + winNum + "_w=" + regionWidth + newline);
                            playlstItem.append("windows" + winNum + "_h=" + regionHeight + newline);
                            playlstItem.append(pref + "item_no=" + editList.size() + newline);
                            playlstItem.append(pref + "item" + j + "=500,1,0,");
                            //text <br>替换为\n
                            //计算文字的xy坐标，x按最长文字算，y按行数
                            String[] splitText = edit.getContent().split("<br>");
                            xPos = yPos = 0;
                            int fontSize = Integer.parseInt(edit.getTextSize());
                            if (splitText.length > 0) {
                                String splitTemp = splitText[0];
                                for (int idx = 1; idx < splitText.length; ++idx) {
                                    if (splitTemp.length() > splitText[idx].length()) splitTemp = splitText[idx];
                                }
                                int textXLen = splitTemp.length() * fontSize;
                                int textYLen = splitText.length * fontSize;
                                if (textXLen > regionWidth) {
                                    xPos = 0;
                                } else {
                                    xPos = (regionWidth - textXLen) / 2;
                                }
                                if (textYLen > regionHeight) {
                                    yPos = 0;
                                } else {
                                    yPos = (regionHeight - textYLen) / 2;
                                }
                            }
                            String xy = String.format("%03d%03d", xPos, yPos);
                            String text = edit.getContent().replace("<br>", "\\n");
                            playlstItem.append("\\C" + xy);
                            playlstItem.append("\\f" + fontCvt(InfoBoardConfig.SANSI_PLIST_MULTI, edit.getTypeface()) + fontSize + fontSize);
                            playlstItem.append("\\c" + fontColorCvt(InfoBoardConfig.SANSI_PLIST_MULTI, edit.getTextColor()) + text);
                            winNum++;
                        }
                        playlstAllItem.append(playlstItem + newline);
                    }

                    playlstAllItem.append(newline + newline);
                }

                playlstAllItem.delete(playlstAllItem.length() - newline.length() * 2, playlstAllItem.length());
                playlstAll.append("nwindows=" + winNum + newline);
                playlstAll.append(playlstAllItem);

                playlst = playlstAll.toString();
            }
        }
        return playlst;
    }

    private String getPublishModel(int model, List<DevInfo> devInfoList, List<List<VmsPubInfo>> pubList) {
        String playlst = "";
        if (devInfoList.size() > 0) {
            DevInfo dev = devInfoList.get(0);
            int W = dev.getWidth();
            int H = dev.getHeight();
            int lineSize = 16;
            int picSize = 48;
            int rgW = (W - lineSize * 3) / 4;
            String nl = System.getProperty("line.separator"); //换行
            String pref = "";
            StringBuilder wndItem = new StringBuilder();
            if (dev.getProtocol().equals(InfoBoardConfig.SANSI_PLIST_MULTI)) {
                switch (model) { //+0x30
                    case 1: {//单行文字模式
                        PlaylstWndInfo wndText = new PlaylstWndInfo();
                        wndText.setWinX(0);
                        wndText.setWinY(0);
                        wndText.setWinW(W);
                        wndText.setWinH(H);
                        List<VmsPubInfo> editList = pubList.get(0);

                        wndItem.append("[playlist]" + nl);
                        wndItem.append("nwindows=" + 1 + nl);
                        wndItem.append("windows0" + "_x=" + wndText.getWinX() + nl);
                        wndItem.append("windows0" + "_y=" + wndText.getWinY() + nl);
                        wndItem.append("windows0" + "_w=" + wndText.getWinW() + nl);
                        wndItem.append("windows0" + "_h=" + wndText.getWinH() + nl);
                        wndItem.append(pref + "item_no=" + editList.size() + nl);
                        for (int i = 0; i < editList.size(); i++) {
                            int fontSize = Integer.parseInt(editList.get(i).getTextSize());
                            String text = editList.get(i).getContent().replace("<br>", "\\n");
                            wndItem.append(pref + "item" + i + "=500,1,0,");

                            //text <br>替换为\n
                            String[] splitText = editList.get(i).getContent().split("<br>");
                            //计算文字的xy坐标，x按最长文字算，y按行数
                            int xPos = 0, yPos = 0;
                            if (splitText.length > 0) {
                                String splitTemp = splitText[0];
                                for (int idx = 1; idx < splitText.length; ++idx) {
                                    if (splitTemp.length() > splitText[idx].length()) splitTemp = splitText[idx];
                                }
                                int textXLen = splitTemp.length() * fontSize;
                                int textYLen = splitText.length * fontSize;
                                if (textXLen > W) {
                                    xPos = 0;
                                } else {
                                    xPos = (W - textXLen) / 2;
                                }
                                if (textYLen > H) {
                                    yPos = 0;
                                } else {
                                    yPos = (H - textYLen) / 2;
                                }
                            }
                            String xy = String.format("%03d%03d", xPos, yPos);
                            wndItem.append("\\C" + xy);
                            wndItem.append("\\f" + fontCvt(InfoBoardConfig.SANSI_PLIST_MULTI, editList.get(i).getTypeface()) + fontSize + fontSize);
                            wndItem.append("\\c" + fontColorCvt(InfoBoardConfig.SANSI_PLIST_MULTI, editList.get(i).getTextColor()) + text);
                            wndItem.append(nl);
                        }
                        playlst = wndItem.toString();
                        break;
                    }
                    case 2: {//单行左图右文模式
                        List<VmsPubInfo> List = pubList.get(0);
                        wndItem.append("[playlist]" + nl);
                        wndItem.append("nwindows=" + 1 + nl);
                        wndItem.append("windows0" + "_x=" + 0 + nl);
                        wndItem.append("windows0" + "_y=" + 0 + nl);
                        wndItem.append("windows0" + "_w=" + W + nl);
                        wndItem.append("windows0" + "_h=" + H + nl);
                        wndItem.append(pref + "item_no=" + List.size() + nl);

                        for (int j = 0; j < List.size(); j++) {
                            wndItem.append(pref + "item" + j + "=500,1,0,");
                            String picXy = String.format("%03d%03d", 20, (H - 96) / 2);
                            wndItem.append("\\C" + picXy);
                            wndItem.append("\\P" + List.get(j).getPicId());
                            //text <br>替换为\n
                            String[] splitText = List.get(j).getContent().split("<br>");
                            //计算文字的xy坐标，x按最长文字算，y按行数
                            int xPos = 0, yPos = 0;
                            int fontSize = Integer.parseInt(List.get(j).getTextSize());
                            if (splitText.length > 0) {
                                String splitTemp = splitText[0];
                                for (int idx = 1; idx < splitText.length; ++idx) {
                                    if (splitTemp.length() > splitText[idx].length()) splitTemp = splitText[idx];
                                }
                                int textXLen = splitTemp.length() * fontSize;
                                int textYLen = splitText.length * fontSize;
                                if (textXLen > W) {
                                    xPos = 0;
                                } else {
                                    xPos = (W - textXLen) / 2;
                                }
                                if (textYLen > H) {
                                    yPos = 0;
                                } else {
                                    yPos = (H - textYLen) / 2;
                                }
                            }
                            String xy = String.format("%03d%03d", xPos, yPos);
                            String text = List.get(j).getContent().replace("<br>", "\\n");
                            wndItem.append("\\C" + xy);
                            wndItem.append("\\f" + fontCvt(InfoBoardConfig.SANSI_PLIST_MULTI, List.get(j).getTypeface()) + fontSize + fontSize);
                            wndItem.append("\\c" + fontColorCvt(InfoBoardConfig.SANSI_PLIST_MULTI, List.get(j).getTextColor()) + text);
                            wndItem.append(nl);
                        }
                        playlst = wndItem.toString();
                        break;
                    }
                    case 3: {//双行文字分车道模式
                        wndItem.append("[playlist]" + nl);
                        int num = pubList.size() + 3;
                        wndItem.append("nwindows=" + num + nl);

                        for (int i = 0; i < pubList.size(); i++) {
                            int winX1 = rgW * i + lineSize * i, winX2 = rgW * (i + 1) + lineSize * i, winW = rgW;
                            List<VmsPubInfo> textList = pubList.get(i);

                            if (i > 0) pref = "windows" + i * 2 + "_";

                            wndItem.append("windows" + i * 2 + "_x=" + winX1 + nl);
                            wndItem.append("windows" + i * 2 + "_y=" + 0 + nl);
                            wndItem.append("windows" + i * 2 + "_w=" + winW + nl);
                            wndItem.append("windows" + i * 2 + "_h=" + H + nl);
                            wndItem.append(pref + "item_no=" + textList.size() + nl);
                            for (int j = 0; j < textList.size(); j++) {
                                wndItem.append(pref + "item" + j + "=500,1,0,");
                                //text <br>替换为\n
                                String[] splitText = textList.get(j).getContent().split("<br>");
                                //计算文字的xy坐标，x按最长文字算，y按行数
                                int xPos = 0, yPos = 0;
                                int fontSize = Integer.parseInt(textList.get(j).getTextSize());
                                if (splitText.length > 0) {
                                    String splitTemp = splitText[0];
                                    for (int idx = 1; idx < splitText.length; ++idx) {
                                        if (splitTemp.length() > splitText[idx].length()) splitTemp = splitText[idx];
                                    }
                                    int textXLen = splitTemp.length() * fontSize;
                                    int textYLen = splitText.length * fontSize;
                                    if (textXLen > winW) {
                                        xPos = 0;
                                    } else {
                                        xPos = (winW - textXLen) / 2;
                                    }
                                    if (textYLen > H) {
                                        yPos = 0;
                                    } else {
                                        yPos = (H - textYLen) / 2;
                                    }
                                }
                                String xy = String.format("%03d%03d", xPos, yPos);
                                String text = textList.get(j).getContent().replace("<br>", "\\n");
                                wndItem.append("\\C" + xy);
                                wndItem.append("\\f" + fontCvt(InfoBoardConfig.SANSI_PLIST_MULTI, textList.get(j).getTypeface()) + fontSize + fontSize);
                                wndItem.append("\\c" + fontColorCvt(InfoBoardConfig.SANSI_PLIST_MULTI, textList.get(j).getTextColor()) + text);
                                wndItem.append(nl);
                            }
                            wndItem.append(nl + nl);

                            if (i != 3) {
                                int n = i * 2 + 1;
                                pref = "windows" + n + "_";
                                if (i != 2) {
                                    wndItem.append("windows" + n + "_x=" + winX2 + nl);
                                    wndItem.append("windows" + n + "_y=" + 0 + nl);
                                    wndItem.append("windows" + n + "_w=" + lineSize + nl);
                                    wndItem.append("windows" + n + "_h=" + H + nl);
                                    wndItem.append(pref + "item_no=" + 1 + nl);
                                    wndItem.append(pref + "item" + 0 + "=500,1,0,");
                                    wndItem.append("\\C" + String.format("%03d%03d", 0, 0));
                                    wndItem.append(picCvt(InfoBoardConfig.SANSI_PLIST_MULTI, "虚线"));
                                    wndItem.append(nl + nl);
                                } else {
                                    wndItem.append("windows" + n + "_x=" + winX2 + nl);
                                    wndItem.append("windows" + n + "_y=" + 0 + nl);
                                    wndItem.append("windows" + n + "_w=" + lineSize + nl);
                                    wndItem.append("windows" + n + "_h=" + H + nl);
                                    wndItem.append(pref + "item_no=" + 1 + nl);
                                    wndItem.append(pref + "item" + 0 + "=500,1,0,");
                                    wndItem.append("\\C" + String.format("%03d%03d", 0, 0));
                                    wndItem.append(picCvt(InfoBoardConfig.SANSI_PLIST_MULTI, "实线"));
                                    wndItem.append(nl + nl);
                                }
                            }

                        }
                        playlst = wndItem.toString();
                        break;
                    }
                    case 4: {//整行文字分车道图标模式
                        wndItem.append("[playlist]" + nl);
                        wndItem.append("nwindows=" + 8 + nl);

                        for (int i = 0; i < pubList.size(); i++) {
                            int winX1 = rgW * i + lineSize * i, winX2 = rgW * (i + 1) + lineSize * i, winW = rgW, winH = H - picSize;
                            List<VmsPubInfo> textList = pubList.get(i);

                            if (i > 0) pref = "windows" + i + "_";
                            int m = i * 2 + 1;

                            String[] splitText = null;
                            int fontSize = 48;
//                            for (int j = 0; j < textList.size(); j++) {
                            if (i == 0 && textList.get(0).getContent().length() > 0) {
                                wndItem.append("windows" + 0 + "_x=" + winX1 + nl);
                                wndItem.append("windows" + 0 + "_y=" + 0 + nl);
                                wndItem.append("windows" + 0 + "_w=" + W + nl);
                                wndItem.append("windows" + 0 + "_h=" + winH + nl);
                                wndItem.append(pref + "item_no=" + textList.size() + nl);
                                wndItem.append(pref + "item" + 0 + "=500,1,0,");

                                //text <br>替换为\n
                                splitText = textList.get(0).getContent().split("<br>");
                                //计算文字的xy坐标，x按最长文字算，y按行数
                                int xPos = 0, yPos = 0;
                                fontSize = Integer.parseInt(textList.get(0).getTextSize());
                                if (splitText.length > 0) {
                                    String splitTemp = splitText[0];
                                    for (int idx = 1; idx < splitText.length; ++idx) {
                                        if (splitTemp.length() > splitText[idx].length())
                                            splitTemp = splitText[idx];
                                    }
                                    int textXLen = splitTemp.length() * fontSize;
                                    int textYLen = splitText.length * fontSize;
                                    if (textXLen > W) {
                                        xPos = 0;
                                    } else {
                                        xPos = (W - textXLen) / 2;
                                    }
                                    if (textYLen > H) {
                                        yPos = 0;
                                    } else {
                                        yPos = (H - picSize - textYLen) / 2;
                                    }
                                }
                                String xy = String.format("%03d%03d", xPos, yPos);
                                String text = textList.get(0).getContent().replace("<br>", "\\n");
                                wndItem.append("\\C" + xy);
                                wndItem.append("\\f" + fontCvt(InfoBoardConfig.SANSI_PLIST_MULTI, textList.get(0).getTypeface()) + fontSize + fontSize);
                                wndItem.append("\\c" + fontColorCvt(InfoBoardConfig.SANSI_PLIST_MULTI, textList.get(0).getTextColor()) + text);
                                wndItem.append(nl + nl);
                            }
//                            }
                            wndItem.append("windows" + m + "_x=" + winX1 + nl);
                            int winY = H - fontSize;
                            wndItem.append("windows" + m + "_y=" + winY + nl);
                            wndItem.append("windows" + m + "_w=" + winW + nl);
                            pref = "windows" + m + "_";
                            wndItem.append("windows" + m + "_h=" + picSize + nl);
                            wndItem.append(pref + "item_no=" + 1 + nl);
                            wndItem.append(pref + "item" + 0 + "=500,1,0,");
                            String xyPic = String.format("%03d%03d", (rgW - picSize) / 2, 0);
                            wndItem.append("\\C" + xyPic);
                            wndItem.append(picCvt(InfoBoardConfig.SANSI_PLIST_MULTI, textList.get(0).getPicId()));
                            wndItem.append(nl + nl);

                            if (i != 3) {
                                int nn = i * 2 + 2;
                                pref = "windows" + nn + "_";
                                if (i != 2) {
                                    wndItem.append("windows" + nn + "_x=" + winX2 + nl);
                                    wndItem.append("windows" + nn + "_y=" + winY + nl);
                                    wndItem.append("windows" + nn + "_w=" + lineSize + nl);
                                    wndItem.append("windows" + nn + "_h=" + picSize + nl);
                                    wndItem.append(pref + "item_no=" + 1 + nl);
                                    wndItem.append(pref + "item" + 0 + "=500,1,0,");
                                    wndItem.append("\\C" + String.format("%03d%03d", 0, 0));
                                    wndItem.append(picCvt(InfoBoardConfig.SANSI_PLIST_MULTI, "虚线"));
                                    wndItem.append(nl + nl);
                                } else {
                                    wndItem.append("windows" + nn + "_x=" + winX2 + nl);
                                    wndItem.append("windows" + nn + "_y=" + winY + nl);
                                    wndItem.append("windows" + nn + "_w=" + lineSize + nl);
                                    wndItem.append("windows" + nn + "_h=" + picSize + nl);
                                    wndItem.append(pref + "item_no=" + 1 + nl);
                                    wndItem.append(pref + "item" + 0 + "=500,1,0,");
                                    wndItem.append("\\C" + String.format("%03d%03d", 0, 0));
                                    wndItem.append(picCvt(InfoBoardConfig.SANSI_PLIST_MULTI, "实线"));
                                    wndItem.append(nl + nl);
                                }
                            }
                        }
                        playlst = wndItem.toString();
                        break;
                    }
                    case 5: {//分车道文字图标上下模式
                        wndItem.append("[playlist]" + nl);
                        int num = pubList.size() * 2 + 3;
                        wndItem.append("nwindows=" + num + nl);
                        for (int i = 0; i < pubList.size(); i++) {
                            int winX1 = rgW * i + lineSize * i, winX2 = rgW * (i + 1) + lineSize * i, winW = rgW, winH = H - picSize;
                            List<VmsPubInfo> txList = pubList.get(i);

                            int n = i * 2 + i;
                            int m = i * 2 + i + 1;
                            if (i > 0) pref = "windows" + n + "_";

                            wndItem.append("windows" + n + "_x=" + winX1 + nl);
                            wndItem.append("windows" + n + "_y=" + 0 + nl);
                            wndItem.append("windows" + n + "_w=" + winW + nl);
                            wndItem.append("windows" + n + "_h=" + winH + nl);
                            wndItem.append(pref + "item_no=" + txList.size() + nl);
                            String[] splitText = null;
                            int fontSize = 0;
                            for (int j = 0; j < txList.size(); j++) {
                                wndItem.append(pref + "item" + j + "=500,1,0,");
                                //text <br>替换为\n
                                splitText = txList.get(j).getContent().split("<br>");
                                //计算文字的xy坐标，x按最长文字算，y按行数
                                int xPos = 0, yPos = 0;
                                fontSize = Integer.parseInt(txList.get(j).getTextSize());
                                if (splitText.length > 0) {
                                    String splitTemp = splitText[0];
                                    for (int idx = 1; idx < splitText.length; ++idx) {
                                        if (splitTemp.length() > splitText[idx].length()) splitTemp = splitText[idx];
                                    }
                                    int textXLen = splitTemp.length() * fontSize;
                                    int textYLen = splitText.length * fontSize;
                                    if (textXLen > winW) {
                                        xPos = 0;
                                    } else {
                                        xPos = (winW - textXLen) / 2;
                                    }
                                    if (textYLen > H) {
                                        yPos = 0;
                                    } else {
                                        yPos = (H - picSize - textYLen) / 2;
                                    }
                                }
                                String xy = String.format("%03d%03d", xPos, yPos);
                                String text = txList.get(j).getContent().replace("<br>", "\\n");
                                wndItem.append("\\C" + xy);
                                wndItem.append("\\f" + fontCvt(InfoBoardConfig.SANSI_PLIST_MULTI, txList.get(j).getTypeface()) + fontSize + fontSize);
                                wndItem.append("\\c" + fontColorCvt(InfoBoardConfig.SANSI_PLIST_MULTI, txList.get(j).getTextColor()) + text);
                                wndItem.append(nl);
                            }
                            wndItem.append("windows" + m + "_x=" + winX1 + nl);
                            int winY = H - splitText.length * fontSize;
                            wndItem.append("windows" + m + "_y=" + winY + nl);
                            wndItem.append("windows" + m + "_w=" + winW + nl);
                            pref = "windows" + m + "_";
                            wndItem.append("windows" + m + "_h=" + picSize + nl);
                            wndItem.append(pref + "item_no=" + 1 + nl);
                            wndItem.append(pref + "item" + 0 + "=500,1,0,");
                            String xyPic = String.format("%03d%03d", (rgW - picSize) / 2, 0);
                            wndItem.append("\\C" + xyPic);
                            wndItem.append(picCvt(InfoBoardConfig.SANSI_PLIST_MULTI, txList.get(0).getPicId()));
                            wndItem.append(nl);
                            if (i != 3) {
                                int nn = i * 2 + i + 2;
                                pref = "windows" + nn + "_";
                                if (i != 2) {
                                    wndItem.append("windows" + nn + "_x=" + winX2 + nl);
                                    wndItem.append("windows" + nn + "_y=" + 0 + nl);
                                    wndItem.append("windows" + nn + "_w=" + lineSize + nl);
                                    wndItem.append("windows" + nn + "_h=" + H + nl);
                                    wndItem.append(pref + "item_no=" + 1 + nl);
                                    wndItem.append(pref + "item" + 0 + "=500,1,0,");
                                    wndItem.append("\\C" + String.format("%03d%03d", 0, 0));
                                    wndItem.append(picCvt(InfoBoardConfig.SANSI_PLIST_MULTI, "虚线"));
                                    wndItem.append(nl + nl);
                                } else {
                                    wndItem.append("windows" + nn + "_x=" + winX2 + nl);
                                    wndItem.append("windows" + nn + "_y=" + 0 + nl);
                                    wndItem.append("windows" + nn + "_w=" + lineSize + nl);
                                    wndItem.append("windows" + nn + "_h=" + H + nl);
                                    wndItem.append(pref + "item_no=" + 1 + nl);
                                    wndItem.append(pref + "item" + 0 + "=500,1,0,");
                                    wndItem.append("\\C" + String.format("%03d%03d", 0, 0));
                                    wndItem.append(picCvt(InfoBoardConfig.SANSI_PLIST_MULTI, "实线"));
                                    wndItem.append(nl + nl);
                                }
                            }
                        }
                        playlst = wndItem.toString();
                        break;
                    }
                    case 6: {//分车道文字图标左右模式
                        wndItem.append("[playlist]" + nl);
                        wndItem.append("nwindows=" + 7 + nl);

                        for (int i = 0; i < pubList.size(); i++) {
                            int winX1 = rgW * i + lineSize * i, winX2 = rgW * (i + 1) + lineSize * i, winW = rgW;
                            List<VmsPubInfo> textList = pubList.get(i);

                            if (i > 0) pref = "windows" + i * 2 + "_";

                            wndItem.append("windows" + i * 2 + "_x=" + winX1 + nl);
                            wndItem.append("windows" + i * 2 + "_y=" + 0 + nl);
                            wndItem.append("windows" + i * 2 + "_w=" + winW + nl);
                            wndItem.append("windows" + i * 2 + "_h=" + H + nl);
                            wndItem.append(pref + "item_no=" + textList.size() + nl);

                            for (int j = 0; j < textList.size(); j++) {
                                //text <br>替换为\n
                                String[] splitText = textList.get(j).getContent().split("<br>");
                                //计算文字的xy坐标，x按最长文字算，y按行数
                                int xPos = 0, yPos = 0;
                                int fontSize = Integer.parseInt(textList.get(j).getTextSize());
                                int textXLen = 0;
                                if (splitText.length > 0) {
                                    String splitTemp = splitText[0];
                                    for (int idx = 1; idx < splitText.length; ++idx) {
                                        if (splitTemp.length() > splitText[idx].length()) splitTemp = splitText[idx];
                                    }
                                    textXLen = splitTemp.length() * fontSize;
                                    int textYLen = splitText.length * fontSize;
                                    if (textXLen > W) {
                                        xPos = 0;
                                    } else {
                                        xPos = (winW - 96 - textXLen) / 2 + 96;
                                    }
                                    if (textYLen > H) {
                                        yPos = 0;
                                    } else {
                                        yPos = (H - textYLen) / 2;
                                    }
                                }
                                wndItem.append(pref + "item" + j + "=500,1,0,");
                                String picXy = String.format("%03d%03d", (winW - 96 - textXLen) / 2 - 5, (H - 96) / 2);
                                wndItem.append("\\C" + picXy);
                                wndItem.append(get96Pic(InfoBoardConfig.SANSI_PLIST_MULTI, textList.get(j).getPicId()));
                                String xy = String.format("%03d%03d", xPos, yPos);
                                String text = textList.get(j).getContent().replace("<br>", "\\n");
                                wndItem.append("\\C" + xy);
                                wndItem.append("\\f" + fontCvt(InfoBoardConfig.SANSI_PLIST_MULTI, textList.get(j).getTypeface()) + fontSize + fontSize);
                                wndItem.append("\\c" + fontColorCvt(InfoBoardConfig.SANSI_PLIST_MULTI, textList.get(j).getTextColor()) + text);
                                wndItem.append(nl);
                            }
                            wndItem.append(nl + nl);

                            if (i != 3) {
                                int n = i * 2 + 1;
                                pref = "windows" + n + "_";
                                if (i != 2) {
                                    wndItem.append("windows" + n + "_x=" + winX2 + nl);
                                    wndItem.append("windows" + n + "_y=" + 0 + nl);
                                    wndItem.append("windows" + n + "_w=" + lineSize + nl);
                                    wndItem.append("windows" + n + "_h=" + H + nl);
                                    wndItem.append(pref + "item_no=" + 1 + nl);
                                    wndItem.append(pref + "item" + 0 + "=500,1,0,");
                                    wndItem.append("\\C" + String.format("%03d%03d", 0, 0));
                                    wndItem.append(picCvt(InfoBoardConfig.SANSI_PLIST_MULTI, "虚线"));
                                    wndItem.append(nl + nl);
                                } else {
                                    wndItem.append("windows" + n + "_x=" + winX2 + nl);
                                    wndItem.append("windows" + n + "_y=" + 0 + nl);
                                    wndItem.append("windows" + n + "_w=" + lineSize + nl);
                                    wndItem.append("windows" + n + "_h=" + H + nl);
                                    wndItem.append(pref + "item_no=" + 1 + nl);
                                    wndItem.append(pref + "item" + 0 + "=500,1,0,");
                                    wndItem.append("\\C" + String.format("%03d%03d", 0, 0));
                                    wndItem.append(picCvt(InfoBoardConfig.SANSI_PLIST_MULTI, "实线"));
                                    wndItem.append(nl + nl);
                                }
                            }

                        }


                        playlst = wndItem.toString();
                        break;
                    }
                    case 7: {//分车道单图标模式
                        wndItem.append("[playlist]" + nl);
                        int num = pubList.size() + 3;
                        wndItem.append("nwindows=" + num + nl);
                        for (int i = 0; i < pubList.size(); i++) {
                            int winX1 = rgW * i + lineSize * i, winX2 = rgW * (i + 1) + lineSize * i, winW = rgW;
                            List<VmsPubInfo> picList = pubList.get(i);

                            if (i > 0) pref = "windows" + i * 2 + "_";

                            wndItem.append("windows" + i * 2 + "_x=" + winX1 + nl);
                            wndItem.append("windows" + i * 2 + "_y=" + 0 + nl);
                            wndItem.append("windows" + i * 2 + "_w=" + winW + nl);
                            wndItem.append("windows" + i * 2 + "_h=" + H + nl);
                            wndItem.append(pref + "item_no=" + 1 + nl);
                            wndItem.append(pref + "item" + 0 + "=500,1,0,");
                            String xyPic = String.format("%03d%03d", (rgW - 96) / 2, 0);
                            wndItem.append("\\C" + xyPic);
                            wndItem.append(get96Pic(InfoBoardConfig.SANSI_PLIST_MULTI, picList.get(0).getPicId()));
                            wndItem.append(nl + nl);
                            if (i != 3) {
                                int n = i * 2 + 1;
                                pref = "windows" + n + "_";
                                if (i != 2) {
                                    wndItem.append("windows" + n + "_x=" + winX2 + nl);
                                    wndItem.append("windows" + n + "_y=" + 0 + nl);
                                    wndItem.append("windows" + n + "_w=" + lineSize + nl);
                                    wndItem.append("windows" + n + "_h=" + H + nl);
                                    wndItem.append(pref + "item_no=" + 1 + nl);
                                    wndItem.append(pref + "item" + 0 + "=500,1,0,");
                                    wndItem.append("\\C" + String.format("%03d%03d", 0, 0));
                                    wndItem.append(picCvt(InfoBoardConfig.SANSI_PLIST_MULTI, "虚线"));
                                    wndItem.append(nl + nl);
                                } else {
                                    wndItem.append("windows" + n + "_x=" + winX2 + nl);
                                    wndItem.append("windows" + n + "_y=" + 0 + nl);
                                    wndItem.append("windows" + n + "_w=" + lineSize + nl);
                                    wndItem.append("windows" + n + "_h=" + H + nl);
                                    wndItem.append(pref + "item_no=" + 1 + nl);
                                    wndItem.append(pref + "item" + 0 + "=500,1,0,");
                                    wndItem.append("\\C" + String.format("%03d%03d", 0, 0));
                                    wndItem.append(picCvt(InfoBoardConfig.SANSI_PLIST_MULTI, "实线"));
                                    wndItem.append(nl + nl);
                                }
                            }
                        }
                        playlst = wndItem.toString();
                        break;
                    }
                }
            }
            else if(dev.getProtocol().equals(InfoBoardConfig.SANSI_PLIST)) {
                if(pubList.size() > 0) {
                    StringBuilder playlstAll = new StringBuilder();
                    playlstAll.append("[list]" + nl);
                    List<VmsPubInfo> editList = pubList.get(0);
                    playlstAll.append("item_no=" + editList.size() + nl);
                    StringBuilder playlstItem = new StringBuilder();
                    StringBuilder playlstAllItem = new StringBuilder();

                    int xPos = 0, yPos = 0;
                    for(int k=0; k < editList.size(); ++k) {
                        VmsPubInfo edit = editList.get(k);
                        playlstItem.setLength(0); //清空
                        playlstItem.append("item"+k+"=500,1,0,");
                        //text <br>替换为\n
                        //计算文字的xy坐标，x按最长文字算，y按行数
                        String[] splitText = edit.getContent().split("<br>");
                        int fontSize = Integer.parseInt(edit.getTextSize());
                        if (splitText.length > 0) {
                            //取最长的x
                            String splitTemp = splitText[0];
                            for (int idx = 1; idx < splitText.length; ++idx) {
                                if (splitText[idx].length() > splitTemp.length()) splitTemp = splitText[idx];
                            }
                            int textXLen = splitTemp.length() * fontSize;
                            int textYLen = splitText.length * fontSize;
                            if (textXLen > W) {
                                xPos = 0;
                            } else {
                                xPos = (W - textXLen) / 2;
                            }
                            if (textYLen > H) {
                                yPos = 0;
                            } else {
                                yPos = (H - textYLen) / 2;
                            }
                        }
                        String xy = String.format("%03d%03d", xPos, yPos);
                        String text = edit.getContent().replace("<br>", "\\n");
                        playlstItem.append("\\C" + xy);
                        playlstItem.append("\\f" + fontCvt(InfoBoardConfig.SANSI_PLIST_MULTI, edit.getTypeface()) + fontSize + fontSize);
                        playlstItem.append("\\c" + fontColorCvt(InfoBoardConfig.SANSI_PLIST_MULTI, edit.getTextColor()) + text);
                    }
                    playlstAllItem.append(playlstItem + nl);
                    playlstAll.append(playlstAllItem);
                    playlst = playlstAll.toString();
                }
            }
        }
        return playlst;
    }

    // 生成playlst 版本2
    private String genPlaylst2(List<DevInfo> devInfoList, List<List<VmsPubInfo>> pubList) {

        String playlst = "";
        if (devInfoList.size() > 0) {
            DevInfo dev = devInfoList.get(0);
            String nl = System.getProperty("line.separator"); //换行
            if (dev.getProtocol().equals(InfoBoardConfig.SANSI_PLIST_MULTI)) {
                //窗口列表
                int rgNum = pubList.size(); //区域数
                List<PlaylstWndInfo> wndList = new ArrayList<>(rgNum * 2 + 3);
                for (int cnt = 0; cnt < rgNum * 2 + 3; ++cnt) wndList.add(new PlaylstWndInfo());
//                List<PlaylstWndInfo> wndList = Arrays.asList(new PlaylstWndInfo[rgNum * 2]);

                int lineSize = 16;
                //区域宽高 (regionNum - 1) * 8 是间隔
                int rgW = (dev.getWidth() - lineSize * 3) / rgNum;
                int picSize = 48;


                for (int i = 0; i < rgNum; ++i) {
                    int rgH = dev.getHeight();
                    //窗口坐标、宽高
                    int winX = rgW * i + lineSize * i, winY = 0, winW = rgW, winH = 0;
                    List<VmsPubInfo> editList = pubList.get(i);
                    boolean bPic = false;
                    boolean bText = false;
                    for (int j = 0; j < editList.size(); ++j) {
                        VmsPubInfo edit = editList.get(j);
                        //一个区域里如果既有图片，又有文字，则 windows 要增加
                        boolean hasPic = !StringUtils.isEmpty(edit.getPicId()) && !edit.getPicId().equals("0");
                        boolean hasText = !StringUtils.isEmpty(edit.getContent());
                        bPic = bPic || hasPic;
                        bText = bText || hasText;

                        if (hasText) {
                            PlaylstWndInfo wndText = wndList.get(i * 2 + i);
                            wndText.setWinX(winX);
                            wndText.setWinY(winY);
                            wndText.setWinW(winW);
                            wndText.setWinH(bPic ? rgH - picSize : rgH);
                            wndText.setType(1);
                            wndText.getItems().add(edit);
                        }

                        if (hasPic) {
                            PlaylstWndInfo wndPic = wndList.get(i * 2 + 1 + i);
                            wndPic.setWinX(winX);
                            wndPic.setWinY(bText ? rgH - picSize : winY);
                            wndPic.setWinW(winW);
                            wndPic.setWinH(picSize);
                            wndPic.setType(0);
                            wndPic.getItems().add(edit);
                            if (rgNum > 1) {
                                if (i != 2) {
                                    PlaylstWndInfo wndDash = wndList.get(i * 2 + 2 + i);
                                    wndDash.setWinX(winW);
                                    wndDash.setWinY(winY);
                                    wndDash.setWinW(lineSize);
                                    wndDash.setWinH(rgH);
                                    wndDash.setType(0);
                                    VmsPubInfo dash = new VmsPubInfo();
                                    dash.setPicId("z04");
                                    wndDash.getItems().add(dash);
                                } else {
                                    PlaylstWndInfo wndSolid = wndList.get(i * 2 + 2 + i);
                                    wndSolid.setWinX(winW);
                                    wndSolid.setWinY(winY);
                                    wndSolid.setWinW(lineSize);
                                    wndSolid.setWinH(rgH);
                                    wndSolid.setType(0);
                                    VmsPubInfo solid = new VmsPubInfo();
                                    solid.setPicId("z05");
                                    wndSolid.getItems().add(solid);
                                }
                            }
                        }
                    }
                }
                //清理wndList
                Iterator<PlaylstWndInfo> iter = wndList.iterator();
                while (iter.hasNext()) {
                    PlaylstWndInfo wnd = iter.next();
                    if (wnd.getItems().size() == 0) {
                        iter.remove();
                    }
                }

                //组装 playlst
                StringBuilder playlstAll = new StringBuilder();
                playlstAll.append("[playlist]" + nl);
                playlstAll.append("nwindows=" + wndList.size() + nl);

                String pref = "";
                for (int n = 0; n < wndList.size(); ++n) {

                    StringBuilder wndItem = new StringBuilder();
                    PlaylstWndInfo wndInfo = wndList.get(n);
                    if (n > 0) pref = "windows" + n + "_";
                    wndItem.append("windows" + n + "_x=" + wndInfo.getWinX() + nl);
                    wndItem.append("windows" + n + "_y=" + wndInfo.getWinY() + nl);
                    wndItem.append("windows" + n + "_w=" + wndInfo.getWinW() + nl);
                    wndItem.append("windows" + n + "_h=" + wndInfo.getWinH() + nl);
                    wndItem.append(pref + "item_no=" + wndInfo.getItems().size() + nl);
                    for (int m = 0; m < wndInfo.getItems().size(); ++m) {
                        StringBuilder playlstItem = new StringBuilder();
                        VmsPubInfo pubInfo = wndInfo.getItems().get(m);
                        playlstItem.append(pref + "item" + m + "=500,1,0,");
                        if (wndInfo.getType() == 0) { //图片
                            String xyPic = String.format("%03d%03d", (wndInfo.getWinW() - picSize) / 2, 0);
                            playlstItem.append("\\C" + xyPic);
                            playlstItem.append(picCvt(InfoBoardConfig.SANSI_PLIST_MULTI, pubInfo.getPicId()));
                        } else {
                            //text <br>替换为\n
                            String[] splitText = pubInfo.getContent().split("<br>");
                            //计算文字的xy坐标，x按最长文字算，y按行数
                            int xPos = 0, yPos = 0;
                            int fontSize = Integer.parseInt(pubInfo.getTextSize());
                            if (splitText.length > 0) {
                                String splitTemp = splitText[0];
                                for (int idx = 1; idx < splitText.length; ++idx) {
                                    if (splitTemp.length() > splitText[idx].length()) splitTemp = splitText[idx];
                                }
                                int textXLen = splitTemp.length() * fontSize;
                                int textYLen = splitText.length * fontSize;
                                if (textXLen > wndInfo.getWinW()) {
                                    xPos = 0;
                                } else {
                                    xPos = (wndInfo.getWinW() - textXLen) / 2;
                                }
                                if (textYLen > wndInfo.getWinH()) {
                                    yPos = 0;
                                } else {
                                    yPos = (wndInfo.getWinH() - textYLen) / 2;
                                }
                            }
                            String xy = String.format("%03d%03d", xPos, yPos);
                            String text = pubInfo.getContent().replace("<br>", "\\n");
                            playlstItem.append("\\C" + xy);
                            playlstItem.append("\\f" + fontCvt(InfoBoardConfig.SANSI_PLIST_MULTI, pubInfo.getTypeface()) + fontSize + fontSize);
                            playlstItem.append("\\c" + fontColorCvt(InfoBoardConfig.SANSI_PLIST_MULTI, pubInfo.getTextColor()) + text);
                        }
                        wndItem.append(playlstItem + nl);
                    }
                    playlstAll.append(wndItem + nl + nl);
                }
                playlstAll.delete(playlstAll.length() - nl.length() * 2, playlstAll.length());
                playlst = playlstAll.toString();
            }
        }
        return playlst;
    }

    // 判断多分区（A7,A8）图片前缀是 \B还是\P

    // playlst上传文件
    private int ULOneFile10(Integer slaveID, String ip, Integer port, byte[] data) {
        byte[] sendPkg = PacketPlaylst(slaveID, "10", data);
//        byte[] recvPkg = null;
        int recv = 1;
        Socket skt = null;
        try {
            skt = iSocketService.clientSocket(ip, port);
            recv = iSocketService.writeAndResult(sendPkg, skt);
            skt.close();
        } catch (Exception ex) {
            log.error("情报板发布：上传文件异常！" + ex.getMessage());
            recv = -1;
        }
        return recv;
    }

    // playlst 组包
    private byte[] PacketPlaylst(Integer slaveID, String pkgType, byte[] data) {
        int len = data == null ? 0 : data.length;
        int pos = 0;
        byte[] ret = new byte[len + 8];
        ret[pos++] = 0x02;
        String sid = String.format("%02d", slaveID);
        ret[pos++] = (byte) (sid.charAt(0));
        ret[pos++] = (byte) (sid.charAt(1));
        ret[pos++] = (byte) (pkgType.charAt(0));
        ret[pos++] = (byte) (pkgType.charAt(1));

        if (len > 0) {
            System.arraycopy(data, 0, ret, 5, len);
        }
        System.out.println("长度：" + ret.length + "，发送报文：" + ByteUtils.bytes2hex(ret));
        pos += len;

        //校验
//        byte[] crc2 = ByteUtils.GetCrc(ret.Skip(1).Take(pos - 1).ToArray());
//        byte[] crcCheck = Arrays.copyOfRange(ret, 1, ret.length - 1);
//        byte[] crc2 = ByteUtils.GetCrc(crcCheck, crcCheck.length);
        byte[] crcBytes = new byte[ret.length - 4];
        System.arraycopy(ret, 1, crcBytes, 0, crcBytes.length);
        byte[] crc2 = ByteUtils.GetCrc(crcBytes, crcBytes.length);
        System.out.println("长度：" + crc2.length + "，发送报文：" + ByteUtils.bytes2hex(crc2));
        ret[pos++] = crc2[0];
        ret[pos++] = crc2[1];

        ret[pos] = 0x03;

        //转义
        byte[] sendPkg = ByteUtils.TransPkg(ret, 5);
        System.out.println("长度：" + sendPkg.length + "，发送报文：" + ByteUtils.bytes2hex(sendPkg));

        return sendPkg;
    }

    // 枫泾停车场
    private byte[] PacketParking(int slaveID, byte pkgType, byte[] data) {

        int len = data == null ? 0 : data.length;
        int pos = 0;
        byte[] ret = new byte[len + 7];
        ret[pos++] = 0x02;
        String sid = String.format("%02d", slaveID);
        ret[pos++] = (byte) (sid.charAt(0) - '0');
        ret[pos++] = (byte) (sid.charAt(1) - '0');
        ret[pos++] = pkgType;

        if (len > 0) {
            System.arraycopy(data, 0, ret, 4, len);
        }
        pos += len;

        //校验
        byte[] crcBytes = new byte[ret.length - 4];
        System.arraycopy(ret, 1, crcBytes, 0, crcBytes.length);
        byte[] crc2 = ByteUtils.GetCrc(crcBytes, crcBytes.length);
        ret[pos++] = crc2[0];
        ret[pos++] = crc2[1];

        ret[pos] = 0x03;

        //转义
        byte[] sendPkg = ByteUtils.TransPkg(ret, 4);
        System.out.println("长度：" + sendPkg.length + "，发送报文：" + ByteUtils.bytes2hex(sendPkg));

        return sendPkg;
    }

    // 字体转换，[in]=字体名，[return]=字体编码
    private String fontCvt(String protocol, String font) {
        String fontCode = "30";
        if (protocol == InfoBoardConfig.SWARCO) {
            switch (font) { //+0x30
                case "黑体":
                    fontCode = "30";
                    break;
                case "楷体":
                    fontCode = "31";
                    break;
                case "宋体":
                    fontCode = "32";
                    break;
                case "仿宋体":
                    fontCode = "33";
                    break;
            }
        } else if (protocol == InfoBoardConfig.SANSI_PLIST_MULTI) {
            switch (font) {
                case "黑体":
                    fontCode = "h";
                    break;
                case "楷体":
                    fontCode = "k";
                    break;
                case "宋体":
                    fontCode = "s";
                    break;
                case "仿宋体":
                    fontCode = "f";
                    break;
            }
        }
        return fontCode;
    }

    // 字体大小转换，[in]=字体大小，[return]=字体大小编码
    private String fontSizeCvt(String protocol, String fontSize) {
        String fontSizeCode = "33";
        if (protocol == InfoBoardConfig.SWARCO) {
            switch (fontSize) { //+0x30
                case "16":
                    fontSizeCode = "31";
                    break;
                case "24":
                    fontSizeCode = "32";
                    break;
                case "32":
                    fontSizeCode = "33";
                    break;
                case "48":
                    fontSizeCode = "34";
                    break;
            }
        } else if (protocol == InfoBoardConfig.DONGHAI_F) {
            switch (fontSize) {
                case "16":
                case "24":
                    fontSizeCode = "7C";
                    break;
                case "32":
                case "36":
                case "48":
                    fontSizeCode = "78";
                    break;
            }
        }
        return fontSizeCode;
    }

    // 字体颜色转换，[in]=字体颜色，[return]=字体颜色编码
    private String fontColorCvt(String protocol, String fontColor) {
        String fontColorCode = "21";
        if (protocol == InfoBoardConfig.SWARCO) {
            switch (fontColor) {
                case "红":
                    fontColorCode = "20";
                    break;
                case "绿":
                    fontColorCode = "21";
                    break;
                case "黄":
                    fontColorCode = "22";
                    break;
            }
        } else if (protocol == InfoBoardConfig.SANSI_PLIST_MULTI) {
            switch (fontColor) {
                case "红":
                    fontColorCode = "255000000000";
                    break;
                case "绿":
                    fontColorCode = "000255000000";
                    break;
                case "黄":
                    fontColorCode = "255255000000";
                    break;
            }
        }
        return fontColorCode;
    }

    // 图片转换，[in]=图片名，[return]=图片编码
    private String picCvt(String protocol, String picId) {
        String picCode = ""; //无图片
        if (protocol == InfoBoardConfig.SWARCO) {
            if (StringUtils.isEmpty(picId)) return "30";
            switch (picId) { //+0x30
                case "0":
                    picCode = "30";
                    break; //无图片
                case "20":
                    picCode = "67";
                    break; //20
                case "25":
                    picCode = "68";
                    break;
                case "30":
                    picCode = "69";
                    break;
                case "35":
                    picCode = "51";
                    break;
                case "40":
                    picCode = "52";
                    break;
                case "45":
                    picCode = "6A";
                    break;
                case "50":
                    picCode = "6B";
                    break;
                case "55":
                    picCode = "6C";
                    break;
                case "60":
                    picCode = "53";
                    break;
                case "65":
                    picCode = "6D";
                    break;
                case "70":
                    picCode = "6E";
                    break;
                case "80":
                    picCode = "54";
                    break;
                case "90":
                    picCode = "6F";
                    break;
                case "100":
                    picCode = "55";
                    break;
                case "110":
                    picCode = "70";
                    break;
                case "120":
                    picCode = "56";
                    break;
            }
        } else if (protocol == InfoBoardConfig.SANSI_PLIST_MULTI) {
            switch (picId) {
                case "叉":
                    picCode = "\\Pz00";
                    break;
                case "上箭头":
                    picCode = "\\Pz01";
                    break;
                case "右上箭头":
                    picCode = "\\Pz02";
                    break;
                case "左上箭头":
                    picCode = "\\Pz03";
                    break;
                case "虚线":
                    picCode = "\\Pz04";
                    break;
                case "实线":
                    picCode = "\\Pz05";
                    break;
                case "120":
                    picCode = "\\P120";
                    break;
                case "110":
                    picCode = "\\P110";
                    break;
                case "100":
                    picCode = "\\P100";
                    break;
                case "90":
                    picCode = "\\P90";
                    break;
                case "80":
                    picCode = "\\P80";
                    break;
                case "70":
                    picCode = "\\P70";
                    break;
                case "65":
                    picCode = "\\P65";
                    break;
                case "60":
                    picCode = "\\P60";
                    break;
                case "55":
                    picCode = "\\P55";
                    break;
                case "50":
                    picCode = "\\P50";
                    break;
                case "45":
                    picCode = "\\P45";
                    break;
                case "40":
                    picCode = "\\P40";
                    break;
                case "35":
                    picCode = "\\P35";
                    break;
                case "30":
                    picCode = "\\P30";
                    break;
                case "25":
                    picCode = "\\P25";
                    break;
                case "20":
                    picCode = "\\P20";
                    break;
                default:
                    picCode = "\\B" + picId;
            }
        }
        return picCode;
    }

    //96像素图片转换
    private String get96Pic(String protocol, String picId) {
        String picCode = ""; //限速80
        if (protocol == InfoBoardConfig.SANSI_PLIST_MULTI) {
            switch (picId) {
                case "35":
                    picCode = "\\Ba10";
                    break;
                case "40":
                    picCode = "\\Ba05";
                    break;
                case "60":
                    picCode = "\\Ba06";
                    break;
                case "80":
                    picCode = "\\Ba07";
                    break;
                case "100":
                    picCode = "\\Ba08";
                    break;
                case "110":
                    picCode = "\\Ba21";
                    break;
                case "120":
                    picCode = "\\Ba09";
                    break;
                case "013":
                    picCode = "\\Bz25";
                    break;
                case "014":
                    picCode = "\\Bz26";
                    break;
                case "060":
                    picCode = "\\Ba00";
                    break;
            }
        }
        return picCode;
    }

    // 限速转换，[in]=限速值，[return]=限速编码
    private String fmsCvt(String protocol, String fmsValue) {
        String fmsCode = "16"; //限速80
        if (protocol == InfoBoardConfig.SANSI_XS) {
            switch (fmsValue) {
                case "40":
                    fmsCode = "12";
                    break;
                case "50":
                    fmsCode = "13";
                    break;
                case "60":
                    fmsCode = "14";
                    break;
                case "70":
                    fmsCode = "15";
                    break;
                case "80":
                    fmsCode = "16";
                    break;
                case "90":
                    fmsCode = "17";
                    break;
                case "100":
                    fmsCode = "18";
                    break;
                case "110":
                    fmsCode = "19";
                    break;
                case "120":
                    fmsCode = "1a";
                    break;
            }
        }
        return fmsCode;
    }

    //东海校验码
    private byte DonghaiCheckNum(String value) {
        byte[] bytes = StringUtils.hexStrTobytes(value);
        byte VerifyByte = bytes[0];
        for (int i = 1; i < bytes.length; i++) {
            VerifyByte = (byte) (VerifyByte ^ bytes[i]);
        }
        VerifyByte = (byte) (VerifyByte & 0x7F);
        return VerifyByte;
    }

    //通知
    @Async
    @Override
    public void notifyResult(JSONObject result) {
        String post;
        R ret;
        int time = 1;
        while (true) {
            try {
                post = HttpUtil.post(HostConfig.DASSHOST + "/device-monitor/infoBoard/notifyResult", JSON.toJSONString(result));
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

    private void notifyPing(JSONArray result) {
        String post;
        R ret;
        int time = 1;
        while (true) {
            try {
                post = HttpUtil.post(HostConfig.DASSHOST + "/device-monitor/infoBoard/notifyPing", JSON.toJSONString(result));
                if (!StringUtils.isEmpty(post)) {
                    if (post.startsWith("{")) {
                        ret = JSON.parseObject(post, R.class);
                        if (R.SUCCESS == ret.getCode()) {
                            break;
                        } else {
                            log.error(JSON.toJSONString(result) + "转发采集通知失败：" + ret.getMsg());
                        }
                    } else {
                        log.error(JSON.toJSONString(result) + "转发采集通知状态未知");
                    }
                    Thread.sleep(time * 1000);
                }
            } catch (InterruptedException e) {
                log.error(JSON.toJSONString(result) + "转发采集通知异常：" + e.getMessage());
            }
            time += 2;
        }
    }
}
