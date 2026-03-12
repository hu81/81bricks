package com.ruoyi.bricks.domain;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 表情对象 brk_face
 * 
 * @author ruoyi
 * @date 2026-03-12
 */
public class BrkFace extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 表情ID */
    private Long faceId;

    /** 表情名称 */
    @Excel(name = "表情名称")
    private String faceName;

    /** 备注 */
    @Excel(name = "备注")
    private String comments;

    /** 表情id */
    @Excel(name = "表情id")
    private String originId;

    /** 表情链接 */
    @Excel(name = "表情链接")
    private String originUrl;

    /** 情图层信息 */
    private List<BrkFaceLayer> brkFaceLayerList;

    public void setFaceId(Long faceId) 
    {
        this.faceId = faceId;
    }

    public Long getFaceId() 
    {
        return faceId;
    }

    public void setFaceName(String faceName) 
    {
        this.faceName = faceName;
    }

    public String getFaceName() 
    {
        return faceName;
    }

    public void setComments(String comments) 
    {
        this.comments = comments;
    }

    public String getComments() 
    {
        return comments;
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

    public List<BrkFaceLayer> getBrkFaceLayerList()
    {
        return brkFaceLayerList;
    }

    public void setBrkFaceLayerList(List<BrkFaceLayer> brkFaceLayerList)
    {
        this.brkFaceLayerList = brkFaceLayerList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("faceId", getFaceId())
            .append("faceName", getFaceName())
            .append("comments", getComments())
            .append("originId", getOriginId())
            .append("originUrl", getOriginUrl())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("brkFaceLayerList", getBrkFaceLayerList())
            .toString();
    }
}
