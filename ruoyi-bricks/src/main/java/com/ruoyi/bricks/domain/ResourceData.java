package com.ruoyi.bricks.domain;

import java.util.Map;
import com.alibaba.fastjson2.annotation.JSONField;

/**
 * Resource data domain object from JSON file
 *
 * @author ruoyi
 * @date 2026-03-10
 */
public class ResourceData
{
    /** Resource type */
    private String type;

    /** Data containing texture and position */
    private Data data;

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Data getData()
    {
        return data;
    }

    public void setData(Data data)
    {
        this.data = data;
    }

    /**
     * Data containing texture and position
     */
    public static class Data
    {
        /** Texture information */
        private Texture texture;

        /** Position information */
        private Map<String, Object> position;

        public Texture getTexture()
        {
            return texture;
        }

        public void setTexture(Texture texture)
        {
            this.texture = texture;
        }

        public Map<String, Object> getPosition()
        {
            return position;
        }

        public void setPosition(Map<String, Object> position)
        {
            this.position = position;
        }
    }

    /**
     * Texture information
     */
    public static class Texture
    {
        /** Image data (base64) */
        private String image;

        public String getImage()
        {
            return image;
        }

        public void setImage(String image)
        {
            this.image = image;
        }
    }
}
