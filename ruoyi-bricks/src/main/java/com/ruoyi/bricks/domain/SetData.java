package com.ruoyi.bricks.domain;

import java.util.Map;
import com.alibaba.fastjson2.annotation.JSONField;

/**
 * Set data JSON parsing domain object
 * 
 * @author ruoyi
 * @date 2026-03-16
 */
public class SetData
{
    /** Type */
    private String type;

    /** Data */
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

    /**
     * Get category data by category name
     */
    public CategoryData getCategoryData(String categoryName)
    {
        if (data == null || categoryName == null)
        {
            return null;
        }
        
        Object categoryObj = data.get(categoryName);
        if (categoryObj instanceof Map)
        {
            @SuppressWarnings("unchecked")
            Map<String, Object> categoryMap = (Map<String, Object>) categoryObj;
            CategoryData categoryData = new CategoryData();
            categoryData.setUuid((String) categoryMap.get("uuid"));
            
            Object dataObj = categoryMap.get("data");
            if (dataObj instanceof Map)
            {
                @SuppressWarnings("unchecked")
                Map<String, Object> innerDataMap = (Map<String, Object>) dataObj;
                categoryData.setVersion((String) innerDataMap.get("version"));
                categoryData.setInstance(innerDataMap.get("instance"));
            }
            return categoryData;
        }
        return null;
    }

    /**
     * Category data inner class
     */
    public static class CategoryData
    {
        private String uuid;
        private String version;
        private Object instance;

        public String getUuid()
        {
            return uuid;
        }

        public void setUuid(String uuid)
        {
            this.uuid = uuid;
        }

        public String getVersion()
        {
            return version;
        }

        public void setVersion(String version)
        {
            this.version = version;
        }

        public Object getInstance()
        {
            return instance;
        }

        public void setInstance(Object instance)
        {
            this.instance = instance;
        }
    }
}
