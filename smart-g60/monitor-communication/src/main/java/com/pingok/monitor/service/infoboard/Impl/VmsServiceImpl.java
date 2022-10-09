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

    @Override
    public JSONObject publish(String pubInfo) {

        int retCode = 200;

        JSONObject jo = JSONObject.parseObject(pubInfo);
//        JSONArray devInfoList = jo.getJSONArray("devInfo");
        List<DevInfo> devInfoList = JSON.parseArray(JSONObject.toJSONString(jo.get("devInfo")), DevInfo.class);
        JSONArray dataList = jo.getJSONArray("data");
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
        String playlst = genPlaylst2(devInfoList, pubList);
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
                case InfoBoardConfig.SANSI_PLIST_MULTI:
                    ret = publishSansiPlistMulti(dev, playlst);
//                    ret = 200;
                    break;
                default:
                    retCode = 500;
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

    /* 三思 播放列表 多分区
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
                        if(!StringUtils.isEmpty(edit.getContent())) {
                            if(winNum > 0) pref = "windows" + winNum + "_";
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
                            if(splitText.length > 0) {
                                String splitTemp = splitText[0];
                                for(int idx = 1; idx < splitText.length; ++idx) {
                                    if(splitTemp.length() > splitText[idx].length()) splitTemp = splitText[idx];
                                }
                                int textXLen = splitTemp.length() * fontSize;
                                int textYLen = splitText.length * fontSize;
                                if(textXLen > regionWidth) {
                                    xPos = 0;
                                } else {
                                    xPos = (regionWidth - textXLen) / 2;
                                }
                                if(textYLen > regionHeight) {
                                    yPos = 0;
                                } else {
                                    yPos = (regionHeight - textYLen) / 2;
                                }
                            }
                            String xy = String.format("%03d%03d", xPos,yPos);
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

    // 生成playlst 版本2
    private String genPlaylst2(List<DevInfo> devInfoList, List<List<VmsPubInfo>> pubList) {

        String playlst = "";
        if(devInfoList.size() > 0) {
            DevInfo dev = devInfoList.get(0);
            String nl = System.getProperty("line.separator"); //换行
            if (dev.getProtocol().equals(InfoBoardConfig.SANSI_PLIST_MULTI)) {
                //窗口列表
                int rgNum = pubList.size(); //区域数
                List<PlaylstWndInfo> wndList = new ArrayList<>(rgNum * 2);
                for(int cnt = 0; cnt < rgNum * 2; ++cnt) wndList.add(new PlaylstWndInfo());
//                List<PlaylstWndInfo> wndList = Arrays.asList(new PlaylstWndInfo[rgNum * 2]);


                //区域宽高 (regionNum - 1) * 8 是间隔
                int rgW = dev.getWidth() / rgNum;
                int picSize = 48;

                for (int i = 0; i < rgNum; ++i) {
                    int rgH = dev.getHeight();
                    //窗口坐标、宽高
                    int winX = rgW * i, winY = 0, winW = rgW, winH = 0;
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

                        if(hasPic) {
                            PlaylstWndInfo wndPic = wndList.get(i*2);
                            wndPic.setWinX(winX);
                            wndPic.setWinY(winY);
                            wndPic.setWinW(winW);
                            wndPic.setWinH(picSize);
                            wndPic.setType(0);
                            wndPic.getItems().add(edit);
                        }
                        if(hasText) {
                            PlaylstWndInfo wndText = wndList.get(i*2 + 1);
                            wndText.setWinX(winX);
                            wndText.setWinY(bPic ? winY + picSize : winY);
                            wndText.setWinW(winW);
                            wndText.setWinH(bPic ? rgH - picSize : rgH);
                            wndText.setType(1);
                            wndText.getItems().add(edit);
                        }
                    }
                }
                //清理wndList
                Iterator<PlaylstWndInfo> iter = wndList.iterator();
                while (iter.hasNext()) {
                    PlaylstWndInfo wnd = iter.next();
                    if(wnd.getItems().size() == 0) {
                        iter.remove();
                    }
                }

                //组装 playlst
                StringBuilder playlstAll = new StringBuilder();
                playlstAll.append("[playlist]" + nl);
                playlstAll.append("nwindows=" + wndList.size() + nl);

                String pref = "";
                for(int n = 0; n < wndList.size(); ++n) {
                    StringBuilder wndItem = new StringBuilder();
                    PlaylstWndInfo wndInfo = wndList.get(n);
                    if(n > 0) pref = "windows" + n + "_";
                    wndItem.append("windows" + n + "_x=" + wndInfo.getWinX() + nl);
                    wndItem.append("windows" + n + "_y=" + wndInfo.getWinY() + nl);
                    wndItem.append("windows" + n + "_w=" + wndInfo.getWinW() + nl);
                    wndItem.append("windows" + n + "_h=" + wndInfo.getWinH() + nl);
                    wndItem.append(pref + "item_no=" + wndInfo.getItems().size() + nl);
                    for(int m = 0; m < wndInfo.getItems().size(); ++m) {
                        StringBuilder playlstItem = new StringBuilder();
                        VmsPubInfo pubInfo = wndInfo.getItems().get(m);
                        playlstItem.append(pref + "item" + m + "=500,1,0,");
                        if(wndInfo.getType() == 0) { //图片
                            String xyPic = String.format("%03d%03d", (wndInfo.getWinW() - picSize) / 2, 0);
                            playlstItem.append("\\C" + xyPic);
                            playlstItem.append("\\P" + picCvt(InfoBoardConfig.SANSI_PLIST_MULTI, pubInfo.getPicId()));
                        }
                        else
                        {
                            //text <br>替换为\n
                            String[] splitText = pubInfo.getContent().split("<br>");
                            //计算文字的xy坐标，x按最长文字算，y按行数
                            int xPos = 0, yPos = 0;
                            int fontSize = Integer.parseInt(pubInfo.getTextSize());
                            if(splitText.length > 0) {
                                String splitTemp = splitText[0];
                                for(int idx = 1; idx < splitText.length; ++idx) {
                                    if(splitTemp.length() > splitText[idx].length()) splitTemp = splitText[idx];
                                }
                                int textXLen = splitTemp.length() * fontSize;
                                int textYLen = splitText.length * fontSize;
                                if(textXLen > wndInfo.getWinW()) {
                                    xPos = 0;
                                } else {
                                    xPos = (wndInfo.getWinW() - textXLen) / 2;
                                }
                                if(textYLen > wndInfo.getWinH()) {
                                    yPos = 0;
                                } else {
                                    yPos = (wndInfo.getWinH() - textYLen) / 2;
                                }
                            }
                            String xy = String.format("%03d%03d", xPos,yPos);
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
        byte[] crcBytes = new byte[ret.length-4];
        System.arraycopy(ret, 1, crcBytes, 0, crcBytes.length);
        byte[] crc2 = ByteUtils.GetCrc(crcBytes, crcBytes.length);
        System.out.println("长度：" + crc2.length + "，发送报文：" + ByteUtils.bytes2hex(crc2));
        ret[pos++] = crc2[0];
        ret[pos++] = crc2[1];

        ret[pos] = 0x03;

        //转义
        byte[] sendPkg = ByteUtils.TransPkg(ret);
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
        } else if(protocol == InfoBoardConfig.SANSI_PLIST_MULTI) {
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
        } else if(protocol == InfoBoardConfig.SANSI_PLIST_MULTI) {
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
        } else if(protocol == InfoBoardConfig.SANSI_PLIST_MULTI) {
            switch (picId) {
                case "叉": picCode = "z00"; break;
                case "上箭头": picCode = "z01"; break;
                case "右上箭头": picCode = "z02"; break;
                case "左上箭头": picCode = "z03"; break;
                default: picCode = picId;
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
