package com.pingok.datacenter.controller.sectorlog;

import com.pingok.datacenter.domain.sectorlog.vo.SectorInfo;
import com.pingok.datacenter.domain.sectorlog.vo.SectorLogVo;
import com.pingok.datacenter.service.sectorlog.ISectorLogService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 扇区日志入库
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/sectorLog")
public class SectorLogController extends BaseController {

    @Autowired
    private ISectorLogService sectorLogService;

    @PostMapping(value = "/get")
    public AjaxResult get(@RequestParam(name = "laneHex") String laneHex, @RequestParam(name = "gid")String gid) {
        sectorLogService.getSectorLog(laneHex,gid);
        return AjaxResult.success();
    }

    @Transactional
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SectorLogVo sectorLogVo)
    {
        SectorInfo sectorInfo=new SectorInfo();
        if(sectorLogService.checkGidUnique(sectorLogVo.getGid())==0)
        {
            sectorInfo.setInsertSectorLog(sectorLogService.insertSectorLog(sectorLogVo));
            if(sectorLogVo.getEf02()!=null && (sectorLogVo.getEf02().length()==58 ||sectorLogVo.getEf02().length()>=64))
            {
                sectorInfo.setInsertCpcEf02Log(sectorLogService.insertCpcEf02Log(sectorInfo.getInsertSectorLog(),sectorLogVo.getEf02()));
            }
            if(sectorLogVo.getEf04()!=null && sectorLogVo.getPassType()==6 && sectorLogVo.getEf04().length()>=46)
            {
                sectorInfo.setInsertCpcEf04Log(sectorLogService.insertCpcEf04Log(sectorInfo.getInsertSectorLog(),sectorLogVo.getEf04()));
            }
            if(sectorLogVo.getFile0015()!=null && sectorLogVo.getFile0015().length()>=86)
            {
                sectorInfo.setInsertCpu0015Log(sectorLogService.insertCpu0015Log(sectorInfo.getInsertSectorLog(),sectorLogVo.getFile0015()));
            }
            if(sectorLogVo.getFile0019()!=null && sectorLogVo.getFile0019().length()>=86)
            {
                sectorInfo.setInsertCpu0019Log(sectorLogService.insertCpu0019Log(sectorInfo.getInsertSectorLog(),sectorLogVo.getFile0019()));
            }
            if(sectorLogVo.getObuVehicleInfo()!=null && sectorLogVo.getObuVehicleInfo().length()>=54)
            {
                sectorInfo.setInsertObuEf01Log(sectorLogService.insertObuEf01Log(sectorInfo.getInsertSectorLog(),sectorLogVo.getObuVehicleInfo()));
            }
            if(sectorLogVo.getEf04()!=null && sectorLogVo.getPassType()==5 && (sectorLogVo.getEf04().length()==182 ||sectorLogVo.getEf04().length()>=190))
            {
                sectorInfo.setInsertObuEf04Log(sectorLogService.insertObuEf04Log(sectorInfo.getInsertSectorLog(),sectorLogVo.getEf04().substring(0,182)));
                if(sectorLogVo.getEf04().length()>182)
                {
                    sectorInfo.setInsertObuEf04LogProv(sectorLogService.insertObuEf04LogProv(sectorInfo.getInsertObuEf04Log(),sectorLogVo.getEf04().substring(182)));
                }
            }
            return AjaxResult.success(sectorInfo);
        }
        else
        {
            return AjaxResult.error("该扇区日志流水已存在");
        }
    }

}
