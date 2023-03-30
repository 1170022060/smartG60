package com.pingok.datacenter.service.roster.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.config.CenterConfig;
import com.pingok.datacenter.domain.roster.blackcard.TblBlackCard;
import com.pingok.datacenter.domain.roster.blackcard.TblBlackCardLog;
import com.pingok.datacenter.domain.roster.blackcard.TblBlackCardStationUsed;
import com.pingok.datacenter.domain.roster.blackcard.TblBlackCardVersion;
import com.pingok.datacenter.domain.roster.blackcard.vo.BlackIncrValidateVo;
import com.pingok.datacenter.domain.roster.blackcard.vo.BlackVo;
import com.pingok.datacenter.domain.roster.vo.VersionAllVo;
import com.pingok.datacenter.domain.roster.vo.VersionVo;
import com.pingok.datacenter.mapper.roster.VersionMapper;
import com.pingok.datacenter.mapper.roster.blackcard.TblBlackCardLogMapper;
import com.pingok.datacenter.mapper.roster.blackcard.TblBlackCardMapper;
import com.pingok.datacenter.mapper.roster.blackcard.TblBlackCardStationUsedMapper;
import com.pingok.datacenter.mapper.roster.blackcard.TblBlackCardVersionMapper;
import com.pingok.datacenter.service.roster.IBlackCardService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.*;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 状态名单 服务层处理
 *
 * @author ruoyi
 */
@Service
public class BlackCardServiceImpl implements IBlackCardService {

    @Autowired
    private TblBlackCardStationUsedMapper tblBlackCardStationUsedMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private TblBlackCardMapper tblBlackCardMapper;
    @Autowired
    private TblBlackCardLogMapper tblBlackCardLogMapper;
    @Autowired
    private TblBlackCardVersionMapper tblBlackCardVersionMapper;
    @Autowired
    private VersionMapper versionMapper;

    

    @Override
    public void blackCard(JSONObject obj) {
        Example example = new Example(TblBlackCardStationUsed.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("version", obj.getString("version"));
        criteria.andEqualTo("stationHex", obj.getString("stationHex"));
        TblBlackCardStationUsed stationUsed = tblBlackCardStationUsedMapper.selectOneByExample(example);
        if (StringUtils.isNull(stationUsed)) {
            stationUsed = new TblBlackCardStationUsed();
            stationUsed.setId(remoteIdProducerService.nextId());
            stationUsed.setStationHex(obj.getString("stationHex"));
            stationUsed.setApplyTime(obj.getDate("applyTime"));
            stationUsed.setCreateTime(DateUtils.getNowDate());
            stationUsed.setVersion(obj.getString("version"));
            tblBlackCardStationUsedMapper.insert(stationUsed);
        }
    }

    @Override
    public void increment() {
        String versionNow=versionMapper.selectVersion("TBL_BLACK_CARD_VERSION");
        String version = DateUtils.getTimeMinute(DateUtils.getBeforeMillisEndWithMinute0or5(5,DateUtils.getNowDate()));
        if(StringUtils.isNotNull(versionNow) && (versionNow.equals(version)))
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            try {
                // 注意格式需要与上面一致，不然会出现异常
                version=DateUtils.getTimeMinute(DateUtils.getPreTime(sdf.parse(versionNow) ,5));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String url = CenterConfig.HOST + "/api/lane-service/black-incr-list";
        OkHttpClient client = new OkHttpClient();
        VersionVo versionVo = new VersionVo();
        versionVo.setVersion(version);
        String jsonStr = JSON.toJSONString(versionVo);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonStr);
        final Request request = new Request.Builder()
                .url(url)
                .addHeader("AuthCode", CenterConfig.STATIONGB)
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
                File file = new File(CenterConfig.CARDPATH);
                if (!file.exists()) {
                    file.mkdir();
                }
                String pathName = CenterConfig.CARDPATH + "/" + fileName;
                file = new File(pathName);
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytes, 0, bytes.length);
                fos.flush();
                fos.close();
                if (version.equals(unzip(pathName, CenterConfig.CARDPATH))) {
                    unzipInside(version, CenterConfig.CARDPATH);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void all(String version) {
        List<String> dataList = Arrays.asList("11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65","99");
        for (String province : dataList) {
            String url = CenterConfig.HOST+"/api/lane-service/black-all-list";
            OkHttpClient client = new OkHttpClient();
            VersionAllVo versionAllVo = new VersionAllVo();
            versionAllVo.setVersion(version);
            versionAllVo.setProvinceId(province);
            String jsonStr = JSON.toJSONString(versionAllVo);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonStr);
            final Request request = new Request.Builder()
                    .url(url)
                    .addHeader("AuthCode", CenterConfig.STATIONGB)
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
                    File file = new File(CenterConfig.CARDPATH+"_all");
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    file = new File(CenterConfig.CARDPATH+"_all" + "/" + province);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    String pathName = CenterConfig.CARDPATH+"_all" + "/" + province + "/" + fileName;
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

    @Override
    public void unzipAll(String version) {
        ZipFile zp = null;
        List<String> dataList = Arrays.asList("11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65" ,"99");
        File zipFile;
        for (String province : dataList) {
            String zipPath = CenterConfig.CARDPATH+"_all" + "/" + province + "/" + version + "_" + province + ".zip";
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
                        String outpath = (CenterConfig.CARDPATH + "_all" + "/" + province + "/" + zipEntryName).replace("/", File.separator);
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
                            insertAll(list, version);
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
                String outpath = (resourcePath + "/" + zipEntryName).replace("/", File.separator);
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
            String zipPath = resourcePath + "/" + version + "_" + data + ".zip";
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
                        String outpath = (resourcePath + "/" + zipEntryName).replace("/", File.separator);
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

    public void insert(List<BlackVo> list, String version) {
        TblBlackCard blackCard;
        TblBlackCardLog blackCardLog;
        TblBlackCardVersion blackCardVersion;
        TblBlackCardVersion blackCardVersion2;

        Example example = new Example(TblBlackCardVersion.class);
        example.createCriteria().andEqualTo("version", version);
        blackCardVersion = tblBlackCardVersionMapper.selectOneByExample(example);
        if (StringUtils.isNull(blackCardVersion)) {
            blackCardVersion=new TblBlackCardVersion();
            blackCardVersion.setId(remoteIdProducerService.nextId());
            blackCardVersion.setVersion(version);
            tblBlackCardVersionMapper.insert(blackCardVersion);
        }

        for (BlackVo blackIncrVo : list) {
            example = new Example(TblBlackCard.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("cardId", blackIncrVo.getCardId());
            criteria.andEqualTo("type", blackIncrVo.getType());
            blackCard = tblBlackCardMapper.selectOneByExample(example);
            if (StringUtils.isNull(blackCard)) {
                blackCard = new TblBlackCard();
                blackCard.setId(remoteIdProducerService.nextId());
                BeanUtils.copyNotNullProperties(blackIncrVo, blackCard);
                blackCard.setVersionId(blackCardVersion.getId());
                blackCard.setUpdateTime(DateUtils.getNowDate());
                tblBlackCardMapper.insert(blackCard);

                blackCardLog = new TblBlackCardLog();
                BeanUtils.copyNotNullProperties(blackCard, blackCardLog);
                blackCardLog.setId(remoteIdProducerService.nextId());
                tblBlackCardLogMapper.insert(blackCardLog);
            } else {
                Example example2=new Example(TblBlackCardVersion.class);
                example2.createCriteria().andEqualTo("id", blackCard.getVersionId());
                blackCardVersion2 = tblBlackCardVersionMapper.selectOneByExample(example2);

                if (Long.parseLong(blackCardVersion2.getVersion()) < Long.parseLong(version)) {
                    BeanUtils.copyNotNullProperties(blackIncrVo, blackCard);
                    blackCard.setVersionId(blackCardVersion.getId());
                    blackCard.setUpdateTime(DateUtils.getNowDate());
                    tblBlackCardMapper.updateByPrimaryKey(blackCard);

                    blackCardLog = new TblBlackCardLog();
                    BeanUtils.copyNotNullProperties(blackCard, blackCardLog);
                    blackCardLog.setId(remoteIdProducerService.nextId());
                    tblBlackCardLogMapper.insert(blackCardLog);
                }
            }
        }
    }

    public void insertAll(List<BlackVo> list, String version) {
        TblBlackCard blackCard;
        TblBlackCardVersion blackCardVersion;
        TblBlackCardVersion blackCardVersion2;

        Example example = new Example(TblBlackCardVersion.class);
        example.createCriteria().andEqualTo("version", version);
        blackCardVersion = tblBlackCardVersionMapper.selectOneByExample(example);
        if (StringUtils.isNull(blackCardVersion)) {
            blackCardVersion=new TblBlackCardVersion();
            blackCardVersion.setId(remoteIdProducerService.nextId());
            blackCardVersion.setVersion(version);
            tblBlackCardVersionMapper.insert(blackCardVersion);
        }


        for (BlackVo blackIncrVo : list) {
            example = new Example(TblBlackCard.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("cardId", blackIncrVo.getCardId());
            criteria.andEqualTo("versionId", blackCardVersion.getId());
            blackCard = tblBlackCardMapper.selectOneByExample(example);
            if (StringUtils.isNull(blackCard)) {
                blackCard = new TblBlackCard();
                blackCard.setId(remoteIdProducerService.nextId());
                BeanUtils.copyNotNullProperties(blackIncrVo, blackCard);
                blackCard.setVersionId(blackCardVersion.getId());
                blackCard.setUpdateTime(DateUtils.getNowDate());
                tblBlackCardMapper.insert(blackCard);
            } else {
                Example example2=new Example(TblBlackCardVersion.class);
                example2.createCriteria().andEqualTo("id", blackCard.getVersionId());
                blackCardVersion2 = tblBlackCardVersionMapper.selectOneByExample(example2);

                if (Long.parseLong(blackCardVersion2.getVersion()) < Long.parseLong(version)) {
                    BeanUtils.copyNotNullProperties(blackIncrVo, blackCard);
                    blackCard.setVersionId(blackCardVersion.getId());
                    blackCard.setUpdateTime(DateUtils.getNowDate());
                    tblBlackCardMapper.updateByPrimaryKey(blackCard);
                }
            }
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
