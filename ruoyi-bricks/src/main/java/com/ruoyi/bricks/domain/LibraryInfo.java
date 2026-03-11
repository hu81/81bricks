package com.ruoyi.bricks.domain;

import java.util.List;
import java.util.Map;
import com.alibaba.fastjson2.annotation.JSONField;

/**
 * Library configuration domain object
 *
 * @author ruoyi
 * @date 2026-03-10
 */
public class LibraryInfo
{
    /** Asset base URL */
    @JSONField(name = "asset_base")
    private String assetBase;

    /** Product version */
    @JSONField(name = "prd_version")
    private String prdVersion;

    /** Configuration */
    private Config config;

    /** Tabs v2 */
    @JSONField(name = "tabs_v2")
    private TabsV2 tabsV2;

    public String getAssetBase()
    {
        return assetBase;
    }

    public void setAssetBase(String assetBase)
    {
        this.assetBase = assetBase;
    }

    public String getPrdVersion()
    {
        return prdVersion;
    }

    public void setPrdVersion(String prdVersion)
    {
        this.prdVersion = prdVersion;
    }

    public Config getConfig()
    {
        return config;
    }

    public void setConfig(Config config)
    {
        this.config = config;
    }

    public TabsV2 getTabsV2()
    {
        return tabsV2;
    }

    public void setTabsV2(TabsV2 tabsV2)
    {
        this.tabsV2 = tabsV2;
    }

    /**
     * Tabs v2 domain object
     */
    public static class TabsV2
    {
        /** Labels list */
        private List<TabLabel> labels;

        public List<TabLabel> getLabels()
        {
            return labels;
        }

        public void setLabels(List<TabLabel> labels)
        {
            this.labels = labels;
        }
    }

    /**
     * Tab label domain object
     */
    public static class TabLabel
    {
        /** Label name */
        private String name;

        /** Sub labels */
        private List<TabLabel> labels;

        /** Assets */
        private List<String> assets;

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public List<TabLabel> getLabels()
        {
            return labels;
        }

        public void setLabels(List<TabLabel> labels)
        {
            this.labels = labels;
        }

        public List<String> getAssets()
        {
            return assets;
        }

        public void setAssets(List<String> assets)
        {
            this.assets = assets;
        }
    }

    /**
     * Configuration domain object
     */
    public static class Config
    {
        /** Default assets list */
        @JSONField(name = "default_assets")
        private List<String> defaultAssets;

        /** Special assets map */
        @JSONField(name = "special_assets")
        private Map<String, String> specialAssets;

        /** Base text colors list */
        @JSONField(name = "base_text_colors")
        private List<String> baseTextColors;

        /** Optional groups list */
        @JSONField(name = "optional_groups")
        private List<String> optionalGroups;

        /** Default colors map */
        @JSONField(name = "default_colors")
        private Map<String, Integer> defaultColors;

        /** Available colors map */
        @JSONField(name = "available_colors")
        private Map<String, List<Integer>> availableColors;

        /** Asset info map */
        @JSONField(name = "asset_info")
        private Map<String, AssetInfo> assetInfo;

        public List<String> getDefaultAssets()
        {
            return defaultAssets;
        }

        public void setDefaultAssets(List<String> defaultAssets)
        {
            this.defaultAssets = defaultAssets;
        }

        public Map<String, String> getSpecialAssets()
        {
            return specialAssets;
        }

        public void setSpecialAssets(Map<String, String> specialAssets)
        {
            this.specialAssets = specialAssets;
        }

        public List<String> getBaseTextColors()
        {
            return baseTextColors;
        }

        public void setBaseTextColors(List<String> baseTextColors)
        {
            this.baseTextColors = baseTextColors;
        }

        public List<String> getOptionalGroups()
        {
            return optionalGroups;
        }

        public void setOptionalGroups(List<String> optionalGroups)
        {
            this.optionalGroups = optionalGroups;
        }

        public Map<String, Integer> getDefaultColors()
        {
            return defaultColors;
        }

        public void setDefaultColors(Map<String, Integer> defaultColors)
        {
            this.defaultColors = defaultColors;
        }

        public Map<String, List<Integer>> getAvailableColors()
        {
            return availableColors;
        }

        public void setAvailableColors(Map<String, List<Integer>> availableColors)
        {
            this.availableColors = availableColors;
        }

        public Map<String, AssetInfo> getAssetInfo()
        {
            return assetInfo;
        }

        public void setAssetInfo(Map<String, AssetInfo> assetInfo)
        {
            this.assetInfo = assetInfo;
        }
    }

    /**
     * Asset info domain object
     */
    public static class AssetInfo
    {
        /** DIY tops flag */
        private Boolean diytops;

        /** Type */
        private String type;

        /** Models */
        private Map<String, Object> models;

        /** DAMS info */
        @JSONField(name = "dams_info")
        private DamInfo damsInfo;

        public Boolean getDiytops()
        {
            return diytops;
        }

        public void setDiytops(Boolean diytops)
        {
            this.diytops = diytops;
        }

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public Map<String, Object> getModels()
        {
            return models;
        }

        public void setModels(Map<String, Object> models)
        {
            this.models = models;
        }

        public DamInfo getDamsInfo()
        {
            return damsInfo;
        }

        public void setDamsInfo(DamInfo damsInfo)
        {
            this.damsInfo = damsInfo;
        }
    }

    /**
     * DAMS info domain object
     */
    public static class DamInfo
    {
        /** DAMS ID */
        private Long id;

        /** DAMS name */
        private String name;

        public Long getId()
        {
            return id;
        }

        public void setId(Long id)
        {
            this.id = id;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }
    }
}
