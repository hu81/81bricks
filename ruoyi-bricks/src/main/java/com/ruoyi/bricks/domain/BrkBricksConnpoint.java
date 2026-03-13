package com.ruoyi.bricks.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 积木模型连接点对象 brk_bricks_connpoint
 * 
 * @author ruoyi
 * @date 2026-03-13
 */
public class BrkBricksConnpoint extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 连接点ID */
    private Long connId;

    /** 积木模型ID */
    private Long bricksId;

    /** 连接点索引 */
    @Excel(name = "连接点索引")
    private Integer connIndex;

    /** 连接类型 */
    @Excel(name = "连接类型")
    private String connType;

    /** Stud类型 */
    @Excel(name = "Stud类型")
    private String studType;

    /** X坐标 */
    @Excel(name = "X坐标")
    private BigDecimal x;

    /** Y坐标 */
    @Excel(name = "Y坐标")
    private BigDecimal y;

    /** Z坐标 */
    @Excel(name = "Z坐标")
    private BigDecimal z;

    /** 法向量X */
    private BigDecimal nx;

    /** 法向量Y */
    private BigDecimal ny;

    /** 法向量Z */
    private BigDecimal nz;

    public void setConnId(Long connId) 
    {
        this.connId = connId;
    }

    public Long getConnId() 
    {
        return connId;
    }

    public void setBricksId(Long bricksId) 
    {
        this.bricksId = bricksId;
    }

    public Long getBricksId() 
    {
        return bricksId;
    }

    public void setConnIndex(Integer connIndex) 
    {
        this.connIndex = connIndex;
    }

    public Integer getConnIndex() 
    {
        return connIndex;
    }

    public void setConnType(String connType) 
    {
        this.connType = connType;
    }

    public String getConnType() 
    {
        return connType;
    }

    public void setStudType(String studType) 
    {
        this.studType = studType;
    }

    public String getStudType() 
    {
        return studType;
    }

    public void setX(BigDecimal x) 
    {
        this.x = x;
    }

    public BigDecimal getX() 
    {
        return x;
    }

    public void setY(BigDecimal y) 
    {
        this.y = y;
    }

    public BigDecimal getY() 
    {
        return y;
    }

    public void setZ(BigDecimal z) 
    {
        this.z = z;
    }

    public BigDecimal getZ() 
    {
        return z;
    }

    public void setNx(BigDecimal nx) 
    {
        this.nx = nx;
    }

    public BigDecimal getNx() 
    {
        return nx;
    }

    public void setNy(BigDecimal ny) 
    {
        this.ny = ny;
    }

    public BigDecimal getNy() 
    {
        return ny;
    }

    public void setNz(BigDecimal nz) 
    {
        this.nz = nz;
    }

    public BigDecimal getNz() 
    {
        return nz;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("connId", getConnId())
            .append("bricksId", getBricksId())
            .append("connIndex", getConnIndex())
            .append("connType", getConnType())
            .append("studType", getStudType())
            .append("x", getX())
            .append("y", getY())
            .append("z", getZ())
            .toString();
    }
}
