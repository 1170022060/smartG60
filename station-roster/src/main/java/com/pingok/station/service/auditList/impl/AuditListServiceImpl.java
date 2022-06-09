package com.pingok.station.service.auditList.impl;

import com.alibaba.fastjson.JSON;
import com.pingok.station.domain.auditList.AuditData;
import com.pingok.station.domain.auditList.vo.AuditVo;
import com.pingok.station.domain.vo.VersionVo;
import com.pingok.station.mapper.auditList.AuditAppendMapper;
import com.pingok.station.mapper.auditList.AuditDataMapper;
import com.pingok.station.service.auditList.IAuditListService;
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

@Slf4j
@Service
public class AuditListServiceImpl implements IAuditListService {

    @Autowired
    private AuditAppendMapper auditAppendMapper;

    @Autowired
    private AuditDataMapper auditDataMapper;

    @Value("${center.host}")
    private String host;

    @Override
    public void increment(String version) {
        String url="http://10.131.4.18:18180/api/lane-service/vehicleblack-incr-list";
        OkHttpClient client = new OkHttpClient();
        VersionVo versionVo=new VersionVo();
        versionVo.setVersion(version);
        String jsonStr = JSON.toJSONString(versionVo);
        RequestBody requestBody =  RequestBody.create(MediaType.parse("application/json"),jsonStr);
        final Request request = new Request.Builder()
                .url(url)
                .addHeader("AuthCode","S0004310010010")
                .addHeader("Json-Md5",backMD5(jsonStr))
                .post(requestBody)
                .build();
        Call call = client.newCall(request);

        try{
            Response response = call.execute();
            byte[] bytes = response.body().bytes();
            File pathFile=new File("D:\\audit");
            if(!pathFile.exists()){
                pathFile.mkdirs();
            }

            String contentHeader = response.header("Content-Disposition");
            String fileName="Audit_Incr.zip";
            if(contentHeader.contains("filename="))
            {
                String str1 = contentHeader.substring(0, contentHeader.indexOf("filename="));
                String str2 = contentHeader.substring(str1.length() + 9);
                if(str2.contains(".zip"))
                {
                    fileName = str2;
                }
            }

            String pathName="D:\\audit"+"\\"+fileName;
            File file  = new File(pathName);
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes,0,bytes.length);
            fos.flush();
            fos.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void file() {
        unzipAll("20220527");
    }
    @Override
    public void all(String version) {
        String url="http://10.131.4.18:18180/api/lane-service/vehicleblack-all-list";
        OkHttpClient client = new OkHttpClient();
        VersionVo versionVo=new VersionVo();
        versionVo.setVersion(version);
        String jsonStr = JSON.toJSONString(versionVo);
        RequestBody requestBody =  RequestBody.create(MediaType.parse("application/json"),jsonStr);
        final Request request = new Request.Builder()
                .url(url)
                .addHeader("AuthCode","S0004310010010")
                .addHeader("Json-Md5",backMD5(jsonStr))
                .post(requestBody)
                .build();
        Call call = client.newCall(request);

        try{
            Response response = call.execute();
            byte[] bytes = response.body().bytes();
            File pathFile=new File("D:\\audit");
            if(!pathFile.exists()){
                pathFile.mkdirs();
            }

            String contentHeader = response.header("Content-Disposition");
            String fileName="Audit.zip";
            if(contentHeader.contains("filename="))
            {
                String str1 = contentHeader.substring(0, contentHeader.indexOf("filename="));
                String str2 = contentHeader.substring(str1.length() + 9);
                if(str2.contains(".zip"))
                {
                    fileName = str2;
                }
            }

            String pathName="D:\\audit"+"\\"+fileName;
            File file  = new File(pathName);
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes,0,bytes.length);
            fos.flush();
            fos.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void unzipAll(String version) {
        auditDataMapper.deleteAll();
        String zipPath = "D:\\audit";
        ZipFile zp=null;//要遍历的路径
        File files = new File(zipPath);		//获取其file对象
        String[] fileNameLists = files.list();	//遍历path下的文件和目录，放在File数组中
        for(String f:fileNameLists){					//遍历File[]数组
            if(f.contains(version) && f.contains(".zip") && f.contains("AUDIT_VEHICLEBLACKLISTDOWN_RES"))
            {
                try {
                    //指定编码，否则压缩包里面不能有中文目录
                    zp = new ZipFile(zipPath+"\\"+f, Charset.forName("gbk"));
                    //遍历里面的文件及文件夹
                    Enumeration entries = zp.entries();
                    while (entries.hasMoreElements()) {
                        ZipEntry entry = (ZipEntry) entries.nextElement();
                        String zipEntryName = entry.getName();
                        InputStream in = zp.getInputStream(entry);
                        String outpath = ("D:\\audit"+"\\"+ zipEntryName).replace("/", File.separator);
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
                            insert(list);
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
    public void insert(List<AuditVo> list) {
        AuditData auditData=new AuditData();
        for(AuditVo auditVo : list) {
            auditData.setApdVer(auditVo.getVersion());
            auditData.setVehicleId(auditVo.getVehicleId());
            auditData.setReason(auditVo.getReason());
            auditData.setOweFee(auditVo.getOweFee());
            auditData.setEvasionCount(auditVo.getEvasionCount());
            auditDataMapper.insertAuditData(auditData);
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
}
