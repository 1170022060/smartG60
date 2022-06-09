package com.pingok.station.service.greenList.impl;

import com.alibaba.fastjson.JSON;
import com.pingok.station.domain.greenList.GreenPassAppointment;
import com.pingok.station.domain.greenList.vo.GreenVo;
import com.pingok.station.domain.vo.VersionVo;
import com.pingok.station.mapper.greenList.GreenPassAppointmentMapper;
import com.pingok.station.service.greenList.IGreenListService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static com.pingok.station.service.cardBlacklist.impl.CardBlacklistServiceImpl.backMD5;
import static com.pingok.station.service.cardBlacklist.impl.CardBlacklistServiceImpl.delFolder;

/**
 * @author
 * @time 2022/4/13 16:32
 */
@Slf4j
@Service
public class GreenListServiceImpl implements IGreenListService {
    @Autowired
    private GreenPassAppointmentMapper greenPassAppointmentMapper;

    @Value("${center.host}")
    private String host;

    @Override
    public void greenList(String version) {
        String url="http://10.131.4.18:18180/api/lane-service/appointment-all-list";
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
            File pathFile=new File("D:\\appointList");
            if(!pathFile.exists()){
                pathFile.mkdirs();
            }

            String contentHeader = response.header("Content-Disposition");
            String fileName="Appoint.zip";
            if(contentHeader.contains("filename="))
            {
                String str1 = contentHeader.substring(0, contentHeader.indexOf("filename="));
                String str2 = contentHeader.substring(str1.length() + 9);
                if(str2.contains(".zip"))
                {
                    fileName = str2;
                }
            }

            String pathName="D:\\appointList"+"\\"+fileName;
            File file  = new File(pathName);
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes,0,bytes.length);
            fos.flush();
            fos.close();

            unzipGreen(pathName,"D:\\appointList");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void insertGreen(List<GreenVo> list) {
        GreenPassAppointment greenPassAppointment=new GreenPassAppointment();
        greenPassAppointmentMapper.deleteAll();
        for(GreenVo greenVo : list) {
            greenPassAppointment.setId(greenVo.getId());
            greenPassAppointment.setPhone(greenVo.getId().substring(0,11));
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = null;
            try {
                date = format.parse(greenVo.getId().substring(11));
                greenPassAppointment.setApointTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            greenPassAppointment.setVehicleClass(0);
            greenPassAppointment.setPlateNum(greenVo.getVehicleId().substring(0, greenVo.getVehicleId().indexOf("_")));
            greenPassAppointment.setPlateColor(Integer.valueOf(greenVo.getVehicleId().substring(greenVo.getVehicleId().substring(0, greenVo.getVehicleId().indexOf("_")).length() + 1)));
            greenPassAppointment.setStartTransYMDH(greenVo.getEndTransportTime());
            greenPassAppointment.setSubmitTime(greenVo.getAppointmentTime());
            greenPassAppointment.setStartDistrictId(greenVo.getStartDistrictId());
            greenPassAppointment.setEndDistrictId(greenVo.getEndDistrictId());
            greenPassAppointmentMapper.insertGreenPass(greenPassAppointment);
        }
    }

    public void unzipGreen(String zipPath,String resourcePath){
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
                    unzipGreen(outpath,"D:\\appointList");
                }
                if(zipEntryName.contains(".json"))
                {
                    List<GreenVo> list =jsonAnalysis(outpath);
                    insertGreen(list);
                }
            }
            zp.close();
        }catch ( Exception e){
            e.printStackTrace();
        }
        delFolder(resourcePath);
    }
    public static List<GreenVo> jsonAnalysis(String jsonPath){

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
            List<GreenVo> listStr = JSON.parseArray(jsonStr, GreenVo.class);
            return listStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
