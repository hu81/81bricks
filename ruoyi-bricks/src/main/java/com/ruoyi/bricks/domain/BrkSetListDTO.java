package com.ruoyi.bricks.domain;

public class BrkSetListDTO extends BrkSet
{
    private static final long serialVersionUID = 1L;

    private boolean includeLdr;

    public boolean isIncludeLdr()
    {
        return includeLdr;
    }

    public void setIncludeLdr(boolean includeLdr)
    {
        this.includeLdr = includeLdr;
    }
}