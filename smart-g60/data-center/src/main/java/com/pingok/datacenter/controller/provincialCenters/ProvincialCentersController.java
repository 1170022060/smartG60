package com.pingok.datacenter.controller.provincialCenters;

import com.alibaba.fastjson.JSONArray;
import com.pingok.datacenter.service.provincialCenters.*;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.web.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 省中心对接 信息操作处理
 *
 * @author xia
 */
@RestController
@Slf4j
@RequestMapping("/provincialCenters")
public class ProvincialCentersController extends BaseController {

    @Autowired
    private IProvincialCentersService iProvincialCentersService;

    @Autowired
    private ISharEnpdResSenderService iSharEnpdResSenderService;

    @Autowired
    private ISharEtctdResSenderService iSharEtctdResSenderService;

    @Autowired
    private ISharGtdResSenderService iSharGtdResSenderService;

    @Autowired
    private ISharGvidResSenderService iSharGvidResSenderService;

    @Autowired
    private ISharOtdResSenderService iSharOtdResSenderService;

    @Autowired
    private ISharSvidResSenderService iSharSvidResSenderService;

    @PostMapping
    public R getData(@RequestParam(value = "name") String name, @RequestParam(value = "dateTimeNow") String dateTimeNow) {
        JSONArray jsonArray = iProvincialCentersService.getData(name, DateUtils.parseDate(dateTimeNow));
        if (jsonArray != null && jsonArray.size() > 0) {
            switch (name) {
                case "SHARE_G60ENPD_REQ_SENDER":
                    iSharEnpdResSenderService.add(jsonArray);
                    break;
                case "SHARE_G60OTD_SENDER":
                    iSharOtdResSenderService.add(jsonArray);
                    break;
                case "SHARE_G60ETCTD_SENDER":
                    iSharEtctdResSenderService.add(jsonArray);
                    break;
                case "SHARE_G60SVID_SENDER":
                    iSharSvidResSenderService.add(jsonArray);
                    break;
                case "SHARE_G60GTD_SENDER":
                    iSharGtdResSenderService.add(jsonArray);
                    break;
                case "SHARE_G60GVID_SENDER":
                    iSharGvidResSenderService.add(jsonArray);
                    break;
            }
        }
        return R.ok();
    }
}
