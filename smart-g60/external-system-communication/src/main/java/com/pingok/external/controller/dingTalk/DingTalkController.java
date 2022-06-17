package com.pingok.external.controller.dingTalk;

import com.alibaba.fastjson.JSONObject;
import com.pingok.external.service.dingTalk.IDeptService;
import com.pingok.external.service.dingTalk.IDingTalkService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 养护车辆GPS 信息操作处理
 *
 * @author qiumin
 */
@RestController
@RequestMapping("/dingTalk")
public class DingTalkController extends BaseController {

    @Autowired
    private IDingTalkService iDingTalkService;

    @Autowired
    private IDeptService iDeptService;


    @PostMapping("/callBack")
    public Map<String, String> callBack(
            @RequestParam(value = "msg_signature", required = false) String msg_signature,
            @RequestParam(value = "timestamp", required = false) String timeStamp,
            @RequestParam(value = "nonce", required = false) String nonce,
            @RequestBody(required = false) JSONObject json) {
        return iDeptService.callBack(msg_signature, timeStamp, nonce, json);
    }

    @GetMapping("/updateDepartmentList")
    public AjaxResult updateDepartmentList() {
        iDingTalkService.updateDepartmentList(null);
        return AjaxResult.success();
    }

}
