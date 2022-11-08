package com.pingok.datacenter.controller.lprtrans;

import com.pingok.datacenter.domain.lprtrans.TblEnLprTrans;
import com.pingok.datacenter.domain.lprtrans.TblExLprTrans;
import com.pingok.datacenter.domain.lprtrans.vo.EnLprInfo;
import com.pingok.datacenter.domain.lprtrans.vo.ExLprInfo;
import com.pingok.datacenter.service.lprtrans.ILprTransService;
import com.pingok.datacenter.service.trans.ITransService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 车道牌识流水入库
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/lprTrans")
public class LprTransController extends BaseController {

    @Autowired
    private ILprTransService lprTransService;

    @Autowired
    private ITransService transService;

    @Transactional
    @PostMapping("/en")
    public AjaxResult en(@Validated @RequestBody TblEnLprTrans tblEnLprTrans)
    {
        EnLprInfo enLprInfo =new EnLprInfo();
        enLprInfo.setInsertEnLpr(lprTransService.insertEnLprTrans(tblEnLprTrans));
        int hour = tblEnLprTrans.getTransTime().getHours();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            // 注意格式需要与上面一致，不然会出现异常
            Date date=sdf.parse(DateUtils.getTimeDay(tblEnLprTrans.getTransTime()));
            if (hour < 19) {
                transService.updateSection(date,tblEnLprTrans.getLaneHex().substring(0,4),1,8,0);
            } else {
                transService.updateSection(DateUtils.getPreTime(date ,1440),tblEnLprTrans.getLaneHex().substring(0,4),1,8,0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        enLprInfo.setInsertEnLprSummary(lprTransService.insertEnLprSummary(tblEnLprTrans));
        return AjaxResult.success();
    }

    @Transactional
    @PostMapping("/ex")
    public AjaxResult ex(@Validated @RequestBody TblExLprTrans tblExLprTrans)
    {
        ExLprInfo exLprInfo =new ExLprInfo();
        exLprInfo.setInsertExLpr(lprTransService.insertExLprTrans(tblExLprTrans));
        int hour = tblExLprTrans.getTransTime().getHours();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            // 注意格式需要与上面一致，不然会出现异常
            Date date=sdf.parse(DateUtils.getTimeDay(tblExLprTrans.getTransTime()));
            if (hour < 19) {
                transService.updateSection(date,tblExLprTrans.getLaneHex().substring(0,4),2,8,0);
            } else {
                transService.updateSection(DateUtils.getPreTime(date ,1440),tblExLprTrans.getLaneHex().substring(0,4),2,8,0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        exLprInfo.setInsertExLprSummary(lprTransService.insertExLprSummary(tblExLprTrans));
        return AjaxResult.success();
    }

}
