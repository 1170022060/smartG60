package com.pingok.station.service.emergList.impl;

import com.alibaba.fastjson.JSON;
import com.pingok.station.domain.emergList.EmgAppend;
import com.pingok.station.domain.emergList.EmgData;
import com.pingok.station.domain.emergList.vo.EmgVo;
import com.pingok.station.domain.tracer.ListTracer;
import com.pingok.station.domain.vo.VersionVo;
import com.pingok.station.mapper.tracer.ListTracerMapper;
import com.pingok.station.mapper.emergList.EmgAppendMapper;
import com.pingok.station.mapper.emergList.EmgDataMapper;
import com.pingok.station.service.emergList.IEmergListService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import com.ruoyi.common.core.utils.bean.BeanUtils;

import static com.pingok.station.service.cardBlacklist.impl.CardBlacklistServiceImpl.backMD5;
import static com.pingok.station.service.cardBlacklist.impl.CardBlacklistServiceImpl.delFolder;

@Slf4j
@Service
public class EmergListServiceImpl implements IEmergListService {

    @Autowired
    private EmgAppendMapper emgAppendMapper;

    @Autowired
    private EmgDataMapper emgDataMapper;

    @Autowired
    private ListTracerMapper listTracerMapper;

    @Value("${center.host}")
    private String host;

    @Value("${center.stationGB}")
    private String stationGB;

    @Value("${center.emergPath}")
    private String emergPath;

    @Override
    public void increment(String version) {
        String url= host + "/api/lane-service/emerg-incr-list";
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
            if(bytes.length>0)
            {
                String fileName = version + ".zip";
                File file=new File(emergPath);
                if(!file.exists()){
                    file.mkdirs();
                }
                String pathName=emergPath+"\\"+fileName;
                file  = new File(pathName);
                if(!file.exists()){
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytes,0,bytes.length);
                fos.flush();
                fos.close();
                unzipEmg(pathName,emergPath,version);
                ListTracer listTracer=new ListTracer();
                listTracer.setListType("emerglist");
                listTracer.setVersion(version);
                if(listTracerMapper.selectListType("emerglist")==0)
                {
                    listTracerMapper.insertTracer("emerglist");
                    listTracerMapper.updateTracer(listTracer);
                }else if (listTracerMapper.selectListType("emerglist")==1)
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
        String url=host+"/api/lane-service/emerg-all-list";
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
                File file=new File(emergPath+"_all");
                if(!file.exists()){
                    file.mkdirs();
                }
                String pathName=emergPath+"_all"+"\\"+fileName;
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
        String resourcePath = emergPath+"_all";
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
                            String versionZip=zipEntryName.substring(0, zipEntryName.indexOf("_"));
                            if(versionZip.equals(version))
                            {
                                List<EmgVo> list = jsonAnalysis(outpath);
                                insertAll(list,version);
                                fileDelete.delete();
                            }
                        }
                    }
                    zp.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void unzipEmg(String zipPath,String resourcePath,String version){
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

                if(zipEntryName.contains(".json"))
                {
                    String versionZip=zipEntryName.substring(0, zipEntryName.indexOf("_"));
                    if(versionZip.equals(version))
                    {
                        List<EmgVo> list =jsonAnalysis(outpath);
                        insertEmgAppend(list,version);
                    }
                }
            }
            zp.close();
        }catch ( Exception e){
            e.printStackTrace();
        }
        delFolder(resourcePath);
    }

    public static List<EmgVo> jsonAnalysis(String jsonPath){

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
            List<EmgVo> listStr = JSON.parseArray(jsonStr, EmgVo.class);
            return listStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insertEmgAppend(List<EmgVo> list, String version) {
        EmgAppend emgAppend=new EmgAppend();
        EmgData emgData=new EmgData();
        for(EmgVo emgAppendVo : list) {
            BeanUtils.copyNotNullProperties(emgAppendVo,emgAppend);
            emgAppend.setFVer(version);
            emgAppend.setVehiclePlateId(emgAppendVo.getVehicleId().substring(0, emgAppendVo.getVehicleId().indexOf("_")));
            emgAppend.setVehiclePlateColor(Integer.valueOf(emgAppendVo.getVehicleId().substring(emgAppendVo.getVehicleId().substring(0, emgAppendVo.getVehicleId().indexOf("_")).length() + 1)));
            emgAppendMapper.insertEmgAppend(emgAppend);
            if(emgAppendVo.getOperation()==1 || emgAppendVo.getOperation()==2)
            {
                BeanUtils.copyNotNullProperties(emgAppendVo,emgData);
                emgData.setFVer(version);
                emgData.setVehiclePlateId(emgAppendVo.getVehicleId().substring(0, emgAppendVo.getVehicleId().indexOf("_")));
                emgData.setVehiclePlateColor(Integer.valueOf(emgAppendVo.getVehicleId().substring(emgAppendVo.getVehicleId().substring(0, emgAppendVo.getVehicleId().indexOf("_")).length() + 1)));
                if(emgAppendVo.getOperation()==1)
                {
                    emgDataMapper.insertEmgData(emgData);
                }
                if(emgAppendVo.getOperation()==2 && emgDataMapper.selectId(emgAppendVo.getId())!=0)
                {
                    emgDataMapper.updateEmgData(emgData);
                }
            }
            if(emgAppendVo.getOperation()==3)
            {
                emgDataMapper.deleteId(emgAppendVo.getId());
            }
        }
    }
    @Override
    public void insertAll(List<EmgVo> list, String version) {
        EmgData emgData=new EmgData();
        for(EmgVo emgAppendVo : list) {
            BeanUtils.copyNotNullProperties(emgAppendVo, emgData);
            emgData.setFVer(version);
            emgData.setVehiclePlateId(emgAppendVo.getVehicleId().substring(0, emgAppendVo.getVehicleId().indexOf("_")));
            emgData.setVehiclePlateColor(Integer.valueOf(emgAppendVo.getVehicleId().substring(emgAppendVo.getVehicleId().substring(0, emgAppendVo.getVehicleId().indexOf("_")).length() + 1)));
            emgDataMapper.insertEmgData(emgData);
        }
    }

}
