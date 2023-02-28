package com.pingok.datacenter.service.provincialCenters.impl;

import cn.com.infosec.netsign.agent.NetSignAgent;
import cn.com.infosec.netsign.agent.NetSignResult;
import cn.com.infosec.netsign.agent.exception.NetSignAgentException;
import cn.com.infosec.netsign.agent.exception.ServerProcessException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.config.EncryptConfig;
import com.pingok.datacenter.config.ProvincialCentersConfig;
import com.pingok.datacenter.service.provincialCenters.IProvincialCentersService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.file.FileUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.*;

/**
 * 服务层处理
 *
 * @author qiumin
 */
@Slf4j
@Service
public class ProvincialCentersServiceImpl implements IProvincialCentersService {

    @Override
    public JSONArray getData(String name, Date dateTimeNow) {
        JSONArray jsonArray = new JSONArray();
        String dateTime = DateUtils.dateTime(dateTimeNow, DateUtils.YYYYMMDDHHMM)+"00000";
        String reqTime = DateUtils.dateTime(dateTimeNow, DateUtils.YYYY_MM_DD) + "T" + DateUtils.dateTime(dateTimeNow, DateUtils.HH_MM)+":00";
        String fileName = name + "_" + dateTime + ".json";
        String filePath = "/data/file/provincialCenters/" + DateUtils.datePath() + "/";
        File file;
        File rFile;
        Response response = null;
        RequestBody fileBody;
        MultipartBody mBody;
        Request request;
        OkHttpClient client;
        String result;
        FileOutputStream fos = null;
        try {
            JSONObject reqContent = new JSONObject();
            reqContent.put("reqTime", reqTime);
            boolean tOf = FileUtils.string2File(reqContent.toJSONString(), filePath + fileName);
            if (tOf) {
                file = new File(filePath + fileName);
                Map<String, String> treeMap = new TreeMap<>();
                treeMap.put("binFile", DigestUtils.md5DigestAsHex(new FileInputStream(ResourceUtils.getFile(filePath + fileName))).toUpperCase());
                treeMap.put("filename", fileName);
                treeMap.put("signType", "SM3");
                treeMap.put("encryptType", "NONE");
                treeMap.put("version", "1.0");

                fileBody = RequestBody.create(MediaType.parse("multipart/form-data;boundary=inputstream"), file);
                mBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("binFile", fileName, fileBody)
                        .addFormDataPart("filename", treeMap.get("filename"))
                        .addFormDataPart("signType", treeMap.get("signType"))
                        .addFormDataPart("encryptType", treeMap.get("encryptType"))
                        .addFormDataPart("version", treeMap.get("version"))
                        .addFormDataPart("sign", sign(treeMap))
                        .build();
                request = new Request.Builder()
                        .url(ProvincialCentersConfig.HOST + fileName)
                        .header("binfile-gzip", "flase")
                        .header("binfile-auth", ProvincialCentersConfig.TICKET)
                        .post(mBody)
                        .build();
                client = new OkHttpClient();
                response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    fileName = FileUtils.getHeaderFileName(response);
                    byte[] bytes = response.body().bytes();
                    if (bytes.length > 0) {
                        rFile = new File(filePath + fileName);
                        if (!rFile.exists()) {
                            rFile.createNewFile();
                        }
                        fos = new FileOutputStream(rFile);
                        fos.write(bytes, 0, bytes.length);
                        List<String> fileNames = FileUtils.unzip(filePath + fileName, filePath);

                        BufferedReader reader;
                        StringBuilder stringBuilder;
                        String line;
                        String content;
                        JSONObject job;
                        JSONArray jar;
                        for (String fn : fileNames) {
                            reader = new BufferedReader(new FileReader(filePath + fn));
                            stringBuilder = new StringBuilder();
                            while ((line = reader.readLine()) != null) {
                                stringBuilder.append(line);
                            }
                            reader.close();
                            content = stringBuilder.toString();
                            job = JSON.parseObject(content);
                            if (job.containsKey("list")) {
                                jar = job.getJSONArray("list");
                                jsonArray.addAll(jar);
                            }
                        }
                    }
                } else {
                    result = response.body().string();
                    log.error(fileName + "请求失败，原因：" + result);
                }

            } else {
                log.error(fileName + "字符串转文件失败");
            }
        } catch (Exception e) {
            log.error(fileName + "请求失败，原因：" + e.getMessage());
        } finally {
            if (response != null) {
                response.close();
            }
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return jsonArray;
    }

    private String sign(Map<String, String> treeMap) {
        String sign = "";
        Set keyset = treeMap.keySet();
        for (Object key : keyset) {
            if (!key.toString().equals("filename")) {
                sign = sign + "&" + key + "=" + treeMap.get(key);
            }
        }
        sign = sign.replaceFirst("&", "");
        sign = sign + "&filename=" + treeMap.get("filename");
        try {
            NetSignAgent.initialize("/root/certs/netsignagent.properties");
            NetSignResult signResult = NetSignAgent.attachedSignature(sign.getBytes("GBK"), EncryptConfig.SUBJECT, EncryptConfig.DIGESTALG, false);
            sign = signResult.getStringResult("Base64_Signed_Text");
        } catch (NetSignAgentException e) {
            throw new RuntimeException(e);
        } catch (ServerProcessException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return sign;
    }


}
