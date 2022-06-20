package com.pingok.vocational.service.roster.impl;

import com.pingok.vocational.domain.roster.TblPursuesListRecord;
import com.pingok.vocational.mapper.roster.TblPursuesListRecordMapper;
import com.pingok.vocational.service.roster.IPursuesListRecordService;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.bean.BeanValidators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 追讨名单 服务层处理
 *
 * @author ruoyi
 */
@Service
public class PursuesListRecordServiceImpl implements IPursuesListRecordService {

    private static final Logger log = LoggerFactory.getLogger(PursuesListRecordServiceImpl.class);

    @Autowired
    private TblPursuesListRecordMapper tblPursuesListRecordMapper;

    @Autowired
    protected Validator validator;

    @Override
    public List<Map> selectPursuesList(Date startTime, Date endTime) {
        return tblPursuesListRecordMapper.selectPursuesList(startTime, endTime);
    }

    @Override
    public String importPursues(List<TblPursuesListRecord> pursuesList) {
        if (StringUtils.isNull(pursuesList) || pursuesList.size() == 0)
        {
            throw new ServiceException("导入追讨名单不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (TblPursuesListRecord pursues : pursuesList)
        {
            try
            {
                // 验证是否存在这个协调ID
                if (tblPursuesListRecordMapper.existsWithPrimaryKey(pursues.getConcertId()))
                {
                    BeanValidators.validateWithException(validator, pursues);
                    this.tblPursuesListRecordMapper.updateByPrimaryKey(pursues);
                }
                else
                {
                    BeanValidators.validateWithException(validator, pursues);
                    this.tblPursuesListRecordMapper.insert(pursues);
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = failureNum + "、协调ID " + pursues.getConcertId() + " 导入失败： ";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条");
        }
        return successMsg.toString();
    }
}
