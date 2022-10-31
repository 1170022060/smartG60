package com.pingok.datacenter.service.roster.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.domain.roster.recovery.TblRecoveryListRecord;
import com.pingok.datacenter.domain.roster.recovery.TblRecoveryListRecordLog;
import com.pingok.datacenter.domain.roster.recovery.TblRecoveryStationUsed;
import com.pingok.datacenter.domain.roster.recovery.TblRecoveryVersion;
import com.pingok.datacenter.domain.roster.recovery.vo.RecoveryVo;
import com.pingok.datacenter.domain.roster.vo.VersionVo;
import com.pingok.datacenter.mapper.roster.recovery.TblRecoveryListRecordLogMapper;
import com.pingok.datacenter.mapper.roster.recovery.TblRecoveryListRecordMapper;
import com.pingok.datacenter.mapper.roster.recovery.TblRecoveryStationUsedMapper;
import com.pingok.datacenter.mapper.roster.recovery.TblRecoveryVersionMapper;
import com.pingok.datacenter.service.roster.IRecoveryService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static com.pingok.datacenter.service.roster.impl.BlackCardServiceImpl.backMD5;
import static com.pingok.datacenter.service.roster.impl.BlackCardServiceImpl.delFolder;

/**
 * 追缴名单 服务层处理
 *
 * @author ruoyi
 */
@Service
public class RecoveryServiceImpl implements IRecoveryService {

    @Autowired
    private TblRecoveryListRecordMapper tblRecoveryListRecordMapper;
    @Autowired
    private TblRecoveryListRecordLogMapper tblRecoveryListRecordLogMapper;
    @Autowired
    private TblRecoveryStationUsedMapper tblRecoveryStationUsedMapper;
    @Autowired
    private TblRecoveryVersionMapper tblRecoveryVersionMapper;

    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Value("${center.host}")
    private String host;

    @Value("${center.stationGB}")
    private String stationGB;

    @Value("${center.recoveryPath}")
    private String recoveryPath;

    @Override
    public void recovery(JSONObject obj) {
        Example example = new Example(TblRecoveryStationUsed.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("version", obj.getString("version"));
        criteria.andEqualTo("stationHex", obj.getString("stationHex"));
        TblRecoveryStationUsed stationUsed = tblRecoveryStationUsedMapper.selectOneByExample(example);
        if (StringUtils.isNull(stationUsed)) {
            stationUsed = new TblRecoveryStationUsed();
            stationUsed.setId(remoteIdProducerService.nextId());
            stationUsed.setStationHex(obj.getString("stationHex"));
            stationUsed.setApplyTime(obj.getDate("applyTime"));
            stationUsed.setCreateTime(DateUtils.getNowDate());
            stationUsed.setVersion(obj.getString("version"));
            tblRecoveryStationUsedMapper.insert(stationUsed);
        }
    }

    @Override
    public void increment(String version) {
        String url=host+"/api/lane-service/vehicleblack-incr-list";
        OkHttpClient client = new OkHttpClient();
        VersionVo versionVo=new VersionVo();
        versionVo.setVersion(version);
        String jsonStr = JSON.toJSONString(versionVo);
        RequestBody requestBody =  RequestBody.create(MediaType.parse("application/json"),jsonStr);
        final Request request = new Request.Builder()
                .url(url)
                .addHeader("AuthCode",stationGB)
                .addHeader("Json-Md5",backMD5(jsonStr))
                .post(requestBody)
                .build();
        Call call = client.newCall(request);

        try{
            Response response = call.execute();
            byte[] bytes = response.body().bytes();
            if(bytes.length>0 && response.code()==200)
            {
                String fileName = version + ".zip";
                File file=new File(recoveryPath);
                if(!file.exists()){
                    file.mkdirs();
                }
                String pathName=recoveryPath+"\\"+fileName;
                file  = new File(pathName);
                if(!file.exists()){
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytes,0,bytes.length);
                fos.flush();
                fos.close();
                unzipIncr(pathName,recoveryPath,version);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void all(String version) {
        String url=host+"/api/lane-service/vehicleblack-all-list";
        OkHttpClient client = new OkHttpClient();
        VersionVo versionVo=new VersionVo();
        versionVo.setVersion(version);
        String jsonStr = JSON.toJSONString(versionVo);
        RequestBody requestBody =  RequestBody.create(MediaType.parse("application/json"),jsonStr);
        final Request request = new Request.Builder()
                .url(url)
                .addHeader("AuthCode",stationGB)
                .addHeader("Json-Md5",backMD5(jsonStr))
                .post(requestBody)
                .build();
        Call call = client.newCall(request);

        try{
            Response response = call.execute();
            byte[] bytes = response.body().bytes();
            if(bytes.length>0 && response.code()==200)
            {
                String fileName = version + ".zip";
                File file=new File(recoveryPath+"_all");
                if(!file.exists()){
                    file.mkdirs();
                }
                String pathName=recoveryPath+"_all"+"\\"+fileName;
                file  = new File(pathName);
                if(!file.exists()){
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytes,0,bytes.length);
                fos.flush();
                fos.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void unzipIncr(String zipPath,String resourcePath,String version){
        //判断生成目录是否生成，如果没有就创建
        File pathFile=new File(resourcePath);
        if(!pathFile.exists()){
            pathFile.mkdirs();
        }
        ZipFile zp=null;
        try{
            //指定编码，否则压缩包里面不能有中文目录
            zp=new ZipFile(zipPath, Charset.forName("gbk"));
            //遍历里面的文件及文件夹
            Enumeration entries=zp.entries();
            while(entries.hasMoreElements()){
                ZipEntry entry= (ZipEntry) entries.nextElement();
                String zipEntryName=entry.getName();
                InputStream in=zp.getInputStream(entry);
                String outpath=(resourcePath+"\\"+zipEntryName).replace("/",File.separator);
                //判断路径是否存在，不存在则创建文件路径
                File file = new  File(outpath.substring(0,outpath.lastIndexOf(File.separator)));
                if(!file.exists()){
                    file.mkdirs();
                }
                //判断文件全路径是否为文件夹,如果是,不需要解压
                if(new File(outpath).isDirectory())
                    continue;
                OutputStream out=new FileOutputStream(outpath);
                byte[] bf=new byte[2048];
                int len;
                while ((len=in.read(bf))>0){
                    out.write(bf,0,len);
                }
                in.close();
                out.close();
                if(zipEntryName.contains(".zip"))
                {
                    unzipIncr(outpath,resourcePath,version);
                }
                if(zipEntryName.contains("data.json"))
                {
                    List<RecoveryVo> list = jsonAnalysis(outpath);
                    insertIncr(list,version);
                }
            }
            zp.close();
        }catch ( Exception e){
            e.printStackTrace();
        }
        delFolder(resourcePath);
    }

    @Override
    public void unzipAll(String version) {
        String resourcePath = recoveryPath+"_all";
        ZipFile zp=null;//要遍历的路径
        File files = new File(resourcePath);		//获取其file对象
        String[] fileNameLists = files.list();	//遍历path下的文件和目录，放在File数组中
        for(String zipName:fileNameLists){					//遍历File[]数组
            if(zipName.equals(version + ".zip"))
            {
                try {
                    //指定编码，否则压缩包里面不能有中文目录
                    zp = new ZipFile(resourcePath+"\\"+zipName, Charset.forName("gbk"));
                    //遍历里面的文件及文件夹
                    Enumeration entries = zp.entries();
                    while (entries.hasMoreElements()) {
                        ZipEntry entry = (ZipEntry) entries.nextElement();
                        String zipEntryName = entry.getName();
                        InputStream in = zp.getInputStream(entry);
                        String outpath = (resourcePath+"\\"+ zipEntryName).replace("/", File.separator);
                        File fileDelete = new File(outpath);
                        //判断路径是否存在，不存在则创建文件路径
                        File file = new File(outpath.substring(0, outpath.lastIndexOf(File.separator)));
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        //判断文件全路径是否为文件夹,如果是,不需要解压
                        if (new File(outpath).isDirectory())
                            continue;
                        OutputStream out = new FileOutputStream(outpath);
                        byte[] bf = new byte[2048];
                        int len;
                        while ((len = in.read(bf)) > 0) {
                            out.write(bf, 0, len);
                        }
                        in.close();
                        out.close();
                        if (zipEntryName.contains(".json")) {
                            List<RecoveryVo> list = jsonAnalysis(outpath);
                            insertAll(list,version);
                            fileDelete.delete();
                        }
                    }
                    zp.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void insertIncr(List<RecoveryVo> list, String version) {
        TblRecoveryListRecord recoveryListRecord;
        TblRecoveryListRecordLog recoveryListRecordLog;
        TblRecoveryVersion recoveryVersion;
        TblRecoveryVersion recoveryVersion2;

        Example example = new Example(TblRecoveryVersion.class);
        example.createCriteria().andEqualTo("version", version);
        recoveryVersion = tblRecoveryVersionMapper.selectOneByExample(example);
        if (StringUtils.isNull(recoveryVersion)) {
            recoveryVersion=new TblRecoveryVersion();
            recoveryVersion.setId(remoteIdProducerService.nextId());
            recoveryVersion.setVersion(version);
        }
        example = new Example(TblRecoveryListRecord.class);
        for (RecoveryVo recoveryVo : list) {
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("vehPlate", recoveryVo.getVehicleId().substring(0, recoveryVo.getVehicleId().indexOf("_")));
            criteria.andEqualTo("vehColor", Integer.valueOf(recoveryVo.getVehicleId().substring(recoveryVo.getVehicleId().substring(0, recoveryVo.getVehicleId().indexOf("_")).length() + 1)));
            recoveryListRecord = tblRecoveryListRecordMapper.selectOneByExample(example);
            if (StringUtils.isNull(recoveryListRecord)) {
                recoveryListRecord = new TblRecoveryListRecord();
                recoveryListRecord.setId(remoteIdProducerService.nextId());
                BeanUtils.copyNotNullProperties(recoveryVo, recoveryListRecord);
                recoveryListRecord.setVersionId(recoveryVersion.getId());
                recoveryListRecord.setVehPlate(recoveryVo.getVehicleId().substring(0, recoveryVo.getVehicleId().indexOf("_")));
                recoveryListRecord.setVehColor(Integer.valueOf(recoveryVo.getVehicleId().substring(recoveryVo.getVehicleId().substring(0, recoveryVo.getVehicleId().indexOf("_")).length() + 1)));
                recoveryListRecord.setCount(recoveryVo.getEvasionCount());
                recoveryListRecord.setStatus(recoveryVo.getOweFee());
                recoveryListRecord.setUpdateTime(DateUtils.getNowDate());
                tblRecoveryListRecordMapper.insert(recoveryListRecord);

                recoveryListRecordLog = new TblRecoveryListRecordLog();
                BeanUtils.copyNotNullProperties(recoveryListRecord, recoveryListRecordLog);
                recoveryListRecordLog.setId(remoteIdProducerService.nextId());
                tblRecoveryListRecordLogMapper.insert(recoveryListRecordLog);
            } else {
                Example example2=new Example(TblRecoveryVersion.class);
                example2.createCriteria().andEqualTo("id", recoveryListRecord.getVersionId());
                recoveryVersion2 = tblRecoveryVersionMapper.selectOneByExample(example2);

                if (Long.parseLong(recoveryVersion2.getVersion()) < Long.parseLong(version)) {
                    BeanUtils.copyNotNullProperties(recoveryVo, recoveryListRecord);
                    recoveryListRecord.setCount(recoveryVo.getEvasionCount());
                    recoveryListRecord.setStatus(recoveryVo.getOweFee());
                    recoveryListRecord.setVersionId(recoveryVersion.getId());
                    recoveryListRecord.setUpdateTime(DateUtils.getNowDate());
                    tblRecoveryListRecordMapper.updateByPrimaryKey(recoveryListRecord);

                    recoveryListRecordLog = new TblRecoveryListRecordLog();
                    BeanUtils.copyNotNullProperties(recoveryListRecord, recoveryListRecordLog);
                    recoveryListRecordLog.setId(remoteIdProducerService.nextId());
                    tblRecoveryListRecordLogMapper.insert(recoveryListRecordLog);
                }
            }
        }
    }

    public void insertAll(List<RecoveryVo> list, String version) {
        TblRecoveryListRecord recoveryListRecord;
        TblRecoveryVersion recoveryVersion;
        TblRecoveryVersion recoveryVersion2;

        Example example = new Example(TblRecoveryVersion.class);
        example.createCriteria().andEqualTo("version", version);
        recoveryVersion = tblRecoveryVersionMapper.selectOneByExample(example);
        if (StringUtils.isNull(recoveryVersion)) {
            recoveryVersion=new TblRecoveryVersion();
            recoveryVersion.setId(remoteIdProducerService.nextId());
            recoveryVersion.setVersion(version);
        }
        example = new Example(TblRecoveryListRecord.class);
        for (RecoveryVo recoveryVo : list) {
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("vehPlate", recoveryVo.getVehicleId().substring(0, recoveryVo.getVehicleId().indexOf("_")));
            criteria.andEqualTo("vehColor", Integer.valueOf(recoveryVo.getVehicleId().substring(recoveryVo.getVehicleId().substring(0, recoveryVo.getVehicleId().indexOf("_")).length() + 1)));
            recoveryListRecord = tblRecoveryListRecordMapper.selectOneByExample(example);
            if (StringUtils.isNull(recoveryListRecord)) {
                recoveryListRecord = new TblRecoveryListRecord();
                recoveryListRecord.setId(remoteIdProducerService.nextId());
                BeanUtils.copyNotNullProperties(recoveryVo, recoveryListRecord);
                recoveryListRecord.setVersionId(recoveryVersion.getId());
                recoveryListRecord.setVehPlate(recoveryVo.getVehicleId().substring(0, recoveryVo.getVehicleId().indexOf("_")));
                recoveryListRecord.setVehColor(Integer.valueOf(recoveryVo.getVehicleId().substring(recoveryVo.getVehicleId().substring(0, recoveryVo.getVehicleId().indexOf("_")).length() + 1)));
                recoveryListRecord.setCount(recoveryVo.getEvasionCount());
                recoveryListRecord.setStatus(recoveryVo.getOweFee());
                recoveryListRecord.setUpdateTime(DateUtils.getNowDate());
                tblRecoveryListRecordMapper.insert(recoveryListRecord);
            } else {
                Example example2=new Example(TblRecoveryVersion.class);
                example2.createCriteria().andEqualTo("id", recoveryListRecord.getVersionId());
                recoveryVersion2 = tblRecoveryVersionMapper.selectOneByExample(example2);

                if (Long.parseLong(recoveryVersion2.getVersion()) < Long.parseLong(version)) {
                    BeanUtils.copyNotNullProperties(recoveryVo, recoveryListRecord);
                    recoveryListRecord.setCount(recoveryVo.getEvasionCount());
                    recoveryListRecord.setStatus(recoveryVo.getOweFee());
                    recoveryListRecord.setVersionId(recoveryVersion.getId());
                    recoveryListRecord.setUpdateTime(DateUtils.getNowDate());
                    tblRecoveryListRecordMapper.updateByPrimaryKey(recoveryListRecord);
                }
            }
        }
    }

    public static List<RecoveryVo> jsonAnalysis(String jsonPath){

        String jsonStr = "";
        try {
            FileReader fileReader = new FileReader(jsonPath);
            Reader reader = new InputStreamReader(new FileInputStream(jsonPath), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            List<RecoveryVo> listStr = JSON.parseArray(jsonStr, RecoveryVo.class);
            return listStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
