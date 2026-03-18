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
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import java.nio.charset.StandardCharsets;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.bricks.mapper.BrkResourceMapper;
import com.ruoyi.bricks.mapper.BrkFaceMapper;
import com.ruoyi.bricks.mapper.BrkBricksMapper;
import com.ruoyi.bricks.mapper.BrkSetMapper;
import com.ruoyi.bricks.domain.BrkResource;
import com.ruoyi.bricks.domain.BrkFace;
import com.ruoyi.bricks.domain.BrkFaceLayer;
import com.ruoyi.bricks.domain.BrkBricks;
import com.ruoyi.bricks.domain.BrkBricksBrick;
import com.ruoyi.bricks.domain.BrkBricksConnpoint;
import com.ruoyi.bricks.domain.BrkBricksMesh;
import com.ruoyi.bricks.domain.BrkSet;
import com.ruoyi.bricks.domain.BrkSetCategory;
import com.ruoyi.bricks.domain.BrkSetMesh;
import com.ruoyi.bricks.domain.LibraryInfo;
import com.ruoyi.bricks.domain.ResourceData;
import com.ruoyi.bricks.domain.FaceData;
import com.ruoyi.bricks.domain.HairData;
import com.ruoyi.bricks.domain.SetData;
import com.alibaba.fastjson2.JSONArray;
import com.ruoyi.bricks.service.IBrkResourceService;
import com.ruoyi.bricks.service.IBrkFaceService;
import com.ruoyi.bricks.service.IBrkBricksService;
import com.ruoyi.bricks.service.IBrkSetService;
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

    @Autowired
    private BrkBricksMapper brkBricksMapper;

    @Autowired
    private BrkSetMapper brkSetMapper;

    @Autowired
    private IBrkBricksService brkBricksService;

    @Autowired
    private IBrkSetService brkSetService;

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
//                saveResourceImages(libraryInfo);
            }

            if (libraryInfo.getConfig() != null && libraryInfo.getConfig().getAssetInfo() != null)
            {
//                saveBricksModels(libraryInfo);
                saveSetModels(libraryInfo);
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

    /**
     * Save bricks models from config.asset_info to brk_bricks tables
     *
     * @param libraryInfo Library configuration object
     */
    private void saveBricksModels(LibraryInfo libraryInfo)
    {
        try
        {
            int insertCount = 0;
            int skipCount = 0;

            java.util.Set<String> assetTypesToProcess = java.util.Set.of(
                "发型", "发饰", "底座", "手持件", "下装", "鞋子", "印字配件", "场景配件", "上装", "上装-自己设计", "贴图配件"
            );

            Map<String, LibraryInfo.AssetInfo> assetInfoMap = libraryInfo.getConfig().getAssetInfo();
            if (assetInfoMap == null || assetInfoMap.isEmpty())
            {
                log.warn("No asset_info found in library JSON");
                return;
            }

            for (Map.Entry<String, LibraryInfo.AssetInfo> entry : assetInfoMap.entrySet())
            {
                String assetUrl = entry.getKey();
                LibraryInfo.AssetInfo assetInfo = entry.getValue();

                if (assetInfo == null)
                {
                    continue;
                }

                String assetType = assetInfo.getType();
                if (assetType == null || !assetTypesToProcess.contains(assetType))
                {
                    continue;
                }

                String originId = assetUrl.substring(assetUrl.lastIndexOf("/") + 1);

                BrkBricks existingBricks = brkBricksMapper.selectBrkBricksByOriginId(originId);
                if (existingBricks != null)
                {
                    skipCount++;
                    log.info("Skipping existing bricks model: {}", originId);
                    continue;
                }

                log.info("Processing bricks model: {}", assetUrl);
                HairData hairData = downloadAndParseHairJson(assetUrl);

                if (hairData == null || hairData.getData() == null || hairData.getData().getModel() == null)
                {
                    log.warn("Failed to download or parse hair JSON: {}", assetUrl);
                    continue;
                }

                HairData.Model model = hairData.getData().getModel();
                HairData.ModelData modelData = model.getData();
                HairData.Config config = modelData != null ? modelData.getConfig() : null;

                BrkBricks brkBricks = new BrkBricks();
                brkBricks.setBricksName(originId.replace(".json", ""));
                brkBricks.setUuid(model.getUuid());
                brkBricks.setAssetType(hairData.getType());
                brkBricks.setCategory(getCategoryFromAssetType(assetType));
                if (config != null)
                {
                    brkBricks.setDiyGroup(config.getDiyGroup());
                    brkBricks.setRootGroup(config.getRoot());
                }
                brkBricks.setDefaultColor(hairData.getData().getDefaultColor());
                brkBricks.setOriginId(originId);
                brkBricks.setOriginUrl(assetUrl);
                brkBricks.setCreateTime(DateUtils.getNowDate());

                List<BrkBricksBrick> brickList = new ArrayList<>();
                if (modelData != null && modelData.getInstance() != null)
                {
                    Map<String, JSONArray> brickMap = modelData.getInstance().getBrick();
                    if (brickMap != null)
                    {
                        for (Map.Entry<String, JSONArray> brickEntry : brickMap.entrySet())
                        {
                            JSONArray brickData = brickEntry.getValue();
                            if (brickData != null && brickData.size() >= 3)
                            {
                                BrkBricksBrick brick = new BrkBricksBrick();
                                brick.setBrickIndex(Integer.parseInt(brickEntry.getKey()));
                                brick.setPartNumber(String.valueOf(brickData.get(0)));
                                brick.setColorId(String.valueOf(brickData.get(1)));

                                Object transformObj = brickData.get(2);
                                if (transformObj instanceof JSONArray)
                                {
                                    JSONArray transform = (JSONArray) transformObj;
                                    if (transform.size() >= 12)
                                    {
                                        brick.setX(parseBigDecimal(transform.get(0)));
                                        brick.setY(parseBigDecimal(transform.get(1)));
                                        brick.setZ(parseBigDecimal(transform.get(2)));
                                        brick.setM11(parseBigDecimal(transform.get(3)));
                                        brick.setM12(parseBigDecimal(transform.get(4)));
                                        brick.setM13(parseBigDecimal(transform.get(5)));
                                        brick.setM21(parseBigDecimal(transform.get(6)));
                                        brick.setM22(parseBigDecimal(transform.get(7)));
                                        brick.setM23(parseBigDecimal(transform.get(8)));
                                        brick.setM31(parseBigDecimal(transform.get(9)));
                                        brick.setM32(parseBigDecimal(transform.get(10)));
                                        brick.setM33(parseBigDecimal(transform.get(11)));
                                    }
                                }
                                brickList.add(brick);
                            }
                        }
                    }
                }

                List<BrkBricksConnpoint> connpointList = new ArrayList<>();
                if (modelData != null && modelData.getInstance() != null)
                {
                    Map<String, JSONArray> connpointMap = modelData.getInstance().getConnpoint();
                    if (connpointMap != null)
                    {
                        for (Map.Entry<String, JSONArray> connEntry : connpointMap.entrySet())
                        {
                            JSONArray connData = connEntry.getValue();
                            if (connData != null && connData.size() >= 5)
                            {
                                BrkBricksConnpoint connpoint = new BrkBricksConnpoint();
                                connpoint.setConnIndex(Integer.parseInt(connEntry.getKey()));
                                connpoint.setConnType(String.valueOf(connData.get(0)));
                                connpoint.setStudType(String.valueOf(connData.get(1)));

                                Object posObj = connData.get(2);
                                Object normalObj = connData.get(3);
                                
                                if (posObj instanceof JSONArray)
                                {
                                    JSONArray pos = (JSONArray) posObj;
                                    if (pos.size() >= 3)
                                    {
                                        connpoint.setX(parseBigDecimal(pos.get(0)));
                                        connpoint.setY(parseBigDecimal(pos.get(1)));
                                        connpoint.setZ(parseBigDecimal(pos.get(2)));
                                    }
                                }
                                if (normalObj instanceof JSONArray)
                                {
                                    JSONArray normal = (JSONArray) normalObj;
                                    if (normal.size() >= 3)
                                    {
                                        connpoint.setNx(parseBigDecimal(normal.get(0)));
                                        connpoint.setNy(parseBigDecimal(normal.get(1)));
                                        connpoint.setNz(parseBigDecimal(normal.get(2)));
                                    }
                                }
                                connpointList.add(connpoint);
                            }
                        }
                    }
                }

                brkBricks.setBricks(brickList);
                brkBricks.setConnpoints(connpointList);

                List<BrkBricksMesh> meshList = new ArrayList<>();
                if (modelData != null && modelData.getInstance() != null)
                {
                    Map<String, Object> meshMap = modelData.getInstance().getMesh();
                    if (meshMap != null && !meshMap.isEmpty())
                    {
                        meshList = parseHairMeshes(meshMap, originId);
                    }
                }

                brkBricksService.insertBrkBricks(brkBricks);

                if (!meshList.isEmpty())
                {
                    for (BrkBricksMesh mesh : meshList)
                    {
                        mesh.setBricksId(brkBricks.getBricksId());
                    }
                    brkBricksMapper.batchBrkBricksMesh(meshList);
                }

                insertCount++;
                log.info("Inserted bricks model: {} with {} bricks, {} connpoints, {} meshes", originId, brickList.size(), connpointList.size(), meshList.size());
            }

            log.info("Bricks processing completed - Insert: {}, Skip: {}", insertCount, skipCount);
        }
        catch (Exception e)
        {
            log.error("Failed to save bricks models", e);
        }
    }

    /**
     * Map asset type to category string
     */
    private String getCategoryFromAssetType(String assetType)
    {
        if (assetType == null)
        {
            return "other";
        }
        switch (assetType)
        {
            case "发型": return "hair";
            case "发饰": return "hairAccessory";
            case "底座": return "base";
            case "手持件": return "handheld";
            case "下装": return "bottoms";
            case "鞋子": return "shoes";
            case "印字配件": return "printAccessory";
            case "场景配件": return "sceneAccessory";
            case "上装": return "tops";
            case "上装-自己设计": return "topsCustom";
            case "贴图配件": return "textureAccessory";
            default: return "other";
        }
    }

    /**
     * Map category name to asset type for set categories
     */
    private String getAssetTypeFromCategoryName(String categoryName)
    {
        if (categoryName == null)
        {
            return "套装";
        }
        switch (categoryName)
        {
            case "tops": return "上装";
            case "bottoms": return "下装";
            case "shoes": return "鞋子";
            case "hair": return "发型";
            case "hairAccessory": return "发饰";
            case "base": return "底座";
            case "handheld": return "手持件";
            case "printAccessory": return "印字配件";
            case "sceneAccessory": return "场景配件";
            case "textureAccessory": return "贴图配件";
            default: return "套装";
        }
    }

    /**
     * Save set models from config.asset_info to brk_set tables
     *
     * @param libraryInfo Library configuration object
     */
    private void saveSetModels(LibraryInfo libraryInfo)
    {
        try
        {
            int insertCount = 0;
            int skipCount = 0;

            Map<String, LibraryInfo.AssetInfo> assetInfoMap = libraryInfo.getConfig().getAssetInfo();
            if (assetInfoMap == null || assetInfoMap.isEmpty())
            {
                log.warn("No asset_info found in library JSON");
                return;
            }

            for (Map.Entry<String, LibraryInfo.AssetInfo> entry : assetInfoMap.entrySet())
            {
                String assetUrl = entry.getKey();
                LibraryInfo.AssetInfo assetInfo = entry.getValue();

                if (assetInfo == null)
                {
                    continue;
                }

                String assetType = assetInfo.getType();
                if (assetType == null || !assetType.equals("套装"))
                {
                    continue;
                }

                String originId = assetUrl.substring(assetUrl.lastIndexOf("/") + 1);

                BrkSet existingSet = brkSetMapper.selectBrkSetByOriginId(originId);
                if (existingSet != null)
                {
                    skipCount++;
                    log.info("Skipping existing set model: {}", originId);
                    continue;
                }

                log.info("Processing set model: {}", assetUrl);
                SetData setData = downloadAndParseSetJson(assetUrl);

                if (setData == null || setData.getData() == null)
                {
                    log.warn("Failed to download or parse set JSON: {}", assetUrl);
                    continue;
                }

                BrkSet brkSet = new BrkSet();
                brkSet.setSetName(originId.replace(".json", ""));
                brkSet.setAssetType(setData.getType());
                brkSet.setOriginId(originId);
                brkSet.setOriginUrl(assetUrl);
                brkSet.setCreateTime(DateUtils.getNowDate());

                List<BrkSetCategory> categoryList = new ArrayList<>();

                Map<String, Object> dataMap = setData.getData();
                if (dataMap != null)
                {
                    for (Map.Entry<String, Object> dataEntry : dataMap.entrySet())
                    {
                        String categoryName = dataEntry.getKey();
                        if ("default_color".equals(categoryName))
                        {
                            continue;
                        }

                        Object categoryObj = dataEntry.getValue();
                        if (!(categoryObj instanceof Map))
                        {
                            continue;
                        }

                        @SuppressWarnings("unchecked")
                        Map<String, Object> categoryMap = (Map<String, Object>) categoryObj;

                        BrkSetCategory category = new BrkSetCategory();
                        category.setCategoryName(categoryName);
                        category.setUuid((String) categoryMap.get("uuid"));
                        category.setVersion("v0");

                        Object dataObjInner = categoryMap.get("data");
                        if (dataObjInner instanceof Map)
                        {
                            @SuppressWarnings("unchecked")
                            Map<String, Object> innerDataMap = (Map<String, Object>) dataObjInner;
                            String version = (String) innerDataMap.get("version");
                            if (version != null)
                            {
                                category.setVersion(version);
                            }

                            Object instanceObj = innerDataMap.get("instance");
                            if (instanceObj instanceof Map)
                            {
                                @SuppressWarnings("unchecked")
                                Map<String, Object> instanceMap = (Map<String, Object>) instanceObj;
                                Object meshObj = instanceMap.get("mesh");
                                if (meshObj instanceof Map)
                                {
                                    @SuppressWarnings("unchecked")
                                    Map<String, Object> meshMap = (Map<String, Object>) meshObj;
                                    List<BrkSetMesh> meshList = parseSetMeshes(meshMap);
                                    category.setMeshes(meshList);
                                }

                                Object brickObj = instanceMap.get("brick");
                                if (brickObj instanceof Map)
                                {
                                    @SuppressWarnings("unchecked")
                                    Map<String, Object> brickMap = (Map<String, Object>) brickObj;
                                    category.setBrickData(brickMap);
                                }
                            }
                        }

                        categoryList.add(category);
                    }
                }

                brkSet.setCategories(categoryList);
                brkSetService.insertBrkSet(brkSet);

                if (categoryList != null && !categoryList.isEmpty())
                {
                    brkSet = brkSetMapper.selectBrkSetByOriginId(originId);
                    if (brkSet != null)
                    {
                        List<BrkSetCategory> savedCategories = brkSetMapper.selectBrkSetCategoryList(brkSet.getSetId());
                        for (int i = 0; i < savedCategories.size() && i < categoryList.size(); i++)
                        {
                            BrkSetCategory savedCategory = savedCategories.get(i);
                            BrkSetCategory originalCategory = categoryList.get(i);
                            if (originalCategory.getMeshes() != null && !originalCategory.getMeshes().isEmpty())
                            {
                                for (BrkSetMesh mesh : originalCategory.getMeshes())
                                {
                                    mesh.setCategoryId(savedCategory.getCategoryId());
                                    mesh.setCreateTime(brkSet.getCreateTime());
                                }
                                brkSetMapper.batchBrkSetMesh(originalCategory.getMeshes());
                            }
                        }
                    }
                }

                insertCount++;
                log.info("Inserted set model: {} with {} categories", originId, categoryList.size());

                for (BrkSetCategory category : categoryList)
                {
                    if ((category.getBrickData() != null && !category.getBrickData().isEmpty()) ||
                        (category.getMeshes() != null && !category.getMeshes().isEmpty()))
                    {
                        saveSetCategoryAsBricks(originId, category, assetUrl);
                    }
                    category.setBrickData(null);
                }
            }

            log.info("Set processing completed - Insert: {}, Skip: {}", insertCount, skipCount);
        }
        catch (Exception e)
        {
            log.error("Failed to save set models", e);
        }
    }

    /**
     * Save set category as brk_bricks entry
     */
    private void saveSetCategoryAsBricks(String setOriginId, BrkSetCategory category, String assetUrl)
    {
        try
        {
            String bricksName = setOriginId.replace(".json", "") + "_" + category.getCategoryName();
            String categoryUuid = category.getUuid();

            BrkBricks existingBricks = null;
            if (categoryUuid != null && !categoryUuid.isEmpty())
            {
                existingBricks = brkBricksMapper.selectBrkBricksByUuid(categoryUuid);
            }
            if (existingBricks != null)
            {
                log.info("Skipping existing bricks for category uuid: {}", categoryUuid);
                return;
            }

            BrkBricks brkBricks = new BrkBricks();
            brkBricks.setBricksName(bricksName);
            brkBricks.setUuid(categoryUuid);
            brkBricks.setAssetType(getAssetTypeFromCategoryName(category.getCategoryName()));
            brkBricks.setCategory(category.getCategoryName());
            brkBricks.setOriginId(bricksName);
            brkBricks.setOriginUrl(assetUrl);
            brkBricks.setCreateTime(DateUtils.getNowDate());

            List<BrkBricksBrick> brickList = new ArrayList<>();

            if (category.getBrickData() != null && !category.getBrickData().isEmpty())
            {
                Map<String, Object> brickMap = category.getBrickData();
                for (Map.Entry<String, Object> entry : brickMap.entrySet())
                {
                    Object brickValue = entry.getValue();
                    if (!(brickValue instanceof List))
                    {
                        continue;
                    }

                    List<?> brickDataList = (List<?>) brickValue;
                    if (brickDataList.size() < 3)
                    {
                        continue;
                    }

                    BrkBricksBrick brick = new BrkBricksBrick();
                    brick.setBrickIndex(Integer.parseInt(entry.getKey()));
                    brick.setPartNumber(String.valueOf(brickDataList.get(0)));
                    brick.setColorId(String.valueOf(brickDataList.get(1)));

                    Object transformObj = brickDataList.get(2);
                    if (transformObj instanceof List)
                    {
                        List<?> transform = (List<?>) transformObj;
                        if (transform.size() >= 12)
                        {
                            brick.setX(parseBigDecimal(transform.get(0)));
                            brick.setY(parseBigDecimal(transform.get(1)));
                            brick.setZ(parseBigDecimal(transform.get(2)));
                            brick.setM11(parseBigDecimal(transform.get(3)));
                            brick.setM12(parseBigDecimal(transform.get(4)));
                            brick.setM13(parseBigDecimal(transform.get(5)));
                            brick.setM21(parseBigDecimal(transform.get(6)));
                            brick.setM22(parseBigDecimal(transform.get(7)));
                            brick.setM23(parseBigDecimal(transform.get(8)));
                            brick.setM31(parseBigDecimal(transform.get(9)));
                            brick.setM32(parseBigDecimal(transform.get(10)));
                            brick.setM33(parseBigDecimal(transform.get(11)));
                        }
                    }
                    brickList.add(brick);
                }
            }
            else if (category.getMeshes() != null)
            {
                for (BrkSetMesh mesh : category.getMeshes())
                {
                    BrkBricksBrick brick = new BrkBricksBrick();
                    brick.setBrickIndex(mesh.getMeshIndex());
                    brick.setPartNumber(mesh.getPartNumber());
                    brick.setColorId("0");
                    brick.setX(java.math.BigDecimal.ZERO);
                    brick.setY(java.math.BigDecimal.ZERO);
                    brick.setZ(java.math.BigDecimal.ZERO);
                    brick.setM11(java.math.BigDecimal.ONE);
                    brick.setM12(java.math.BigDecimal.ZERO);
                    brick.setM13(java.math.BigDecimal.ZERO);
                    brick.setM21(java.math.BigDecimal.ZERO);
                    brick.setM22(java.math.BigDecimal.ONE);
                    brick.setM23(java.math.BigDecimal.ZERO);
                    brick.setM31(java.math.BigDecimal.ZERO);
                    brick.setM32(java.math.BigDecimal.ZERO);
                    brick.setM33(java.math.BigDecimal.ONE);
                    brickList.add(brick);
                }
            }

            brkBricks.setBricks(brickList);
            brkBricksService.insertBrkBricks(brkBricks);

            log.info("Inserted bricks for category: {} with {} bricks", bricksName, brickList.size());
        }
        catch (Exception e)
        {
            log.error("Failed to save set category as bricks: {}", category.getCategoryName(), e);
        }
    }

    /**
     * Parse set meshes from mesh map
     */
    @SuppressWarnings("unchecked")
    private List<BrkSetMesh> parseSetMeshes(Map<String, Object> meshMap)
    {
        List<BrkSetMesh> meshList = new ArrayList<>();

        if (meshMap == null || meshMap.isEmpty())
        {
            return meshList;
        }

        for (Map.Entry<String, Object> entry : meshMap.entrySet())
        {
            try
            {
                String meshIndex = entry.getKey();
                Object meshValue = entry.getValue();

                if (!(meshValue instanceof List))
                {
                    continue;
                }

                List<?> meshDataList = (List<?>) meshValue;
                if (meshDataList.size() < 2)
                {
                    continue;
                }

                Object firstItem = meshDataList.get(0);
                Object secondItem = meshDataList.get(1);

                String refId = firstItem instanceof String ? (String) firstItem : null;

                Map<String, Object> meshInfo = null;
                if (secondItem instanceof Map)
                {
                    meshInfo = (Map<String, Object>) secondItem;
                }

                if (refId == null || meshInfo == null)
                {
                    continue;
                }

                BrkSetMesh mesh = new BrkSetMesh();
                mesh.setMeshIndex(Integer.parseInt(meshIndex));
                mesh.setRefId(refId);

                String refForHuman = (String) meshInfo.get("ref_for_human");
                if (refForHuman != null)
                {
                    mesh.setPartNumber(refForHuman);
                }

                String base = (String) meshInfo.get("base");
                if (base != null)
                {
                    mesh.setBase(base);
                }

                String type = (String) meshInfo.get("type");
                if (type != null)
                {
                    mesh.setMeshType(type);
                }
                else
                {
                    mesh.setMeshType("texface");
                }

                Object texturesObj = meshInfo.get("textures");
                if (texturesObj instanceof Map)
                {
                    Map<String, Object> texturesMap = (Map<String, Object>) texturesObj;
                    mesh.setTextureLeft((String) texturesMap.get("left"));
                    mesh.setTextureRight((String) texturesMap.get("right"));
                    mesh.setTextureTop((String) texturesMap.get("top"));
                    mesh.setTextureBack((String) texturesMap.get("back"));
                    mesh.setTextureBottom((String) texturesMap.get("bottom"));
                }

                meshList.add(mesh);
            }
            catch (Exception e)
            {
                log.warn("Failed to parse mesh: {}", entry.getKey(), e);
            }
        }

        return meshList;
    }

    /**
     * Parse hair meshes from mesh map
     */
    @SuppressWarnings("unchecked")
    private List<BrkBricksMesh> parseHairMeshes(Map<String, Object> meshMap, String originId)
    {
        List<BrkBricksMesh> meshList = new ArrayList<>();

        if (meshMap == null || meshMap.isEmpty())
        {
            return meshList;
        }

        for (Map.Entry<String, Object> entry : meshMap.entrySet())
        {
            try
            {
                String meshIndex = entry.getKey();
                Object meshValue = entry.getValue();

                if (!(meshValue instanceof List))
                {
                    continue;
                }

                List<?> meshDataList = (List<?>) meshValue;
                if (meshDataList.size() < 2)
                {
                    continue;
                }

                Object firstItem = meshDataList.get(0);
                Object secondItem = meshDataList.get(1);

                String refId = firstItem instanceof String ? (String) firstItem : null;

                Map<String, Object> meshInfo = null;
                if (secondItem instanceof Map)
                {
                    meshInfo = (Map<String, Object>) secondItem;
                }

                if (refId == null || meshInfo == null)
                {
                    continue;
                }

                BrkBricksMesh mesh = new BrkBricksMesh();
                mesh.setMeshIndex(Integer.parseInt(meshIndex));
                mesh.setRefId(refId);

                String refForHuman = (String) meshInfo.get("ref_for_human");
                if (refForHuman != null)
                {
                    mesh.setPartNumber(refForHuman);
                }

                String base = (String) meshInfo.get("base");
                if (base != null)
                {
                    mesh.setBase(base);
                }

                String type = (String) meshInfo.get("type");
                if (type != null)
                {
                    mesh.setMeshType(type);
                }
                else
                {
                    mesh.setMeshType("texface");
                }

                Object texturesObj = meshInfo.get("textures");
                if (texturesObj instanceof Map)
                {
                    Map<String, Object> texturesMap = (Map<String, Object>) texturesObj;
                    mesh.setTextureLeft((String) texturesMap.get("left"));
                    mesh.setTextureRight((String) texturesMap.get("right"));
                    mesh.setTextureTop((String) texturesMap.get("top"));
                    mesh.setTextureBack((String) texturesMap.get("back"));
                    mesh.setTextureBottom((String) texturesMap.get("bottom"));
                }

                meshList.add(mesh);
            }
            catch (Exception e)
            {
                log.warn("Failed to parse hair mesh: {}", entry.getKey(), e);
            }
        }

        return meshList;
    }

    private java.math.BigDecimal parseBigDecimal(Object obj)
    {
        if (obj == null)
        {
            return java.math.BigDecimal.ZERO;
        }
        if (obj instanceof Number)
        {
            return new java.math.BigDecimal(((Number) obj).doubleValue()).setScale(6, java.math.RoundingMode.HALF_UP);
        }
        try
        {
            return new java.math.BigDecimal(String.valueOf(obj)).setScale(6, java.math.RoundingMode.HALF_UP);
        }
        catch (Exception e)
        {
            return java.math.BigDecimal.ZERO;
        }
    }

    /**
     * Download and parse hair JSON file
     *
     * @param url URL to download
     * @return HairData object or null if failed
     */
    private HairData downloadAndParseHairJson(String url)
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
            connection.setRequestProperty("Accept-Encoding", "gzip, deflate");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK)
            {
                inputStream = connection.getInputStream();
                
                String contentEncoding = connection.getContentEncoding();
                if ("gzip".equalsIgnoreCase(contentEncoding))
                {
                    inputStream = new GZIPInputStream(inputStream);
                }
                else if ("deflate".equalsIgnoreCase(contentEncoding))
                {
                    inputStream = new InflaterInputStream(inputStream);
                }

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

                return JSON.parseObject(response, HairData.class);
            }
            else
            {
                log.warn("Failed to download hair JSON, response code: {} from URL: {}", responseCode, url);
                return null;
            }
        }
        catch (Exception e)
        {
            log.error("Failed to download hair JSON from URL: {}", url, e);
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
     * Download and parse set JSON file
     *
     * @param url URL to download
     * @return SetData object or null if failed
     */
    private SetData downloadAndParseSetJson(String url)
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
            connection.setRequestProperty("Accept-Encoding", "gzip, deflate");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK)
            {
                inputStream = connection.getInputStream();
                
                String contentEncoding = connection.getContentEncoding();
                if ("gzip".equalsIgnoreCase(contentEncoding))
                {
                    inputStream = new GZIPInputStream(inputStream);
                }
                else if ("deflate".equalsIgnoreCase(contentEncoding))
                {
                    inputStream = new InflaterInputStream(inputStream);
                }

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

                return JSON.parseObject(response, SetData.class);
            }
            else
            {
                log.warn("Failed to download set JSON, response code: {} from URL: {}", responseCode, url);
                return null;
            }
        }
        catch (Exception e)
        {
            log.error("Failed to download set JSON from URL: {}", url, e);
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
}
