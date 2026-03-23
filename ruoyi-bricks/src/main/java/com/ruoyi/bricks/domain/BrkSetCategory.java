package com.ruoyi.bricks.domain;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 套装分类对象 brk_set_category
 * 
 * @author ruoyi
 * @date 2026-03-16
 */
public class BrkSetCategory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 分类ID */
    private Long categoryId;

    /** 套装ID */
    @Excel(name = "套装ID")
    private Long setId;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String categoryName;

    /** 分类UUID */
    @Excel(name = "分类UUID")
    private String uuid;

    /** 版本 */
    @Excel(name = "版本")
    private String version;

    /** 网格列表 */
    private List<BrkSetMesh> meshes;

    /** 积木数据(临时存储，用于解析) */
    private java.util.Map<String, Object> brickData;

    /** 连接点列表(临时存储，用于解析) */
    private List<BrkBricksConnpoint> connpoints;

    /** 分组列表(临时存储，用于解析) */
    private List<BrkBricksGroup> groups;

    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }

    public void setSetId(Long setId) 
    {
        this.setId = setId;
    }

    public Long getSetId() 
    {
        return setId;
    }

    public void setCategoryName(String categoryName) 
    {
        this.categoryName = categoryName;
    }

    public String getCategoryName() 
    {
        return categoryName;
    }

    public void setUuid(String uuid) 
    {
        this.uuid = uuid;
    }

    public String getUuid() 
    {
        return uuid;
    }

    public void setVersion(String version) 
    {
        this.version = version;
    }

    public String getVersion() 
    {
        return version;
    }

    public List<BrkSetMesh> getMeshes()
    {
        return meshes;
    }

    public void setMeshes(List<BrkSetMesh> meshes)
    {
        this.meshes = meshes;
    }

    public java.util.Map<String, Object> getBrickData()
    {
        return brickData;
    }

    public void setBrickData(java.util.Map<String, Object> brickData)
    {
        this.brickData = brickData;
    }

    public List<BrkBricksConnpoint> getConnpoints()
    {
        return connpoints;
    }

    public void setConnpoints(List<BrkBricksConnpoint> connpoints)
    {
        this.connpoints = connpoints;
    }

    public List<BrkBricksGroup> getGroups()
    {
        return groups;
    }

    public void setGroups(List<BrkBricksGroup> groups)
    {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("categoryId", getCategoryId())
            .append("setId", getSetId())
            .append("categoryName", getCategoryName())
            .append("uuid", getUuid())
            .append("version", getVersion())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("meshes", getMeshes())
            .toString();
    }
}
