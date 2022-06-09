package com.ruoyi.common.core.utils.snmp;

import com.ruoyi.common.core.utils.snmp.config.ServerSnmpConfig;
import lombok.extern.slf4j.Slf4j;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TableEvent;
import org.snmp4j.util.TableUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

/**
 * @author
 * @time 2022/4/14 12:45
 */
@Slf4j
public final class SnmpUtil {
    private static Snmp snmp = null;
    private CommunityTarget target;

    @SuppressWarnings("squid:S3010")
    public SnmpUtil(String intranetDeviceIp, Integer snmpPort) throws IOException {

        if (snmp == null) {
            snmp = new Snmp(new DefaultUdpTransportMapping());
            snmp.listen();
        }
        //初始化CommunityTarget
        target = new CommunityTarget();
        target.setCommunity(new OctetString("public"));
        target.setVersion(SnmpConstants.version2c);
        target.setAddress(new UdpAddress(intranetDeviceIp + "/" + snmpPort));
        target.setTimeout(1000);
        target.setRetries(1);
    }

    private ResponseEvent snmpGet(String oid) {
        PDU pdu = new PDU();
        pdu.addOID(new VariableBinding(new OID(oid)));
        ResponseEvent re = null;
        try {
            re = snmp.get(pdu, target);
        } catch (Exception e) {
            log.error("snmpGet 异常" + e.getMessage());
        }
        return re;
    }

    private List<TableEvent> snmpWalk(String oid) {
        TableUtils utils = new TableUtils(snmp, new DefaultPDUFactory(PDU.GETBULK));
        OID[] columnOid = new OID[]{new OID(oid)};
        return utils.getTable(target, columnOid, null, null);
    }

    /**
     * 获取cpu负载
     *
     * @return
     */
    public BigDecimal getCpuUsage() {
        List<TableEvent> list = snmpWalk(ServerSnmpConfig.SNMPWALK_HRPROCESSLOAD);
        BigDecimal usage = new BigDecimal("0");
        for (TableEvent tableEvent : list) {
            try {
                String s = tableEvent.toString().split("=")[3].split("]")[0].trim();
                if (!"-4,exception".equals(s)) {
                    usage = usage.add(new BigDecimal(tableEvent.toString().split("=")[3].split("]")[0].trim()));
                } else {
                    return new BigDecimal("-1");
                }
            } catch (Exception e) {
                log.error("获取cpu负载失败" + e.getMessage());
                return new BigDecimal("-1");
            }
        }
        usage = usage.divide(new BigDecimal(list.size()), 2, BigDecimal.ROUND_HALF_UP);
        return usage;
    }

    /**
     * 获取cpu使用率
     *
     * @return
     */
    public BigDecimal getCpu() {
        BigDecimal totalSize;
        ResponseEvent responseEvent = snmpGet(ServerSnmpConfig.SNMPWALK_SSCPUIDLE);
        if (responseEvent != null && responseEvent.getResponse() != null) {
            totalSize = new BigDecimal(responseEvent.getResponse().toString().split("=")[4].split("]")[0].trim());
        } else {
            return new BigDecimal("-1");
        }
        return new BigDecimal("100").subtract(totalSize);
    }

    /**
     * 获取磁盘占用率
     *
     * @return
     */
    public BigDecimal getDiskUsageForWindows() {
        BigDecimal multiply = new BigDecimal("-1");
        try {
            BigDecimal diskSize = getDiskSize();
            BigDecimal diskUsed = getDiskUsed();
            if (BigDecimal.ZERO.compareTo(diskSize) < 0 && BigDecimal.ZERO.compareTo(diskUsed) < 0) {
                multiply = getDiskUsed().divide(getDiskSize(), 3, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
            }
        } catch (Exception e) {
            log.error("获取windows磁盘占用异常" + e.getMessage());
        }

        return multiply;
    }

    /**
     * 获取磁盘占用率
     *
     * @return
     */
    public BigDecimal getDiskUsage() {
        BigDecimal totalSize = null;
        //org.snmp4j.util.TableEvent[index=null,vbs=null,status=-1,exception=null,report=null]
        List<TableEvent> tableEvents = snmpWalk(ServerSnmpConfig.SNMPWALK_DSKPERCENT);
        if (tableEvents.isEmpty()) {
            totalSize = getDiskUsageForWindows();
            return totalSize;
        }
        for (int i = 0; i < tableEvents.size(); i++) {
            if ("-1,exception".equals(tableEvents.get(i).toString().split("=")[3].split("]")[0].trim())) {
                return new BigDecimal("-1");
            }
            String s = tableEvents.get(i).toString().split("=")[3].split("]")[0].trim();
            if (!"-4,exception".equals(s)) {
                totalSize = new BigDecimal(tableEvents.get(i).toString().split("=")[3].split("]")[0].trim());
            } else {
                return new BigDecimal("-1");
            }
        }

        return totalSize;
    }

    /**
     * 获取内存使用率
     *
     * @return
     */
    public BigDecimal getMemoryUsage() {
        BigDecimal usage;
        BigDecimal totalSize;
        try {
            ResponseEvent event = snmpGet(ServerSnmpConfig.SNMPGET_HRMEMORYSIZE);
            if (event != null && event.getResponse() != null) {
                totalSize = new BigDecimal(event.getResponse().toString().split("=")[4].split("]")[0].trim());
                usage = getMemoryUsed();
                return usage.multiply(new BigDecimal("100")).divide(totalSize, 0, BigDecimal.ROUND_HALF_UP);
            } else {
                return new BigDecimal("-1");
            }
        } catch (Exception e) {
            log.error("获取内存使用率失败" + e.getMessage());
            return new BigDecimal("-1");
        }
    }


    /**
     * 获取内存已使用
     *
     * @return
     */
    public BigDecimal getMemoryUsed() {
        List<TableEvent> list = snmpWalk(ServerSnmpConfig.SNMPWALK_HRSTORAGEDESCR);
        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            String trim = list.get(i).toString().split("=")[3].split("]")[0].trim();
            if (trim.contains("Physical memory")) {
                //linux系统
                index = i;
                List<TableEvent> usedList = snmpWalk(ServerSnmpConfig.SNMPWALK_HRSTORAGEUSED);
                return new BigDecimal(usedList.get(index).toString().split("=")[3].split("]")[0].trim());
            } else if (trim.contains("Physical Memory")) {
                //windows系统
                List<TableEvent> list1 = snmpWalk(ServerSnmpConfig.SNMPWALK_MEMORY_WIN);
                BigDecimal re = new BigDecimal("0");
                for (int j = 0; j < list1.size(); j++) {
                    String trim1 = list1.get(j).toString().split("=")[3].split("]")[0].trim();
                    BigDecimal b = new BigDecimal(trim1);
                    re = re.add(b);
                }
                return re;
            }
        }
        return new BigDecimal("0");

    }

    /**
     * 获取磁盘
     *
     * @return
     */
    public BigDecimal getDiskSize() {
        List<TableEvent> list = snmpWalk(ServerSnmpConfig.SNMPWALK_HRSTORAGEDESCR);
        BigDecimal diskSize = new BigDecimal(-1);
        if (!list.isEmpty()) {
            diskSize = new BigDecimal("0");
            List<TableEvent> usedList1 = snmpWalk(ServerSnmpConfig.SNMPWALK_AHU);
            List<TableEvent> usedList2 = snmpWalk(ServerSnmpConfig.SNMPWALK_AUR);
            for (int i = 0; i < list.size(); i++) {
                boolean re = list.get(i).toString().split("=")[3].split("]")[0].trim().contains(":") || list.get(i).toString().split("=")[3].split("]")[0].trim().contains("/");
                if (re) {
                    diskSize = diskSize.add(new BigDecimal(usedList1.get(i).toString().split("=")[3].split("]")[0].trim()).multiply(new BigDecimal(usedList2.get(i).toString().split("=")[3].split("]")[0].trim())).divide(new BigDecimal("1073741824"), 2, BigDecimal.ROUND_HALF_UP));
                }
            }
        }
        return diskSize;
    }

    /**
     * 获取磁盘已使用
     *
     * @return
     */
    public BigDecimal getDiskUsed() {
        List<TableEvent> list = snmpWalk(ServerSnmpConfig.SNMPWALK_HRSTORAGEDESCR);
        BigDecimal diskSize = new BigDecimal(-1);
        if (!list.isEmpty()) {
            diskSize = new BigDecimal("0");
            List<TableEvent> usedList1 = snmpWalk(ServerSnmpConfig.SNMPWALK_AHU);
            List<TableEvent> usedList2 = snmpWalk(ServerSnmpConfig.SNMPWALK_HRSTORAGEUSED);
            for (int i = 0; i < list.size(); i++) {
                boolean re = list.get(i).toString().split("=")[3].split("]")[0].trim().contains(":") || list.get(i).toString().split("=")[3].split("]")[0].trim().contains("/");
                if (re) {
                    diskSize = diskSize.add(new BigDecimal(usedList1.get(i).toString().split("=")[3].split("]")[0].trim()).multiply(new BigDecimal(usedList2.get(i).toString().split("=")[3].split("]")[0].trim())).divide(new BigDecimal("1073741824"), 2, BigDecimal.ROUND_HALF_UP));
                }
            }
        }
        return diskSize;
    }

    /**
     * 获取IO负载
     *
     * @return
     */
    public Float getIOUsage() {
        ResponseEvent event = snmpGet(ServerSnmpConfig.SNMPGET_IOLOAD);
        if (event != null && event.getResponse() != null) {
            float usage = Float.parseFloat(event.getResponse().toString().split("=")[4].split("]")[0].trim());
            if (usage > 1) {
                return usage;
            } else {
                return usage * 100;
            }
        }
        return null;
    }

    /**
     * 获取性某性能参数数据
     *
     * @param parameterId
     * @return
     */
    public Object getData(Integer parameterId) {
        Object data = null;
        try {
            if (parameterId.equals(ServerSnmpConfig.PERFORMANCE_PARAM_CPUUSAGE)) {
                data = getCpuUsage();
            } else if (parameterId.equals(ServerSnmpConfig.PERFORMANCE_PARAM_MEMORYUSAGE)) {
                data = getMemoryUsage();
            } else if (parameterId.equals(ServerSnmpConfig.PERFORMANCE_PARAM_IOUSAGE)) {
                data = getIOUsage();
            }
        } catch (Exception e) {
            log.error("获取性能参数异常" + e.getMessage());
        }
        return data;
    }

    /**
     * 获取设备物理地址
     *
     * @return
     */
    public String getMacAddress() {
        try {
            List<TableEvent> list = snmpWalk(ServerSnmpConfig.SNMPWALK_IFDESCR);
            int index = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).toString().split("=")[3].split("]")[0].trim().contains("eth")) {
                    index = i;
                }
            }
            List<TableEvent> ifAddressList = snmpWalk(ServerSnmpConfig.SNMPWALK_IFPHYSADDRESS);
            return ifAddressList.get(index).toString().split("=")[3].split("]")[0].trim();
        } catch (Exception e) {
            log.error("获取物理地址失败" + e.getMessage());
            return "failure";
        }
    }

    /**
     * 获取设备内存大小  单位为GB
     *
     * @return
     */
    public String getMemoryDesc() {
        ResponseEvent event = snmpGet(ServerSnmpConfig.SNMPGET_HRMEMORYSIZE);
        Long bytes = Long.parseLong(event == null ? "0" : event.getResponse().toString().split("=")[4].split("]")[0].trim());
        float gb = bytes / 1024.0f / 1024.0f;
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        return decimalFormat.format(gb) + "GB";
    }

    /**
     * @return
     * @throws IOException 获取cpu描述信息
     */
    public String getCpuDesc() throws IOException {
        ResponseEvent event = snmpGet(ServerSnmpConfig.SNMPGET_CPUDESC);
        return event == null ? "" : event.getResponse().toString().split("=")[4].split("]")[0].trim().split(":")[1].trim();
    }

    /**
     * 获取存储设备大小 (空盘空间不足百分百)
     *
     * @param deviceCode
     * @return
     */
    public BigDecimal getDiskSize(String deviceCode) {

        List<TableEvent> list = snmpWalk(ServerSnmpConfig.SNMPGET_DSKTOTAL);
        BigDecimal diskSize = new BigDecimal(-1);
        if (list != null) {
            try {
                for (TableEvent tableEvent : list) {
                    diskSize = new BigDecimal(tableEvent.toString().split("=")[3].split("]")[0].trim()).divide(ServerSnmpConfig.DEVIDE_NUM).divide(ServerSnmpConfig.DEVIDE_NUM, 2, RoundingMode.HALF_UP);
                }
            } catch (Exception e) {
                log.error("获取磁盘大小失败" + e.getMessage());
            }
        }
        if (BigDecimal.ZERO.compareTo(diskSize) > 0) {
            diskSize = getDiskSize();
        }
        return diskSize;
    }


    /**
     * 获取磁盘描述
     *
     * @param deviceCode
     * @return
     * @throws IOException
     */
    public BigDecimal getDiskDesc(String deviceCode) {
        return getDiskSize(deviceCode).divide(ServerSnmpConfig.DEVIDE_NUM, 2, RoundingMode.HALF_UP).divide(ServerSnmpConfig.DEVIDE_NUM, 2, RoundingMode.HALF_UP);
    }

    /**
     * snmp协议检测
     */
    public boolean snmpCheck() {
        ResponseEvent re = snmpGet(".1.3.6.1.4.1.2021.255.1");
        return re == null || re.getResponse() != null;
    }

    /**
     * 获取机器名
     *
     * @return
     */
    public String getSysName() {
        String name = null;
        try {
            List<TableEvent> list = snmpWalk(ServerSnmpConfig.SNMPWALK_SYSNAME);
            if (list != null) {
                for (TableEvent tableEvent : list) {
                    name = (tableEvent.toString().split("=")[3].split("]")[0].trim());
                }
            }
        } catch (Exception e) {
            log.error("获取机器名失败" + e.getMessage());
        }
        return name;
    }

    /**
     * 获取内存总大小(实际值)
     *
     * @return
     */
    public BigDecimal getMemoryTotalSize() {
        try {
            ResponseEvent event = snmpGet(ServerSnmpConfig.GET_MEMORY);
            if (event != null && event.getResponse() != null) {
                String trim = event.getResponse().toString().split("=")[4].split("]")[0].trim();
                return new BigDecimal(trim).divide(ServerSnmpConfig.DEVIDE_NUM, 2, RoundingMode.HALF_UP).divide(ServerSnmpConfig.DEVIDE_NUM, 2, RoundingMode.HALF_UP);
            } else {
                throw new RuntimeException("获取内存信息失败");
            }
        } catch (Exception e) {
            log.error("获取内存总大小失败" + e.getMessage());
        }
        return new BigDecimal("-1");
    }

    /**
     * 获取系统描述
     *
     * @return
     */
    public String getSysDsc() {
        try {
            ResponseEvent event = snmpGet(ServerSnmpConfig.SYS_DSC);
            if (event != null && event.getResponse() != null) {
                return event.getResponse().toString().split("=")[4].split("]")[0].trim();
            } else {
                throw new RuntimeException("获取系统描述信息失败");
            }
        } catch (Exception e) {
            log.error("获取系统描述信息失败" + e.getMessage());
        }
        return "failure";
    }

    /**
     * 获取接口数
     *
     * @return
     */
    public Integer getIfNumber() {
        try {
            ResponseEvent event = snmpGet(ServerSnmpConfig.IF_NUM);
            if (event != null && event.getResponse() != null) {
                String trim = event.getResponse().toString().split("=")[4].split("]")[0].trim();
                return Integer.parseInt(trim);
            } else {
                throw new RuntimeException("获取接口信息失败");
            }
        } catch (Exception e) {
            log.error("获取接口数量失败" + e.getMessage());
        }
        return -1;
    }

    /**
     * 获取cpu核数(n个cpu有n条数据)
     *
     * @return
     */
    public Integer getCpuNum() {
        List<TableEvent> list = snmpWalk(ServerSnmpConfig.CPU_NUM);
        Integer num = -1;
        if (list != null) {
            num = list.size();
        }
        return num;
    }
}
