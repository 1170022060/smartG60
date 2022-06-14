package com.pingok.datacenter.service.transimage.impl;

import com.pingok.datacenter.domain.transimage.TblTransImage;
import com.pingok.datacenter.domain.transimage.vo.ImageArrEnum;
import com.pingok.datacenter.domain.transimage.vo.ImageEnum;
import com.pingok.datacenter.mapper.transimage.TblTransImageMapper;
import com.pingok.datacenter.service.transimage.ITransImageService;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 流水图片入库 服务层处理
 *
 * @author ruoyi
 */
@Service
public class TransImageServiceImpl implements ITransImageService {

    @Autowired
    private TblTransImageMapper tblTransImageMapper;

    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public void insertImageArr(ImageArrEnum imageArrEnum) {
        TblTransImage tblTransImage=new TblTransImage();
        if(imageArrEnum.getImageArr()!=null)
        {
            if(imageArrEnum.getImageArr().getLaneLprImageArr()!=null)
            {
                for(String laneLprImage:imageArrEnum.getImageArr().getLaneLprImageArr())
                {
                    tblTransImage.setId(remoteIdProducerService.nextId());
                    tblTransImage.setGid(imageArrEnum.getGid());
                    tblTransImage.setImage(laneLprImage);
                    tblTransImageMapper.insert(tblTransImage);
                }
            }
            if(imageArrEnum.getImageArr().getLaneCarImageArr()!=null)
            {
                for (String laneCarImage : imageArrEnum.getImageArr().getLaneCarImageArr()) {
                    tblTransImage.setId(remoteIdProducerService.nextId());
                    tblTransImage.setGid(imageArrEnum.getGid());
                    tblTransImage.setImage(laneCarImage);
                    tblTransImageMapper.insert(tblTransImage);
                }
            }
            if(imageArrEnum.getImageArr().getLaneVideoImageArr()!=null) {
                for (String laneVideoImage : imageArrEnum.getImageArr().getLaneVideoImageArr()) {
                    tblTransImage.setId(remoteIdProducerService.nextId());
                    tblTransImage.setGid(imageArrEnum.getGid());
                    tblTransImage.setImage(laneVideoImage);
                    tblTransImageMapper.insert(tblTransImage);
                }
            }
        }
    }

    @Override
    public void insertImage(ImageEnum imageEnum) {
        TblTransImage tblTransImage=new TblTransImage();
        tblTransImage.setGid(imageEnum.getGid());
        if(imageEnum.getImage().getLaneLprImage()!=null)
        {
            tblTransImage.setId(remoteIdProducerService.nextId());
            tblTransImage.setImage(imageEnum.getImage().getLaneLprImage());
            tblTransImageMapper.insert(tblTransImage);
        }
        if(imageEnum.getImage().getLaneCarImage()!=null)
        {
            tblTransImage.setId(remoteIdProducerService.nextId());
            tblTransImage.setImage(imageEnum.getImage().getLaneCarImage());
            tblTransImageMapper.insert(tblTransImage);
        }
        if(imageEnum.getImage().getLaneVideoImage()!=null)
        {
            tblTransImage.setId(remoteIdProducerService.nextId());
            tblTransImage.setImage(imageEnum.getImage().getLaneVideoImage());
            tblTransImageMapper.insert(tblTransImage);
        }
    }
}
