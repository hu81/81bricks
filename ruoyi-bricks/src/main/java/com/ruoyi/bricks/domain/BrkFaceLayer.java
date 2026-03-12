package com.ruoyi.bricks.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 情图层对象 brk_face_layer
 * 
 * @author ruoyi
 * @date 2026-03-12
 */
public class BrkFaceLayer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 图层ID */
    private Long layerId;

    /** 表情id */
    @Excel(name = "表情id")
    private Long faceId;

    /** 图层类型 */
    @Excel(name = "图层类型")
    private String layerType;

    /** 图片数据 */
    @Excel(name = "图片数据")
    private String data;

    /** x坐标 */
    @Excel(name = "x坐标")
    private Long x;

    /** y坐标 */
    @Excel(name = "y坐标")
    private Long y;

    /** 图层宽度 */
    @Excel(name = "图层宽度")
    private Long width;

    /** 图层高度 */
    @Excel(name = "图层高度")
    private Long height;

    public void setLayerId(Long layerId) 
    {
        this.layerId = layerId;
    }

    public Long getLayerId() 
    {
        return layerId;
    }
    public void setFaceId(Long faceId) 
    {
        this.faceId = faceId;
    }

    public Long getFaceId() 
    {
        return faceId;
    }
    public void setLayerType(String layerType) 
    {
        this.layerType = layerType;
    }

    public String getLayerType() 
    {
        return layerType;
    }
    public void setData(String data) 
    {
        this.data = data;
    }

    public String getData() 
    {
        return data;
    }
    public void setX(Long x) 
    {
        this.x = x;
    }

    public Long getX() 
    {
        return x;
    }
    public void setY(Long y) 
    {
        this.y = y;
    }

    public Long getY() 
    {
        return y;
    }
    public void setWidth(Long width) 
    {
        this.width = width;
    }

    public Long getWidth() 
    {
        return width;
    }
    public void setHeight(Long height) 
    {
        this.height = height;
    }

    public Long getHeight() 
    {
        return height;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("layerId", getLayerId())
            .append("faceId", getFaceId())
            .append("layerType", getLayerType())
            .append("data", getData())
            .append("x", getX())
            .append("y", getY())
            .append("width", getWidth())
            .append("height", getHeight())
            .toString();
    }
}
