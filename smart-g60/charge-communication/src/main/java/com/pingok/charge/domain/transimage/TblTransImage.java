package com.pingok.charge.domain.transimage;

import javax.persistence.Id;
import java.io.Serializable;
/**
 * 车道流水图片表 TBL_TRANS_IMAGE
 *
 * @author ruoyi
 */
public class TblTransImage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * GID
     */
    private String gid;

    /**
     * 图片
     */
    private String Image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
