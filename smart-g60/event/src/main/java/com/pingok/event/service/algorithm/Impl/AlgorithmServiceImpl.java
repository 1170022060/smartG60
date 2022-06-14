package com.pingok.event.service.algorithm.Impl;

import com.pingok.event.domain.algorithm.LaneAvgSpeed;
import com.pingok.event.domain.device.TblDeviceInfo;
import com.pingok.event.domain.videoEvent.TblEventPlateInfo;
import com.pingok.event.mapper.device.TblDeviceInfoMapper;
import com.pingok.event.mapper.videoEvent.TblEventPlateInfoMapper;
import com.pingok.event.service.algorithm.IAlgorithmService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

/**
 * @author
 * @time 2022/6/8 9:32
 */
@Slf4j
@Service
public class AlgorithmServiceImpl implements IAlgorithmService {

    // 车道数变化的桩号 及对应的车道数(桩号<27.3-3车道，<38.205-4车道，<65-3车道)
//    private static final Float[] mileageLimits = new Float[] { 27.3f, 38.205f, 65.0f };
//    private static final Integer[] laneNums = new Integer[] { 3, 4, 3 };

    @Autowired
    private RemoteConfigService remoteConfigService;
    @Autowired
    private TblDeviceInfoMapper tblDeviceInfoMapper;
    @Autowired
    private TblEventPlateInfoMapper tblEventPlateInfoMapper;

    // 根据桩号、车道号查询车道平均速度 mileage 桩号(格式：26+300)；laneNo 车道号(1，2，3，……)
    @Override
    public List<LaneAvgSpeed> getLaneAvgSpeed(String mileage, String laneNo, Integer dir) {

        List<LaneAvgSpeed> ret  = new ArrayList<>();
        try{
            // get计算区间
            Float calcMileage = 6f;
            // 2022-06-09 sql故障
            R<String> r = remoteConfigService.getConfigKey("speed.mileage");
            if(R.SUCCESS == r.getCode()) {
                calcMileage = Float.parseFloat(r.getMsg());
            }
            // get邻道id
            List<String> laneIds = getJoinLaneIdList(laneNo);
            // get calcMileage范围内的相机id
            List<String> cameraIds = getRelCameraIdList(mileage, dir, calcMileage);
            // get速度
            for (int i = 0; i < laneIds.size(); ++i) {
                String[] splitIds = laneIds.get(i).split(",");
                Double total = 0.0;
                for (String id : splitIds) {
                    List<TblEventPlateInfo> tblEventPlateInfos = tblEventPlateInfoMapper.selectByLane(id, cameraIds);
                    if(tblEventPlateInfos.size() > 0) {
                        total += tblEventPlateInfos.stream().mapToDouble(elm -> elm.getUiSpeed()).average().orElse(0);
                    }
                }
                if(total == 0) continue;
                // 计算平均速度
                Double avgSpeed = total / splitIds.length;
                LaneAvgSpeed laneAvgSpeed = new LaneAvgSpeed();
                laneAvgSpeed.setStationNum(mileage);
                laneAvgSpeed.setLineNum(laneIds.get(i));
                laneAvgSpeed.setLineType(i == 0 ? 0 : 1);
                laneAvgSpeed.setLineSpeed(avgSpeed.toString());
                ret.add(laneAvgSpeed);
            }

        } catch (Exception e) {
            log.error("getLaneAvgSpeed异常："+e.getMessage());
        }

        return ret;
    }

    // get邻道id，laneNo格式：1,2,3,...
    private List<String> getJoinLaneIdList(String laneNo) {
        List<String> ids = new ArrayList<>();
        ids.add(laneNo); // 查询车道
        String[] splits = laneNo.split(",");
        //相邻车道（如果车道号不存在，则相机不会有该车道的记录）
        ids.add(Integer.toString(Integer.parseInt(splits[0])-1));
        ids.add(Integer.toString(Integer.parseInt(splits[splits.length-1])+1));
        return ids;
    }

    // get calcMileage范围内的相机id（mileage格式：[K、k]26+300）
    private List<String> getRelCameraIdList(String mileage, Integer dir, Float calcMileage) {

        Float startMileage, endMileage = 0f;
        startMileage = endMileage = parseMileage(mileage);
        if(dir == 1) { //上行
            startMileage -= calcMileage;
        } else {
            endMileage += calcMileage;
        }
        // get区间范围内的相机id
        Example example = new Example(TblDeviceInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIsNotNull("isControl");
        List<TblDeviceInfo> tblDeviceInfos = tblDeviceInfoMapper.selectByExample(example);
        Float finalStartMileage = startMileage;
        Float finalEndMileage = endMileage;
        List<String> findDeviceInfos = tblDeviceInfos.stream()
                .filter(device -> parseMileage(device.getPileNo()) > finalStartMileage
                        && parseMileage(device.getPileNo()) < finalEndMileage)
                .map(device->device.getDeviceId())
                .collect(Collectors.toList());

        return findDeviceInfos;
    }

    // string桩号转float，桩号格式：[K、k]26+300
    private Float parseMileage(String mileage) {
        char c = mileage.charAt(0);
        String value = mileage;
        if(c == 'k' || c == 'K') {
            value = mileage.substring(1);
        } else {
            value = "0+000";
        }
        return Float.parseFloat(value.replace('+', '.'));
    }

}
