package com.ruoyi.bricks.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 积木模型分组对象 brk_bricks_group
 * 
 * @author ruoyi
 * @date 2026-03-23
 */
public class BrkBricksGroup extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long groupId;

    private Long bricksId;

    private Integer groupIndex;

    private String groupName;

    private String refId;

    private BigDecimal x;

    private BigDecimal y;

    private BigDecimal z;

    private BigDecimal m11;

    private BigDecimal m12;

    private BigDecimal m13;

    private BigDecimal m14;

    private BigDecimal m21;

    private BigDecimal m22;

    private BigDecimal m23;

    private BigDecimal m24;

    private BigDecimal m31;

    private BigDecimal m32;

    private BigDecimal m33;

    private BigDecimal m34;

    public Long getGroupId()
    {
        return groupId;
    }

    public void setGroupId(Long groupId)
    {
        this.groupId = groupId;
    }

    public Long getBricksId()
    {
        return bricksId;
    }

    public void setBricksId(Long bricksId)
    {
        this.bricksId = bricksId;
    }

    public Integer getGroupIndex()
    {
        return groupIndex;
    }

    public void setGroupIndex(Integer groupIndex)
    {
        this.groupIndex = groupIndex;
    }

    public String getGroupName()
    {
        return groupName;
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public String getRefId()
    {
        return refId;
    }

    public void setRefId(String refId)
    {
        this.refId = refId;
    }

    public BigDecimal getX()
    {
        return x;
    }

    public void setX(BigDecimal x)
    {
        this.x = x;
    }

    public BigDecimal getY()
    {
        return y;
    }

    public void setY(BigDecimal y)
    {
        this.y = y;
    }

    public BigDecimal getZ()
    {
        return z;
    }

    public void setZ(BigDecimal z)
    {
        this.z = z;
    }

    public BigDecimal getM11()
    {
        return m11;
    }

    public void setM11(BigDecimal m11)
    {
        this.m11 = m11;
    }

    public BigDecimal getM12()
    {
        return m12;
    }

    public void setM12(BigDecimal m12)
    {
        this.m12 = m12;
    }

    public BigDecimal getM13()
    {
        return m13;
    }

    public void setM13(BigDecimal m13)
    {
        this.m13 = m13;
    }

    public BigDecimal getM14()
    {
        return m14;
    }

    public void setM14(BigDecimal m14)
    {
        this.m14 = m14;
    }

    public BigDecimal getM21()
    {
        return m21;
    }

    public void setM21(BigDecimal m21)
    {
        this.m21 = m21;
    }

    public BigDecimal getM22()
    {
        return m22;
    }

    public void setM22(BigDecimal m22)
    {
        this.m22 = m22;
    }

    public BigDecimal getM23()
    {
        return m23;
    }

    public void setM23(BigDecimal m23)
    {
        this.m23 = m23;
    }

    public BigDecimal getM24()
    {
        return m24;
    }

    public void setM24(BigDecimal m24)
    {
        this.m24 = m24;
    }

    public BigDecimal getM31()
    {
        return m31;
    }

    public void setM31(BigDecimal m31)
    {
        this.m31 = m31;
    }

    public BigDecimal getM32()
    {
        return m32;
    }

    public void setM32(BigDecimal m32)
    {
        this.m32 = m32;
    }

    public BigDecimal getM33()
    {
        return m33;
    }

    public void setM33(BigDecimal m33)
    {
        this.m33 = m33;
    }

    public BigDecimal getM34()
    {
        return m34;
    }

    public void setM34(BigDecimal m34)
    {
        this.m34 = m34;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("groupId", getGroupId())
            .append("bricksId", getBricksId())
            .append("groupIndex", getGroupIndex())
            .append("groupName", getGroupName())
            .append("refId", getRefId())
            .append("x", getX())
            .append("y", getY())
            .append("z", getZ())
            .toString();
    }
}