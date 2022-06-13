package com.pingok.algorithm.event.service.impl;

import com.pingok.algorithm.event.entity.TblAlgEvent;
import com.pingok.algorithm.event.mapper.TblAlgEventMapper;
import com.pingok.algorithm.event.service.TblAlgEventService;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TblAlgEventServiceImpl implements TblAlgEventService {

    @Resource
    private TblAlgEventMapper algEventMapper;

    @Resource
    private RemoteIdProducerService remoteIdProducerService;

    /**
     * 查询事件列表
     * @param tblAlgEvent
     * @return
     */
    @Override
    public List<TblAlgEvent> listByBean(TblAlgEvent tblAlgEvent){
        return algEventMapper.select(tblAlgEvent);
    }

    /**
     * 查询事未结束件列表
     * @return
     */
    @Override
    public List<TblAlgEvent> selectNoFinishEventList(){
        return algEventMapper.selectNoFinishEventList();
    }

    /**
     * 查询事件详情
     * @param tblAlgEvent
     * @return
     */
    @Override
    public TblAlgEvent selectByBean(TblAlgEvent tblAlgEvent){
        return algEventMapper.selectOne(tblAlgEvent);
    }

    /**
     * 保存事件记录
     *
     * @param tblAlgEvent
     * @return Boolean
     */
    @Override
    public Boolean saveTblAlgEvent(TblAlgEvent tblAlgEvent){
        tblAlgEvent.setId(remoteIdProducerService.nextId());
        tblAlgEvent.setRiseNum(0);
        tblAlgEvent.setSampleNum(0);
        algEventMapper.insert(tblAlgEvent);
        return true;
    }

    /**
     * 修改事件记录
     *
     * @param tblAlgEvent
     * @return Boolean
     */
    @Override
    public Boolean modifyTblAlgEvent(TblAlgEvent tblAlgEvent){
        algEventMapper.updateByPrimaryKey(tblAlgEvent);
        return true;
    }

    /**
     * 删除事件记录
     *
     * @param id
     * @return Boolean
     */
    @Override
    public Boolean deleteTblAlgEvent(Long id){
        algEventMapper.deleteByPrimaryKey(id);
        return true;
    }
}
