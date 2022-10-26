package com.pingok.datacenter.service.roster.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.domain.roster.rate.TblRateVersion;
import com.pingok.datacenter.domain.roster.rate.vo.RateFVo;
import com.pingok.datacenter.domain.roster.rate.vo.RateVersionVo;
import com.pingok.datacenter.domain.roster.rate.vo.RateRVo;
import com.pingok.datacenter.domain.roster.vo.VersionVo;
import com.pingok.datacenter.domain.roster.rate.TblRateStationUsed;
import com.pingok.datacenter.mapper.roster.rate.TblRateMapper;
import com.pingok.datacenter.mapper.roster.rate.TblRateProvMapper;
import com.pingok.datacenter.mapper.roster.rate.TblRateStationUsedMapper;
import com.pingok.datacenter.mapper.roster.rate.TblRateVersionMapper;
import com.pingok.datacenter.service.roster.IRateService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static com.pingok.datacenter.service.roster.impl.BlackCardServiceImpl.backMD5;

/**
 * 最小费率 服务层处理
 *
 * @author ruoyi
 */
@Service
public class RateServiceImpl implements IRateService {

    @Autowired
    private TblRateMapper tblRateMapper;
    @Autowired
    private TblRateVersionMapper tblRateVersionMapper;
    @Autowired
    private TblRateProvMapper tblRateProvMapper;
    @Autowired
    private TblRateStationUsedMapper tblRateStationUsedMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Value("${center.host}")
    private String host;

    @Value("${center.ratePath}")
    private String ratePath;

    @Override
    public void rate(JSONObject obj) {
        TblRateStationUsed stationUsed = new TblRateStationUsed();
        stationUsed.setId(remoteIdProducerService.nextId());
        stationUsed.setStationHex(obj.getString("stationHex"));
        stationUsed.setApplyTime(obj.getDate("applyTime"));
        stationUsed.setCreateTime(DateUtils.getNowDate());
        stationUsed.setVersion(obj.getString("version"));
        tblRateStationUsedMapper.insert(stationUsed);
    }

    @Override
    public void rateDownload(String version) {
        List<String> stringList= tblRateMapper.selectStationGB();
        for (String stationGB:stringList)
        {
            String url = host + "/api/lane-service/all-road-fee";
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
                    File file = new File(ratePath);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    String pathName = ratePath + "\\" + fileName;
                    file = new File(pathName);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(bytes, 0, bytes.length);
                    fos.flush();
                    fos.close();
                    RateVersionVo rateVersionVo=unzip(pathName, ratePath);
                    if (version.equals(rateVersionVo.getVersion())) {
                        Long versionId=insertVersion(rateVersionVo);

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public RateVersionVo unzip(String zipPath, String resourcePath) {
        //判断生成目录是否生成，如果没有就创建
        File pathFile = new File(resourcePath);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        ZipFile zp = null;
        RateVersionVo rateVersionVo = null;
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
                    rateVersionVo = jsonAnalysisVa(outpath);
                }
            }
            zp.close();
            return rateVersionVo;
        } catch (Exception e) {
            e.printStackTrace();
            return rateVersionVo;
        }
    }

    public Long insertVersion(RateVersionVo rateVersionVo) {
        TblRateVersion tblRateVersion;

        Example example = new Example(TblRateVersion.class);
        example.createCriteria().andEqualTo("version", rateVersionVo.getVersion());
        tblRateVersion = tblRateVersionMapper.selectOneByExample(example);
        if (StringUtils.isNull(tblRateVersion)) {
            tblRateVersion = new TblRateVersion();
            tblRateVersion.setId(remoteIdProducerService.nextId());
            tblRateVersion.setVersion(rateVersionVo.getVersion());
            tblRateVersion.setValidTime(rateVersionVo.getValidTime());
        }
        return tblRateVersion.getId();
    }

    public static RateVersionVo jsonAnalysisVa(String jsonPath) {

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
            RateVersionVo listStr = JSON.parseObject(jsonStr, RateVersionVo.class);
            return listStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<RateRVo> jsonAnalysisR(String jsonPath) {
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
            List<RateRVo> listStr = JSON.parseArray(jsonStr, RateRVo.class);
            return listStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<RateFVo> jsonAnalysisF(String jsonPath) {
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
            List<RateFVo> listStr = JSON.parseArray(jsonStr, RateFVo.class);
            return listStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
