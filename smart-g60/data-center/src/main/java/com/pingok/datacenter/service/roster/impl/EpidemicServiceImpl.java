package com.pingok.datacenter.service.roster.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.domain.roster.blackcard.TblBlackCardVersion;
import com.pingok.datacenter.domain.roster.blackcard.vo.BlackVo;
import com.pingok.datacenter.domain.roster.epidemic.*;
import com.pingok.datacenter.domain.roster.epidemic.vo.EpidemicVo;
import com.pingok.datacenter.domain.roster.epidemic.vo.PrefixVo;
import com.pingok.datacenter.domain.roster.vo.VersionVo;
import com.pingok.datacenter.mapper.roster.VersionMapper;
import com.pingok.datacenter.mapper.roster.epidemic.*;
import com.pingok.datacenter.service.roster.IEpidemicService;
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
 * 中高风险名单 服务层处理
 *
 * @author ruoyi
 */
@Service
public class EpidemicServiceImpl implements IEpidemicService {
    @Autowired
    private TblPrefixListRecordMapper tblPrefixListRecordMapper;
    @Autowired
    private TblEpidemicListRecordMapper tblEpidemicListRecordMapper;
    @Autowired
    private TblPrefixStationUsedMapper tblPrefixStationUsedMapper;
    @Autowired
    private TblEpidemicStationUsedMapper tblEpidemicStationUsedMapper;
    @Autowired
    private TblEpidemicVersionMapper tblEpidemicVersionMapper;
    @Autowired
    private TblPrefixVersionMapper tblPrefixVersionMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private VersionMapper versionMapper;

    @Value("${center.host}")
    private String host;

    @Value("${center.stationGB}")
    private String stationGB;

    @Value("${center.epidemicPath}")
    private String epidemicPath;

    @Value("${center.prefixPath}")
    private String prefixPath;

    @Override
    public void epidemic(JSONObject obj) {
        Example example = new Example(TblEpidemicStationUsed.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("version", obj.getString("version"));
        criteria.andEqualTo("stationHex", obj.getString("stationHex"));
        TblEpidemicStationUsed stationUsed = tblEpidemicStationUsedMapper.selectOneByExample(example);
        if (StringUtils.isNull(stationUsed)) {
            stationUsed = new TblEpidemicStationUsed();
            stationUsed.setId(remoteIdProducerService.nextId());
            stationUsed.setStationHex(obj.getString("stationHex"));
            stationUsed.setApplyTime(obj.getDate("applyTime"));
            stationUsed.setCreateTime(DateUtils.getNowDate());
            stationUsed.setVersion(obj.getString("version"));
            tblEpidemicStationUsedMapper.insert(stationUsed);
        }
    }

    @Override
    public void epidemicPrefix(JSONObject obj) {
        Example example = new Example(TblPrefixStationUsed.class);
        example.createCriteria().andEqualTo("version", obj.getString("version"));
        example.createCriteria().andEqualTo("stationHex", obj.getString("stationHex"));
        TblPrefixStationUsed stationUsed = tblPrefixStationUsedMapper.selectOneByExample(example);
        if (StringUtils.isNull(stationUsed)) {
            stationUsed = new TblPrefixStationUsed();
            stationUsed.setId(remoteIdProducerService.nextId());
            stationUsed.setStationHex(obj.getString("stationHex"));
            stationUsed.setApplyTime(obj.getDate("applyTime"));
            stationUsed.setCreateTime(DateUtils.getNowDate());
            stationUsed.setVersion(obj.getString("version"));
            tblPrefixStationUsedMapper.insert(stationUsed);
        }
    }

    @Override
    public void epidemicDownload() {
        String versionNow=versionMapper.selectVersionAll("TBL_EPIDEMIC_VERSION");
        String version = DateUtils.getTimeDay(DateUtils.getNowDate());
        if(StringUtils.isNotNull(versionNow) && (versionNow.equals(version)))
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            try {
                // 注意格式需要与上面一致，不然会出现异常
                version=DateUtils.getTimeDay(DateUtils.getPreTime(sdf.parse(versionNow) ,1440));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String url = host + "/api/lane-service/epidemic-area-list";
        OkHttpClient client = new OkHttpClient();
        VersionVo versionVo = new VersionVo();
        versionVo.setVersion(version);
        String jsonStr = JSON.toJSONString(versionVo);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonStr);
        final Request request = new Request.Builder()
                .url(url)
                .addHeader("AuthCode", stationGB)
                .addHeader("Json-Md5", backMD5(jsonStr))
                .post(requestBody)
                .build();
        Call call = client.newCall(request);

        try {
            Response response = call.execute();
            byte[] bytes = response.body().bytes();
            if(bytes.length>0 && response.code()==200)
            {
                String fileName = version + ".zip";
                File file = new File(epidemicPath);
                if (!file.exists()) {
                    file.mkdir();
                }
                String pathName = epidemicPath + "\\" + fileName;
                file = new File(pathName);
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytes, 0, bytes.length);
                fos.flush();
                fos.close();
                unzipEpidemic(pathName,prefixPath,version);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void prefixDownload(String version) {
        String url = host + "/api/lane-service/epidemic-prefix-list";
        OkHttpClient client = new OkHttpClient();
        VersionVo versionVo = new VersionVo();
        versionVo.setVersion(version);
        String jsonStr = JSON.toJSONString(versionVo);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonStr);
        final Request request = new Request.Builder()
                .url(url)
                .addHeader("AuthCode", stationGB)
                .addHeader("Json-Md5", backMD5(jsonStr))
                .post(requestBody)
                .build();
        Call call = client.newCall(request);

        try {
            Response response = call.execute();
            byte[] bytes = response.body().bytes();
            if(bytes.length>0 && response.code()==200)
            {
                String fileName = version + ".zip";
                File file = new File(prefixPath);
                if (!file.exists()) {
                    file.mkdir();
                }
                String pathName = prefixPath + "\\" + fileName;
                file = new File(pathName);
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytes, 0, bytes.length);
                fos.flush();
                fos.close();
                unzipPrefix(pathName,prefixPath,version);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void unzipPrefix(String zipPath,String resourcePath,String version){
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
                    unzipPrefix(outpath,resourcePath,version);
                }
                if(zipEntryName.contains(".json"))
                {
                    PrefixVo prefixVo =jsonAnalysisP(outpath);
                    insertPrefix(prefixVo,version);
                }
            }
            zp.close();
        }catch ( Exception e){
            e.printStackTrace();
        }
        delFolder(resourcePath);
    }

    public void unzipEpidemic(String zipPath,String resourcePath,String version){
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
                    unzipEpidemic(outpath,resourcePath,version);
                }
                if(zipEntryName.contains(".json"))
                {
                    List<EpidemicVo> list =jsonAnalysisE(outpath);
                    insertEpidemic(list,version);
                }
            }
            zp.close();
        }catch ( Exception e){
            e.printStackTrace();
        }
        delFolder(resourcePath);
    }

    public void insertEpidemic(List<EpidemicVo> list, String version) {
        TblEpidemicVersion epidemicVersion;

        Example example = new Example(TblEpidemicVersion.class);
        example.createCriteria().andEqualTo("version", version);
        epidemicVersion = tblEpidemicVersionMapper.selectOneByExample(example);
        if (StringUtils.isNull(epidemicVersion)) {
            epidemicVersion = new TblEpidemicVersion();
            epidemicVersion.setId(remoteIdProducerService.nextId());
            epidemicVersion.setVersion(version);
            tblEpidemicVersionMapper.insert(epidemicVersion);
            for (EpidemicVo epidemicVo : list) {
                TblEpidemicListRecord epidemicListRecord=new TblEpidemicListRecord();
                epidemicListRecord.setId(remoteIdProducerService.nextId());
                epidemicListRecord.setVersionId(epidemicVersion.getId());
                BeanUtils.copyNotNullProperties(epidemicVo, epidemicListRecord);
                epidemicListRecord.setDbTime(DateUtils.getNowDate());
                epidemicListRecord.setStartTime(epidemicVo.getEffective());
                tblEpidemicListRecordMapper.insert(epidemicListRecord);
            }
        }
    }

    public void insertPrefix(PrefixVo prefixVo, String version) {
        TblPrefixVersion prefixVersion;

        Example example = new Example(TblPrefixVersion.class);
        example.createCriteria().andEqualTo("version", version);
        prefixVersion = tblPrefixVersionMapper.selectOneByExample(example);
        if (StringUtils.isNull(prefixVersion)) {
            prefixVersion=new TblPrefixVersion();
            prefixVersion.setId(remoteIdProducerService.nextId());
            prefixVersion.setVersion(version);
            tblPrefixVersionMapper.insert(prefixVersion);

            TblPrefixListRecord prefixListRecord=new TblPrefixListRecord();
            String[] prefixList=JSON.parseObject(prefixVo.getPrefix(), String[].class);
            for(String prefix :prefixList)
            {
                prefixListRecord.setId(remoteIdProducerService.nextId());
                prefixListRecord.setPrefix(prefix);
                prefixListRecord.setStartTime(prefixVo.getEffective());
                prefixListRecord.setVersionId(prefixVersion.getId());
                prefixListRecord.setDbTime(DateUtils.getNowDate());
                tblPrefixListRecordMapper.insert(prefixListRecord);
            }
        }
    }

    public static List<EpidemicVo> jsonAnalysisE(String jsonPath){

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
            List<EpidemicVo> listStr = JSON.parseArray(jsonStr, EpidemicVo.class);
            return listStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static PrefixVo jsonAnalysisP(String jsonPath){

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
            PrefixVo listStr = JSON.parseObject(jsonStr, PrefixVo.class);
            return listStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
