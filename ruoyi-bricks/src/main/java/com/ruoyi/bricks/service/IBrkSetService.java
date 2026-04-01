package com.ruoyi.bricks.service;

import java.util.List;
import com.ruoyi.bricks.domain.BrkSet;
import com.ruoyi.bricks.domain.BrkSetCategory;
import com.ruoyi.bricks.domain.BrkSetMesh;

/**
 * 套装Service接口
 * 
 * @author ruoyi
 * @date 2026-03-16
 */
public interface IBrkSetService 
{
    public BrkSet selectBrkSetBySetId(Long setId);

    public BrkSet selectBrkSetByUuid(String uuid);

    public BrkSet selectBrkSetByOriginId(String originId);

    public List<BrkSet> selectBrkSetList(BrkSet brkSet);

    public List<BrkSet> selectBrkSetListWithCategories(BrkSet brkSet);

    public List<BrkSet> selectBrkSetListWithSimpleCategories(BrkSet brkSet, boolean includeLdr);

    public int insertBrkSet(BrkSet brkSet);

    public int updateBrkSet(BrkSet brkSet);

    public int deleteBrkSetBySetId(Long setId);

    public int deleteBrkSetBySetIds(Long[] setIds);

    public List<BrkSetCategory> selectBrkSetCategoryList(Long setId);

    public List<BrkSetMesh> selectBrkSetMeshList(Long categoryId);

    public List<String> generateSetLdrContent(Long setId, boolean replaceDefaultColor, boolean useOriginalParts, boolean removeAbnormalParts);

    public List<String> generateSetLdrContent(Long setId, boolean replaceDefaultColor, boolean useOriginalParts, boolean removeAbnormalParts, boolean includeHandheld);

    public List<String> generateSetLdrContent(Long setId, boolean replaceDefaultColor, boolean useOriginalParts, boolean removeAbnormalParts, boolean includeHandheld, boolean replaceCustomGroup);

    public int updatePreviewImg(Long setId, String previewImg);
}
