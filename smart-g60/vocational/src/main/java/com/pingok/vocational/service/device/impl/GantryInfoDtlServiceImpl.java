package com.pingok.vocational.service.device.impl;

import com.pingok.vocational.domain.device.TblGantryInfoDtl;
import com.pingok.vocational.domain.device.vo.DtlEnum;
import com.pingok.vocational.mapper.device.TblGantryInfoDtlMapper;
import com.pingok.vocational.service.device.IGantryInfoDtlService;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 门架设备详情 服务层处理
 *
 * @author ruoyi
 */
@Service
public class GantryInfoDtlServiceImpl implements IGantryInfoDtlService {

    @Autowired
    private TblGantryInfoDtlMapper tblGantryInfoDtlMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public List<TblGantryInfoDtl> selectGantryInfoDtl(Long infoId) {
        Example example = new Example(TblGantryInfoDtl.class);
        example.createCriteria().andEqualTo("infoId", infoId);
        return tblGantryInfoDtlMapper.selectByExample(example);
    }

    @Override
    public int deleteGantryInfoDtlByIds(Long[] ids) {
        int result=0;
        for(Long id : ids){
            tblGantryInfoDtlMapper.deleteByPrimaryKey(id);
            result++;
        }
        return result;
    }

    @Override
    public Long[] selectDtlId(Long infoId) {
        return tblGantryInfoDtlMapper.selectDtlId(infoId);
    }

    @Override
    public int insertGantryInfoDtl(DtlEnum dtlEnum) {
        int result=0;
        deleteGantryInfoDtlByIds(selectDtlId(dtlEnum.getInfoId()));
        for(TblGantryInfoDtl tblGantryInfoDtl:dtlEnum.getDtlList())
        {
            if(tblGantryInfoDtl.getId()==null)
            {
                tblGantryInfoDtl.setId(remoteIdProducerService.nextId());
            }
            tblGantryInfoDtlMapper.insert(tblGantryInfoDtl);
            result++;
        }
        return result;
    }



}
