package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteDataCenterService;
import com.ruoyi.system.api.domain.gantry.ChargeFlowModel;
import com.ruoyi.system.api.domain.gantry.TblGantryChargeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * datacenter
 *
 * @author xia
 */
@Component
public class RemoteDataCenterFallbackFactory implements FallbackFactory<RemoteDataCenterService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteDataCenterFallbackFactory.class);

    @Override
    public RemoteDataCenterService create(Throwable throwable)
    {
        log.error("数据中心服务调用失败:{}", throwable.getMessage());
        return new RemoteDataCenterService()
        {
            @Override
            public R rushRecord(String year, String startTime, String endTime, String twoHours) {
                return null;
            }

            @Override
            public R simulatedSorting(String year, String startTime, String endTime) {
                return null;
            }

            @Override
            public R<ChargeFlowModel> selectChargeFlow(String chargingUnitId, String statisticsDate) {
                return null;
            }

            @Override
            public R<List<ChargeFlowModel>> selectChargeFlowList(String chargingUnitId, String startDate, String endDate) {
                return null;
            }

            @Override
            public R<List<TblGantryChargeInfo>> chargeInfo(String chargingUnitId) {
                return null;
            }

            @Override
            public R<List<Map>> info(String startTime,String endTime) {
                return null;
            }

            @Override
            public R<List<Map>> gantryList(Integer direction) {
                return null;
            }

            @Override
            public R<List<Map>> passRecord(String gantryId, String startTime, String endTime) {
                return null;
            }
        };
    }
}
