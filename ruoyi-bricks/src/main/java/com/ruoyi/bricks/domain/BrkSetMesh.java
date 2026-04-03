package com.ruoyi.bricks.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 套装网格对象 brk_set_mesh
 * 
 * @author ruoyi
 * @date 2026-03-16
 */
public class BrkSetMesh extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 网格ID */
    private Long meshId;

    /** 分类ID */
    @Excel(name = "分类ID")
    private Long categoryId;

    /** 网格索引 */
    @Excel(name = "网格索引")
    private Integer meshIndex;

    /** 纹理引用ID */
    @Excel(name = "纹理引用ID")
    private String refId;

    /** 零件号 */
    @Excel(name = "零件号")
    private String partNumber;

    /** 基础零件号 */
    @Excel(name = "基础零件号")
    private String base;

    /** 网格类型 */
    @Excel(name = "网格类型")
    private String meshType;

    /** 左侧纹理 */
    private String textureLeft;

    /** 右侧纹理 */
    private String textureRight;

    /** 顶部纹理 */
    private String textureTop;

    /** 背面纹理 */
    private String textureBack;

    /** 底部纹理 */
    private String textureBottom;

    /** 前面纹理 */
    private String textureFront;

    public void setMeshId(Long meshId) 
    {
        this.meshId = meshId;
    }

    public Long getMeshId() 
    {
        return meshId;
    }

    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
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

    public void setTextureFront(String textureFront)
    {
        this.textureFront = textureFront;
    }

    public String getTextureFront()
    {
        return textureFront;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("meshId", getMeshId())
            .append("categoryId", getCategoryId())
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
            .append("textureFront", getTextureFront())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .toString();
    }
}
