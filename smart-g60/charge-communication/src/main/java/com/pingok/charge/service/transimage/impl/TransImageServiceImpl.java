package com.pingok.charge.service.transimage.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.pingok.charge.config.CenterConfig;
import com.pingok.charge.config.DaasConfig;
import com.pingok.charge.domain.transimage.vo.Image;
import com.pingok.charge.domain.transimage.vo.ImageArr;
import com.pingok.charge.domain.transimage.vo.ImageArrEnum;
import com.pingok.charge.domain.transimage.vo.ImageEnum;
import com.pingok.charge.service.transimage.ITransImageService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 流水图片入库 服务层处理
 *
 * @author ruoyi
 */
@Service
@Slf4j
public class TransImageServiceImpl implements ITransImageService {


    @Override
    public ImageArr getImageArr(String gid, String transDate) {
        Map<String, Object> body = new HashMap<>();
        body.put("Gid", gid);
        body.put("TransDate", transDate);
        String r = HttpUtil.post(CenterConfig.HOST + "/api/Lane/LaneImage", body.toString());
        ImageArr ret = null;
        if (!StringUtils.isEmpty(r)) {
            ret = JSON.parseObject(r, ImageArr.class);
        }
        return ret;
    }

    @Override
    public Image getImage(String laneHex, String gid, String transDate) {
        Map<String, Object> body = new HashMap<>();
        body.put("LaneHex", laneHex);
        body.put("Gid", gid);
        body.put("TransDate", transDate);
        String r = HttpUtil.post(CenterConfig.HOST + "/api/Lane/LaneImage", body.toString());
        Image ret = null;
        if (!StringUtils.isEmpty(r)) {
            ret = JSON.parseObject(r, Image.class);
        }
        return ret;
    }

    @Override
    public void updateImageArr(ImageArrEnum imageArrEnum) {
        String r = HttpUtil.post(DaasConfig.HOST + "/data-center/transImage/imageArr", JSON.toJSONString(imageArrEnum));
        R ret;
        if (!StringUtils.isEmpty(r)) {
            ret = JSON.parseObject(r, R.class);
            if (ret.getCode() == R.FAIL) {
                log.error("车道流水图片(放行车)上传失败，错误" + ret.getMsg());
            }
        }else {
            log.error("车道流水图片(放行车)上传状态未知");
        }
    }

    @Override
    public void updateImage(ImageEnum imageEnum) {
        String r = HttpUtil.post(DaasConfig.HOST + "/data-center/transImage/image", JSON.toJSONString(imageEnum));
        R ret;
        if (!StringUtils.isEmpty(r)) {
            ret = JSON.parseObject(r, R.class);
            if (ret.getCode() == R.FAIL) {
                log.error("车道流水图片上传失败，错误" + ret.getMsg());
            }
        }else {
            log.error("车道流水图片上传状态未知");
        }
    }
}
