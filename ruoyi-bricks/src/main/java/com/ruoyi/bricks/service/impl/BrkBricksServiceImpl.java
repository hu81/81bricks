package com.ruoyi.bricks.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.stream.Collectors;
import com.ruoyi.bricks.domain.BrkBricksBrick;
import com.ruoyi.bricks.domain.BrkBricksConnpoint;
import com.ruoyi.bricks.domain.BrkBricksGroup;
import com.ruoyi.bricks.domain.BrkBricksMesh;
import com.ruoyi.bricks.mapper.BrkBricksMapper;
import com.ruoyi.bricks.mapper.BrkBricksGroupMapper;
import com.ruoyi.bricks.mapper.BrkSetMapper;
import com.ruoyi.bricks.domain.BrkSetMesh;
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
    private static final Map<String, Integer> DEFAULT_COLOR_MAP = new HashMap<>();
    static
    {
        DEFAULT_COLOR_MAP.put("1009000", 78);
        DEFAULT_COLOR_MAP.put("1009003", 15);
        DEFAULT_COLOR_MAP.put("1009004", 0);
        DEFAULT_COLOR_MAP.put("1009005", 15);
    }

    @Autowired
    private BrkBricksMapper brkBricksMapper;

    @Autowired
    private BrkBricksGroupMapper brkBricksGroupMapper;

    @Autowired
    private BrkSetMapper brkSetMapper;

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
    public List<BrkBricks> selectBrkBricksListWithLdrData(BrkBricks brkBricks)
    {
        List<BrkBricks> list = brkBricksMapper.selectBrkBricksList(brkBricks);
        if (list != null && !list.isEmpty())
        {
            for (BrkBricks bricks : list)
            {
                if (bricks.getBricksId() != null)
                {
                    bricks.setBricks(brkBricksMapper.selectBrkBricksBrickList(bricks.getBricksId()));
                    bricks.setConnpoints(brkBricksMapper.selectBrkBricksConnpointList(bricks.getBricksId()));
                    bricks.setLdrData(generateLdrContent(bricks.getBricksId(), true, true, false));
                }
            }
        }
        return list;
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

        if (brkBricks.getGroups() != null && !brkBricks.getGroups().isEmpty())
        {
            for (BrkBricksGroup group : brkBricks.getGroups())
            {
                group.setBricksId(brkBricks.getBricksId());
                group.setCreateTime(brkBricks.getCreateTime());
            }
            brkBricksGroupMapper.batchBrkBricksGroup(brkBricks.getGroups());
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
    public List<String> generateLdrContent(Long bricksId)
    {
        return generateLdrContent(bricksId, false, false, false, null, null, null);
    }

    @Override
    public List<String> generateLdrContent(Long bricksId, boolean replaceDefaultColor, boolean useOriginalParts)
    {
        return generateLdrContent(bricksId, replaceDefaultColor, useOriginalParts, false, null, null, null);
    }

    @Override
    public List<String> generateLdrContent(Long bricksId, boolean replaceDefaultColor, boolean useOriginalParts, boolean removeAbnormalParts)
    {
        return generateLdrContent(bricksId, replaceDefaultColor, useOriginalParts, removeAbnormalParts, null, null, null);
    }

    @Override
    public List<String> generateLdrContent(Long bricksId, boolean replaceDefaultColor, boolean useOriginalParts, boolean removeAbnormalParts,
            Double offsetX, Double offsetY, Double offsetZ)
    {
        List<String> lines = new ArrayList<>();
        BrkBricks bricks = selectBrkBricksByBricksId(bricksId);
        if (bricks == null || bricks.getBricks() == null)
        {
            return lines;
        }

        if (offsetX == null) offsetX = 0.0;
        if (offsetY == null) offsetY = 0.0;
        if (offsetZ == null) offsetZ = 0.0;

        Map<String, String> meshRefMap = new HashMap<>();
        if (useOriginalParts)
        {
            List<BrkBricksMesh> meshes = brkBricksMapper.selectBrkBricksMeshList(bricksId);
            if (meshes != null)
            {
                for (BrkBricksMesh mesh : meshes)
                {
                    meshRefMap.put(mesh.getRefId(), mesh.getPartNumber());
                }
            }
        }

        Integer defaultColor = null;
        if (replaceDefaultColor && bricks.getDefaultColor() != null)
        {
            defaultColor = bricks.getDefaultColor();
        }

        if (offsetX == 0.0 && offsetY == 0.0 && offsetZ == 0.0)
        {
            lines.add("0 Name: " + bricks.getBricksName());
            lines.add("0 Comment: Generated from brk_bricks");
        } else {
            lines.add("");
        }

        for (BrkBricksBrick brick : bricks.getBricks())
        {
            BrkBricksBrick processedBrick = new BrkBricksBrick();
            BeanUtils.copyBeanProp(processedBrick, brick);

            if (replaceDefaultColor)
            {
                String colorId = brick.getColorId();
                if (DEFAULT_COLOR_MAP.containsKey(colorId))
                {
                    Integer replaceColor = DEFAULT_COLOR_MAP.get(colorId);

                    if ("1009000".equals(colorId))
                    {
                        replaceColor = DEFAULT_COLOR_MAP.get("1009000");
                    }
                    else if (defaultColor != null)
                    {
                        replaceColor = defaultColor;
                    }

                    if (replaceColor != null)
                    {
                        processedBrick.setColorId(String.valueOf(replaceColor));
                    }
                }
            }

            if (useOriginalParts && processedBrick.getPartNumber() != null && processedBrick.getPartNumber().contains("_"))
            {
                String refId = processedBrick.getPartNumber();
                String newPartNumber = null;

                if (meshRefMap.containsKey(refId))
                {
                    newPartNumber = meshRefMap.get(refId);
                }
                else
                {
                    BrkSetMesh setMesh = brkSetMapper.selectBrkSetMeshByRefId(refId);
                    if (setMesh != null && setMesh.getPartNumber() != null)
                    {
                        newPartNumber = setMesh.getPartNumber();
                    }
                }

                if (newPartNumber == null || newPartNumber.isEmpty())
                {
                    String partNumber = processedBrick.getPartNumber();
                    int firstUnderscore = partNumber.indexOf('_');
                    int secondUnderscore = partNumber.indexOf('_', firstUnderscore + 1);
                    if (firstUnderscore != -1 && secondUnderscore != -1 && secondUnderscore > firstUnderscore + 1)
                    {
                        newPartNumber = partNumber.substring(firstUnderscore + 1, secondUnderscore);
                    }
                }

                if (newPartNumber != null && !newPartNumber.isEmpty())
                {
                    processedBrick.setPartNumber(newPartNumber);
                }
            }

            if (removeAbnormalParts && processedBrick.getPartNumber() != null)
            {
                String partNumber = processedBrick.getPartNumber();
                if (partNumber.contains("_") || partNumber.startsWith("qm") || partNumber.startsWith("qz"))
                {
                    continue;
                }
            }

            double x = (brick.getX() != null ? brick.getX().doubleValue() : 0.0) + offsetX;
            double y = (brick.getY() != null ? brick.getY().doubleValue() : 0.0) + offsetY;
            double z = (brick.getZ() != null ? brick.getZ().doubleValue() : 0.0) + offsetZ;

            String ldrLine = String.format("1 %s %.4f %.4f %.4f %.4f %.4f %.4f %.4f %.4f %.4f %.4f %.4f %.4f %s.dat",
                    processedBrick.getColorId() != null ? processedBrick.getColorId() : "0",
                    x, y, z,
                    processedBrick.getM11() != null ? processedBrick.getM11().doubleValue() : 1.0,
                    processedBrick.getM12() != null ? processedBrick.getM12().doubleValue() : 0.0,
                    processedBrick.getM13() != null ? processedBrick.getM13().doubleValue() : 0.0,
                    processedBrick.getM21() != null ? processedBrick.getM21().doubleValue() : 0.0,
                    processedBrick.getM22() != null ? processedBrick.getM22().doubleValue() : 1.0,
                    processedBrick.getM23() != null ? processedBrick.getM23().doubleValue() : 0.0,
                    processedBrick.getM31() != null ? processedBrick.getM31().doubleValue() : 0.0,
                    processedBrick.getM32() != null ? processedBrick.getM32().doubleValue() : 0.0,
                    processedBrick.getM33() != null ? processedBrick.getM33().doubleValue() : 1.0,
                    processedBrick.getPartNumber() != null ? processedBrick.getPartNumber() : "");

            lines.add(ldrLine);
        }

        return lines;
    }
}
