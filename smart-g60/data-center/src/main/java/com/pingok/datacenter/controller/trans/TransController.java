package com.pingok.datacenter.controller.trans;

import com.pingok.datacenter.domain.trans.vo.*;
import com.pingok.datacenter.service.trans.ITransService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
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
                enTransInfo.setInsertEnEtcPass(transService.insertEnEtcPass(enTransEnum.getTblEnEtcPass(),enTransInfo.getInsertEnTrans().getRecordId()));
            }
            if(enTransEnum.getTblEnTrans().getPassType()==5)
            {
                if(enTransEnum.getTblEnTrans()!=null)
                {
                    if(enTransEnum.getTblEnEtcPass().getEtcCardNet().equals("3101"))
                    {
                        transService.updateSection(enTransEnum.getTblEnTrans().getWorkDate(),enTransEnum.getTblEnTrans().getLaneHex().substring(4,8),1,1,0);
                    }
                    else
                    {
                        transService.updateSection(enTransEnum.getTblEnTrans().getWorkDate(),enTransEnum.getTblEnTrans().getLaneHex().substring(4,8),1,2,0);
                    }
                }
            }
            if(enTransEnum.getTblEnTrans().getPassType()==6) {
                if(enTransEnum.getTblEnMtcPass()!=null)
                {
                    enTransInfo.setInsertEnMtcPass(transService.insertEnMtcPass(enTransEnum.getTblEnMtcPass(),enTransInfo.getInsertEnTrans().getRecordId()));
                    transService.updateSection(enTransEnum.getTblEnTrans().getWorkDate(),enTransEnum.getTblEnTrans().getLaneHex().substring(4,8),1,3,0);
                }
            }
            if(enTransEnum.getTblEnTrans().getPassType()==0 && enTransEnum.getTblEnTrans().getVehStatus()==90) {
                transService.updateSection(enTransEnum.getTblEnTrans().getWorkDate(),enTransEnum.getTblEnTrans().getLaneHex().substring(4,8),1,5,0);
            }
            if(enTransEnum.getTblEnTrans().getPassType()==0 && enTransEnum.getTblEnTrans().getVehStatus()==91) {
                transService.updateSection(enTransEnum.getTblEnTrans().getWorkDate(),enTransEnum.getTblEnTrans().getLaneHex().substring(4,8),1,6,0);
            }
            if(enTransEnum.getTblEnTrans().getPassType()==0 && enTransEnum.getTblEnTrans().getVehStatus()==92) {
                transService.updateSection(enTransEnum.getTblEnTrans().getWorkDate(),enTransEnum.getTblEnTrans().getLaneHex().substring(4,8),1,7,0);
            }
            enTransInfo.setInsertEnTransSummary(transService.insertEnTransSummary(enTransEnum));
        }
        return AjaxResult.success();
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
                if(exTransEnum.getTblExTrans().getPassType()==5)
                {
                    if(exTransEnum.getTblExEtcPass().getEtcCardNet().equals("3101"))
                    {
                        transService.updateSection(exTransEnum.getTblExTrans().getWorkDate(), exTransEnum.getTblExTrans().getLaneHex().substring(4, 8), 2, 1,exTransEnum.getTblExTrans().getAmount());
                    }
                    else
                    {
                        transService.updateSection(exTransEnum.getTblExTrans().getWorkDate(), exTransEnum.getTblExTrans().getLaneHex().substring(4, 8), 2, 2,exTransEnum.getTblExTrans().getAmount());
                    }
                }
                exTransInfo.setInsertExEtcPass(transService.insertExEtcPass(exTransEnum.getTblExEtcPass(),recordId));
            }
            if(exTransEnum.getTblExMtcPass()!=null)
            {
                if(exTransEnum.getTblExTrans().getPassType()==6)
                {
                    if(exTransEnum.getTblExMtcPass().getCpcCardEnNet().equals("3101"))
                    {
                        transService.updateSection(exTransEnum.getTblExTrans().getWorkDate(), exTransEnum.getTblExTrans().getLaneHex().substring(4, 8), 2, 3,exTransEnum.getTblExTrans().getAmount());
                    }
                    else
                    {
                        transService.updateSection(exTransEnum.getTblExTrans().getWorkDate(), exTransEnum.getTblExTrans().getLaneHex().substring(4, 8), 2, 4,exTransEnum.getTblExTrans().getAmount());
                    }
                }
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
            if(exTransEnum.getTblExTrans().getPassType()==0 && exTransEnum.getTblExTrans().getVehStatus()==90) {
                transService.updateSection(exTransEnum.getTblExTrans().getWorkDate(),exTransEnum.getTblExTrans().getLaneHex().substring(4,8),2,5,0);
            }
            if(exTransEnum.getTblExTrans().getPassType()==0 && exTransEnum.getTblExTrans().getVehStatus()==91) {
                transService.updateSection(exTransEnum.getTblExTrans().getWorkDate(),exTransEnum.getTblExTrans().getLaneHex().substring(4,8),2,6,0);
            }
            if(exTransEnum.getTblExTrans().getPassType()==0 && exTransEnum.getTblExTrans().getVehStatus()==92) {
                transService.updateSection(exTransEnum.getTblExTrans().getWorkDate(),exTransEnum.getTblExTrans().getLaneHex().substring(4,8),2,7,0);
            }
            exTransInfo.setInsertExTransSummary(transService.insertExTransSummary(exTransEnum));
        }
        return AjaxResult.success();
    }

}
