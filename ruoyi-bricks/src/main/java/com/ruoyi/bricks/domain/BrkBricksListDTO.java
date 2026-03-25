package com.ruoyi.bricks.domain;

public class BrkBricksListDTO extends BrkBricks
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