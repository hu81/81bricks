package com.ruoyi.bricks.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 表情对象 brk_face
 * 
 * @author ruoyi
 * @date 2026-03-11
 */
public class BrkFace extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 表情ID */
    private Long faceId;

    /** 表情id */
    @Excel(name = "表情id")
    private String originId;

    /** 表情链接 */
    @Excel(name = "表情链接")
    private String originUrl;

    /** 眼睛id */
    @Excel(name = "眼睛id")
    private String eyeOriginId;

    /** 嘴巴id */
    @Excel(name = "嘴巴id")
    private String monthOriginId;

    /** 眉毛id */
    @Excel(name = "眉毛id")
    private String eyebrowOriginId;

    /** 眼镜id */
    @Excel(name = "眼镜id")
    private String glassOriginId;

    /** 贴纸id */
    @Excel(name = "贴纸id")
    private String stickerOriginId;

    public void setFaceId(Long faceId) 
    {
        this.faceId = faceId;
    }

    public Long getFaceId() 
    {
        return faceId;
    }

    public void setOriginId(String originId) 
    {
        this.originId = originId;
    }

    public String getOriginId() 
    {
        return originId;
    }

    public void setOriginUrl(String originUrl) 
    {
        this.originUrl = originUrl;
    }

    public String getOriginUrl() 
    {
        return originUrl;
    }

    public void setEyeOriginId(String eyeOriginId) 
    {
        this.eyeOriginId = eyeOriginId;
    }

    public String getEyeOriginId() 
    {
        return eyeOriginId;
    }

    public void setMonthOriginId(String monthOriginId) 
    {
        this.monthOriginId = monthOriginId;
    }

    public String getMonthOriginId() 
    {
        return monthOriginId;
    }

    public void setEyebrowOriginId(String eyebrowOriginId) 
    {
        this.eyebrowOriginId = eyebrowOriginId;
    }

    public String getEyebrowOriginId() 
    {
        return eyebrowOriginId;
    }

    public void setGlassOriginId(String glassOriginId) 
    {
        this.glassOriginId = glassOriginId;
    }

    public String getGlassOriginId() 
    {
        return glassOriginId;
    }

    public void setStickerOriginId(String stickerOriginId) 
    {
        this.stickerOriginId = stickerOriginId;
    }

    public String getStickerOriginId() 
    {
        return stickerOriginId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("faceId", getFaceId())
            .append("originId", getOriginId())
            .append("originUrl", getOriginUrl())
            .append("eyeOriginId", getEyeOriginId())
            .append("monthOriginId", getMonthOriginId())
            .append("eyebrowOriginId", getEyebrowOriginId())
            .append("glassOriginId", getGlassOriginId())
            .append("stickerOriginId", getStickerOriginId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
