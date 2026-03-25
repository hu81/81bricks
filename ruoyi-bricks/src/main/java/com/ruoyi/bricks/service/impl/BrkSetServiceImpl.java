package com.ruoyi.bricks.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bricks.domain.BrkSet;
import com.ruoyi.bricks.domain.BrkSetCategory;
import com.ruoyi.bricks.domain.BrkSetMesh;
import com.ruoyi.bricks.domain.BrkBricks;
import com.ruoyi.bricks.domain.BrkBricksConnpoint;
import com.ruoyi.bricks.mapper.BrkSetMapper;
import com.ruoyi.bricks.service.IBrkSetService;
import com.ruoyi.bricks.service.IBrkBricksService;

/**
 * 套装Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-03-16
 */
@Service
public class BrkSetServiceImpl implements IBrkSetService 
{
    @Autowired
    private BrkSetMapper brkSetMapper;

    @Autowired
    private IBrkBricksService brkBricksService;

    @Override
    public BrkSet selectBrkSetBySetId(Long setId)
    {
        BrkSet brkSet = brkSetMapper.selectBrkSetBySetId(setId);
        if (brkSet != null)
        {
            brkSet.setCategories(brkSetMapper.selectBrkSetCategoryList(setId));
        }
        return brkSet;
    }

    @Override
    public BrkSet selectBrkSetByUuid(String uuid)
    {
        BrkSet brkSet = brkSetMapper.selectBrkSetByUuid(uuid);
        if (brkSet != null)
        {
            brkSet.setCategories(brkSetMapper.selectBrkSetCategoryList(brkSet.getSetId()));
        }
        return brkSet;
    }

    @Override
    public BrkSet selectBrkSetByOriginId(String originId)
    {
        return brkSetMapper.selectBrkSetByOriginId(originId);
    }

    @Override
    public List<BrkSet> selectBrkSetList(BrkSet brkSet)
    {
        return brkSetMapper.selectBrkSetList(brkSet);
    }

    @Override
    public List<BrkSet> selectBrkSetListWithCategories(BrkSet brkSet)
    {
        List<BrkSet> list = brkSetMapper.selectBrkSetList(brkSet);
        if (list != null && !list.isEmpty())
        {
            for (BrkSet set : list)
            {
                if (set.getSetId() != null)
                {
                    set.setCategories(brkSetMapper.selectBrkSetCategoryList(set.getSetId()));
                }
            }
        }
        return list;
    }

    @Override
    public List<BrkSet> selectBrkSetListWithSimpleCategories(BrkSet brkSet, boolean includeLdr)
    {
        List<BrkSet> list = brkSetMapper.selectBrkSetList(brkSet);
        if (list != null && !list.isEmpty())
        {
            for (BrkSet set : list)
            {
                if (set.getSetId() != null)
                {
                    set.setCategories(brkSetMapper.selectBrkSetCategoryList(set.getSetId()));
                    if (set.getCategories() != null)
                    {
                        for (BrkSetCategory category : set.getCategories())
                        {
                            category.setMeshes(null);
                        }
                    }
                    if (includeLdr)
                    {
                        set.setLdrData(generateSetLdrContent(set.getSetId(), true, true, true, false));
                    }
                }
            }
        }
        return list;
    }

    @Override
    public int insertBrkSet(BrkSet brkSet)
    {
        brkSet.setCreateTime(DateUtils.getNowDate());
        brkSet.setUpdateTime(brkSet.getCreateTime());
        int rows = brkSetMapper.insertBrkSet(brkSet);
        
        if (brkSet.getCategories() != null && !brkSet.getCategories().isEmpty())
        {
            for (BrkSetCategory category : brkSet.getCategories())
            {
                category.setSetId(brkSet.getSetId());
                category.setCreateTime(brkSet.getCreateTime());
            }
            brkSetMapper.batchBrkSetCategory(brkSet.getCategories());
        }
        
        return rows;
    }

    @Override
    public int updateBrkSet(BrkSet brkSet)
    {
        brkSet.setUpdateTime(DateUtils.getNowDate());
        return brkSetMapper.updateBrkSet(brkSet);
    }

    @Override
    public int deleteBrkSetBySetId(Long setId)
    {
        brkSetMapper.deleteBrkSetMeshBySetId(setId);
        brkSetMapper.deleteBrkSetCategoryBySetId(setId);
        return brkSetMapper.deleteBrkSetBySetId(setId);
    }

    @Override
    public int deleteBrkSetBySetIds(Long[] setIds)
    {
        for (Long setId : setIds)
        {
            brkSetMapper.deleteBrkSetMeshBySetId(setId);
            brkSetMapper.deleteBrkSetCategoryBySetId(setId);
        }
        return brkSetMapper.deleteBrkSetBySetIds(setIds);
    }

    @Override
    public List<BrkSetCategory> selectBrkSetCategoryList(Long setId)
    {
        List<BrkSetCategory> categories = brkSetMapper.selectBrkSetCategoryList(setId);
        if (categories != null)
        {
            for (BrkSetCategory category : categories)
            {
                category.setMeshes(brkSetMapper.selectBrkSetMeshList(category.getCategoryId()));
            }
        }
        return categories;
    }

    @Override
    public List<BrkSetMesh> selectBrkSetMeshList(Long categoryId)
    {
        return brkSetMapper.selectBrkSetMeshList(categoryId);
    }

    @Override
    public List<String> generateSetLdrContent(Long setId, boolean replaceDefaultColor, boolean useOriginalParts, boolean removeAbnormalParts)
    {
        return generateSetLdrContent(setId, replaceDefaultColor, useOriginalParts, removeAbnormalParts, false);
    }

    @Override
    public List<String> generateSetLdrContent(Long setId, boolean replaceDefaultColor, boolean useOriginalParts, boolean removeAbnormalParts, boolean includeHandheld)
    {
        List<String> mergedLines = new ArrayList<>();

        List<BrkSetCategory> categories = brkSetMapper.selectBrkSetCategoryList(setId);
        if (categories == null || categories.isEmpty())
        {
            return mergedLines;
        }

        Map<String, List<BrkBricks>> categoryModels = new HashMap<>();
        for (BrkSetCategory category : categories)
        {
            BrkBricks bricks = brkBricksService.selectBrkBricksByUuid(category.getUuid());
            if (bricks != null)
            {
                String modelCategory = bricks.getCategory();
                if (shouldIncludeCategory(modelCategory, includeHandheld))
                {
                    categoryModels.computeIfAbsent(modelCategory, k -> new ArrayList<>()).add(bricks);
                }
            }
        }

        if (categoryModels.isEmpty())
        {
            return mergedLines;
        }

        mergedLines.add("0 Name: Set-" + setId);
        mergedLines.add("0 Comment: Merged from set " + setId + (includeHandheld ? " (Handheld)" : ""));

        Map<String, BrkBricks> processedModels = new HashMap<>();
        BrkBricks rootModel = findRootModel(categoryModels, includeHandheld);
        if (rootModel == null)
        {
            rootModel = categoryModels.values().iterator().next().get(0);
        }

        mergedLines.addAll(brkBricksService.generateLdrContent(rootModel.getBricksId(), replaceDefaultColor, useOriginalParts, removeAbnormalParts, true));
        processedModels.put(rootModel.getCategory(), rootModel);

        Map<String, double[]> cumulativeOffsets = new HashMap<>();
        cumulativeOffsets.put(rootModel.getCategory(), new double[]{0.0, 0.0, 0.0});

        List<String[]> connectionOrder = getConnectionOrder(includeHandheld);

        for (String[] connection : connectionOrder)
        {
            String fromCategory = connection[0];
            String toCategory = connection[1];

            if (!processedModels.containsKey(fromCategory) || !categoryModels.containsKey(toCategory) || processedModels.containsKey(toCategory))
            {
                continue;
            }

            BrkBricks parentModel = processedModels.get(fromCategory);
            List<BrkBricks> childModels = categoryModels.get(toCategory);

            if (childModels != null && !childModels.isEmpty())
            {
                for (BrkBricks childModel : childModels)
                {
                    if (processedModels.containsKey(childModel.getCategory()))
                    {
                        continue;
                    }

                    double[] parentOffset = cumulativeOffsets.get(fromCategory);
                    double parentOffsetX = parentOffset != null ? parentOffset[0] : 0.0;
                    double parentOffsetY = parentOffset != null ? parentOffset[1] : 0.0;
                    double parentOffsetZ = parentOffset != null ? parentOffset[2] : 0.0;

                    BrkBricksConnpoint tubeConn = findConnPointByType(parentModel, "tube");
                    BrkBricksConnpoint studConn = findConnPointByType(childModel, "stud");

                    double offsetX = 0, offsetY = 0, offsetZ = 0;
                    if (tubeConn != null && studConn != null)
                    {
                        offsetX = tubeConn.getX().doubleValue() - studConn.getX().doubleValue();
                        offsetY = tubeConn.getY().doubleValue() - studConn.getY().doubleValue();
                        offsetZ = tubeConn.getZ().doubleValue() - studConn.getZ().doubleValue();
                    }

                    double totalOffsetX = parentOffsetX + offsetX;
                    double totalOffsetY = parentOffsetY + offsetY;
                    double totalOffsetZ = parentOffsetZ + offsetZ;

                    mergedLines.addAll(brkBricksService.generateLdrContent(childModel.getBricksId(), replaceDefaultColor, useOriginalParts, removeAbnormalParts, totalOffsetX, totalOffsetY, totalOffsetZ, true));
                    processedModels.put(childModel.getCategory(), childModel);
                    cumulativeOffsets.put(childModel.getCategory(), new double[]{totalOffsetX, totalOffsetY, totalOffsetZ});
                }
            }
        }

        return mergedLines;
    }

    private boolean shouldIncludeCategory(String category, boolean includeHandheld)
    {
        if (includeHandheld)
        {
            return !"tops".equalsIgnoreCase(category);
        }
        else
        {
            return !"tops_handheld".equalsIgnoreCase(category) && !"handheld".equalsIgnoreCase(category);
        }
    }

    private List<String[]> getConnectionOrder(boolean includeHandheld)
    {
        List<String[]> connectionOrder = new ArrayList<>();
        if (includeHandheld)
        {
            connectionOrder.add(new String[]{"hair", "tops_handheld"});
            connectionOrder.add(new String[]{"hairAccessory", "tops_handheld"});
            connectionOrder.add(new String[]{"tops_handheld", "bottoms"});
            connectionOrder.add(new String[]{"tops_handheld", "legs"});
            connectionOrder.add(new String[]{"bottoms", "shoes"});
            connectionOrder.add(new String[]{"legs", "shoes"});
            connectionOrder.add(new String[]{"shoes", "base"});
            connectionOrder.add(new String[]{"handheld", "tops_handheld"});
        }
        else
        {
            connectionOrder.add(new String[]{"hair", "tops"});
            connectionOrder.add(new String[]{"hair", "topsCustom"});
            connectionOrder.add(new String[]{"hairAccessory", "tops"});
            connectionOrder.add(new String[]{"hairAccessory", "topsCustom"});
            connectionOrder.add(new String[]{"tops", "bottoms"});
            connectionOrder.add(new String[]{"topsCustom", "bottoms"});
            connectionOrder.add(new String[]{"tops", "legs"});
            connectionOrder.add(new String[]{"topsCustom", "legs"});
            connectionOrder.add(new String[]{"bottoms", "shoes"});
            connectionOrder.add(new String[]{"legs", "shoes"});
            connectionOrder.add(new String[]{"shoes", "base"});
        }
        return connectionOrder;
    }

    private BrkBricks findRootModel(Map<String, List<BrkBricks>> categoryModels, boolean includeHandheld)
    {
        if (includeHandheld)
        {
            if (categoryModels.containsKey("tops_handheld"))
            {
                return categoryModels.get("tops_handheld").get(0);
            }
        }
        else
        {
            if (categoryModels.containsKey("tops"))
            {
                return categoryModels.get("tops").get(0);
            }
            if (categoryModels.containsKey("topsCustom"))
            {
                return categoryModels.get("topsCustom").get(0);
            }
        }
        if (categoryModels.containsKey("hair"))
        {
            return categoryModels.get("hair").get(0);
        }
        if (categoryModels.containsKey("base"))
        {
            return categoryModels.get("base").get(0);
        }
        if (categoryModels.containsKey("bottoms"))
        {
            return categoryModels.get("bottoms").get(0);
        }
        if (categoryModels.containsKey("legs"))
        {
            return categoryModels.get("legs").get(0);
        }
        return null;
    }

    private BrkBricksConnpoint findConnPointByType(BrkBricks model, String type)
    {
        if (model.getConnpoints() == null)
        {
            return null;
        }
        for (BrkBricksConnpoint conn : model.getConnpoints())
        {
            if (type.equalsIgnoreCase(conn.getStudType()))
            {
                return conn;
            }
        }
        return null;
    }

    @Override
    public int updatePreviewImg(Long setId, String previewImg)
    {
        BrkSet brkSet = new BrkSet();
        brkSet.setSetId(setId);
        brkSet.setPreviewImg(previewImg);
        brkSet.setUpdateTime(DateUtils.getNowDate());
        return brkSetMapper.updateBrkSet(brkSet);
    }
}
