package com.pingok.external.controller.dingTalk;

import com.alibaba.fastjson.JSONObject;
import com.pingok.external.service.dingTalk.IDeptService;
import com.pingok.external.service.dingTalk.IDingTalkService;
import com.pingok.external.service.dingTalk.IRoleService;
import com.pingok.external.service.dingTalk.IUserService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
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
    private IDeptService iDeptService;

    @Autowired
    private IDingTalkService iDingTalkService;

    @Autowired
    private IRoleService iRoleService;

    @Autowired
    private IUserService iUserService;


    @PostMapping("/callBack")
    public Map<String, String> callBack(
            @RequestParam(value = "msg_signature", required = false) String msg_signature,
            @RequestParam(value = "timestamp", required = false) String timeStamp,
            @RequestParam(value = "nonce", required = false) String nonce,
            @RequestBody(required = false) JSONObject json) {
        return iDingTalkService.callBack(msg_signature, timeStamp, nonce, json);
    }

    /**
     * 更新钉钉部门信息
     *
     * @return
     */
    @RequiresPermissions("externalSystem:dingTalk:updateDepartmentList")
    @Log(title = "钉钉信息管理", businessType = BusinessType.UPDATE)
    @GetMapping("/updateDepartmentList")
    public AjaxResult updateDepartmentList() {
        iDeptService.updateDepartmentList(null);
        return AjaxResult.success();
    }

    /**
     * 更新钉钉角色信息
     *
     * @return
     */
    @RequiresPermissions("externalSystem:dingTalk:updateRoleList")
    @Log(title = "钉钉信息管理", businessType = BusinessType.UPDATE)
    @GetMapping("/updateRoleList")
    public AjaxResult updateRoleList() {
        iRoleService.updateRoleList();
        return AjaxResult.success();
    }

    /**
     * 更新钉钉用户信息
     *
     * @return
     */
//    @RequiresPermissions("externalSystem:dingTalk:updateUserList")
//    @Log(title = "钉钉信息管理", businessType = BusinessType.UPDATE)
    @GetMapping("/updateUserList")
    public AjaxResult updateUserList() {
        iUserService.updateUserList();
        return AjaxResult.success();
    }

}
