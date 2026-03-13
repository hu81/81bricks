package com.ruoyi.bricks.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 积木模型积木对象 brk_bricks_brick
 * 
 * @author ruoyi
 * @date 2026-03-13
 */
public class BrkBricksBrick extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 积木ID */
    private Long brickId;

    /** 积木模型ID */
    private Long bricksId;

    /** 积木索引 */
    @Excel(name = "积木索引")
    private Integer brickIndex;

    /** 积木件号 */
    @Excel(name = "积木件号")
    private String partNumber;

    /** 颜色ID */
    @Excel(name = "颜色ID")
    private String colorId;

    /** X坐标 */
    @Excel(name = "X坐标")
    private BigDecimal x;

    /** Y坐标 */
    @Excel(name = "Y坐标")
    private BigDecimal y;

    /** Z坐标 */
    @Excel(name = "Z坐标")
    private BigDecimal z;

    /** 变换矩阵M11 */
    private BigDecimal m11;

    /** 变换矩阵M12 */
    private BigDecimal m12;

    /** 变换矩阵M13 */
    private BigDecimal m13;

    /** 变换矩阵M14 */
    private BigDecimal m14;

    /** 变换矩阵M21 */
    private BigDecimal m21;

    /** 变换矩阵M22 */
    private BigDecimal m22;

    /** 变换矩阵M23 */
    private BigDecimal m23;

    /** 变换矩阵M24 */
    private BigDecimal m24;

    /** 变换矩阵M31 */
    private BigDecimal m31;

    /** 变换矩阵M32 */
    private BigDecimal m32;

    /** 变换矩阵M33 */
    private BigDecimal m33;

    /** 变换矩阵M34 */
    private BigDecimal m34;

    public void setBrickId(Long brickId) 
    {
        this.brickId = brickId;
    }

    public Long getBrickId() 
    {
        return brickId;
    }

    public void setBricksId(Long bricksId) 
    {
        this.bricksId = bricksId;
    }

    public Long getBricksId() 
    {
        return bricksId;
    }

    public void setBrickIndex(Integer brickIndex) 
    {
        this.brickIndex = brickIndex;
    }

    public Integer getBrickIndex() 
    {
        return brickIndex;
    }

    public void setPartNumber(String partNumber) 
    {
        this.partNumber = partNumber;
    }

    public String getPartNumber() 
    {
        return partNumber;
    }

    public void setColorId(String colorId) 
    {
        this.colorId = colorId;
    }

    public String getColorId() 
    {
        return colorId;
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

    public void setM11(BigDecimal m11) 
    {
        this.m11 = m11;
    }

    public BigDecimal getM11() { return m11; }

    public void setM12(BigDecimal m12) 
    {
        this.m12 = m12;
    }

    public BigDecimal getM12() { return m12; }

    public void setM13(BigDecimal m13) 
    {
        this.m13 = m13;
    }

    public BigDecimal getM13() { return m13; }

    public void setM14(BigDecimal m14) 
    {
        this.m14 = m14;
    }

    public BigDecimal getM14() { return m14; }

    public void setM21(BigDecimal m21) 
    {
        this.m21 = m21;
    }

    public BigDecimal getM21() { return m21; }

    public void setM22(BigDecimal m22) 
    {
        this.m22 = m22;
    }

    public BigDecimal getM22() { return m22; }

    public void setM23(BigDecimal m23) 
    {
        this.m23 = m23;
    }

    public BigDecimal getM23() { return m23; }

    public void setM24(BigDecimal m24) 
    {
        this.m24 = m24;
    }

    public BigDecimal getM24() { return m24; }

    public void setM31(BigDecimal m31) 
    {
        this.m31 = m31;
    }

    public BigDecimal getM31() { return m31; }

    public void setM32(BigDecimal m32) 
    {
        this.m32 = m32;
    }

    public BigDecimal getM32() { return m32; }

    public void setM33(BigDecimal m33) 
    {
        this.m33 = m33;
    }

    public BigDecimal getM33() { return m33; }

    public void setM34(BigDecimal m34) 
    {
        this.m34 = m34;
    }

    public BigDecimal getM34() { return m34; }

    public String toLdrLine()
    {
        return String.format("1 %s %s %s %s %s %s %s %s %s %s %s %s %s %s %s",
            nvl(colorId), nvl(x), nvl(y), nvl(z),
            nvl(m11), nvl(m12), nvl(m13),
            nvl(m21), nvl(m22), nvl(m23),
            nvl(m31), nvl(m32), nvl(m33),
            nvl(partNumber));
    }

    private String nvl(Object obj)
    {
        return obj == null ? "0" : obj.toString();
    }

    private String nvl(BigDecimal obj)
    {
        return obj == null ? "0" : obj.toString();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("brickId", getBrickId())
            .append("bricksId", getBricksId())
            .append("brickIndex", getBrickIndex())
            .append("partNumber", getPartNumber())
            .append("colorId", getColorId())
            .append("x", getX())
            .append("y", getY())
            .append("z", getZ())
            .toString();
    }
}
