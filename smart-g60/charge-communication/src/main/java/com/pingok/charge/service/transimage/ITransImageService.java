package com.pingok.charge.service.transimage;


import com.pingok.charge.domain.transimage.vo.Image;
import com.pingok.charge.domain.transimage.vo.ImageArr;
import com.pingok.charge.domain.transimage.vo.ImageArrEnum;
import com.pingok.charge.domain.transimage.vo.ImageEnum;

/**
 * 车道流水图片 业务层
 *
 * @author xia
 */
public interface ITransImageService {
    /**
     * 获取车道流水图片(放行车)
     * @param gid
     * @param transDate
     * @return
     */
    ImageArr getImageArr(String gid, String transDate);

    /**
     * 获取车道流水图片
     * @param laneHex
     * @param gid
     * @param transDate
     * @return
     */
    Image getImage(String laneHex,String gid, String transDate);

    /**
     * 上传车道流水图片(放行车)
     * @param imageArrEnum
     */
    void updateImageArr(ImageArrEnum imageArrEnum);

    /**
     * 上传车道流水图片
     * @param imageEnum
     */
    void updateImage(ImageEnum imageEnum);
}
