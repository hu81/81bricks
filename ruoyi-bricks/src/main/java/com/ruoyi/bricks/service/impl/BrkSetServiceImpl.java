package com.ruoyi.bricks.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bricks.domain.BrkSet;
import com.ruoyi.bricks.domain.BrkSetCategory;
import com.ruoyi.bricks.domain.BrkSetMesh;
import com.ruoyi.bricks.mapper.BrkSetMapper;
import com.ruoyi.bricks.service.IBrkSetService;

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
}
