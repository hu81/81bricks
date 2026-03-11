package com.ruoyi.bricks.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.bricks.mapper.BrkResourceMapper;
import com.ruoyi.bricks.domain.BrkResource;
import com.ruoyi.bricks.domain.LibraryInfo;
import com.ruoyi.bricks.domain.ResourceData;
import com.ruoyi.bricks.service.IBrkResourceService;
import com.ruoyi.common.config.RuoYiConfig;
import com.alibaba.fastjson2.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 资源图片Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-03-10
 */
@Service
public class BrkResourceServiceImpl implements IBrkResourceService 
{
    private static final Logger log = LoggerFactory.getLogger(BrkResourceServiceImpl.class);

    @Autowired
    private BrkResourceMapper brkResourceMapper;

    /**
     * 查询资源图片
     * 
     * @param resourceId 资源图片主键
     * @return 资源图片
     */
    @Override
    public BrkResource selectBrkResourceByResourceId(Long resourceId)
    {
        return brkResourceMapper.selectBrkResourceByResourceId(resourceId);
    }

    /**
     * 查询资源图片列表
     * 
     * @param brkResource 资源图片
     * @return 资源图片
     */
    @Override
    public List<BrkResource> selectBrkResourceList(BrkResource brkResource)
    {
        return brkResourceMapper.selectBrkResourceList(brkResource);
    }

    /**
     * 新增资源图片
     * 
     * @param brkResource 资源图片
     * @return 结果
     */
    @Override
    public int insertBrkResource(BrkResource brkResource)
    {
        brkResource.setCreateTime(DateUtils.getNowDate());
        return brkResourceMapper.insertBrkResource(brkResource);
    }

    /**
     * 修改资源图片
     * 
     * @param brkResource 资源图片
     * @return 结果
     */
    @Override
    public int updateBrkResource(BrkResource brkResource)
    {
        brkResource.setUpdateTime(DateUtils.getNowDate());
        return brkResourceMapper.updateBrkResource(brkResource);
    }

    /**
     * 批量删除资源图片
     * 
     * @param resourceIds 需要删除的资源图片主键
     * @return 结果
     */
    @Override
    public int deleteBrkResourceByResourceIds(Long[] resourceIds)
    {
        return brkResourceMapper.deleteBrkResourceByResourceIds(resourceIds);
    }

    /**
     * 删除资源图片信息
     * 
     * @param resourceId 资源图片主键
     * @return 结果
     */
    @Override
    public int deleteBrkResourceByResourceId(Long resourceId)
    {
        return brkResourceMapper.deleteBrkResourceByResourceId(resourceId);
    }

    /**
     * Parse library JSON file and save eye images to database
     *
     * @return Library configuration object
     */
    @Override
    public LibraryInfo parseLibraryJson(String url)
    {
        String jsonContent = "";
        try
        {
            if (StringUtils.isEmpty(url)) {
                String jsonFilePath = RuoYiConfig.getProfile() + "/library/library_20260306.json";
                log.info("Parsing library JSON file from: {}", jsonFilePath);
                File file = new File(jsonFilePath);
                StringBuilder jsonString = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new FileReader(file)))
                {
                    String line;
                    while ((line = reader.readLine()) != null)
                    {
                        jsonString.append(line);
                    }
                    jsonContent = jsonString.toString();
                }
            } else {
                log.info("Downloading library JSON from URL: {}", url);
                jsonContent = downloadUrlContent(url);
            }

            LibraryInfo libraryInfo = JSON.parseObject(jsonContent, LibraryInfo.class);

            if (libraryInfo.getConfig() != null && libraryInfo.getConfig().getAssetInfo() != null)
            {
                saveResourceImages(libraryInfo);
            }

            log.info("Successfully parsed library JSON file and saved eye images");
            return libraryInfo;
        }
        catch (Exception e)
        {
            log.error("Failed to parse library JSON file", e);
            throw new RuntimeException("Failed to parse library JSON", e);
        }
    }

    /**
     * Save eye images from library asset_info to database
     *
     * @param libraryInfo Library configuration object
     */
    private void saveResourceImages(LibraryInfo libraryInfo)
    {
        try
        {
            int insertCount = 0;
            int updateCount = 0;
            int skipCount = 0;
            for (Map.Entry<String, LibraryInfo.AssetInfo> entry : libraryInfo.getConfig().getAssetInfo().entrySet())
            {
                String url = entry.getKey();
                LibraryInfo.AssetInfo assetInfo = entry.getValue();

                if ("眼睛".equals(assetInfo.getType()) || "眉毛".equals(assetInfo.getType()) || "眼镜".equals(assetInfo.getType()) || "衣服贴纸".equals(assetInfo.getType())
                        || "嘴巴".equals(assetInfo.getType()) || "贴纸".equals(assetInfo.getType()))
                {
                    log.info("Processing asset: {}", url);
                    ResourceData resourceData = downloadAndParseResourceJson(url);

                    if (resourceData != null && resourceData.getData() != null && resourceData.getData().getTexture() != null)
                    {
                        String imageData = resourceData.getData().getTexture().getImage();
                        String originId = url.substring(url.lastIndexOf("/") + 1);
                        String type = resourceData.getType();

                        if (imageData != null && !imageData.isEmpty())
                        {
                            BrkResource existingResource = brkResourceMapper.selectBrkResourceByOriginId(originId);

                            if (existingResource != null)
                            {
                                int existingDataLength = existingResource.getData() != null ? existingResource.getData().length() : 0;
                                int newDataLength = imageData.length();

                                if (existingDataLength == newDataLength)
                                {
                                    skipCount++;
                                    log.info("Skipped (data unchanged): {}", originId);
                                }
                                else
                                {
                                    existingResource.setData(imageData);
                                    existingResource.setUpdateTime(DateUtils.getNowDate());
                                    brkResourceMapper.updateBrkResource(existingResource);
                                    updateCount++;
                                    log.info("Updated: {}", originId);
                                }
                            }
                            else
                            {
                                BrkResource resource = new BrkResource();
                                resource.setType(type);
                                resource.setOriginId(originId);
                                resource.setOriginUrl(url);
                                resource.setData(imageData);

                                brkResourceMapper.insertBrkResource(resource);
                                insertCount++;
                                log.info("Inserted: {}", originId);
                            }
                        }
                    }
                }
            }
            log.info("Total processed - Insert: {}, Update: {}, Skip: {}", insertCount, updateCount, skipCount);
        }
        catch (Exception e)
        {
            log.error("Failed to save eye images", e);
        }
    }

    /**
     * Download and parse resource JSON file
     *
     * @param url URL to download
     * @return ResourceData object
     */
    private ResourceData downloadAndParseResourceJson(String url)
    {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try
        {
            URL httpUrl = new URL(url);
            connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK)
            {
                inputStream = connection.getInputStream();
                String response = readInputStream(inputStream);

                ResourceData resourceData = JSON.parseObject(response, ResourceData.class);
                log.debug("Parsed resource JSON from URL: {}", url);
                return resourceData;
            }
            else
            {
                log.warn("HTTP response code {} for URL: {}", responseCode, url);
            }
        }
        catch (Exception e)
        {
            log.error("Failed to download and parse resource JSON from URL: {}", url, e);
        }
        finally
        {
            if (inputStream != null)
            {
                try
                {
                    inputStream.close();
                }
                catch (IOException e)
                {
                    log.error("Failed to close input stream", e);
                }
            }
            if (connection != null)
            {
                connection.disconnect();
            }
        }
        return null;
    }

    /**
     * Download content from URL
     *
     * @param urlString URL to download
     * @return Content as string
     */
    private String downloadUrlContent(String urlString)
    {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try
        {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(30000);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK)
            {
                inputStream = connection.getInputStream();
                return readInputStream(inputStream);
            }
            else
            {
                throw new RuntimeException("HTTP response code: " + responseCode);
            }
        }
        catch (Exception e)
        {
            log.error("Failed to download content from URL: {}", urlString, e);
            throw new RuntimeException("Failed to download library JSON", e);
        }
        finally
        {
            if (inputStream != null)
            {
                try
                {
                    inputStream.close();
                }
                catch (IOException e)
                {
                    log.error("Failed to close input stream", e);
                }
            }
            if (connection != null)
            {
                connection.disconnect();
            }
        }
    }

    /**
     * Read input stream to string
     *
     * @param inputStream Input stream
     * @return String content
     * @throws IOException IO exception
     */
    private String readInputStream(InputStream inputStream) throws IOException
    {
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8")))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                response.append(line);
            }
        }
        return response.toString();
    }
}
