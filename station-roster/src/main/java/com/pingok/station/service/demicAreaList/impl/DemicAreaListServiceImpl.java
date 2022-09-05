package com.pingok.station.service.demicAreaList.impl;

import com.alibaba.fastjson.JSON;
import com.pingok.station.domain.demicAreaList.EpidemicArea;
import com.pingok.station.domain.demicAreaList.vo.DemicVo;
import com.pingok.station.domain.tracer.ListTracer;
import com.pingok.station.domain.vo.VersionVo;
import com.pingok.station.mapper.tracer.ListTracerMapper;
import com.pingok.station.mapper.demicAreaList.EpidemicAreaMapper;
import com.pingok.station.service.demicAreaList.IDemicAreaListService;
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

/**
 * @author
 * @time 2022/4/13 16:32
 */
@Slf4j
@Service
public class DemicAreaListServiceImpl implements IDemicAreaListService {

    @Autowired
    private EpidemicAreaMapper epidemicAreaMapper;

    @Autowired
    private ListTracerMapper listTracerMapper;

    @Value("${center.host}")
    private String host;

    @Value("${center.stationGB}")
    private String stationGB;

    @Value("${center.demicPath}")
    private String demicPath;

    @Override
    public void demicAreaList(String version) {
        String url= host + "/api/lane-service/epidemic-area-list";
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
                String fileName = version +"Demic"+ ".zip";
                File file=new File(demicPath);
                if(!file.exists()){
                    file.mkdirs();
                }
                String pathName=demicPath+"\\"+fileName;
                file  = new File(pathName);
                if(!file.exists()){
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytes,0,bytes.length);
                fos.flush();
                fos.close();
                unzipDemic(pathName,demicPath,version);
                ListTracer listTracer=new ListTracer();
                listTracer.setListType("demiclist");
                listTracer.setVersion(version);
                if(listTracerMapper.selectListType("demiclist")==0)
                {
                    listTracerMapper.insertTracer("demiclist");
                    listTracerMapper.updateTracer(listTracer);
                }else if (listTracerMapper.selectListType("demiclist")==1)
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
    public void insertDemicArea(List<DemicVo> list,String version) {
        EpidemicArea epidemicArea =new EpidemicArea();
        epidemicAreaMapper.deleteAll();
        for(DemicVo demicVo : list) {
            BeanUtils.copyNotNullProperties(demicVo,epidemicArea);
            epidemicArea.setStationID(demicVo.getStationId());
            epidemicArea.setStationHEX(demicVo.getStationHex());
            epidemicArea.setStartTime(demicVo.getEffective());
            epidemicArea.setVersion(version);
            epidemicAreaMapper.insertDemicArea(epidemicArea);
        }
    }

    @Override
    public void test(String version) {
        unzipDemic(demicPath+"\\2022-07-15.zip",demicPath,version);
    }

    public void unzipDemic(String zipPath,String resourcePath,String version){
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
                    unzipDemic(outpath,resourcePath,version);
                }
                if(zipEntryName.contains(".json"))
                {
                    List<DemicVo> list =jsonAnalysis(outpath);
                    insertDemicArea(list,version);
                }
            }
            zp.close();
        }catch ( Exception e){
            e.printStackTrace();
        }
        delFolder(resourcePath);
    }

    public static List<DemicVo> jsonAnalysis(String jsonPath){
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
            List<DemicVo> listStr = JSON.parseArray(jsonStr, DemicVo.class);
            return listStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
