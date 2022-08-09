package com.pingok.station.service.suspectList.impl;

import com.alibaba.fastjson.JSON;
import com.pingok.station.domain.suspectList.SuspectArea;
import com.pingok.station.domain.suspectList.vo.SuspectVo;
import com.pingok.station.domain.vo.VersionVo;
import com.pingok.station.mapper.suspectList.SuspectAreaMapper;
import com.pingok.station.service.suspectList.ISuspectListService;
import com.ruoyi.common.core.utils.bean.BeanUtils;
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

import static com.pingok.station.service.cardBlacklist.impl.CardBlacklistServiceImpl.backMD5;
import static com.pingok.station.service.cardBlacklist.impl.CardBlacklistServiceImpl.delFolder;

@Slf4j
@Service
public class SuspectListServiceImpl implements ISuspectListService {

    @Autowired
    private SuspectAreaMapper suspectAreaMapper;

    @Value("${center.host}")
    private String host;

    @Value("${center.stationGB}")
    private String stationGB;

    @Value("${center.suspectPath}")
    private String suspectPath;

    @Override
    public void suspectList(String version) {
        String url= host + "/api/lane-service/suspect-invalid-list";
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
                String fileName = version +"Suspect"+ ".zip";
                File file=new File(suspectPath);
                if(!file.exists()){
                    file.mkdirs();
                }
                String pathName=suspectPath+"\\"+fileName;
                file  = new File(pathName);
                if(!file.exists()){
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytes,0,bytes.length);
                fos.flush();
                fos.close();
                unzipSuspect(pathName,suspectPath,version);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void insertSuspect(List<SuspectVo> list, String version) {
        SuspectArea suspectArea =new SuspectArea();
        suspectAreaMapper.deleteAll();
        for(SuspectVo suspectVo : list) {
            BeanUtils.copyNotNullProperties(suspectVo,suspectArea);
            suspectArea.setStartTime(suspectVo.getEffective());
            suspectArea.setVersion(version);
            suspectAreaMapper.insertSuspect(suspectArea);
        }
    }

    @Override
    public void test(String version) {
        unzipSuspect(suspectPath+"\\2022-07-29.zip",suspectPath,version);
    }

    public void unzipSuspect(String zipPath,String resourcePath,String version){
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
                    unzipSuspect(outpath,resourcePath,version);
                }
                if(zipEntryName.contains(".json"))
                {
                    List<SuspectVo> list =jsonAnalysis(outpath);
                    insertSuspect(list,version);
                }
            }
            zp.close();
        }catch ( Exception e){
            e.printStackTrace();
        }
        delFolder(resourcePath);
    }

    public static List<SuspectVo> jsonAnalysis(String jsonPath){

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
            List<SuspectVo> listStr = JSON.parseArray(jsonStr, SuspectVo.class);
            return listStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
