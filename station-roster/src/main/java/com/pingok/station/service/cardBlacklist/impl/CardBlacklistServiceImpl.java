package com.pingok.station.service.cardBlacklist.impl;

import com.alibaba.fastjson.JSON;
import com.pingok.station.domain.cardBlacklist.BCardAppend;
import com.pingok.station.domain.cardBlacklist.vo.BlackVo;
import com.pingok.station.domain.tracer.ListTracer;
import com.pingok.station.domain.vo.BlackIncrValidateVo;
import com.pingok.station.domain.vo.RedisValueVo;
import com.pingok.station.domain.vo.VersionAllVo;
import com.pingok.station.domain.vo.VersionVo;
import com.pingok.station.mapper.tracer.ListTracerMapper;
import com.pingok.station.mapper.cardBlacklist.BCardAppendMapper;
import com.pingok.station.service.cardBlacklist.ICardBlacklistService;
import com.ruoyi.common.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author
 * @time 2022/4/13 16:32
 */
@Slf4j
@Service
public class CardBlacklistServiceImpl implements ICardBlacklistService {

    @Autowired
    private BCardAppendMapper bCardAppendMapper;

    @Autowired
    private ListTracerMapper listTracerMapper;

    @Value("${center.host}")
    private String host;

    @Value("${center.stationGB}")
    private String stationGB;

    @Value("${center.cardPath}")
    private String cardPath;

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    @Override
    public void increment(String version) {
        String url = host + "/api/lane-service/black-incr-list";
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
                File file = new File(cardPath);
                if (!file.exists()) {
                    file.mkdir();
                }
                String pathName = cardPath + "\\" + fileName;
                file = new File(pathName);
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytes, 0, bytes.length);
                fos.flush();
                fos.close();
                if (version.equals(unzip(pathName, cardPath))) {
                    unzipInside(version, cardPath);
                    ListTracer listTracer=new ListTracer();
                    listTracer.setListType("blacklist");
                    listTracer.setVersion(version);
                    if(listTracerMapper.selectListType("blacklist")==0)
                    {
                        listTracerMapper.insertTracer("blacklist");
                        listTracerMapper.updateTracer(listTracer);
                    }else if (listTracerMapper.selectListType("blacklist")==1)
                    {
                        listTracerMapper.updateTracer(listTracer);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void test(String version) {
        unzipInside(unzip("D:\\blacklist\\BASIC_CARDBLACKLISTINCREDOWN_RES_310201_20220512144151267.zip", cardPath),cardPath);
    }

    @Override
    public void all(String version) {
        List<String> dataList = Arrays.asList("11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65");
        for (String province : dataList) {
            String url = host+"/api/lane-service/black-all-list";
            OkHttpClient client = new OkHttpClient();
            VersionAllVo versionAllVo = new VersionAllVo();
            versionAllVo.setVersion(version);
            versionAllVo.setProvinceId(province);
            String jsonStr = JSON.toJSONString(versionAllVo);
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
                    String fileName = version +"_" +province+ ".zip";
                    File file = new File(cardPath+"_all");
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    file = new File(cardPath+"_all" + "\\" + province);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    String pathName = cardPath+"_all" + "\\" + province + "\\" + fileName;
                    file = new File(pathName);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(bytes, 0, bytes.length);
                    fos.flush();
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void unzipAll(String version) {
        ZipFile zp = null;
        List<String> dataList = Arrays.asList("11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65");
        File zipFile;
        for (String province : dataList) {
            String zipPath = cardPath+"_all" + "\\" + province + "\\" + version + "_" + province + ".zip";
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
                        String outpath = (cardPath + "_all" + "\\" + province + "\\" + zipEntryName).replace("/", File.separator);
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
                            List<BlackVo> list = jsonAnalysis(outpath);
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
    }

    @Override
    public void insert(List<BlackVo> list, String version) {
        BCardAppend bCardAppend = new BCardAppend();
        for (BlackVo blackIncrVo : list) {
            bCardAppend.setApdVer(version);
            bCardAppend.setProvId(Integer.valueOf(blackIncrVo.getIssuerId().substring(0, 2)));
            bCardAppend.setCardId(blackIncrVo.getCardId());
            bCardAppend.setCTime(blackIncrVo.getCreationTime());
            bCardAppend.setITime(blackIncrVo.getInsertTime());
            bCardAppend.setIssuerId(blackIncrVo.getIssuerId());
            bCardAppend.setCStatus(blackIncrVo.getStatus());
            bCardAppend.setCType(blackIncrVo.getType());
            bCardAppendMapper.insertBCardAppend(bCardAppend);
        }
    }

    @Override
    public void jedisInsert(List<BlackVo> list, String version) {
        Jedis jedis = new Jedis(redisHost, redisPort);
        List<RedisValueVo> redisValueVoList;
        Integer db;
        for (BlackVo blackIncrVo : list) {
            redisValueVoList = new ArrayList<>();
            db = Integer.valueOf(blackIncrVo.getIssuerId().substring(0, 2));
            if (blackIncrVo.getStatus() == 1) {
                jedis.select(db);
                {
                    if (StringUtils.isNotBlank(jedis.get(blackIncrVo.getCardId()))) {
                        int flag = 0;
                        redisValueVoList = JSON.parseArray(jedis.get(blackIncrVo.getCardId()), RedisValueVo.class);
                        for (RedisValueVo redisValueVo : redisValueVoList) {
                            if (redisValueVo.getType().equals(blackIncrVo.getType())) {
                                flag = 1;
                                break;
                            }
                        }
                        if (flag == 0) {
                            RedisValueVo redisValueVo = new RedisValueVo();
                            redisValueVo.setType(blackIncrVo.getType());
                            redisValueVo.setVersion(version);
                            redisValueVoList.add(redisValueVo);
                            jedis.set(blackIncrVo.getCardId(), JSON.toJSONString(redisValueVoList));
                        }
                    } else {
                        RedisValueVo redisValueVo = new RedisValueVo();
                        redisValueVo.setType(blackIncrVo.getType());
                        redisValueVo.setVersion(version);
                        redisValueVoList.add(redisValueVo);

                        jedis.set(blackIncrVo.getCardId(), JSON.toJSONString(redisValueVoList));
                    }
                }
            }
            if (blackIncrVo.getStatus() == 2) {
                jedis.select(db);
                {
                    if (StringUtils.isNotBlank(jedis.get(blackIncrVo.getCardId()))) {
                        redisValueVoList = JSON.parseArray(jedis.get(blackIncrVo.getCardId()), RedisValueVo.class);
                        for (RedisValueVo redisValueVo : redisValueVoList) {
                            if (redisValueVo.getType().equals(blackIncrVo.getType())) {
                                redisValueVoList.remove(redisValueVo);
                                if (redisValueVoList.size() == 0) {
                                    jedis.del(blackIncrVo.getCardId());
                                    break;
                                } else {
                                    jedis.set(blackIncrVo.getCardId(), JSON.toJSONString(redisValueVoList));
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

    @Override
    public Boolean findByCardId(String cardId) {
        Jedis jedis = new Jedis(redisHost, redisPort);
        jedis.select(Integer.parseInt(cardId.substring(0, 2)));
        return StringUtils.isNotBlank(jedis.get(cardId));
    }

    public static String backMD5(String inStr) {

        MessageDigest md5 = null;

        try {
            md5 = MessageDigest.getInstance("MD5");

        } catch (Exception e) {

            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];

        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; i++) {

            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();

    }

    public void unzipInside(String version, String resourcePath) {
        //判断生成目录是否生成，如果没有就创建
        File pathFile = new File(resourcePath);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        ZipFile zp = null;
        List<String> dataList = Arrays.asList("11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65");
        File zipFile;
        for (String data : dataList) {
            String zipPath = resourcePath + "\\" + version + "_" + data + ".zip";
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
                            List<BlackVo> list = jsonAnalysis(outpath);
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

    public static String unzip(String zipPath, String resourcePath) {
        //判断生成目录是否生成，如果没有就创建
        File pathFile = new File(resourcePath);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        ZipFile zp = null;
        String version = null;
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
                    BlackIncrValidateVo blackIncrValidateVo = jsonAnalysisVa(outpath);
                    version = blackIncrValidateVo.getVersion();
                }
            }
            zp.close();
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return version;
        }
    }

    public static List<BlackVo> jsonAnalysis(String jsonPath) {

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
            List<BlackVo> listStr = JSON.parseArray(jsonStr, BlackVo.class);
            return listStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BlackIncrValidateVo jsonAnalysisVa(String jsonPath) {

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

    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除指定文件夹下所有文件
    //param path 文件夹完整绝对路径
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }
}
