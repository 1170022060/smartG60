package com.pingok.datacenter.service.sectorlog.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.domain.sectorlog.*;
import com.pingok.datacenter.domain.sectorlog.vo.SectorLogVo;
import com.pingok.datacenter.mapper.sectorlog.*;
import com.pingok.datacenter.service.sectorlog.ISectorLogService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 扇区日志入库 服务层处理
 *
 * @author ruoyi
 */
@Service
@Slf4j
public class SectorLogServiceImpl implements ISectorLogService {

    @Autowired
    private TblSectorLogMapper tblSectorLogMapper;

    @Autowired
    private TblCpcEf02LogMapper tblCpcEf02LogMapper;

    @Autowired
    private TblCpcEf04LogMapper tblCpcEf04LogMapper;

    @Autowired
    private TblCpu0015LogMapper tblCpu0015LogMapper;

    @Autowired
    private TblCpu0019LogMapper tblCpu0019LogMapper;

    @Autowired
    private TblObuEf01LogMapper tblObuEf01LogMapper;

    @Autowired
    private TblObuEf04LogMapper tblObuEf04LogMapper;

    @Autowired
    private TblObuEf04LogProvMapper tblObuEf04LogProvMapper;

    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public void getSectorLog(String laneHex, String gid) {
        Map<String, Object> body = new HashMap<>();
        body.put("LaneHex", laneHex);
        body.put("Gid", gid);
        String r = HttpUtil.post("10.131.155.7:8220/api/Lane/QueryReadRecord", JSON.toJSONString(body));
        R ret = null;
        if (!StringUtils.isEmpty(r)) {
            ret = JSON.parseObject(r, R.class);
        }
        if(ret.getCode()==0)
        {
            JSONObject obj= (JSONObject) ret.getData();
            SectorLogVo sectorLogVo=new SectorLogVo();
            sectorLogVo.setGid(obj.getString("Gid"));
            sectorLogVo.setPassType(obj.getInteger("PassType"));
            sectorLogVo.setTransTime(obj.getDate("TransTime"));
            sectorLogVo.setObuVehicleInfo(obj.getString("ObuVehicleInfo"));
            sectorLogVo.setFile0015(obj.getString("File0015"));
            sectorLogVo.setFile0019(obj.getString("File0019"));
            sectorLogVo.setEf04(obj.getString("EF04"));
            sectorLogVo.setEf02(obj.getString("EF02"));
            String rs = HttpUtil.post("localhost:9306/sectorLog", JSON.toJSONString(sectorLogVo));
            R rets;
            if (!StringUtils.isEmpty(rs)) {
                rets = JSON.parseObject(rs, R.class);
                if (rets.getCode() == R.FAIL) {
                    log.error("车道日志上传失败，错误" + rets.getMsg());
                }
            } else {
                log.error("车道日志上传状态未知");
            }
        }
    }

    @Override
    public long insertSectorLog(SectorLogVo sectorLogVo) {
        TblSectorLog tblSectorLog = new TblSectorLog();
        BeanUtils.copyNotNullProperties(sectorLogVo,tblSectorLog);
        tblSectorLog.setId(remoteIdProducerService.nextId());
        tblSectorLogMapper.insert(tblSectorLog);
        return tblSectorLog.getId();
    }

    @Override
    public int insertCpcEf02Log(Long logId, String ef02) {
        TblCpcEf02Log tblCpcEf02Log = new TblCpcEf02Log();
        tblCpcEf02Log.setId(remoteIdProducerService.nextId());
        tblCpcEf02Log.setLogId(logId);
        String proCount = ef02.substring(0, 2);
        tblCpcEf02Log.setProCount(bytes2int(hexString2Bytes(proCount)));
        String gantryCount = ef02.substring(2, 4);
        tblCpcEf02Log.setGantryCount(bytes2int(hexString2Bytes(gantryCount)));
        String fee = ef02.substring(4, 10);
        tblCpcEf02Log.setFee(bytes2int(hexString2Bytes(fee)));
        String mileage = ef02.substring(10, 16);
        tblCpcEf02Log.setMileage(bytes2int(hexString2Bytes(mileage)));
        tblCpcEf02Log.setEnEtcGantryHex(ef02.substring(16, 22));
        if (ef02.substring(22, 30) != "00000000") {
            tblCpcEf02Log.setEnTime(format(bytes2long(hexString2Bytes(ef02.substring(22, 30)))));
        }
        tblCpcEf02Log.setLastEtcGantryHex(ef02.substring(30, 36));
        if (ef02.substring(36, 44) != "00000000") {
            tblCpcEf02Log.setLastPassTime(format(bytes2long(hexString2Bytes(ef02.substring(36, 44)))));
        }
        String lastFee = ef02.substring(44, 50);
        tblCpcEf02Log.setLastFee(bytes2int(hexString2Bytes(lastFee)));
        String lastMileage = ef02.substring(50, 56);
        tblCpcEf02Log.setLastMileage(bytes2int(hexString2Bytes(lastMileage)));
        String etcGantryCount = ef02.substring(56, 58);
        tblCpcEf02Log.setEtcGantryCount(bytes2int(hexString2Bytes(etcGantryCount)));
        int i = 58;
        while (ef02.length() - i > 0) {
            tblCpcEf02Log.setEtcGantryRecords(tblCpcEf02Log.getEtcGantryRecords() + '|' + ef02.substring(58, i += 6));
        }
        return tblCpcEf02LogMapper.insert(tblCpcEf02Log);
    }

    @Override
    public int insertCpcEf04Log(Long logId, String ef04) {
        TblCpcEf04Log tblCpcEf04Log = new TblCpcEf04Log();
        tblCpcEf04Log.setId(remoteIdProducerService.nextId());
        tblCpcEf04Log.setLogId(logId);
        String provinceId = ef04.substring(0, 2);
        tblCpcEf04Log.setProvinceId(String.valueOf(bytes2int(hexString2Bytes(provinceId))));
        String gantryCount = ef04.substring(2, 4);
        tblCpcEf04Log.setGantryCount(bytes2int(hexString2Bytes(gantryCount)));
        String fee = ef04.substring(4, 10);
        tblCpcEf04Log.setFee(bytes2int(hexString2Bytes(fee)));
        String mileage = ef04.substring(10, 16);
        tblCpcEf04Log.setMileage(bytes2int(hexString2Bytes(mileage)));
        tblCpcEf04Log.setEnEtcGantryHex(ef04.substring(16, 22));
        if (ef04.substring(22, 30) != "00000000") {
            tblCpcEf04Log.setEnTime(format(bytes2long(hexString2Bytes(ef04.substring(22, 30)))));
        }
        tblCpcEf04Log.setLastEtcGantryHex(ef04.substring(30, 36));
        if (ef04.substring(36, 44) != "00000000") {
            tblCpcEf04Log.setLastPassTime(format(bytes2long(hexString2Bytes(ef04.substring(36, 44)))));
        }
        String fitFlag = ef04.substring(44, 46);
        tblCpcEf04Log.setFitFlag(bytes2int(hexString2Bytes(fitFlag)));
        return tblCpcEf04LogMapper.insert(tblCpcEf04Log);
    }

    @Override
    public int insertCpu0015Log(Long logId, String file0015) {
        TblCpu0015Log tblCpu0015Log = new TblCpu0015Log();
        tblCpu0015Log.setId(remoteIdProducerService.nextId());
        tblCpu0015Log.setLogId(logId);
        tblCpu0015Log.setIssuerIdentity(file0015.substring(0, 16));
        String cardType = file0015.substring(16, 18);
        tblCpu0015Log.setCardType(bytes2int(hexString2Bytes(cardType)));
        String cardVersion = file0015.substring(18, 20);
        tblCpu0015Log.setCardVersion(bytes2int(hexString2Bytes(cardVersion)));
        tblCpu0015Log.setCardNet(file0015.substring(20, 24));
        tblCpu0015Log.setCardId(file0015.substring(24, 40));
        tblCpu0015Log.setEnableTime(file0015.substring(40, 48));
        tblCpu0015Log.setExpirationTime(file0015.substring(48, 56));
        String vehPlate = file0015.substring(56, 80);
        tblCpu0015Log.setVehPlate(hexString2String(vehPlate, "GBK", "UTF-8"));
        String userType = file0015.substring(80, 82);
        tblCpu0015Log.setUserType(bytes2int(hexString2Bytes(userType)));
        String vehColor = file0015.substring(82, 84);
        tblCpu0015Log.setVehColor(bytes2int(hexString2Bytes(vehColor)));
        String vehClass = file0015.substring(84, 86);
        tblCpu0015Log.setVehClass(bytes2int(hexString2Bytes(vehClass)));
        return tblCpu0015LogMapper.insert(tblCpu0015Log);
    }

    @Override
    public int insertCpu0019Log(Long logId, String file0019) {
        TblCpu0019Log tblCpu0019Log = new TblCpu0019Log();
        tblCpu0019Log.setId(remoteIdProducerService.nextId());
        tblCpu0019Log.setLogId(logId);
        String consumeSign = file0019.substring(0, 2);
        tblCpu0019Log.setConsumeSign(bytes2int(hexString2Bytes(consumeSign)));
        String length = file0019.substring(2, 4);
        tblCpu0019Log.setLength(bytes2int(hexString2Bytes(length)));
        String lockSign = file0019.substring(4, 6);
        tblCpu0019Log.setLockSign(bytes2int(hexString2Bytes(lockSign)));
        tblCpu0019Log.setNet(file0019.substring(6, 10));
        tblCpu0019Log.setStation(file0019.substring(10, 14));
        tblCpu0019Log.setLane(file0019.substring(14, 16));
        String transTime = file0019.substring(16, 24);
        tblCpu0019Log.setTransTime(format(bytes2long(hexString2Bytes(transTime))));
        String vehClass = file0019.substring(24, 26);
        tblCpu0019Log.setVehClass(bytes2int(hexString2Bytes(vehClass)));
        String transType = file0019.substring(26, 28);
        tblCpu0019Log.setTransType(bytes2int(hexString2Bytes(transType)));
        tblCpu0019Log.setGantryHex(file0019.substring(28, 34));
        String gantryTime = file0019.substring(34, 42);
        tblCpu0019Log.setGantryTime(format(bytes2long(hexString2Bytes(gantryTime))));
        String vehPlate = file0019.substring(42, 66);
        tblCpu0019Log.setVehPlate(hexString2String(vehPlate, "GBK", "UTF-8"));
        String vehColor = file0019.substring(66, 68);
        tblCpu0019Log.setVehColor(bytes2int(hexString2Bytes(vehColor)));
        String truckAxle = file0019.substring(68, 70);
        tblCpu0019Log.setTruckAxle(bytes2int(hexString2Bytes(truckAxle)));
        String truckWeight = file0019.substring(70, 76);
        tblCpu0019Log.setTruckWeight(bytes2int(hexString2Bytes(truckWeight)));
        String vehSign = file0019.substring(76, 78);
        tblCpu0019Log.setVehSign(bytes2int(hexString2Bytes(vehSign)));
        String fee = file0019.substring(78, 86);
        tblCpu0019Log.setFee(bytes2int(hexString2Bytes(fee)));
        return tblCpu0019LogMapper.insert(tblCpu0019Log);
    }

    @Override
    public int insertObuEf01Log(Long logId, String obuVehicleInfo) {
        TblObuEf01Log tblObuEf01Log = new TblObuEf01Log();
        tblObuEf01Log.setId(remoteIdProducerService.nextId());
        tblObuEf01Log.setLogId(logId);
        String vehPlate = obuVehicleInfo.substring(0, 24);
        tblObuEf01Log.setVehPlate(hexString2String(vehPlate, "GBK", "UTF-8"));
        String vehColor = obuVehicleInfo.substring(24, 28);
        tblObuEf01Log.setVehColor(bytes2int(hexString2Bytes(vehColor)));
        String vehClass = obuVehicleInfo.substring(28, 30);
        tblObuEf01Log.setVehClass(bytes2int(hexString2Bytes(vehClass)));
        String vehUserType = obuVehicleInfo.substring(30, 32);
        tblObuEf01Log.setVehUserType(bytes2int(hexString2Bytes(vehUserType)));
        String length = obuVehicleInfo.substring(32, 36);
        String weight = obuVehicleInfo.substring(36, 38);
        String height = obuVehicleInfo.substring(38, 40);
        tblObuEf01Log.setVehLwh(String.valueOf(bytes2int(hexString2Bytes(length))) + ',' + String.valueOf(bytes2int(hexString2Bytes(weight))) + ',' + String.valueOf(bytes2int(hexString2Bytes(height))));
        String wheels = obuVehicleInfo.substring(40, 42);
        tblObuEf01Log.setWheels(bytes2int(hexString2Bytes(wheels)));
        String axle = obuVehicleInfo.substring(42, 44);
        tblObuEf01Log.setAxle(bytes2int(hexString2Bytes(axle)));
        String wheelbase = obuVehicleInfo.substring(44, 48);
        tblObuEf01Log.setWheelbase(bytes2int(hexString2Bytes(wheelbase)));
        String loadOrSeats = obuVehicleInfo.substring(48, 54);
        tblObuEf01Log.setLoadOrSeats(bytes2int(hexString2Bytes(loadOrSeats)));
        return tblObuEf01LogMapper.insert(tblObuEf01Log);
    }

    @Override
    public long insertObuEf04Log(Long logId, String ef04) {
        TblObuEf04Log tblObuEf04Log = new TblObuEf04Log();
        tblObuEf04Log.setId(remoteIdProducerService.nextId());
        tblObuEf04Log.setLogId(logId);
        String tollProvinceCnt = ef04.substring(120, 122);
        tblObuEf04Log.setTollProvinceCnt(bytes2int(hexString2Bytes(tollProvinceCnt)));
        String tollOrgFee = ef04.substring(122, 128);
        tblObuEf04Log.setTollOrgFee(bytes2int(hexString2Bytes(tollOrgFee)));
        String tollFee = ef04.substring(128, 134);
        tblObuEf04Log.setTollFee(bytes2int(hexString2Bytes(tollFee)));
        String tollSuccTimes = ef04.substring(134, 138);
        tblObuEf04Log.setTollSuccTimes(bytes2int(hexString2Bytes(tollSuccTimes)));
        String tollMileage = ef04.substring(138, 144);
        tblObuEf04Log.setTollMileage(bytes2int(hexString2Bytes(tollMileage)));
        String noCardTimes = ef04.substring(144, 146);
        tblObuEf04Log.setNoCardTimes(bytes2int(hexString2Bytes(noCardTimes)));
        tblObuEf04Log.setLocalHex(ef04.substring(146, 152));
        String localOrgFee = ef04.substring(152, 158);
        tblObuEf04Log.setLocalOrgFee(bytes2int(hexString2Bytes(localOrgFee)));
        String localSuccTimes = ef04.substring(158, 160);
        tblObuEf04Log.setLocalSuccTimes(bytes2int(hexString2Bytes(localSuccTimes)));
        String localFee = ef04.substring(160, 166);
        tblObuEf04Log.setLocalFee(bytes2int(hexString2Bytes(localFee)));
        tblObuEf04Log.setDigest(ef04.substring(166, 182));
        tblObuEf04LogMapper.insert(tblObuEf04Log);
        return tblObuEf04Log.getId();
    }

    @Override
    public int insertObuEf04LogProv(Long logId, String province) {
        int i = 0;
        int result = 0;
        while ((province.length() - i) > 0) {
            TblObuEf04LogProv tblObuEf04LogProv = new TblObuEf04LogProv();
            tblObuEf04LogProv.setId(remoteIdProducerService.nextId());
            tblObuEf04LogProv.setLogId(logId);
            tblObuEf04LogProv.setProvId(province.substring(i, i += 2));
            String provFee = province.substring(i, i += 6);
            tblObuEf04LogProv.setProvFee(bytes2int(hexString2Bytes(provFee)));
            tblObuEf04LogProvMapper.insert(tblObuEf04LogProv);
            result++;
        }
        return result;
    }

    @Override
    public int checkGidUnique(String gid) {
        return tblSectorLogMapper.checkFieldNameUnique(gid);
    }

    public static byte[] hexString2Bytes(String src) {
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            ret[i] = Integer.valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return ret;
    }

    public static String hexString2String(String src, String oldchartype, String chartype) {
        try {
            byte[] bts = hexString2Bytes(src);
            if (oldchartype.equals(chartype))
                return new String(bts, oldchartype);
            else
                return new String(new String(bts, oldchartype).getBytes(), chartype);
        } catch (Exception e) {
            return src;
        }
    }

    public static int bytes2int(byte[] bytes) {
        if (bytes.length == 0)
            return 0;
        int result = (bytes[0] & 0xff) << (8 * (bytes.length - 1));
        if (bytes.length > 1)
            for (int i = 1; i < bytes.length; i++) {
                result = result | (bytes[i] & 0xff) << (8 * (bytes.length - 1 - i));
            }
        return result;
    }

    public static long bytes2long(byte[] bytes) {
        if (bytes.length == 0)
            return 0;
        long result = (bytes[0] & 0xff) << (8 * (bytes.length - 1));
        if (bytes.length > 1)
            for (int i = 1; i < bytes.length; i++) {
                result = result | (bytes[i] & 0xff) << (8 * (bytes.length - 1 - i));
            }
        return result;
    }

    public static Date format(long time) {
        Date date = new Date();
        date.setTime(time * 1000L);
        return date;
    }
}
