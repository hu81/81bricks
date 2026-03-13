package com.ruoyi.bricks.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.bricks.mapper.BrkResourceMapper;
import com.ruoyi.bricks.mapper.BrkFaceMapper;
import com.ruoyi.bricks.domain.BrkResource;
import com.ruoyi.bricks.domain.BrkFace;
import com.ruoyi.bricks.domain.BrkFaceLayer;
import com.ruoyi.bricks.domain.LibraryInfo;
import com.ruoyi.bricks.domain.ResourceData;
import com.ruoyi.bricks.domain.FaceData;
import com.ruoyi.bricks.service.IBrkResourceService;
import com.ruoyi.bricks.service.IBrkFaceService;
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

    @Autowired
    private BrkFaceMapper brkFaceMapper;

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

            if (libraryInfo.getTabsV2() != null && libraryInfo.getTabsV2().getLabels() != null)
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
     * Save eye images from tabs_v2/labels[name=表情]/labels/assets to database
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

            if (libraryInfo.getTabsV2() == null || libraryInfo.getTabsV2().getLabels() == null)
            {
                log.warn("No tabs_v2 found in library JSON");
                return;
            }

            LibraryInfo.TabLabel expressionLabel = null;
            for (LibraryInfo.TabLabel label : libraryInfo.getTabsV2().getLabels())
            {
                if ("表情".equals(label.getName()))
                {
                    expressionLabel = label;
                    break;
                }
            }

            if (expressionLabel == null)
            {
                log.warn("No label with name='表情' found in tabs_v2");
                return;
            }

            if (expressionLabel.getLabels() == null)
            {
                log.warn("No sub-labels found under '表情' label");
                return;
            }

            for (LibraryInfo.TabLabel subLabel : expressionLabel.getLabels())
            {
                if ("推荐".equals(subLabel.getName()))
                {
                    saveFaceImages(subLabel);
                    continue;
                }

                if (subLabel.getAssets() == null || subLabel.getAssets().isEmpty())
                {
                    continue;
                }

                for (String assetUrl : subLabel.getAssets())
                {
                    log.info("Processing asset: {}", assetUrl);
                    ResourceData resourceData = downloadAndParseResourceJson(assetUrl);

                    if (resourceData != null && resourceData.getData() != null && resourceData.getData().getTexture() != null)
                    {
                        String imageData = resourceData.getData().getTexture().getImage();
                        String originId = assetUrl.substring(assetUrl.lastIndexOf("/") + 1);
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
                                resource.setOriginUrl(assetUrl);
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
     * Save face images from 推荐 label to brk_face and brk_face_layer tables
     *
     * @param recommendLabel The 推荐 label containing face assets
     */
    private void saveFaceImages(LibraryInfo.TabLabel recommendLabel)
    {
        try
        {
            int insertCount = 0;
            int skipCount = 0;

            if (recommendLabel.getAssets() == null || recommendLabel.getAssets().isEmpty())
            {
                log.warn("No assets found in '推荐' label");
                return;
            }

            for (String assetUrl : recommendLabel.getAssets())
            {
                log.info("Processing face asset: {}", assetUrl);
                FaceData faceData = downloadAndParseFaceJson(assetUrl);

                if (faceData == null)
                {
                    log.warn("Failed to download or parse face JSON: {}", assetUrl);
                    continue;
                }

                String originId = assetUrl.substring(assetUrl.lastIndexOf("/") + 1);

                BrkFace existingFace = brkFaceMapper.selectBrkFaceByOriginId(originId);

                if (existingFace != null)
                {
                    skipCount++;
                    log.info("Skipping existing face: {}", originId);
                    continue;
                }

                if (faceData.getData() == null)
                {
                    log.warn("No data found in face JSON: {}", assetUrl);
                    continue;
                }

                Map<String, Object> dataMap = faceData.getData();

                BrkFace brkFace = new BrkFace();
                brkFace.setFaceName(originId.replace(".json", ""));
                brkFace.setOriginId(originId);
                brkFace.setOriginUrl(assetUrl);
                brkFace.setCreateTime(DateUtils.getNowDate());

                brkFaceMapper.insertBrkFace(brkFace);

                Long faceId = brkFace.getFaceId();

                List<BrkFaceLayer> layerList = new ArrayList<>();

                java.util.HashMap<String, String> layerTypeMap = new java.util.HashMap<>();
                layerTypeMap.put("眼睛", "eye");
                layerTypeMap.put("眉毛", "eyebrow");
                layerTypeMap.put("嘴巴", "mouth");
                layerTypeMap.put("眼镜", "glasses");
                layerTypeMap.put("贴纸", "sticker");

                String[] layerTypes = {"嘴巴", "眼睛", "眉毛", "眼镜", "贴纸"};
                for (String layerType : layerTypes)
                {
                    if (dataMap.containsKey(layerType))
                    {
                        Object layerObj = dataMap.get(layerType);
                        String imageData = null;

                        if (layerObj instanceof List)
                        {
                            List<?> layerListData = (List<?>) layerObj;
                            if (!layerListData.isEmpty())
                            {
                                Object firstItem = layerListData.get(0);
                                if (firstItem instanceof Map)
                                {
                                    Map<?, ?> firstMap = (Map<?, ?>) firstItem;
                                    imageData = (String) firstMap.get("image");
                                }
                            }
                        }
                        else if (layerObj instanceof Map)
                        {
                            Map<?, ?> layerMap = (Map<?, ?>) layerObj;
                            imageData = (String) layerMap.get("image");
                        }

                        if (imageData != null && !imageData.isEmpty())
                        {
                            BrkFaceLayer layer = new BrkFaceLayer();
                            layer.setFaceId(faceId);
                            layer.setLayerType(layerTypeMap.get(layerType));
                            layer.setData(imageData);
                            layer.setX(null);
                            layer.setY(null);
                            layer.setWidth(null);
                            layer.setHeight(null);
                            layer.setCreateTime(DateUtils.getNowDate());
                            layerList.add(layer);
                        }
                    }
                }

                if (!layerList.isEmpty())
                {
                    brkFaceMapper.batchBrkFaceLayer(layerList);
                }

                insertCount++;
                log.info("Inserted face: {} with {} layers", originId, layerList.size());
            }

            log.info("Face processing completed - Insert: {}, Skip: {}", insertCount, skipCount);
        }
        catch (Exception e)
        {
            log.error("Failed to save face images", e);
        }
    }

    /**
     * Download and parse resource JSON file
     *
     * @param url URL to download
     * @return ResourceData object or null if failed
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
            connection.setReadTimeout(30000);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK)
            {
                inputStream = connection.getInputStream();
                String response = readInputStream(inputStream);

                if (response == null || response.trim().isEmpty())
                {
                    log.warn("Empty response from URL: {}", url);
                    return null;
                }

                String trimmed = response.trim();
                if (!trimmed.startsWith("{") && !trimmed.startsWith("["))
                {
                    log.warn("Response is not valid JSON from URL: {}", url);
                    return null;
                }

                return JSON.parseObject(response, ResourceData.class);
            }
            else
            {
                log.warn("HTTP error {} from URL: {}", responseCode, url);
                return null;
            }
        }
        catch (Exception e)
        {
            log.error("Failed to download and parse resource JSON from URL: {}", url, e);
            return null;
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
     * Download and parse face JSON file
     *
     * @param url URL to download
     * @return FaceData object or null if failed
     */
    private FaceData downloadAndParseFaceJson(String url)
    {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try
        {
            URL httpUrl = new URL(url);
            connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(30000);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK)
            {
                inputStream = connection.getInputStream();
                String response = readInputStream(inputStream);

                if (response == null || response.trim().isEmpty())
                {
                    log.warn("Empty response from URL: {}", url);
                    return null;
                }

                String trimmed = response.trim();
                if (!trimmed.startsWith("{") && !trimmed.startsWith("["))
                {
                    log.warn("Response is not valid JSON from URL: {}", url);
                    return null;
                }

                return JSON.parseObject(response, FaceData.class);
            }
            else
            {
                log.warn("HTTP error {} from URL: {}", responseCode, url);
                return null;
            }
        }
        catch (Exception e)
        {
            log.error("Failed to download and parse face JSON from URL: {}", url, e);
            return null;
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
