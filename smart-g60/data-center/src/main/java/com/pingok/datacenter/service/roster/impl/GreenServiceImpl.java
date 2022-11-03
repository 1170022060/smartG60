package com.pingok.datacenter.service.roster.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.domain.roster.green.*;
import com.pingok.datacenter.domain.roster.green.vo.GreenVo;
import com.pingok.datacenter.domain.roster.vo.VersionVo;
import com.pingok.datacenter.mapper.roster.VersionMapper;
import com.pingok.datacenter.mapper.roster.green.*;
import com.pingok.datacenter.service.roster.IGreenService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static com.pingok.datacenter.service.roster.impl.BlackCardServiceImpl.backMD5;
import static com.pingok.datacenter.service.roster.impl.BlackCardServiceImpl.delFolder;

/**
 * 绿通名单 服务层处理
 *
 * @author ruoyi
 */
@Service
public class GreenServiceImpl implements IGreenService {

    @Autowired
    private TblGreenListRecordMapper tblGreenListRecordMapper;
    @Autowired
    private TblGreenStationUsedMapper tblGreenStationUsedMapper;
    @Autowired
    private TblGreenVersionMapper tblGreenVersionMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private VersionMapper versionMapper;

    @Value("${center.host}")
    private String host;

    @Value("${center.stationGB}")
    private String stationGB;

    @Value("${center.greenPath}")
    private String greenPath;

    @Override
    public void green(JSONObject obj) {
        Example example = new Example(TblGreenStationUsed.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("version", obj.getString("version"));
        criteria.andEqualTo("stationHex", obj.getString("stationHex"));
        TblGreenStationUsed stationUsed = tblGreenStationUsedMapper.selectOneByExample(example);
        if (StringUtils.isNull(stationUsed)) {
            stationUsed = new TblGreenStationUsed();
            stationUsed.setId(remoteIdProducerService.nextId());
            stationUsed.setStationHex(obj.getString("stationHex"));
            stationUsed.setApplyTime(obj.getDate("applyTime"));
            stationUsed.setCreateTime(DateUtils.getNowDate());
            stationUsed.setVersion(obj.getString("version"));
            tblGreenStationUsedMapper.insert(stationUsed);
        }
    }

    @Override
    public void greenDownload() {
        String versionNow=versionMapper.selectVersion("TBL_GREEN_VERSION");
        String version = DateUtils.getTimeMinute(DateUtils.getBeforeMillisEndWithMinute0or5(15,DateUtils.getNowDate()));
        if(StringUtils.isNotNull(versionNow) && (versionNow.equals(version)))
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            try {
                // 注意格式需要与上面一致，不然会出现异常
                version=DateUtils.getTimeMinute(DateUtils.getPreTime(sdf.parse(versionNow) ,15));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String url= host + "/api/lane-service/appointment-all-list";
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
                String fileName = version +"Green"+ ".zip";
                File file=new File(greenPath);
                if(!file.exists()){
                    file.mkdirs();
                }
                String pathName=greenPath+"\\"+fileName;
                file  = new File(pathName);
                if(!file.exists()){
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytes,0,bytes.length);
                fos.flush();
                fos.close();
                unzipGreen(pathName,greenPath,version);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void unzipGreen(String zipPath,String resourcePath,String version){
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
                    unzipGreen(outpath,resourcePath,version);
                }
                if(zipEntryName.contains(".json"))
                {
                    List<GreenVo> list =jsonAnalysis(outpath);
                    insertGreen(list,version);
                }
            }
            zp.close();
        }catch ( Exception e){
            e.printStackTrace();
        }
        delFolder(resourcePath);
    }

    @Transactional
    public void insertGreen(List<GreenVo> list, String version) {
        TblGreenVersion greenVersion;

        Example example = new Example(TblGreenVersion.class);
        example.createCriteria().andEqualTo("version", version);
        greenVersion = tblGreenVersionMapper.selectOneByExample(example);
        if (StringUtils.isNull(greenVersion)) {
            greenVersion = new TblGreenVersion();
            greenVersion.setId(remoteIdProducerService.nextId());
            greenVersion.setVersion(version);
            tblGreenVersionMapper.insert(greenVersion);
            for (GreenVo greenVo : list) {
                TblGreenListRecord greenListRecord=new TblGreenListRecord();
                greenListRecord.setId(remoteIdProducerService.nextId());
                greenListRecord.setVersionId(greenVersion.getId());
                BeanUtils.copyNotNullProperties(greenVo, greenListRecord);
                greenListRecord.setPhone(greenVo.getId().substring(0,11));
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = null;
                try {
                    date = format.parse(greenVo.getId().substring(11));
                    greenListRecord.setAppointTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMddHH");
                Date date2 = null;
                try {
                    date2 = format2.parse(greenVo.getEndTransportTime());
                    greenListRecord.setEndTransportTime(date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                greenListRecord.setDbTime(DateUtils.getNowDate());
                tblGreenListRecordMapper.insert(greenListRecord);
            }
        }
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
