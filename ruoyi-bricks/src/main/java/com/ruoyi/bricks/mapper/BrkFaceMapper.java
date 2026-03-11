package com.ruoyi.bricks.mapper;

import java.util.List;
import com.ruoyi.bricks.domain.BrkFace;

/**
 * 表情Mapper接口
 * 
 * @author ruoyi
 * @date 2026-03-11
 */
public interface BrkFaceMapper 
{
    /**
     * 查询表情
     * 
     * @param faceId 表情主键
     * @return 表情
     */
    public BrkFace selectBrkFaceByFaceId(Long faceId);

    /**
     * 查询表情列表
     * 
     * @param brkFace 表情
     * @return 表情集合
     */
    public List<BrkFace> selectBrkFaceList(BrkFace brkFace);

    /**
     * 新增表情
     * 
     * @param brkFace 表情
     * @return 结果
     */
    public int insertBrkFace(BrkFace brkFace);

    /**
     * 修改表情
     * 
     * @param brkFace 表情
     * @return 结果
     */
    public int updateBrkFace(BrkFace brkFace);

    /**
     * 删除表情
     * 
     * @param faceId 表情主键
     * @return 结果
     */
    public int deleteBrkFaceByFaceId(Long faceId);

    /**
     * 批量删除表情
     * 
     * @param faceIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBrkFaceByFaceIds(Long[] faceIds);
}
