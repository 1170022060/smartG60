package com.pingok.charge.service.gantry.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.charge.config.GantryConfig;
import com.pingok.charge.domain.gantry.vo.GantryV2X;
import com.pingok.charge.service.gantry.IGantryV2XService;
import com.ruoyi.common.core.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class GantryV2XServiceImpl implements IGantryV2XService {

    @Override
    public boolean sendEvent(JSONObject data) {

        boolean ret = true;

        GantryV2X v2x = JSON.parseObject(data.toJSONString(), GantryV2X.class);
        String url = String.format("http://%s:%s", v2x.getGantryID(), GantryConfig.V2XUrl);
        String filename = String.format("NOTIFY_EVENT_REQ_%s_%s.json", v2x.getGantryID(), DateUtils.dateTimeNowDtl());

        //发送 Multipart/form-data 请求
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig reqConfig = RequestConfig.custom().setSocketTimeout(3000)
                .setConnectTimeout(3000)
                .setConnectionRequestTimeout(3000).build();
        httpPost.setConfig(reqConfig);

        try{
            //MultipartEntityBuilder下载 httpmime 包
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(Charset.forName("UTF-8"));
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            byte[] sendData = data.toJSONString().getBytes(StandardCharsets.UTF_8);
            builder.addBinaryBody("binFile", sendData, ContentType.APPLICATION_OCTET_STREAM, filename);
            //表单其他参数
            //builder.addPart("version", new StringBody("1.0", ContentType.TEXT_PLAIN));
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            HttpResponse resp = httpClient.execute(httpPost);
            if(resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //解析返还数据
                String result = EntityUtils.toString(resp.getEntity(), Charset.forName("UTF-8"));

            }
        }catch (Exception ex) {
            log.error("车路协同 发送异常：" + ex.getMessage());
            ret = false;
        }

        return ret;
    }
}
