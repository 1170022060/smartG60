package com.pingok.charge.controller.trans;

import com.pingok.charge.domain.trans.vo.EnTransEnum;
import com.pingok.charge.domain.trans.vo.ExTransEnum;
import com.pingok.charge.domain.transimage.vo.ImageArrEnum;
import com.pingok.charge.domain.transimage.vo.ImageEnum;
import com.pingok.charge.service.trans.ITransService;
import com.pingok.charge.service.transimage.ITransImageService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 车道流水 信息操作处理
 *
 * @author ruoyi
 */
@RestController
@Slf4j
@RequestMapping("/trans")
public class TransController extends BaseController {
    @Autowired
    private ITransService transService;
    @Autowired
    private ITransImageService transImageService;

    @PostMapping("/en")
    public AjaxResult updateEn(@Validated @RequestBody EnTransEnum enTransEnum) {
        log.info("enTransEnum--update----请求参数-----" + enTransEnum);
        transService.addEn(enTransEnum);
        transService.updateEn(enTransEnum);
        if(enTransEnum.getTblEnTrans().getVehStatus()==90)
        {
            ImageArrEnum imageArrEnum=new ImageArrEnum();
            imageArrEnum.setGid(enTransEnum.getTblEnTrans().getGid());
            imageArrEnum.setImageArr(transImageService.getImageArr(enTransEnum.getTblEnTrans().getGid(),enTransEnum.getTblEnTrans().getGid().substring(21,29)));
            log.info("imageArrEnum--update----请求参数-----" + imageArrEnum);
            transImageService.updateImageArr(imageArrEnum);
        }
        if(enTransEnum.getTblEnTrans().getVehStatus()!=90)
        {
            ImageEnum imageEnum=new ImageEnum();
            imageEnum.setGid(enTransEnum.getTblEnTrans().getGid());
            imageEnum.setImage(transImageService.getImage(enTransEnum.getTblEnTrans().getLaneHex(),enTransEnum.getTblEnTrans().getGid(),enTransEnum.getTblEnTrans().getGid().substring(21,29)));
            log.info("imageEnum--update----请求参数-----" + imageEnum);
            transImageService.updateImage(imageEnum);
        }
        return AjaxResult.success();
    }

    @PostMapping("/ex")
    public AjaxResult updateEx(@Validated @RequestBody ExTransEnum exTransEnum) {
        log.info("exTransEnum--update----请求参数-----" + exTransEnum);
        transService.addEx(exTransEnum);
        transService.updateEx(exTransEnum);
        if(exTransEnum.getTblExTrans().getVehStatus()==90)
        {
            ImageArrEnum imageArrEnum=new ImageArrEnum();
            imageArrEnum.setGid(exTransEnum.getTblExTrans().getGid());
            imageArrEnum.setImageArr(transImageService.getImageArr(exTransEnum.getTblExTrans().getGid(),exTransEnum.getTblExTrans().getGid().substring(21,29)));
            log.info("imageArrEnum--update----请求参数-----" + imageArrEnum);
            transImageService.updateImageArr(imageArrEnum);
        }
        if(exTransEnum.getTblExTrans().getVehStatus()!=90)
        {
            ImageEnum imageEnum=new ImageEnum();
            imageEnum.setGid(exTransEnum.getTblExTrans().getGid());
            imageEnum.setImage(transImageService.getImage(exTransEnum.getTblExTrans().getLaneHex(),exTransEnum.getTblExTrans().getGid(),exTransEnum.getTblExTrans().getGid().substring(21,29)));
            log.info("imageEnum--update----请求参数-----" + imageEnum);
            transImageService.updateImage(imageEnum);
        }
        return AjaxResult.success();
    }
}
