package com.ruoyi.bricks.domain;

import java.util.Map;
import com.alibaba.fastjson2.annotation.JSONField;

/**
 * 发型/积木模型数据解析
 * 
 * @author ruoyi
 * @date 2026-03-13
 */
public class HairData
{
    /** 类型 */
    private String type;

    /** 数据 */
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

    public static class Data
    {
        /** 模型 */
        private Model model;

        /** 默认颜色 */
        @JSONField(name = "default_color")
        private Integer defaultColor;

        public Model getModel()
        {
            return model;
        }

        public void setModel(Model model)
        {
            this.model = model;
        }

        public Integer getDefaultColor()
        {
            return defaultColor;
        }

        public void setDefaultColor(Integer defaultColor)
        {
            this.defaultColor = defaultColor;
        }
    }

    public static class Model
    {
        /** UUID */
        private String uuid;

        /** 数据 */
        private ModelData data;

        public String getUuid()
        {
            return uuid;
        }

        public void setUuid(String uuid)
        {
            this.uuid = uuid;
        }

        public ModelData getData()
        {
            return data;
        }

        public void setData(ModelData data)
        {
            this.data = data;
        }
    }

    public static class ModelData
    {
        /** 版本 */
        private String version;

        /** 实例 */
        private Instance instance;

        /** 配置 */
        private Config config;

        public String getVersion()
        {
            return version;
        }

        public void setVersion(String version)
        {
            this.version = version;
        }

        public Instance getInstance()
        {
            return instance;
        }

        public void setInstance(Instance instance)
        {
            this.instance = instance;
        }

        public Config getConfig()
        {
            return config;
        }

        public void setConfig(Config config)
        {
            this.config = config;
        }
    }

    public static class Instance
    {
        /** 网格 */
        private Map<String, Object> mesh;

        /** 积木 */
        private Map<String, Object[]> brick;

        /** 分组 */
        private Map<String, Object[]> group;

        /** 连接点 */
        private Map<String, Object[]> connpoint;

        public Map<String, Object> getMesh()
        {
            return mesh;
        }

        public void setMesh(Map<String, Object> mesh)
        {
            this.mesh = mesh;
        }

        public Map<String, Object[]> getBrick()
        {
            return brick;
        }

        public void setBrick(Map<String, Object[]> brick)
        {
            this.brick = brick;
        }

        public Map<String, Object[]> getGroup()
        {
            return group;
        }

        public void setGroup(Map<String, Object[]> group)
        {
            this.group = group;
        }

        public Map<String, Object[]> getConnpoint()
        {
            return connpoint;
        }

        public void setConnpoint(Map<String, Object[]> connpoint)
        {
            this.connpoint = connpoint;
        }
    }

    public static class Config
    {
        /** DIY分组 */
        @JSONField(name = "diy_group")
        private String diyGroup;

        /** 根节点 */
        private Integer root;

        public String getDiyGroup()
        {
            return diyGroup;
        }

        public void setDiyGroup(String diyGroup)
        {
            this.diyGroup = diyGroup;
        }

        public Integer getRoot()
        {
            return root;
        }

        public void setRoot(Integer root)
        {
            this.root = root;
        }
    }
}
