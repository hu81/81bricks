package com.ruoyi.bricks.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bricks.mapper.BrkResourceMapper;
import com.ruoyi.bricks.domain.BrkResource;
import com.ruoyi.bricks.service.IBrkResourceService;

/**
 * 资源图片Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-03-10
 */
@Service
public class BrkResourceServiceImpl implements IBrkResourceService 
{
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
}
