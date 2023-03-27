package com.pingok.devicemonitor.controller.gantry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.devicemonitor.domain.gantry.*;
import com.pingok.devicemonitor.domain.gantry.vo.*;
import com.pingok.devicemonitor.service.gantry.IGantryService;
import com.pingok.devicemonitor.service.gantry.IGantryUpperService;
import com.pingok.devicemonitor.service.gantry.IGantryUpperStoreService;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.system.api.domain.gantry.TblGantryEventRelease;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author
 * @time 2022/5/23 12:35
 */
@Slf4j
@RestController
@RequestMapping("/gantryUpper")
public class GantryUpperController {
    @Autowired
    private IGantryUpperService iGantryUpperService;

    @Autowired
    private IGantryService iGantryService;

    @Autowired
    private IGantryUpperStoreService iGantryUpperStoreService;


    //    @RequiresPermissions("deviceMonitor:gantryUpper:eventProcessing")
    @Log(title = "门架管理", businessType = BusinessType.OTHER)
    @PostMapping("/eventProcessing")
    public AjaxResult eventProcessing(@RequestBody TblGantryEventRelease tblGantryEventRelease) {
        if (StringUtils.isNull(tblGantryEventRelease)) {
            AjaxResult.error("请求参数不能为空！");
        }
        if (StringUtils.isNull(tblGantryEventRelease.getStakeNum())) {
            AjaxResult.error("桩号不能为空！");
        }
        if (StringUtils.isNull(tblGantryEventRelease.getDirection())) {
            AjaxResult.error("方向不能为空！");
        }
        if (StringUtils.isNull(tblGantryEventRelease.getEventType())) {
            AjaxResult.error("事件播报类型不能为空！");
        }
        if (StringUtils.isNull(tblGantryEventRelease.getEventId())) {
            AjaxResult.error("事件类型编号不能为空！");
        }
        if (StringUtils.isNull(tblGantryEventRelease.getReportBeginTime())) {
            AjaxResult.error("播报开始时间不能为空！");
        }
        if (StringUtils.isNull(tblGantryEventRelease.getReportEndTime())) {
            AjaxResult.error("播报结束时间不能为空！");
        }
        if (StringUtils.isNull(tblGantryEventRelease.getCryptoGraphicDigest())) {
            AjaxResult.error("消息摘要不能为空！");
        }
        if (StringUtils.isNull(tblGantryEventRelease.getEventInfo())) {
            AjaxResult.error("事件消息内容不能为空！");
        }
        iGantryService.eventProcessing(tblGantryEventRelease);
        return AjaxResult.success();
    }


    @PostMapping(value = "/bin/{reqFileName}")
    public void handleRequest(@PathVariable String reqFileName, MultipartFile binFile, HttpServletResponse response) {
        // 设置headers（）
//        HttpServletRequest wrappedRequest = new CustomHttpServletRequestWrapper(request);

        String resFileName = reqFileName.replace("REQ", "RES").replace("@_@", ".");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + resFileName);

        String reqType = iGantryUpperService.getReqType(reqFileName);
        JSONObject ret = iGantryUpperService.genResponse(reqType, 200);
        try {

            InputStreamReader isr;
            BufferedReader br;
            StringBuilder sb = new StringBuilder();
            isr = new InputStreamReader(binFile.getInputStream(), StandardCharsets.UTF_8);
            br = new BufferedReader(isr);
            String lineTxt;
            while ((lineTxt = br.readLine()) != null) {
                sb.append(lineTxt);
            }
            isr.close();
            br.close();

            if (sb != null) {
                switch (reqType) {
                    case "GBUPLOAD_VIU"://牌识流水
                        iGantryUpperService.handleViu(JSON.parseObject(sb.toString(), TblGantryTravelImage.class));
                        break;
                    case "GBUPLOAD_VIPU"://牌识图片（按需、全量）
                        iGantryUpperService.handleVipu(JSON.parseObject(sb.toString(), TblGantryPicture.class));
                        break;
                    case "GBUPLOAD_SVIPU"://牌识图片（失败或未匹配）
                        List<TblGantryPictureFail> gantryPictureFails = iGantryUpperStoreService.changeSvipu(reqFileName, JSONArray.parseArray(sb.toString()));
                        if (gantryPictureFails != null && gantryPictureFails.size() > 0) {
                            iGantryUpperService.handleSvipu(gantryPictureFails);
                        }
                        break;
                    case "GBUPLOAD_ETCTU"://交易上传
                        iGantryUpperService.handleEtctu(JSON.parseObject(sb.toString(), TblGantryTransaction.class));
                        break;
                    case "GBUPLOAD_ETCSU": //交易小时汇总上传
                        List<TblGantrySumTransaction> gantrySumTransactions = iGantryUpperStoreService.changeEtcsu(reqFileName, JSONArray.parseArray(sb.toString()));
                        if (gantrySumTransactions != null && gantrySumTransactions.size() > 0) {
                            iGantryUpperService.handleEtcsu(gantrySumTransactions);
                        }
                        break;
                    case "GBUPLOAD_VISU"://牌识小时汇总上传
                        List<TblGantrySumTravelImage> gantrySumTravelImages = iGantryUpperStoreService.changeVisu(reqFileName, JSONArray.parseArray(sb.toString()));
                        if (gantrySumTravelImages != null && gantrySumTravelImages.size() > 0) {
                            iGantryUpperService.handleVisu(gantrySumTravelImages);
                        }
                        break;
                    case "GBUPLOAD_LOGBUPLOAD"://日志文件按需调取
                        iGantryUpperService.handleLog(JSONObject.parseObject(sb.toString()));
                        break;
                    case "GBUPLOAD_VEHICLEMONITOR"://车检器数据
                        break;
                    case "RM_BASEINFOUPLOAD"://基础信息数据
                        iGantryUpperService.handleBaseInfoUpload(JSONObject.parseObject(sb.toString()));
                        break;
                    case "RM_TGHBU"://运行状态数据
                        iGantryUpperService.handleTghbu(JSONObject.parseObject(sb.toString()));
                        break;
                    case "RM_SPECIALEVENTUPLOAD"://异常事件
                        iGantryUpperService.handleSpecialEventUpload(JSONObject.parseObject(sb.toString()));
                        break;
                }
            }
        } catch (Exception e) {
            ret = iGantryUpperService.genResponse(reqType, 500);
        } finally {
            try {
                OutputStream os = response.getOutputStream();
                os.write(ret.toJSONString().getBytes(StandardCharsets.UTF_8));
                os.close();
            } catch (IOException e) {
                log.error("门架请求返回失败，请求类型：" + reqType + "，" + e.getMessage());
            }

        }
    }


    @PostMapping("/GetErrorData")
    public AjaxResult GetErrorData(@RequestBody GetErrorDataModel data) {
        return iGantryUpperService.GetErrorData(data);
    }

    @PostMapping("/FixErrorData")
    public AjaxResult FixErrorData(@RequestBody FixErrorDataModel data) {
        return iGantryUpperService.FixErrorData(data);
    }

    @PostMapping("/PicRealTransfer")
    public AjaxResult PicRealTransfer(@RequestBody PicRealTransferModel data) {
        return iGantryUpperService.PicRealTransfer(data);
    }

    @PostMapping("/VIUpload")
    public AjaxResult VIUpload(@RequestBody VIUploadModel data) {
        return iGantryUpperService.VIUpload(data);
    }

    @PostMapping("/VIPUpload")
    public AjaxResult VIPUpload(@RequestBody VIPUploadModel data) {
        return iGantryUpperService.VIPUpload(data);
    }

    @PostMapping("/ETCTUpload")
    public AjaxResult ETCTUpload(@RequestBody ETCTUploadModel data) {
        return iGantryUpperService.ETCTUpload(data);
    }

    @PostMapping("/HourSumUpload")
    public AjaxResult HourSumUpload(@RequestBody HourSumUploadModel data) {
        return iGantryUpperService.HourSumUpload(data);
    }

    @PostMapping("/LogBUpload")
    public AjaxResult LogBUpload(@RequestBody LogBUploadModel data) {
        return iGantryUpperService.LogBUpload(data);
    }

    @PostMapping("/test")
    public AjaxResult test(@RequestBody TblGantryPicture data){
        iGantryUpperService.handleVipu(data);
        return AjaxResult.success();
    }
}
