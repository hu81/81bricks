package com.ruoyi.bricks.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 积木模型网格对象 brk_bricks_mesh
 * 
 * @author ruoyi
 * @date 2026-03-17
 */
public class BrkBricksMesh extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long meshId;

    private Long bricksId;

    private Integer meshIndex;

    private String refId;

    private String partNumber;

    private String base;

    private String meshType;

    private String textureLeft;

    private String textureRight;

    private String textureTop;

    private String textureBack;

    private String textureBottom;

    public void setMeshId(Long meshId) 
    {
        this.meshId = meshId;
    }

    public Long getMeshId() 
    {
        return meshId;
    }

    public void setBricksId(Long bricksId) 
    {
        this.bricksId = bricksId;
    }

    public Long getBricksId() 
    {
        return bricksId;
    }

    public void setMeshIndex(Integer meshIndex) 
    {
        this.meshIndex = meshIndex;
    }

    public Integer getMeshIndex() 
    {
        return meshIndex;
    }

    public void setRefId(String refId) 
    {
        this.refId = refId;
    }

    public String getRefId() 
    {
        return refId;
    }

    public void setPartNumber(String partNumber) 
    {
        this.partNumber = partNumber;
    }

    public String getPartNumber() 
    {
        return partNumber;
    }

    public void setBase(String base) 
    {
        this.base = base;
    }

    public String getBase() 
    {
        return base;
    }

    public void setMeshType(String meshType) 
    {
        this.meshType = meshType;
    }

    public String getMeshType() 
    {
        return meshType;
    }

    public void setTextureLeft(String textureLeft) 
    {
        this.textureLeft = textureLeft;
    }

    public String getTextureLeft() 
    {
        return textureLeft;
    }

    public void setTextureRight(String textureRight) 
    {
        this.textureRight = textureRight;
    }

    public String getTextureRight() 
    {
        return textureRight;
    }

    public void setTextureTop(String textureTop) 
    {
        this.textureTop = textureTop;
    }

    public String getTextureTop() 
    {
        return textureTop;
    }

    public void setTextureBack(String textureBack) 
    {
        this.textureBack = textureBack;
    }

    public String getTextureBack() 
    {
        return textureBack;
    }

    public void setTextureBottom(String textureBottom) 
    {
        this.textureBottom = textureBottom;
    }

    public String getTextureBottom() 
    {
        return textureBottom;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("meshId", getMeshId())
            .append("bricksId", getBricksId())
            .append("meshIndex", getMeshIndex())
            .append("refId", getRefId())
            .append("partNumber", getPartNumber())
            .append("base", getBase())
            .append("meshType", getMeshType())
            .append("textureLeft", getTextureLeft())
            .append("textureRight", getTextureRight())
            .append("textureTop", getTextureTop())
            .append("textureBack", getTextureBack())
            .append("textureBottom", getTextureBottom())
            .toString();
    }
}
