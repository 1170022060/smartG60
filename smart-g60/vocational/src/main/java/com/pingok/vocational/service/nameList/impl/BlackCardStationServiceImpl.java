package com.pingok.vocational.service.nameList.impl;

import com.alibaba.fastjson.JSONObject;
import com.pingok.vocational.mapper.nameList.TblBlackCardLogNMapper;
import com.pingok.vocational.mapper.nameList.TblBlackCardStationNMapper;
import com.pingok.vocational.mapper.nameList.TblEpidemicStationUsedMapper;
import com.pingok.vocational.mapper.nameList.TblRateStationUsedMapper;
import com.pingok.vocational.service.nameList.IBlackCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@Service
public class BlackCardStationServiceImpl implements IBlackCardService {

    @Autowired
    private TblBlackCardStationNMapper tblBlackCardStationNMapper;
    @Autowired
    private TblBlackCardLogNMapper tblBlackCardLogNMapper;
    @Autowired
    private TblEpidemicStationUsedMapper tblEpidemicStationUsedMapper;
    @Autowired
    private TblRateStationUsedMapper tblRateStationUsedMapper;

    @Override
    public List<Map> getBlackCardList(String stationName, String version) {
        return tblBlackCardStationNMapper.getBlackCardList(stationName,version);
    }

    @Override
    public List<Map> findById(Long id) {
        return tblBlackCardLogNMapper.findById(id);
    }

    @Override
    public Object statisticsVersion() {
        Object blackCardObj = tblBlackCardStationNMapper.getLatestBCVersion();
        Object epidemicObj = tblEpidemicStationUsedMapper.getLatestESVersion();
        Object rateObj = tblRateStationUsedMapper.getLatestRSVersion();
        List<Map> BlackCardList = tblBlackCardStationNMapper.getLatestBCStation();
        List<Map> EpidmicList = tblEpidemicStationUsedMapper.getLatestEStation();
        List<Map> RateList = tblRateStationUsedMapper.getLatestRStation();
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        JSONObject object = new JSONObject();
        if (BlackCardList != null && BlackCardList.size() > 0) {
            for (int i=0;i<BlackCardList.size();i++){
                Iterator iter = BlackCardList.get(i).keySet().iterator();
                while (iter.hasNext()){
                    String key = (String) iter.next();
                    if (key.equals("version")){
                        if ( blackCardObj.equals(BlackCardList.get(i).get(key))){
                            count1++;
                            object.put("blackCard",count1);
                        }
                    }
                }
            }
            object.put("versionBC",blackCardObj);
        }else{
            object.put("blackCard",0);
        }
        if (EpidmicList != null && EpidmicList.size() > 0) {
            for (int i=0;i<EpidmicList.size();i++){
                Iterator iter = EpidmicList.get(i).keySet().iterator();
                while (iter.hasNext()){
                    String key = (String) iter.next();
                    if (key.equals("version")){
                        if ( epidemicObj.equals(EpidmicList.get(i).get(key))){
                            count2++;
                            object.put("epidemic",count2);
                        }
                    }
                }
            }
            object.put("versionEpi",epidemicObj);
        }else{
            object.put("epidemic",0);
        }
        if (RateList != null && RateList.size() > 0) {
            for (int i=0;i<RateList.size();i++){
                Iterator iter = RateList.get(i).keySet().iterator();
                while (iter.hasNext()){
                    String key = (String) iter.next();
                    if (key.equals("version")){
                        if ( rateObj.equals(RateList.get(i).get(key))){
                            count3++;
                            object.put("minRate",count3);
                        }
                    }
                }
            }
            object.put("versionRate",rateObj);
        }else{
            object.put("minRate",0);
        }
        object.put("alreadyUpdate",count1+count2+count3);
        object.put("notUpdate",BlackCardList.size()-count1+EpidmicList.size()-count2+RateList.size()-count3);
        object.put("total",tblBlackCardStationNMapper.getStationCount());
        return object;
    }
}
