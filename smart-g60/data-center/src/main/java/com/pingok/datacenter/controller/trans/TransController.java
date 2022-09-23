package com.pingok.datacenter.controller.trans;

import com.pingok.datacenter.domain.trans.vo.EnTransEnum;
import com.pingok.datacenter.domain.trans.vo.EnTransInfo;
import com.pingok.datacenter.domain.trans.vo.ExTransEnum;
import com.pingok.datacenter.domain.trans.vo.ExTransInfo;
import com.pingok.datacenter.service.trans.ITransService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 流水入库
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/trans")
public class TransController extends BaseController {

    @Autowired
    private ITransService transService;

    @Transactional
    @PostMapping("/en")
    public AjaxResult en(@Validated @RequestBody EnTransEnum enTransEnum)
    {
        EnTransInfo enTransInfo =new EnTransInfo();
        if(enTransEnum.getTblEnTrans()!=null)
        {
            enTransInfo.setInsertEnTrans(transService.insertEnTrans(enTransEnum.getTblEnTrans()));
            if(enTransEnum.getTblEnEtcPass()!=null)
            {
                enTransInfo.setInsertEnEtcPass(transService.insertEnEtcPass(enTransEnum.getTblEnEtcPass(),enTransInfo.getInsertEnTrans()));
            }
            if(enTransEnum.getTblEnMtcPass()!=null)
            {
                enTransInfo.setInsertEnMtcPass(transService.insertEnMtcPass(enTransEnum.getTblEnMtcPass(),enTransInfo.getInsertEnTrans()));
            }
            enTransInfo.setInsertEnTransSummary(transService.insertEnTransSummary(enTransEnum));
        }
        return AjaxResult.success(enTransInfo);
    }

    @Transactional
    @PostMapping("/ex")
    public AjaxResult ex(@Validated @RequestBody ExTransEnum exTransEnum)
    {
        ExTransInfo exTransInfo =new ExTransInfo();
        if(exTransEnum.getTblExTrans()!=null)
        {
            exTransInfo.setInsertExTrans(transService.insertExTrans(exTransEnum.getTblExTrans()));
            Long recordId=exTransInfo.getInsertExTrans().getRecordId();
            if(exTransEnum.getTblExEtcPass()!=null)
            {
                exTransInfo.setInsertExEtcPass(transService.insertExEtcPass(exTransEnum.getTblExEtcPass(),recordId));
            }
            if(exTransEnum.getTblExMtcPass()!=null)
            {
                exTransInfo.setInsertExMtcPass(transService.insertExMtcPass(exTransEnum.getTblExMtcPass(),recordId));
            }
            if(exTransEnum.getTblExPaperPass()!=null)
            {
                exTransInfo.setInsertExPaperPass(transService.insertExPaperPass(exTransEnum.getTblExPaperPass(),recordId));
            }
            if(exTransEnum.getTblExTransSplit()!=null)
            {
                exTransInfo.setInsertExTransSplit(transService.insertExTransSplit(exTransInfo.getInsertExTrans(),exTransEnum.getTblExTransSplit()));
            }
            exTransInfo.setInsertExTransSummary(transService.insertExTransSummary(exTransEnum));
        }

        return AjaxResult.success(exTransInfo);
    }

}
