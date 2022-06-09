package com.pingok.station.service.obuBlacklist.impl;

import com.alibaba.fastjson.JSON;
import com.pingok.station.domain.obuBlacklist.BObuAppend;
import com.pingok.station.domain.obuBlacklist.vo.BlackObuVo;
import com.pingok.station.domain.vo.*;
import com.pingok.station.mapper.obuBlacklist.BObuAppendMapper;
import com.pingok.station.service.obuBlacklist.IObuBlacklistService;
import com.ruoyi.common.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static com.pingok.station.service.cardBlacklist.impl.CardBlacklistServiceImpl.backMD5;
import static com.pingok.station.service.cardBlacklist.impl.CardBlacklistServiceImpl.delFolder;

@Slf4j
@Service
public class ObuBlacklistServiceImpl implements IObuBlacklistService {

    @Autowired
    private BObuAppendMapper bObuAppendMapper;

    @Value("${center.host}")
    private String host;


    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    @Override
    public void increment(String version) {
        String url=host+"/api/lane-service/obu-black-incr-list";
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
            File pathFile=new File("D:\\obuBlacklist");
            if(!pathFile.exists()){
                pathFile.mkdirs();
            }

            String contentHeader = response.header("Content-Disposition");
            String fileName="obuBlack.zip";
            if(contentHeader.contains("filename="))
            {
                String str1 = contentHeader.substring(0, contentHeader.indexOf("filename="));
                String str2 = contentHeader.substring(str1.length() + 9);
                if(str2.contains(".zip"))
                {
                    fileName = str2;
                }
            }

            String pathName="D:\\obuBlacklist"+"\\"+fileName;
            File file  = new File(pathName);
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes,0,bytes.length);
            fos.flush();
            fos.close();

            unzipInside(unzip(pathName,"D:\\obuBlacklist"),"D:\\obuBlacklist");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void all(String version) {
        List<String> dataList= Arrays.asList("11","12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65");
        for(String data : dataList) {
            String url = host+"/api/lane-service/obu-black-all-list";
            OkHttpClient client = new OkHttpClient();
            VersionAllVo versionAllVo = new VersionAllVo();
            versionAllVo.setVersion(version);
            versionAllVo.setProvinceId(data);
            String jsonStr = JSON.toJSONString(versionAllVo);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonStr);
            final Request request = new Request.Builder()
                    .url(url)
                    .addHeader("AuthCode", "S0004310010010")
                    .addHeader("Json-Md5", backMD5(jsonStr))
                    .post(requestBody)
                    .build();
            Call call = client.newCall(request);

            try {
                Response response = call.execute();
                byte[] bytes = response.body().bytes();
                File pathFile = new File("D:\\obuBlacklist");
                if (!pathFile.exists()) {
                    pathFile.mkdirs();
                }
                File pathFileInside = new File("D:\\obuBlacklist"+"\\"+data);
                if (!pathFileInside.exists()) {
                    pathFileInside.mkdirs();
                }
                String contentHeader = response.header("Content-Disposition");
                String fileName = "obuBlackAll.zip";
                if (contentHeader.contains("filename=")) {
                    String str1 = contentHeader.substring(0, contentHeader.indexOf("filename="));
                    String str2 = contentHeader.substring(str1.length() + 9);
                    if (str2.contains(".zip")) {
                        fileName = str2;
                    }
                }

                String pathName = "D:\\obuBlacklist"+"\\"+data + "\\" + fileName;
                File file = new File(pathName);
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytes, 0, bytes.length);
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void unzipAll(String version){
        ZipFile zp=null;
        List<String> dataList=Arrays.asList("11","12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65");
        for(String data : dataList) {
            String zipPath = "D:\\obuBlacklist" + "\\" +data+ "\\" + version + "_" + data + ".zip";
            try {
                //指定编码，否则压缩包里面不能有中文目录
                zp = new ZipFile(zipPath, Charset.forName("gbk"));
                //遍历里面的文件及文件夹
                Enumeration entries = zp.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry entry = (ZipEntry) entries.nextElement();
                    String zipEntryName = entry.getName();
                    InputStream in = zp.getInputStream(entry);
                    String outpath = ("D:\\obuBlacklist" + "\\" +data+ "\\"+ zipEntryName).replace("/", File.separator);
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
                        List<BlackObuVo> list = jsonAnalysis(outpath);
                        jedisInsert(list, version);
                        fileDelete.delete();
                    }
                }
                zp.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void insert(List<BlackObuVo> list, String version) {
        BObuAppend bObuAppend=new BObuAppend();
        for(BlackObuVo blackObuVo : list) {
            bObuAppend.setApdVer(version);
            bObuAppend.setProvId(Integer.valueOf(blackObuVo.getIssuerId().substring(0,2)));
            bObuAppend.setCardId(blackObuVo.getOBUId());
            bObuAppend.setCTime(blackObuVo.getCreationTime());
            bObuAppend.setITime(blackObuVo.getInsertTime());
            bObuAppend.setIssuerId(blackObuVo.getIssuerId());
            bObuAppend.setCStatus(blackObuVo.getStatus());
            bObuAppend.setCType(blackObuVo.getType());
            bObuAppendMapper.insertBObuAppend(bObuAppend);
        }
    }
    @Override
    public void jedisInsert(List<BlackObuVo> list, String version) {
        Jedis jedis = new Jedis(redisHost,redisPort);
        for(BlackObuVo blackObuVo : list) {
            List<RedisValueVo> redisValueVoList =new ArrayList<>();
            if(blackObuVo.getStatus()==1) {
                jedis.select(Integer.valueOf(blackObuVo.getIssuerId().substring(0, 2))+100);
                {
                    if(StringUtils.isNotBlank(jedis.get(blackObuVo.getOBUId())))
                    {
                        int flag=0;
                        redisValueVoList=JSON.parseArray(jedis.get(blackObuVo.getOBUId()), RedisValueVo.class);
                        for(RedisValueVo redisValueVo:redisValueVoList)
                        {
                            if(redisValueVo.getType().equals(blackObuVo.getType()))
                            {
                                flag=1;
                                break;
                            }
                        }
                        if(flag==0)
                        {
                            RedisValueVo redisValueVo=new RedisValueVo();
                            redisValueVo.setType(blackObuVo.getType());
                            redisValueVo.setVersion(version);
                            redisValueVoList.add(redisValueVo);
                            jedis.set(blackObuVo.getOBUId(),JSON.toJSONString(redisValueVoList));
                        }
                    }
                    else
                    {
                        RedisValueVo redisValueVo=new RedisValueVo();
                        redisValueVo.setType(blackObuVo.getType());
                        redisValueVo.setVersion(version);
                        redisValueVoList.add(redisValueVo);

                        jedis.set(blackObuVo.getOBUId(), JSON.toJSONString(redisValueVoList));
                    }
                }
            }
            if(blackObuVo.getStatus()==2) {
                jedis.select(Integer.valueOf(blackObuVo.getIssuerId().substring(0, 2))+100);
                {
                    if (StringUtils.isNotBlank(jedis.get(blackObuVo.getOBUId())))
                    {
                        redisValueVoList=JSON.parseArray(jedis.get(blackObuVo.getOBUId()), RedisValueVo.class);
                        for(RedisValueVo redisValueVo:redisValueVoList)
                        {
                            if(redisValueVo.getType().equals(blackObuVo.getType()))
                            {
                                redisValueVoList.remove(redisValueVo);
                                if(redisValueVoList.size()==0)
                                {
                                    jedis.del(blackObuVo.getOBUId());
                                    break;
                                }
                                else{
                                    jedis.set(blackObuVo.getOBUId(),JSON.toJSONString(redisValueVoList));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        jedis.close();
    }

    public void unzipInside(String version,String resourcePath){
        //判断生成目录是否生成，如果没有就创建
        File pathFile=new File(resourcePath);
        if(!pathFile.exists()){
            pathFile.mkdirs();
        }
        ZipFile zp=null;
        List<String> dataList= Arrays.asList("11","12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65");
        File zipFile;
        for(String data : dataList) {
            String zipPath=resourcePath+"\\"+version+"_"+data+".zip";
            zipFile = new File(zipPath);
            if (zipFile.exists()) {
                try {
                    //指定编码，否则压缩包里面不能有中文目录
                    zp = new ZipFile(zipPath, Charset.forName("gbk"));
                    //遍历里面的文件及文件夹
                    Enumeration entries = zp.entries();
                    while (entries.hasMoreElements()) {
                        ZipEntry entry = (ZipEntry) entries.nextElement();
                        String zipEntryName = entry.getName();
                        InputStream in = zp.getInputStream(entry);
                        String outpath = (resourcePath + "\\" + zipEntryName).replace("/", File.separator);
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
                            List<BlackObuVo> list = jsonAnalysis(outpath);
                            insert(list, version);
                            jedisInsert(list, version);
                        }
                    }
                    zp.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        delFolder(resourcePath);
    }

    public static String unzip(String zipPath,String resourcePath){
        //判断生成目录是否生成，如果没有就创建
        File pathFile=new File(resourcePath);
        if(!pathFile.exists()){
            pathFile.mkdirs();
        }
        ZipFile zp=null;
        String version="";
        try{
            //指定编码，否则压缩包里面不能有中文目录
            zp=new ZipFile(zipPath,Charset.forName("gbk"));
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
                    BlackIncrValidateVo blackIncrValidateVo =jsonAnalysisVa(outpath);
                    version=blackIncrValidateVo.getVersion();
                }
            }
            zp.close();
            return version;
        }catch ( Exception e){
            e.printStackTrace();
            return version;
        }
    }

    public static List<BlackObuVo> jsonAnalysis(String jsonPath){

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
            List<BlackObuVo> listStr = JSON.parseArray(jsonStr, BlackObuVo.class);
            return listStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static BlackIncrValidateVo jsonAnalysisVa(String jsonPath){

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
            BlackIncrValidateVo listStr = JSON.parseObject(jsonStr, BlackIncrValidateVo.class);
            return listStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
