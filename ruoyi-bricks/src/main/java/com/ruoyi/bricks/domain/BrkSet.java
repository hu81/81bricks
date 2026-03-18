package com.ruoyi.bricks.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 套装对象 brk_set
 * 
 * @author ruoyi
 * @date 2026-03-16
 */
public class BrkSet extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 套装ID */
    private Long setId;

    /** 套装名称 */
    @Excel(name = "套装名称")
    private String setName;

    /** 套装UUID */
    @Excel(name = "套装UUID")
    private String uuid;

    /** 资产类型 */
    @Excel(name = "资产类型")
    private String assetType;

    /** 原始ID */
    @Excel(name = "原始ID")
    private String originId;

    /** 原始链接 */
    @Excel(name = "原始链接")
    private String originUrl;

    /** 备注 */
    @Excel(name = "备注")
    private String comments;

    /** 分类列表 */
    private java.util.List<BrkSetCategory> categories;

    public void setSetId(Long setId) 
    {
        this.setId = setId;
    }

    public Long getSetId() 
    {
        return setId;
    }

    public void setSetName(String setName) 
    {
        this.setName = setName;
    }

    public String getSetName() 
    {
        return setName;
    }

    public void setUuid(String uuid) 
    {
        this.uuid = uuid;
    }

    public String getUuid() 
    {
        return uuid;
    }

    public void setAssetType(String assetType) 
    {
        this.assetType = assetType;
    }

    public String getAssetType() 
    {
        return assetType;
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

    public void setComments(String comments) 
    {
        this.comments = comments;
    }

    public String getComments() 
    {
        return comments;
    }

    public java.util.List<BrkSetCategory> getCategories()
    {
        return categories;
    }

    public void setCategories(java.util.List<BrkSetCategory> categories)
    {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("setId", getSetId())
            .append("setName", getSetName())
            .append("uuid", getUuid())
            .append("assetType", getAssetType())
            .append("originId", getOriginId())
            .append("originUrl", getOriginUrl())
            .append("comments", getComments())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("categories", getCategories())
            .toString();
    }
}
