package com.ruoyi.bricks.mapper;

import java.util.List;
import com.ruoyi.bricks.domain.BrkBricks;
import com.ruoyi.bricks.domain.BrkBricksBrick;
import com.ruoyi.bricks.domain.BrkBricksConnpoint;

/**
 * 积木模型Mapper接口
 * 
 * @author ruoyi
 * @date 2026-03-13
 */
public interface BrkBricksMapper 
{
    public BrkBricks selectBrkBricksByBricksId(Long bricksId);

    public BrkBricks selectBrkBricksByUuid(String uuid);

    public BrkBricks selectBrkBricksByOriginId(String originId);

    public List<BrkBricks> selectBrkBricksList(BrkBricks brkBricks);

    public int insertBrkBricks(BrkBricks brkBricks);

    public int updateBrkBricks(BrkBricks brkBricks);

    public int deleteBrkBricksByBricksId(Long bricksId);

    public int deleteBrkBricksByBricksIds(Long[] bricksIds);

    public List<BrkBricksBrick> selectBrkBricksBrickList(Long bricksId);

    public int batchBrkBricksBrick(List<BrkBricksBrick> list);

    public int deleteBrkBricksBrickByBricksId(Long bricksId);

    public List<BrkBricksConnpoint> selectBrkBricksConnpointList(Long bricksId);

    public int batchBrkBricksConnpoint(List<BrkBricksConnpoint> list);

    public int deleteBrkBricksConnpointByBricksId(Long bricksId);
}
