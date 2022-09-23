package com.pingok.datacenter.service.gantry.impl;

import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.domain.gantry.*;
import com.pingok.datacenter.domain.gantry.model.*;
import com.pingok.datacenter.mapper.gantry.*;
import com.pingok.datacenter.service.gantry.IGantryUpperService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.api.RemoteFileService;
import com.ruoyi.system.api.RemoteIdProducerService;
import com.ruoyi.system.api.domain.SysFile;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author
 * @time 2022/5/20 12:23
 */
@Slf4j
@Service
public class GantryUpperServiceImpl implements IGantryUpperService {

    @Autowired
    private TblGantryInfoMapper tblGantryInfoMapper;
    @Autowired
    private TblGantryInfoDtlMapper tblGantryInfoDtlMapper;
    @Autowired
    private TblGantryPictureMapper tblGantryPictureMapper;
    @Autowired
    private TblGantryPictureFailMapper tblGantryPictureFailMapper;
    @Autowired
    private TblGantrySumTransactionMapper tblGantrySumTransactionMapper;
    @Autowired
    private TblGantrySumTravelImageMapper tblGantrySumTravelImageMapper;
    @Autowired
    private TblGantryTransactionMapper tblGantryTransactionMapper;
    @Autowired
    private TblGantryTravelImageMapper tblGantryTravelImageMapper;
    @Autowired
    private RemoteFileService remoteFileService;
    @Autowired
    private TblGantryLogfileMapper tblGantryLogfileMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public void handleViu(List<TblGantryTravelImage> data) {
        try {
            for (TblGantryTravelImage g : data) {
                if (!tblGantryTravelImageMapper.existsWithPrimaryKey(g.getPicId())) {
                    tblGantryTravelImageMapper.insert(g);
                } else {
                    tblGantryTravelImageMapper.updateByPrimaryKey(g);
                }
            }
        } catch (Exception ex) {
            log.error("存储门架牌识数据异常：" + ex.getMessage());
        }
    }

    @Override
    public void handleVipu(List<TblGantryPicture> data) {
        try {
            for (TblGantryPicture g : data) {
                if (!tblGantryPictureMapper.existsWithPrimaryKey(g.getPicId())) {
                    tblGantryPictureMapper.insert(g);
                } else {
                    tblGantryPictureMapper.updateByPrimaryKey(g);
                }
            }
        } catch (Exception ex) {
            log.error("存储门架牌识图片异常：" + ex.getMessage());
        }
    }


    @Override
    public void handleSvipu(List<TblGantryPictureFail> data) {
        try {
            for (TblGantryPictureFail g : data) {
                if (!tblGantryPictureFailMapper.existsWithPrimaryKey(g.getPicId())) {
                    tblGantryPictureFailMapper.insert(g);
                } else {
                    tblGantryPictureFailMapper.updateByPrimaryKey(g);
                }
            }
        } catch (Exception ex) {
            log.error("存储门架牌识图片异常：" + ex.getMessage());
        }
    }

    @Override
    public void handleEtctu(List<TblGantryTransaction> data) {
        try {
            for (TblGantryTransaction g : data) {
                if (!tblGantryTransactionMapper.existsWithPrimaryKey(g.getTradeId())) {
                    tblGantryTransactionMapper.insert(g);
                } else {
                    tblGantryTransactionMapper.updateByPrimaryKey(g);
                }
            }
        } catch (Exception ex) {
            log.error("存储门架交易流水异常：" + ex.getMessage());
        }
    }

    @Override
    public void handleEtcsu(List<TblGantrySumTransaction> data) {
        try {
            for (TblGantrySumTransaction g : data) {
                if (!tblGantryTransactionMapper.existsWithPrimaryKey(g.getCollectId())) {
                    tblGantrySumTransactionMapper.insert(g);
                } else {
                    tblGantrySumTransactionMapper.updateByPrimaryKey(g);
                }
            }
        } catch (Exception ex) {
            log.error("存储ETC 门架交易小时批次汇总异常：" + ex.getMessage());
        }
    }

    @Override
    public void handleVisu(List<TblGantrySumTravelImage> data) {
        try {
            for (TblGantrySumTravelImage g : data) {
                if (!tblGantrySumTravelImageMapper.existsWithPrimaryKey(g.getCollectId())) {
                    tblGantrySumTravelImageMapper.insert(g);
                } else {
                    tblGantrySumTravelImageMapper.updateByPrimaryKey(g);
                }
            }
        } catch (Exception ex) {
            log.error("存储ETC 门架牌识小时批次汇总异常：" + ex.getMessage());
        }
    }

    @Override
    public void handleLog(JSONObject data) {
        try {
            String gantryId = null;
            String path = "/root/"+DateUtils.getNowTimestampLong();
            String fileName = data.getString("reqFileName") + ".zip";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
            String pathName = path + "/" + fileName;
            file = new File(pathName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data.getString("data").getBytes(), 0, data.getString("data").getBytes().length);
            fos.flush();
            fos.close();
            ZipFile zp = new ZipFile(pathName, Charset.forName("gbk"));
            Enumeration entries = zp.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String zipEntryName = entry.getName();
                if (zipEntryName.contains(".json")) {
                    FileReader fileReader = new FileReader(pathName);
                    Reader reader = new InputStreamReader(new FileInputStream(pathName), "utf-8");
                    int ch = 0;
                    StringBuffer sb = new StringBuffer();
                    while ((ch = reader.read()) != -1) {
                        sb.append((char) ch);
                    }
                    fileReader.close();
                    reader.close();
                    JSONObject object = JSONObject.parseObject(sb.toString());
                    gantryId = object.getString("gantryId");
                    break;
                }
            }
            zp.close();
            file.delete();
            InputStream inputStream = new ByteArrayInputStream(data.getString("data").getBytes());
            MultipartFile mFile = new MockMultipartFile(ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
            R<SysFile> r = remoteFileService.upload(mFile);
            if (r != null) {
                if (R.SUCCESS == r.getCode()) {
                    if(gantryId!=null){
                        Example example = new Example(TblGantryLogfile.class);
                        example.createCriteria().andEqualTo("gantryId",gantryId);
                        TblGantryLogfile tblGantryLogfile = tblGantryLogfileMapper.selectOneByExample(example);
                        if(tblGantryLogfile==null){
                            tblGantryLogfile = new TblGantryLogfile();
                            tblGantryLogfile.setGantryId(gantryId);
                            tblGantryLogfile.setId(remoteIdProducerService.nextId());
                            tblGantryLogfile.setUrl(r.getData().getUrl());
                            tblGantryLogfileMapper.insert(tblGantryLogfile);
                        }else {
                            tblGantryLogfile.setUrl(r.getData().getUrl());
                            tblGantryLogfileMapper.updateByPrimaryKey(tblGantryLogfile);
                        }
                    }else {
                        throw new ServiceException("门架日志存储失败，原因：JSON文件内未包含门架编号");
                    }
                } else {
                    throw new ServiceException("门架日志存储失败，原因：" + r.getMsg());
                }
            } else {
                throw new ServiceException("门架日志存储失败，原因未知");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 调取相关上传异常数据，对上传异常数据进行人工干预修正
    @Override
    public AjaxResult GetErrorData(GetErrorDataModel body) {
        body.setMsgId(genMsgId(body.getGantryId()));
        body.setMsgTime(DateUtils.dateTimeNow("yyyy-MM-dd'T'HH:mm:ss"));
        String data = JSONObject.toJSONString(body);
        JSONObject ret = send(body.getGantryId(), "NOTIFY_GETERRORDATA", data);
        if (ret == null) {
            return AjaxResult.error();
        }
        return AjaxResult.success(ret);
    }

    @Override
    public AjaxResult FixErrorData(FixErrorDataModel body) {
        body.setMsgId(genMsgId(body.getGantryId()));
        body.setMsgTime(DateUtils.dateTimeNow("yyyy-MM-dd'T'HH:mm:ss"));
        String data = JSONObject.toJSONString(body);
        JSONObject ret = send(body.getGantryId(), "NOTIFY_FIXERRORDATA", data);
        if (ret == null) {
            return AjaxResult.error();
        }
        return AjaxResult.success(ret);
    }

    @Override
    public AjaxResult PicRealTransfer(PicRealTransferModel body) {
        String data = JSONObject.toJSONString(body);
        JSONObject ret = send(body.getGantryId(), "NOTIFY_PICREALTRANSFER", data);
        if (ret == null) {
            return AjaxResult.error();
        }
        return AjaxResult.success(ret);
    }

    // 牌识数据重传
    @Override
    public AjaxResult VIUpload(VIUploadModel body) {
        body.setMsgId(genMsgId(body.getGantryId()));
        body.setMsgTime(DateUtils.dateTimeNow("yyyy-MM-dd'T'HH:mm:ss"));
        String data = JSONObject.toJSONString(body);
        JSONObject ret = send(body.getGantryId(), "NOTIFY_VIUPLOAD", data);
        if (ret == null) {
            return AjaxResult.error();
        }
        return AjaxResult.success();
    }

    // 牌识图片按需调取
    @Override
    public AjaxResult VIPUpload(VIPUploadModel body) {
        body.setMsgId(genMsgId(body.getGantryId()));
        body.setMsgTime(DateUtils.dateTimeNow("yyyy-MM-dd'T'HH:mm:ss"));
        String data = JSONObject.toJSONString(body);
        JSONObject ret = send(body.getGantryId(), "NOTIFY_VIPUPLOAD", data);
        if (ret == null) {
            return AjaxResult.error();
        }
        return AjaxResult.success();
    }

    // 交易重传
    @Override
    public AjaxResult ETCTUpload(ETCTUploadModel body) {
        body.setMsgId(genMsgId(body.getGantryId()));
        body.setMsgTime(DateUtils.dateTimeNow("yyyy-MM-dd'T'HH:mm:ss"));
        String data = JSONObject.toJSONString(body);
        JSONObject ret = send(body.getGantryId(), "NOTIFY_ETCTUPLOAD", data);
        if (ret == null) {
            return AjaxResult.error();
        }
        return AjaxResult.success();
    }

    // 小时批次汇总重传
    @Override
    public AjaxResult HourSumUpload(HourSumUploadModel body) {
        body.setMsgId(genMsgId(body.getGantryId()));
        body.setMsgTime(DateUtils.dateTimeNow("yyyy-MM-dd'T'HH:mm:ss"));
        String data = JSONObject.toJSONString(body);
        JSONObject ret = send(body.getGantryId(), "NOTIFY_HOURSUMUPLOAD", data);
        if (ret == null) {
            return AjaxResult.error();
        }
        return AjaxResult.success();
    }

    // 日志文件按需调取
    @Override
    public AjaxResult LogBUpload(LogBUploadModel body) {
        body.setMsgId(genMsgId(body.getGantryId()));
        body.setMsgTime(DateUtils.dateTimeNow("yyyy-MM-dd'T'HH:mm:ss"));
        String data = JSONObject.toJSONString(body);
        JSONObject ret = send(body.getGantryId(), "NOTIFY_LOGBUPLOAD", data);
        if (ret == null) {
            return AjaxResult.error();
        }
        return AjaxResult.success();
    }

    // 生成msgId
    String genMsgId(String gantryId) {
        return String.format("%s%s%02d", gantryId, DateUtils.dateTimeNow(), 1);
    }

    // 生成请求文件名
    String genFileName(String reqType, String gantryId) {
        return String.format("%s_REQ_%s_%s@_@json", reqType,
                DateUtils.dateTimeNow("yyyyMMddHHmmssSSSS"), gantryId.substring(0, 16));
    }

    // 获取请求的后台地址
    String getUrl(String reqType, String gantryId) {
        Example example = new Example(TblGantryInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("gantryId", gantryId);
        TblGantryInfo tblGantryInfo = tblGantryInfoMapper.selectOneByExample(example);

        example = new Example(TblGantryInfoDtl.class);
        criteria = example.createCriteria();
        criteria.andEqualTo("InfoId", tblGantryInfo.getId());
        TblGantryInfoDtl tblGantryInfoDtl = tblGantryInfoDtlMapper.selectOneByExample(example);
        String url = String.format("http://%s:8080/etcdfs/api/%s/bin/%s", tblGantryInfoDtl.getMainIp(),
                gantryId.substring(0, 16), genFileName(reqType, gantryId));
        return url;
    }

    JSONObject send(String gantryId, String reqType, String data) {
        String url = getUrl(reqType, gantryId);
        String reqFileName = genFileName(reqType, gantryId);
        OkHttpClient client = new OkHttpClient();
        MultipartBody binFile = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("binFile", reqFileName, RequestBody.create(
                        MediaType.parse("application/octet-stream"), data.getBytes(StandardCharsets.UTF_8)))
                .build();
        Request req = new Request.Builder().url(url).post(binFile).build();
        JSONObject ret = null;
        try {
            // 改为同步模式，解析后再发送给前端？
            Call call = client.newCall(req);
            Response resp = call.execute();
            if (resp.isSuccessful()) {
                ret = JSONObject.parseObject(resp.body().string());
            }
            // 异步模式
//            call.enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    log.error("门架服务器返回失败：" + reqType + "," + e.getMessage());
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    JSONObject jo = JSONObject.parseObject(response.body().string());
//                    String fileName = jo.getString("binFile");
//                    if(!fileName.isEmpty()) {
//                        String respType = getRespType(fileName);
//                        switch (respType) {
//                            case "NOTIFY_GETERRORDATA": handleGetErrorData(jo); break;
//                            case "NOTIFY_FIXERRORDATA": handleFixErrorData(jo); break;
//                            case "NOTIFY_PICREALTRANSFER": handlePicRealTransfer(jo); break;
//                        }
//                    }
//                }
//            });
        } catch (Exception e) {
            log.error("发送请求至门架服务器失败：", e.getMessage());
        }
        return ret;
    }

    // 解析返回类型
    String getRespType(String respFileName) {
        int pos = respFileName.indexOf('_');
        pos = respFileName.indexOf('_', pos + 1);
        return respFileName.substring(0, pos);
    }
}
