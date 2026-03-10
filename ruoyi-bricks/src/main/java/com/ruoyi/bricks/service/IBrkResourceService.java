package com.ruoyi.bricks.service;

import java.util.List;
import com.ruoyi.bricks.domain.BrkResource;

/**
 * 资源图片Service接口
 * 
 * @author ruoyi
 * @date 2026-03-10
 */
public interface IBrkResourceService 
{
    /**
     * 查询资源图片
     * 
     * @param resourceId 资源图片主键
     * @return 资源图片
     */
    public BrkResource selectBrkResourceByResourceId(Long resourceId);

    /**
     * 查询资源图片列表
     * 
     * @param brkResource 资源图片
     * @return 资源图片集合
     */
    public List<BrkResource> selectBrkResourceList(BrkResource brkResource);

    /**
     * 新增资源图片
     * 
     * @param brkResource 资源图片
     * @return 结果
     */
    public int insertBrkResource(BrkResource brkResource);

    /**
     * 修改资源图片
     * 
     * @param brkResource 资源图片
     * @return 结果
     */
    public int updateBrkResource(BrkResource brkResource);

    /**
     * 批量删除资源图片
     * 
     * @param resourceIds 需要删除的资源图片主键集合
     * @return 结果
     */
    public int deleteBrkResourceByResourceIds(Long[] resourceIds);

    /**
     * 删除资源图片信息
     * 
     * @param resourceId 资源图片主键
     * @return 结果
     */
    public int deleteBrkResourceByResourceId(Long resourceId);
}
