package com.ruoyi.bricks.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.stream.Collectors;
import com.ruoyi.bricks.domain.BrkBricksBrick;
import com.ruoyi.bricks.domain.BrkBricksConnpoint;
import com.ruoyi.bricks.mapper.BrkBricksMapper;
import com.ruoyi.bricks.domain.BrkBricks;
import com.ruoyi.bricks.service.IBrkBricksService;

/**
 * 积木模型Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-03-13
 */
@Service
public class BrkBricksServiceImpl implements IBrkBricksService 
{
    @Autowired
    private BrkBricksMapper brkBricksMapper;

    @Override
    public BrkBricks selectBrkBricksByBricksId(Long bricksId)
    {
        BrkBricks bricks = brkBricksMapper.selectBrkBricksByBricksId(bricksId);
        if (bricks != null)
        {
            bricks.setBricks(brkBricksMapper.selectBrkBricksBrickList(bricksId));
            bricks.setConnpoints(brkBricksMapper.selectBrkBricksConnpointList(bricksId));
        }
        return bricks;
    }

    @Override
    public BrkBricks selectBrkBricksByUuid(String uuid)
    {
        BrkBricks bricks = brkBricksMapper.selectBrkBricksByUuid(uuid);
        if (bricks != null)
        {
            bricks.setBricks(brkBricksMapper.selectBrkBricksBrickList(bricks.getBricksId()));
            bricks.setConnpoints(brkBricksMapper.selectBrkBricksConnpointList(bricks.getBricksId()));
        }
        return bricks;
    }

    @Override
    public BrkBricks selectBrkBricksByOriginId(String originId)
    {
        return brkBricksMapper.selectBrkBricksByOriginId(originId);
    }

    @Override
    public List<BrkBricks> selectBrkBricksList(BrkBricks brkBricks)
    {
        return brkBricksMapper.selectBrkBricksList(brkBricks);
    }

    @Override
    public int insertBrkBricks(BrkBricks brkBricks)
    {
        brkBricks.setCreateTime(DateUtils.getNowDate());
        brkBricks.setUpdateTime(brkBricks.getCreateTime());
        int rows = brkBricksMapper.insertBrkBricks(brkBricks);
        
        if (brkBricks.getBricks() != null && !brkBricks.getBricks().isEmpty())
        {
            for (BrkBricksBrick brick : brkBricks.getBricks())
            {
                brick.setBricksId(brkBricks.getBricksId());
            }
            brkBricksMapper.batchBrkBricksBrick(brkBricks.getBricks());
        }
        
        if (brkBricks.getConnpoints() != null && !brkBricks.getConnpoints().isEmpty())
        {
            for (BrkBricksConnpoint connpoint : brkBricks.getConnpoints())
            {
                connpoint.setBricksId(brkBricks.getBricksId());
            }
            brkBricksMapper.batchBrkBricksConnpoint(brkBricks.getConnpoints());
        }
        
        return rows;
    }

    @Override
    public int updateBrkBricks(BrkBricks brkBricks)
    {
        brkBricks.setUpdateTime(DateUtils.getNowDate());
        
        brkBricksMapper.deleteBrkBricksBrickByBricksId(brkBricks.getBricksId());
        if (brkBricks.getBricks() != null && !brkBricks.getBricks().isEmpty())
        {
            for (BrkBricksBrick brick : brkBricks.getBricks())
            {
                brick.setBricksId(brkBricks.getBricksId());
            }
            brkBricksMapper.batchBrkBricksBrick(brkBricks.getBricks());
        }
        
        brkBricksMapper.deleteBrkBricksConnpointByBricksId(brkBricks.getBricksId());
        if (brkBricks.getConnpoints() != null && !brkBricks.getConnpoints().isEmpty())
        {
            for (BrkBricksConnpoint connpoint : brkBricks.getConnpoints())
            {
                connpoint.setBricksId(brkBricks.getBricksId());
            }
            brkBricksMapper.batchBrkBricksConnpoint(brkBricks.getConnpoints());
        }
        
        return brkBricksMapper.updateBrkBricks(brkBricks);
    }

    @Override
    public int deleteBrkBricksByBricksId(Long bricksId)
    {
        brkBricksMapper.deleteBrkBricksBrickByBricksId(bricksId);
        brkBricksMapper.deleteBrkBricksConnpointByBricksId(bricksId);
        return brkBricksMapper.deleteBrkBricksByBricksId(bricksId);
    }

    @Override
    public int deleteBrkBricksByBricksIds(Long[] bricksIds)
    {
        for (Long bricksId : bricksIds)
        {
            brkBricksMapper.deleteBrkBricksBrickByBricksId(bricksId);
            brkBricksMapper.deleteBrkBricksConnpointByBricksId(bricksId);
        }
        return brkBricksMapper.deleteBrkBricksByBricksIds(bricksIds);
    }

    @Override
    public String generateLdrContent(Long bricksId)
    {
        BrkBricks bricks = selectBrkBricksByBricksId(bricksId);
        if (bricks == null || bricks.getBricks() == null)
        {
            return "";
        }

        StringBuilder ldr = new StringBuilder();
        ldr.append("0 Name: ").append(bricks.getBricksName()).append("\n");
        ldr.append("0 Comment: Generated from brk_bricks\n");
        
        for (BrkBricksBrick brick : bricks.getBricks())
        {
            ldr.append(brick.toLdrLine()).append("\n");
        }
        
        return ldr.toString();
    }
}
