package com.ruoyi.bricks.domain;

import java.util.Map;
import com.alibaba.fastjson2.annotation.JSONField;

/**
 * Face data domain object from JSON file
 *
 * @author ruoyi
 * @date 2026-03-12
 */
public class FaceData
{
    private String type;

    private Map<String, Object> data;

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Map<String, Object> getData()
    {
        return data;
    }

    public void setData(Map<String, Object> data)
    {
        this.data = data;
    }
}
