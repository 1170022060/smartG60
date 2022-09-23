package com.pingok.station.service.auditList.impl;

import com.alibaba.fastjson.JSON;
import com.pingok.station.domain.auditList.AuditAppend;
import com.pingok.station.domain.auditList.AuditData;
import com.pingok.station.domain.auditList.AuditPre;
import com.pingok.station.domain.auditList.AuditPreTrans;
import com.pingok.station.domain.auditList.vo.AuditPreVo;
import com.pingok.station.domain.auditList.vo.AuditVo;
import com.pingok.station.domain.auditList.vo.TransactionsVo;
import com.pingok.station.domain.tracer.ListTracer;
import com.pingok.station.domain.vo.VersionVo;
import com.pingok.station.mapper.tracer.ListTracerMapper;
import com.pingok.station.mapper.auditList.AuditAppendMapper;
import com.pingok.station.mapper.auditList.AuditDataMapper;
import com.pingok.station.mapper.auditList.AuditPreMapper;
import com.pingok.station.mapper.auditList.AuditPreTransMapper;
import com.pingok.station.service.auditList.IAuditListService;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static com.pingok.station.service.cardBlacklist.impl.CardBlacklistServiceImpl.backMD5;
import static com.pingok.station.service.cardBlacklist.impl.CardBlacklistServiceImpl.delFolder;

@Slf4j
@Service
public class AuditListServiceImpl implements IAuditListService {

    @Autowired
    private AuditAppendMapper auditAppendMapper;

    @Autowired
    private AuditDataMapper auditDataMapper;

    @Autowired
    private AuditPreMapper auditPreMapper;

    @Autowired
    private AuditPreTransMapper auditPreTransMapper;

    @Autowired
    private ListTracerMapper listTracerMapper;

    @Value("${center.host}")
    private String host;

    @Value("${center.stationGB}")
    private String stationGB;

    @Value("${center.auditPath}")
    private String auditPath;

    @Value("${center.auditPrePath}")
    private String auditPrePath;

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
                File file=new File(auditPath);
                if(!file.exists()){
                    file.mkdirs();
                }
                String pathName=auditPath+"\\"+fileName;
                file  = new File(pathName);
                if(!file.exists()){
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytes,0,bytes.length);
                fos.flush();
                fos.close();
                unzipIncr(pathName,auditPath);
                ListTracer listTracer=new ListTracer();
                listTracer.setListType("auditlist");
                listTracer.setVersion(version);
                if(listTracerMapper.selectListType("auditlist")==0)
                {
                    listTracerMapper.insertTracer("auditlist");
                    listTracerMapper.updateTracer(listTracer);
                }else if (listTracerMapper.selectListType("auditlist")==1)
                {
                    listTracerMapper.updateTracer(listTracer);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void preAll(String version) {
        String url=host+"/api/lane-service/pre-black-list";
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
                File file=new File(auditPrePath);
                if(!file.exists()){
                    file.mkdirs();
                }
                String pathName=auditPrePath+"\\"+fileName;
                file  = new File(pathName);
                if(!file.exists()){
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytes,0,bytes.length);
                fos.flush();
                fos.close();
                unzipPre(pathName,auditPrePath);
                ListTracer listTracer=new ListTracer();
                listTracer.setListType("auditPrelist");
                listTracer.setVersion(version);
                if(listTracerMapper.selectListType("auditPrelist")==0)
                {
                    listTracerMapper.insertTracer("auditPrelist");
                    listTracerMapper.updateTracer(listTracer);
                }else if (listTracerMapper.selectListType("auditPrelist")==1)
                {
                    listTracerMapper.updateTracer(listTracer);
                }
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
                File file=new File(auditPrePath+"_all");
                if(!file.exists()){
                    file.mkdirs();
                }
                String pathName=auditPrePath+"_all"+"\\"+fileName;
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

    public void unzipIncr(String zipPath,String resourcePath){
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
                    unzipIncr(outpath,resourcePath);
                }
                if(zipEntryName.contains("data.json"))
                {
                    List<AuditVo> list = jsonAnalysis(outpath);
                    insertData(list);
                    insertAppend(list);
                }
            }
            zp.close();
        }catch ( Exception e){
            e.printStackTrace();
        }
        delFolder(resourcePath);
    }

    public void unzipPre(String zipPath,String resourcePath){
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
                File fileDelete = new File(outpath);
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
                    unzipIncr(outpath,resourcePath);
                }
                if(zipEntryName.contains("json"))
                {
                    List<AuditPreVo> list = jsonAnalysisPre(outpath);
                    insertPreData(list);
                    fileDelete.delete();
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
        String resourcePath = auditPrePath+"_all";
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
                            List<AuditVo> list = jsonAnalysis(outpath);
                            insertData(list);
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

    @Override
    public void insertData(List<AuditVo> list) {
        AuditData auditData=new AuditData();
        for(AuditVo auditVo : list) {
            if(auditVo.getStatus()==1)
            {
                if(auditDataMapper.selectVehicleId(auditVo.getVehicleId())!=0)
                {
                    BeanUtils.copyNotNullProperties(auditVo,auditData);
                    auditData.setApdVer(auditVo.getVersion());
                    auditDataMapper.updateAuditData(auditData);
                }else{
                    BeanUtils.copyNotNullProperties(auditVo,auditData);
                    auditData.setApdVer(auditVo.getVersion());
                    auditDataMapper.insertAuditData(auditData);
                }
            }
            if(auditVo.getStatus()==2)
            {
                auditDataMapper.deleteVehicleId(auditVo.getVehicleId());
            }
        }
    }

    public void insertAppend(List<AuditVo> list) {
        AuditAppend auditAppend=new AuditAppend();
        for(AuditVo auditVo : list) {
            BeanUtils.copyNotNullProperties(auditVo,auditAppend);
            auditAppend.setApdVer(auditVo.getVersion());
            auditAppend.setAType(auditVo.getType());
            auditAppendMapper.insertAuditAppend(auditAppend);
        }
    }

    @Transactional
    public void insertPreData(List<AuditPreVo> list) {
        AuditPre auditPre=new AuditPre();
        auditPreMapper.deleteAll();
        auditPreTransMapper.deleteAll();
        for(AuditPreVo auditPreVo : list) {
            BeanUtils.copyNotNullProperties(auditPreVo,auditPre);
            auditPre.setApdVer(auditPreVo.getVersion());
            auditPreMapper.insertAuditPre(auditPre);
            if(auditPreVo.getTransactions()!=null)
            {
                AuditPreTrans auditPreTrans=new AuditPreTrans();
                for(TransactionsVo transactionVo:auditPreVo.getTransactions())
                {
                    BeanUtils.copyNotNullProperties(transactionVo,auditPreTrans);
                    auditPreTrans.setVehicleId(auditPreVo.getVehicleId());
                    auditPreTransMapper.insertAuditPreTrans(auditPreTrans);
                }
            }
        }
    }

    public static List<AuditVo> jsonAnalysis(String jsonPath){

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
            List<AuditVo> listStr = JSON.parseArray(jsonStr, AuditVo.class);
            return listStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<AuditPreVo> jsonAnalysisPre(String jsonPath){

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
            List<AuditPreVo> listStr = JSON.parseArray(jsonStr, AuditPreVo.class);
            return listStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
