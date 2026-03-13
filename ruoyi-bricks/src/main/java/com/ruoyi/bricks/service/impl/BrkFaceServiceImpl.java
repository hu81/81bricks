package com.ruoyi.bricks.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.bricks.domain.BrkFaceLayer;
import com.ruoyi.bricks.mapper.BrkFaceMapper;
import com.ruoyi.bricks.domain.BrkFace;
import com.ruoyi.bricks.service.IBrkFaceService;

/**
 * 表情Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-03-12
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
        List<BrkFace> list = brkFaceMapper.selectBrkFaceList(brkFace);
        for (BrkFace face : list)
        {
            face.setLayers(brkFaceMapper.selectBrkFaceLayerList(face.getFaceId()));
        }
        return list;
    }

    /**
     * 新增表情
     * 
     * @param brkFace 表情
     * @return 结果
     */
    @Transactional
    @Override
    public int insertBrkFace(BrkFace brkFace)
    {
        brkFace.setCreateTime(DateUtils.getNowDate());
        int rows = brkFaceMapper.insertBrkFace(brkFace);
        insertBrkFaceLayer(brkFace);
        return rows;
    }

    /**
     * 修改表情
     * 
     * @param brkFace 表情
     * @return 结果
     */
    @Transactional
    @Override
    public int updateBrkFace(BrkFace brkFace)
    {
        BrkFace existingFace = brkFaceMapper.selectBrkFaceByFaceId(brkFace.getFaceId());
        if (existingFace != null)
        {
            brkFace.setOriginId(existingFace.getOriginId());
            brkFace.setOriginUrl(existingFace.getOriginUrl());
        }
        brkFace.setUpdateTime(DateUtils.getNowDate());
        brkFaceMapper.deleteBrkFaceLayerByFaceId(brkFace.getFaceId());
        insertBrkFaceLayer(brkFace);
        return brkFaceMapper.updateBrkFace(brkFace);
    }

    /**
     * 批量删除表情
     * 
     * @param faceIds 需要删除的表情主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteBrkFaceByFaceIds(Long[] faceIds)
    {
        brkFaceMapper.deleteBrkFaceLayerByFaceIds(faceIds);
        return brkFaceMapper.deleteBrkFaceByFaceIds(faceIds);
    }

    /**
     * 删除表情信息
     * 
     * @param faceId 表情主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteBrkFaceByFaceId(Long faceId)
    {
        brkFaceMapper.deleteBrkFaceLayerByFaceId(faceId);
        return brkFaceMapper.deleteBrkFaceByFaceId(faceId);
    }

    /**
     * 新增情图层信息
     * 
     * @param brkFace 表情对象
     */
    public void insertBrkFaceLayer(BrkFace brkFace)
    {
        List<BrkFaceLayer> layers = brkFace.getLayers();
        Long faceId = brkFace.getFaceId();
        if (StringUtils.isNotNull(layers))
        {
            List<BrkFaceLayer> list = new ArrayList<BrkFaceLayer>();
            for (BrkFaceLayer layer : layers)
            {
                layer.setFaceId(faceId);
                list.add(layer);
            }
            if (list.size() > 0)
            {
                brkFaceMapper.batchBrkFaceLayer(list);
            }
        }
    }
}
