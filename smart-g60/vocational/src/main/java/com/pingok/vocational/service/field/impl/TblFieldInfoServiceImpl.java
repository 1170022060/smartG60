package com.pingok.vocational.service.field.impl;

import com.pingok.vocational.domain.device.vo.TreeSelect;
import com.pingok.vocational.domain.field.TblFieldInfo;
import com.pingok.vocational.domain.field.vo.FieldVo;
import com.pingok.vocational.mapper.field.TblFieldInfoMapper;
import com.pingok.vocational.service.field.TblFieldInfoService;
import com.ruoyi.common.core.constant.UserConstants;
import com.ruoyi.common.core.utils.PinYinUtil;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 场地信息 服务层处理
 *
 * @author ruoyi
 */
@Service
public class TblFieldInfoServiceImpl implements TblFieldInfoService {
    @Autowired
    private TblFieldInfoMapper tblFieldInfoMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public TblFieldInfo selectFieldInfoById(Long Id) {
        return tblFieldInfoMapper.selectByPrimaryKey(Id);
    }


    @Override
    public List<Map> selectFieldInfo(String stationBelong, String roadBelong, String fieldName, Integer type,Integer status) {
        return tblFieldInfoMapper.selectFieldInfo(stationBelong, roadBelong, fieldName, type, status);
    }


    @Override
    public int insertFieldInfo(TblFieldInfo tblFieldInfo) {

        tblFieldInfo.setId(remoteIdProducerService.nextId());
        if (tblFieldInfo.getParentId() == null){
            tblFieldInfo.setParentId(0L);
        }
        tblFieldInfo.setStatus(1);
        tblFieldInfo.setCreateTime(new Date());
        tblFieldInfo.setFieldNum(PinYinUtil.getPinYinHeadChar(tblFieldInfo.getFieldName()));
        tblFieldInfo.setCreateUserId(SecurityUtils.getUserId());
        return tblFieldInfoMapper.insert(tblFieldInfo);
    }

    @Override
    public int updateFieldInfo(TblFieldInfo tblFieldInfo) {
        tblFieldInfo.setUpdateTime(new Date());
        tblFieldInfo.setUpdateUserId(SecurityUtils.getUserId());
        if(StringUtils.isNotNull(tblFieldInfo.getFieldName()))
        {
            tblFieldInfo.setFieldNum(PinYinUtil.getPinYinHeadChar(tblFieldInfo.getFieldName()));
        }
        return tblFieldInfoMapper.updateByPrimaryKey(tblFieldInfo);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        TblFieldInfo tblFieldInfo= tblFieldInfoMapper.selectByPrimaryKey(id);
        tblFieldInfo.setUpdateTime(new Date());
        tblFieldInfo.setUpdateUserId(SecurityUtils.getUserId());
        tblFieldInfo.setStatus(status);
        return tblFieldInfoMapper.updateByPrimaryKey(tblFieldInfo);
    }

    @Override
    public String checkFieldNameUnique(TblFieldInfo tblFieldInfo) {
        Long id = StringUtils.isNull(tblFieldInfo.getId()) ? -1L : tblFieldInfo.getId();
        Integer type = tblFieldInfo.getType();
        TblFieldInfo info = tblFieldInfoMapper.checkFieldNameUnique(tblFieldInfo.getFieldName(),type);
        if (StringUtils.isNotNull(info) && info.getId().longValue() != id.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public List<FieldVo> selectFieldTypeName() {

        List<FieldVo> lists =tblFieldInfoMapper.selectFieldTypeName();
        for (FieldVo list : lists) {
            list.setFieldInfo(tblFieldInfoMapper.selectFieldName(list.getType()));
        }
        return lists;
    }

    @Override
    public List<Map> selectServiceName() {
        return tblFieldInfoMapper.selectFieldName(4);
    }

    @Override
    public List<TblFieldInfo> selectAll() {
        return tblFieldInfoMapper.selectAll();
    }

    @Override
    public List<TblFieldInfo> buildTreeMenu(List<TblFieldInfo> fieldInfo) {
        List<TblFieldInfo> returnList = new ArrayList<TblFieldInfo>();
        List<Long> tempList = new ArrayList<Long>();
        for (TblFieldInfo tblFieldInfo : fieldInfo) {
            tempList.add(tblFieldInfo.getId());
        }
        for (Iterator<TblFieldInfo> iterator = fieldInfo.iterator(); iterator.hasNext(); ) {
            TblFieldInfo field = (TblFieldInfo) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(field.getParentId())) {
                recursionFn(fieldInfo, field);
                returnList.add(field);
            }
        }
        if (returnList.isEmpty()) {
            returnList = fieldInfo;
        }
        return returnList;
    }

    private void recursionFn(List<TblFieldInfo> list, TblFieldInfo field) {
        // 得到子节点列表
        List<TblFieldInfo> childList = getChildList(list, field);
        field.setChildren(childList);
        for (TblFieldInfo tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }
    /**
     * 得到子节点列表
     */
    private List<TblFieldInfo> getChildList(List<TblFieldInfo> list, TblFieldInfo t) {
        List<TblFieldInfo> tlist = new ArrayList<TblFieldInfo>();
        Iterator<TblFieldInfo> it = list.iterator();
        while (it.hasNext()) {
            TblFieldInfo n = (TblFieldInfo) it.next();
            if (n.getParentId().longValue() == t.getId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }
    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<TblFieldInfo> list, TblFieldInfo t) {
        return getChildList(list, t).size() > 0;
    }

    @Override
    public List<TreeSelect> fieldTreeMenu(List<TblFieldInfo> menu) {
        List<TblFieldInfo> categoryTrees = buildTreeMenu(menu);
        return categoryTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    @Override
    public List<Map> selectServiceField() {
        return tblFieldInfoMapper.selectServiceField();
    }

    @Override
    public List<Map> getChildrenField(Long id) {
        return tblFieldInfoMapper.getChildrenField(id);
    }
}
