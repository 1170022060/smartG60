package com.pingok.datacenter.service.roster.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.config.CenterConfig;
import com.pingok.datacenter.domain.roster.rescue.TblRescueListRecord;
import com.pingok.datacenter.domain.roster.rescue.TblRescueListRecordLog;
import com.pingok.datacenter.domain.roster.rescue.TblRescueVersion;
import com.pingok.datacenter.domain.roster.rescue.vo.RescueVo;
import com.pingok.datacenter.domain.roster.rescue.TblRescueStationUsed;
import com.pingok.datacenter.domain.roster.vo.VersionVo;
import com.pingok.datacenter.mapper.roster.VersionMapper;
import com.pingok.datacenter.mapper.roster.rescue.TblRescueVersionMapper;
import com.pingok.datacenter.mapper.roster.rescue.TblRescueListRecordLogMapper;
import com.pingok.datacenter.mapper.roster.rescue.TblRescueListRecordMapper;
import com.pingok.datacenter.mapper.roster.rescue.TblRescueStationUsedMapper;
import com.pingok.datacenter.service.roster.IRescueService;
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
import java.text.SimpleDateFormat;
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
public class RescueServiceImpl implements IRescueService {

    @Autowired
    private TblRescueListRecordMapper tblRescueListRecordMapper;
    @Autowired
    private TblRescueListRecordLogMapper tblRescueListRecordLogMapper;
    @Autowired
    private TblRescueStationUsedMapper tblRescueStationUsedMapper;
    @Autowired
    private TblRescueVersionMapper tblRescueVersionMapper;
    @Autowired
    private VersionMapper versionMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;


    @Override
    public void rescue(JSONObject obj) {
        Example example = new Example(TblRescueStationUsed.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("version", obj.getString("version"));
        criteria.andEqualTo("stationHex", obj.getString("stationHex"));
        TblRescueStationUsed stationUsed = tblRescueStationUsedMapper.selectOneByExample(example);
        if (StringUtils.isNull(stationUsed)) {
            stationUsed = new TblRescueStationUsed();
            stationUsed.setId(remoteIdProducerService.nextId());
            stationUsed.setStationHex(obj.getString("stationHex"));
            stationUsed.setApplyTime(obj.getDate("applyTime"));
            stationUsed.setCreateTime(DateUtils.getNowDate());
            stationUsed.setVersion(obj.getString("version"));
            tblRescueStationUsedMapper.insert(stationUsed);
        }
    }

    @Override
    public void increment() {
        String versionNow=versionMapper.selectVersion("TBL_RESCUE_VERSION");
        String version = DateUtils.getTimeMinute(DateUtils.getBeforeMillisEndWithMinute0or5(15,DateUtils.getNowDate()));
        if(StringUtils.isNotNull(versionNow) && (versionNow.equals(version)))
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            try {
                // 注意格式需要与上面一致，不然会出现异常
                version=DateUtils.getTimeMinute(DateUtils.getPreTime(sdf.parse(versionNow) ,15));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String url= CenterConfig.HOST+"/api/lane-service/emerg-incr-list";
        OkHttpClient client = new OkHttpClient();
        VersionVo versionVo=new VersionVo();
        versionVo.setVersion(version);
        String jsonStr = JSON.toJSONString(versionVo);
        RequestBody requestBody =  RequestBody.create(MediaType.parse("application/json"),jsonStr);
        final Request request = new Request.Builder()
                .url(url)
                .addHeader("AuthCode",CenterConfig.STATIONGB)
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
                File file=new File(CenterConfig.RESCUEPATH);
                if(!file.exists()){
                    file.mkdirs();
                }
                String pathName=CenterConfig.RESCUEPATH+"/"+fileName;
                file  = new File(pathName);
                if(!file.exists()){
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytes,0,bytes.length);
                fos.flush();
                fos.close();
                unzipIncr(pathName,CenterConfig.RESCUEPATH,version);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void all(String version) {
        String url=CenterConfig.HOST+"/api/lane-service/emerg-all-list";
        OkHttpClient client = new OkHttpClient();
        VersionVo versionVo=new VersionVo();
        versionVo.setVersion(version);
        String jsonStr = JSON.toJSONString(versionVo);
        RequestBody requestBody =  RequestBody.create(MediaType.parse("application/json"),jsonStr);
        final Request request = new Request.Builder()
                .url(url)
                .addHeader("AuthCode",CenterConfig.STATIONGB)
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
                File file=new File(CenterConfig.RESCUEPATH+"_all");
                if(!file.exists()){
                    file.mkdirs();
                }
                String pathName=CenterConfig.RESCUEPATH+"_all"+"/"+fileName;
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

    @Override
    public void unzipAll(String version) {
        String resourcePath = CenterConfig.RESCUEPATH+"_all";
        ZipFile zp=null;//要遍历的路径
        File files = new File(resourcePath);		//获取其file对象
        String[] fileNameLists = files.list();	//遍历path下的文件和目录，放在File数组中
        for(String zipName:fileNameLists){					//遍历File[]数组
            if(zipName.equals(version + ".zip"))
            {
                try {
                    //指定编码，否则压缩包里面不能有中文目录
                    zp = new ZipFile(resourcePath+"/"+zipName, Charset.forName("gbk"));
                    //遍历里面的文件及文件夹
                    Enumeration entries = zp.entries();
                    while (entries.hasMoreElements()) {
                        ZipEntry entry = (ZipEntry) entries.nextElement();
                        String zipEntryName = entry.getName();
                        InputStream in = zp.getInputStream(entry);
                        String outpath = (resourcePath+"/"+ zipEntryName).replace("/", File.separator);
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
                            List<RescueVo> list = jsonAnalysis(outpath);
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
                String outpath=(resourcePath+"/"+zipEntryName).replace("/",File.separator);
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
                if(zipEntryName.contains("json"))
                {
                    List<RescueVo> list = jsonAnalysis(outpath);
                    insertIncr(list,version);
                }
            }
            zp.close();
        }catch ( Exception e){
            e.printStackTrace();
        }
        delFolder(resourcePath);
    }

    public void insertIncr(List<RescueVo> list, String version) {
        TblRescueListRecord rescueListRecord;
        TblRescueListRecordLog rescueListRecordLog;
        TblRescueVersion rescueVersion;
        TblRescueVersion rescueVersion2;

        Example example = new Example(TblRescueVersion.class);
        example.createCriteria().andEqualTo("version", version);
        rescueVersion = tblRescueVersionMapper.selectOneByExample(example);
        if (StringUtils.isNull(rescueVersion)) {
            rescueVersion=new TblRescueVersion();
            rescueVersion.setId(remoteIdProducerService.nextId());
            rescueVersion.setVersion(version);
            tblRescueVersionMapper.insert(rescueVersion);
        }

        for (RescueVo rescueVo : list) {
            example = new Example(TblRescueListRecord.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("vehicleId", rescueVo.getVehicleId());
            rescueListRecord = tblRescueListRecordMapper.selectOneByExample(example);
            if (StringUtils.isNull(rescueListRecord)) {
                rescueListRecord = new TblRescueListRecord();
                BeanUtils.copyNotNullProperties(rescueVo, rescueListRecord);
                rescueListRecord.setId(remoteIdProducerService.nextId());
                rescueListRecord.setRescueId(rescueVo.getId());
                rescueListRecord.setVersionId(rescueVersion.getId());
                rescueListRecord.setUpdateTime(DateUtils.getNowDate());
                tblRescueListRecordMapper.insert(rescueListRecord);

                rescueListRecordLog = new TblRescueListRecordLog();
                BeanUtils.copyNotNullProperties(rescueListRecord, rescueListRecordLog);
                rescueListRecordLog.setId(remoteIdProducerService.nextId());
                tblRescueListRecordLogMapper.insert(rescueListRecordLog);
            } else {
                Example example2=new Example(TblRescueVersion.class);
                example2.createCriteria().andEqualTo("id", rescueListRecord.getVersionId());
                rescueVersion2 = tblRescueVersionMapper.selectOneByExample(example2);

                if (Long.parseLong(rescueVersion2.getVersion()) < Long.parseLong(version)) {
                    BeanUtils.copyNotNullProperties(rescueVo, rescueListRecord);
                    rescueListRecord.setRescueId(rescueVo.getId());
                    rescueListRecord.setVersionId(rescueVersion.getId());
                    rescueListRecord.setUpdateTime(DateUtils.getNowDate());
                    tblRescueListRecordMapper.updateByPrimaryKey(rescueListRecord);

                    rescueListRecordLog = new TblRescueListRecordLog();
                    BeanUtils.copyNotNullProperties(rescueListRecord, rescueListRecordLog);
                    rescueListRecordLog.setId(remoteIdProducerService.nextId());
                    tblRescueListRecordLogMapper.insert(rescueListRecordLog);
                }
            }
        }
    }

    public void insertAll(List<RescueVo> list, String version) {
        TblRescueListRecord rescueListRecord;
        TblRescueVersion rescueVersion;
        TblRescueVersion rescueVersion2;

        Example example = new Example(TblRescueVersion.class);
        example.createCriteria().andEqualTo("version", version);
        rescueVersion = tblRescueVersionMapper.selectOneByExample(example);
        if (StringUtils.isNull(rescueVersion)) {
            rescueVersion=new TblRescueVersion();
            rescueVersion.setId(remoteIdProducerService.nextId());
            rescueVersion.setVersion(version);
            tblRescueVersionMapper.insert(rescueVersion);
        }

        for (RescueVo rescueVo : list) {
            example = new Example(TblRescueListRecord.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("vehicleId", rescueVo.getVehicleId());
            rescueListRecord = tblRescueListRecordMapper.selectOneByExample(example);
            if (StringUtils.isNull(rescueListRecord)) {
                rescueListRecord = new TblRescueListRecord();
                BeanUtils.copyNotNullProperties(rescueVo, rescueListRecord);
                rescueListRecord.setId(remoteIdProducerService.nextId());
                rescueListRecord.setRescueId(rescueVo.getId());
                rescueListRecord.setVersionId(rescueVersion.getId());
                rescueListRecord.setUpdateTime(DateUtils.getNowDate());
                tblRescueListRecordMapper.insert(rescueListRecord);
            } else {
                Example example2=new Example(TblRescueVersion.class);
                example2.createCriteria().andEqualTo("id", rescueListRecord.getVersionId());
                rescueVersion2 = tblRescueVersionMapper.selectOneByExample(example2);

                if (Long.parseLong(rescueVersion2.getVersion()) < Long.parseLong(version)) {
                    BeanUtils.copyNotNullProperties(rescueVo, rescueListRecord);
                    rescueListRecord.setRescueId(rescueVo.getId());
                    rescueListRecord.setVersionId(rescueVersion.getId());
                    rescueListRecord.setUpdateTime(DateUtils.getNowDate());
                    tblRescueListRecordMapper.updateByPrimaryKey(rescueListRecord);
                }
            }
        }
    }

    public static List<RescueVo> jsonAnalysis(String jsonPath){

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
            List<RescueVo> listStr = JSON.parseArray(jsonStr, RescueVo.class);
            return listStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
