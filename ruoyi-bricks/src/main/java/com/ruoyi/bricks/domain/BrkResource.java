package com.ruoyi.bricks.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 资源图片对象 brk_resource
 * 
 * @author ruoyi
 * @date 2026-03-10
 */
public class BrkResource extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 资源ID */
    private Long resourceId;

    /** 资源类型 */
    @Excel(name = "资源类型")
    private String type;

    /** 资源id */
    @Excel(name = "资源id")
    private String originId;

    /** 资源链接 */
    @Excel(name = "资源链接")
    private String originUrl;

    /** 图片数据 */
    @Excel(name = "图片数据")
    private String data;

    public void setResourceId(Long resourceId) 
    {
        this.resourceId = resourceId;
    }

    public Long getResourceId() 
    {
        return resourceId;
    }

    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
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

    public void setData(String data) 
    {
        this.data = data;
    }

    public String getData() 
    {
        return data;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("resourceId", getResourceId())
            .append("type", getType())
            .append("originId", getOriginId())
            .append("originUrl", getOriginUrl())
            .append("data", getData())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
