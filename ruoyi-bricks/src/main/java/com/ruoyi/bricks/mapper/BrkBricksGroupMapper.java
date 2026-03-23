package com.ruoyi.bricks.mapper;

import java.util.List;
import com.ruoyi.bricks.domain.BrkBricksGroup;

public interface BrkBricksGroupMapper
{
    public List<BrkBricksGroup> selectBrkBricksGroupList(Long bricksId);

    public int batchBrkBricksGroup(List<BrkBricksGroup> list);

    public int deleteBrkBricksGroupByBricksId(Long bricksId);
}