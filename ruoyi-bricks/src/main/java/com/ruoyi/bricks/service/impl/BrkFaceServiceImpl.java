package com.ruoyi.bricks.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bricks.mapper.BrkFaceMapper;
import com.ruoyi.bricks.domain.BrkFace;
import com.ruoyi.bricks.service.IBrkFaceService;

/**
 * 表情Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-03-11
 */
@Service
public class BrkFaceServiceImpl implements IBrkFaceService 
{
    @Autowired
    private BrkFaceMapper brkFaceMapper;

    /**
     * 查询表情
     * 
     * @param faceId 表情主键
     * @return 表情
     */
    @Override
    public BrkFace selectBrkFaceByFaceId(Long faceId)
    {
        return brkFaceMapper.selectBrkFaceByFaceId(faceId);
    }

    /**
     * 查询表情列表
     * 
     * @param brkFace 表情
     * @return 表情
     */
    @Override
    public List<BrkFace> selectBrkFaceList(BrkFace brkFace)
    {
        return brkFaceMapper.selectBrkFaceList(brkFace);
    }

    /**
     * 新增表情
     * 
     * @param brkFace 表情
     * @return 结果
     */
    @Override
    public int insertBrkFace(BrkFace brkFace)
    {
        brkFace.setCreateTime(DateUtils.getNowDate());
        return brkFaceMapper.insertBrkFace(brkFace);
    }

    /**
     * 修改表情
     * 
     * @param brkFace 表情
     * @return 结果
     */
    @Override
    public int updateBrkFace(BrkFace brkFace)
    {
        brkFace.setUpdateTime(DateUtils.getNowDate());
        return brkFaceMapper.updateBrkFace(brkFace);
    }

    /**
     * 批量删除表情
     * 
     * @param faceIds 需要删除的表情主键
     * @return 结果
     */
    @Override
    public int deleteBrkFaceByFaceIds(Long[] faceIds)
    {
        return brkFaceMapper.deleteBrkFaceByFaceIds(faceIds);
    }

    /**
     * 删除表情信息
     * 
     * @param faceId 表情主键
     * @return 结果
     */
    @Override
    public int deleteBrkFaceByFaceId(Long faceId)
    {
        return brkFaceMapper.deleteBrkFaceByFaceId(faceId);
    }
}
