package com.pingok.datacenter.service.transimage;


import com.pingok.datacenter.domain.transimage.vo.ImageArrEnum;
import com.pingok.datacenter.domain.transimage.vo.ImageEnum;
import org.springframework.transaction.annotation.Transactional;

/**
 * 车道流水图片入库 业务层
 *
 * @author ruoyi
 */
public interface ITransImageService {
    /**
     * 车道流水图片入库
     *
     * @param imageArrEnum 放行车图片信息
     */
    @Transactional
    public void insertImageArr(ImageArrEnum imageArrEnum);

    /**
     * 车道流水图片入库
     *
     * @param imageEnum 图片信息
     */
    @Transactional
    public void insertImage(ImageEnum imageEnum);
}
