package com.pingok.datacenter.controller.transimage;

import com.pingok.datacenter.domain.transimage.vo.ImageArrEnum;
import com.pingok.datacenter.domain.transimage.vo.ImageEnum;
import com.pingok.datacenter.service.transimage.ITransImageService;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 流水图片入库
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/transImage")
public class TransImageController {

    @Autowired
    private ITransImageService transImageService;

    @PostMapping("/imageArr")
    public AjaxResult imageArr(@Validated @RequestBody ImageArrEnum imageArrEnum)
    {
        transImageService.insertImageArr(imageArrEnum);
        return AjaxResult.success();
    }


    @PostMapping("/image")
    public AjaxResult image(@Validated @RequestBody ImageEnum imageEnum)
    {
        transImageService.insertImage(imageEnum);
        return AjaxResult.success();
    }
}
