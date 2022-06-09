package com.pingok.charge.controller.gantry;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.charge.domain.gantry.*;
import com.pingok.charge.service.gantry.IGantryUpperService;
import com.pingok.charge.service.gantry.IGantryUpperStoreService;
import com.ruoyi.common.core.web.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author
 * @time 2022/5/19 20:40
 */
@Slf4j
@RestController
@RequestMapping("/sdfs/G0060310010")
public class GantryUpperController extends BaseController {

    @Autowired
    private IGantryUpperService iGantryUpperService;

    @Autowired
    private IGantryUpperStoreService iGantryUpperStoreService;

    @PostMapping(value = "/bin/{reqFileName}")
    public void handleRequest(@PathVariable String reqFileName, HttpServletRequest request, HttpServletResponse response) {
        // 设置headers（）
        String resFileName = reqFileName.replace("REQ", "RES").replace("@_@", ".");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + resFileName);

        String reqType = iGantryUpperService.getReqType(reqFileName);
        JSONObject ret = iGantryUpperService.genResponse(reqType, 200);
        try {
            BufferedReader reader = request.getReader();
            StringBuffer sb = new StringBuffer();
            String str = null;
            while (null != (str = reader.readLine())) {
                sb.append(str);
            }
            if (sb != null) {
                switch (reqType) {
                    case "GBUPLOAD_VIU":
                        List<TblGantryTravelImage> gantryTravelImages = iGantryUpperStoreService.saveViu(reqFileName, JSONObject.parseObject(sb.toString()));
                        if (gantryTravelImages != null && gantryTravelImages.size() > 0) {
                            iGantryUpperService.updateViu(reqFileName, gantryTravelImages);
                        }
                        break; //牌识流水
                    case "GBUPLOAD_VIPU":
                        List<TblGantryPicture> gantryPictures = iGantryUpperStoreService.saveVipu(reqFileName, JSONArray.parseArray(sb.toString()));
                        if (gantryPictures != null && gantryPictures.size() > 0) {
                            iGantryUpperService.updateVipu(reqFileName, gantryPictures);
                        }
                        break; //牌识图片（按需、全量）
                    case "GBUPLOAD_SVIPU":
                        List<TblGantryPictureFail> gantryPictureFails = iGantryUpperStoreService.saveSvipu(reqFileName, JSONArray.parseArray(sb.toString()));
                        if (gantryPictureFails != null && gantryPictureFails.size() > 0) {
                            iGantryUpperService.updateSvipu(reqFileName, gantryPictureFails);
                        }
                        break; //牌识图片（失败或未匹配）
                    case "GBUPLOAD_ETCTU":
                        List<TblGantryTransaction> gantryTransactions = iGantryUpperStoreService.saveEtctu(reqFileName, JSONObject.parseObject(sb.toString()));
                        if (gantryTransactions != null && gantryTransactions.size() > 0) {
                            iGantryUpperService.updateEtctu(reqFileName, gantryTransactions);
                        }
                        break; //交易上传
                    case "GBUPLOAD_ETCSU":
                        List<TblGantrySumTransaction> gantrySumTransactions = iGantryUpperStoreService.saveEtcsu(reqFileName,JSONArray.parseArray(sb.toString()));
                        if (gantrySumTransactions != null && gantrySumTransactions.size() > 0) {
                            iGantryUpperService.updateEtcsu(reqFileName, gantrySumTransactions);
                        }
                        break; //交易小时汇总上传
                    case "GBUPLOAD_VISU":
                        List<TblGantrySumTravelImage> gantrySumTravelImages = iGantryUpperStoreService.saveVisu(reqFileName,JSONArray.parseArray(sb.toString()));
                        if (gantrySumTravelImages != null && gantrySumTravelImages.size() > 0) {
                            iGantryUpperService.updateVisu(reqFileName, gantrySumTravelImages);
                        }
                        break; //牌识小时汇总上传
                    case "GBUPLOAD_LOGBUPLOAD":

                        break; //日志文件按需调取
                    case "GBUPLOAD_VEHICLEMONITOR":
                        break; //车检器数据
                    case "RM_BASEINFOUPLOAD":
                        break;//基础信息数据
                    case "RM_TGHBU":
                        break; //运行状态数据
                    case "RM_SPECIALEVENTUPLOAD":
                        break; //异常事件
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
}
