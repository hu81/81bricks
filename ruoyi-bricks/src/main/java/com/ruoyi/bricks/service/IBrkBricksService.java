package com.ruoyi.bricks.service;

import java.util.List;
import com.ruoyi.bricks.domain.BrkBricks;

/**
 * 积木模型Service接口
 * 
 * @author ruoyi
 * @date 2026-03-13
 */
public interface IBrkBricksService 
{
    public BrkBricks selectBrkBricksByBricksId(Long bricksId);

    public BrkBricks selectBrkBricksByUuid(String uuid);

    public BrkBricks selectBrkBricksByOriginId(String originId);

    public List<BrkBricks> selectBrkBricksList(BrkBricks brkBricks);

    public int insertBrkBricks(BrkBricks brkBricks);

    public int updateBrkBricks(BrkBricks brkBricks);

    public int deleteBrkBricksByBricksId(Long bricksId);

    public int deleteBrkBricksByBricksIds(Long[] bricksIds);

    public String generateLdrContent(Long bricksId);
}
