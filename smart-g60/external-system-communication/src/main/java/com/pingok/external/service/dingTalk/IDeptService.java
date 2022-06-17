package com.pingok.external.service.dingTalk;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface IDeptService {


    /**
     * 获取token
     */
    String gettoken();


    /**
     * 事件订阅
     * @param msg_signature
     * @param timeStamp
     * @param nonce
     * @param json
     * @return
     */
    Map<String, String> callBack(String msg_signature, String timeStamp, String nonce, JSONObject json);
}
