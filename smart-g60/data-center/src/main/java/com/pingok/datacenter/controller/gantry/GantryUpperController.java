//package com.pingok.datacenter.controller.gantry;
//
//import com.alibaba.fastjson.JSONObject;
//import com.pingok.datacenter.domain.gantry.*;
//import com.pingok.datacenter.domain.gantry.model.*;
//import com.pingok.datacenter.service.gantry.IGantryUpperService;
//import com.ruoyi.common.core.web.domain.AjaxResult;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
///**
// * @author
// * @time 2022/5/20 12:21
// */
//@RestController
//@RequestMapping("/gantryUpper")
//public class GantryUpperController {
//    @Autowired
//    private IGantryUpperService iGantryUpperService;
//
//
//    @PostMapping("/viu")
//    public AjaxResult handleViu(@RequestBody List<TblGantryTravelImage> data) {
//        iGantryUpperService.handleViu(data);
//        return AjaxResult.success();
//    }
//
//    @PostMapping("/vipu")
//    public AjaxResult handleVipu(@RequestBody List<TblGantryPicture> data) {
//        iGantryUpperService.handleVipu(data);
//        return AjaxResult.success();
//    }
//
//    @PostMapping("/svipu")
//    public AjaxResult handleSvipu(@RequestBody List<TblGantryPictureFail> data) {
//        iGantryUpperService.handleSvipu(data);
//        return AjaxResult.success();
//    }
//
//    @PostMapping("/etctu")
//    public AjaxResult handleEtctu(@RequestBody List<TblGantryTransaction> data) {
//        iGantryUpperService.handleEtctu(data);
//        return AjaxResult.success();
//    }
//
//    @PostMapping("/etcsu")
//    public AjaxResult handleEtcsu(@RequestBody List<TblGantrySumTransaction> data) {
//        iGantryUpperService.handleEtcsu(data);
//        return AjaxResult.success();
//    }
//
//    @PostMapping("/visu")
//    public AjaxResult handleVisu(@RequestBody List<TblGantrySumTravelImage> data) {
//        iGantryUpperService.handleVisu(data);
//        return AjaxResult.success();
//    }
//
//    @PostMapping("/log")
//    public AjaxResult handlelog(@RequestBody JSONObject data) {
//        iGantryUpperService.handleLog(data);
//        return AjaxResult.success();
//    }
//
//
//
//    @PostMapping("/GetErrorData")
//    public AjaxResult GetErrorData(@RequestBody GetErrorDataModel data) {
//        return iGantryUpperService.GetErrorData(data);
//    }
//
//    @PostMapping("/FixErrorData")
//    public AjaxResult FixErrorData(@RequestBody FixErrorDataModel data) {
//        return iGantryUpperService.FixErrorData(data);
//    }
//
//    @PostMapping("/PicRealTransfer")
//    public AjaxResult PicRealTransfer(@RequestBody PicRealTransferModel data) {
//        return iGantryUpperService.PicRealTransfer(data);
//    }
//
//    @PostMapping("/VIUpload")
//    public AjaxResult VIUpload(@RequestBody VIUploadModel data) {
//        return iGantryUpperService.VIUpload(data);
//    }
//
//    @PostMapping("/VIPUpload")
//    public AjaxResult VIPUpload(@RequestBody VIPUploadModel data) {
//        return iGantryUpperService.VIPUpload(data);
//    }
//
//    @PostMapping("/ETCTUpload")
//    public AjaxResult ETCTUpload(@RequestBody ETCTUploadModel data) {
//        return iGantryUpperService.ETCTUpload(data);
//    }
//
//    @PostMapping("/HourSumUpload")
//    public AjaxResult HourSumUpload(@RequestBody HourSumUploadModel data) {
//        return iGantryUpperService.HourSumUpload(data);
//    }
//
//    @PostMapping("/LogBUpload")
//    public AjaxResult LogBUpload(@RequestBody LogBUploadModel data) {
//        return iGantryUpperService.LogBUpload(data);
//    }
//
//}
