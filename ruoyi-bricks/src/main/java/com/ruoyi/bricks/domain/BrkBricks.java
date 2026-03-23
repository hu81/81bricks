package com.ruoyi.bricks.domain;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 积木模型对象 brk_bricks
 * 
 * @author ruoyi
 * @date 2026-03-13
 */
public class BrkBricks extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 积木模型ID */
    private Long bricksId;

    /** 积木模型名称 */
    @Excel(name = "积木模型名称")
    private String bricksName;

    /** 模型UUID */
    @Excel(name = "模型UUID")
    private String uuid;

    /** 资产类型 */
    @Excel(name = "资产类型")
    private String assetType;

    /** 分类(hair/body/shoes等) */
    @Excel(name = "分类")
    private String category;

    /** DIY分组 */
    @Excel(name = "DIY分组")
    private String diyGroup;

    /** 根分组ID */
    @Excel(name = "根分组ID")
    private Integer rootGroup;

    /** 默认颜色ID */
    @Excel(name = "默认颜色ID")
    private Integer defaultColor;

    /** 原始ID */
    @Excel(name = "原始ID")
    private String originId;

    /** 原始链接 */
    @Excel(name = "原始链接")
    private String originUrl;

    /** 备注 */
    @Excel(name = "备注")
    private String comments;

    /** 积木列表 */
    private List<BrkBricksBrick> bricks;

    /** 连接点列表 */
    private List<BrkBricksConnpoint> connpoints;

    /** 分组列表 */
    private List<BrkBricksGroup> groups;

    /** LDR数据 */
    private List<String> ldrData;

    public void setBricksId(Long bricksId) 
    {
        this.bricksId = bricksId;
    }

    public Long getBricksId() 
    {
        return bricksId;
    }

    public void setBricksName(String bricksName) 
    {
        this.bricksName = bricksName;
    }

    public String getBricksName() 
    {
        return bricksName;
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

    public void setCategory(String category) 
    {
        this.category = category;
    }

    public String getCategory() 
    {
        return category;
    }

    public void setDiyGroup(String diyGroup) 
    {
        this.diyGroup = diyGroup;
    }

    public String getDiyGroup() 
    {
        return diyGroup;
    }

    public void setRootGroup(Integer rootGroup) 
    {
        this.rootGroup = rootGroup;
    }

    public Integer getRootGroup() 
    {
        return rootGroup;
    }

    public void setDefaultColor(Integer defaultColor) 
    {
        this.defaultColor = defaultColor;
    }

    public Integer getDefaultColor() 
    {
        return defaultColor;
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

    public List<BrkBricksBrick> getBricks()
    {
        return bricks;
    }

    public void setBricks(List<BrkBricksBrick> bricks)
    {
        this.bricks = bricks;
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

    public List<String> getLdrData()
    {
        return ldrData;
    }

    public void setLdrData(List<String> ldrData)
    {
        this.ldrData = ldrData;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("bricksId", getBricksId())
            .append("bricksName", getBricksName())
            .append("uuid", getUuid())
            .append("assetType", getAssetType())
            .append("category", getCategory())
            .append("diyGroup", getDiyGroup())
            .append("rootGroup", getRootGroup())
            .append("defaultColor", getDefaultColor())
            .append("originId", getOriginId())
            .append("originUrl", getOriginUrl())
        .append("comments", getComments())
        .append("createBy", getCreateBy())
        .append("createTime", getCreateTime())
        .append("updateBy", getUpdateBy())
        .append("updateTime", getUpdateTime())
        .append("bricks", getBricks())
        .append("connpoints", getConnpoints())
        .append("ldrData", getLdrData())
        .toString();
    }
}
