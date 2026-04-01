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
            bricks.setGroups(brkBricksGroupMapper.selectBrkBricksGroupList(bricksId));
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
            bricks.setGroups(brkBricksGroupMapper.selectBrkBricksGroupList(bricks.getBricksId()));
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
    public List<BrkBricks> selectBrkBricksListWithLdrData(BrkBricks brkBricks, boolean includeLdr)
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
                    if (includeLdr)
                    {
                        bricks.setLdrData(generateLdrContent(bricks.getBricksId(), true, true, false));
                    }
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
        return generateLdrContent(bricksId, false, false, false, null, null, null, false);
    }

    @Override
    public List<String> generateLdrContent(Long bricksId, boolean replaceDefaultColor, boolean useOriginalParts)
    {
        return generateLdrContent(bricksId, replaceDefaultColor, useOriginalParts, false, null, null, null, false);
    }

    @Override
    public List<String> generateLdrContent(Long bricksId, boolean replaceDefaultColor, boolean useOriginalParts, boolean removeAbnormalParts)
    {
        return generateLdrContent(bricksId, replaceDefaultColor, useOriginalParts, removeAbnormalParts, true);
    }

    @Override
    public List<String> generateLdrContent(Long bricksId, boolean replaceDefaultColor, boolean useOriginalParts, boolean removeAbnormalParts,
            boolean replaceCustomGroup)
    {
        return generateLdrContent(bricksId, replaceDefaultColor, useOriginalParts, removeAbnormalParts, null, null, null, false, replaceCustomGroup);
    }

    @Override
    public List<String> generateLdrContent(Long bricksId, boolean replaceDefaultColor, boolean useOriginalParts, boolean removeAbnormalParts,
            Double offsetX, Double offsetY, Double offsetZ)
    {
        return generateLdrContent(bricksId, replaceDefaultColor, useOriginalParts, removeAbnormalParts, offsetX, offsetY, offsetZ, false, true);
    }

    @Override
    public List<String> generateLdrContent(Long bricksId, boolean replaceDefaultColor, boolean useOriginalParts, boolean removeAbnormalParts,
            Double offsetX, Double offsetY, Double offsetZ, boolean translateByGroup)
    {
        return generateLdrContent(bricksId, replaceDefaultColor, useOriginalParts, removeAbnormalParts, offsetX, offsetY, offsetZ, translateByGroup, true);
    }

    @Override
    public List<String> generateLdrContent(Long bricksId, boolean replaceDefaultColor, boolean useOriginalParts, boolean removeAbnormalParts,
            Double offsetX, Double offsetY, Double offsetZ, boolean translateByGroup, boolean replaceCustomGroup)
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

        double groupOffsetX = 0.0;
        double groupOffsetY = 0.0;
        double groupOffsetZ = 0.0;

        if (translateByGroup && bricks.getGroups() != null && !bricks.getGroups().isEmpty())
        {
            BrkBricksGroup maxGroup = null;
            int maxGroupIndex = Integer.MIN_VALUE;

            for (BrkBricksGroup group : bricks.getGroups())
            {
                if (group.getGroupIndex() != null && group.getGroupIndex() > maxGroupIndex)
                {
                    maxGroupIndex = group.getGroupIndex();
                    maxGroup = group;
                }
            }

            if (maxGroup != null)
            {
                groupOffsetX = maxGroup.getX() != null ? maxGroup.getX().doubleValue() : 0.0;
                groupOffsetY = maxGroup.getY() != null ? maxGroup.getY().doubleValue() : 0.0;
                groupOffsetZ = maxGroup.getZ() != null ? maxGroup.getZ().doubleValue() : 0.0;
            }
        }

        double totalOffsetX = groupOffsetX + offsetX;
        double totalOffsetY = groupOffsetY + offsetY;
        double totalOffsetZ = groupOffsetZ + offsetZ;

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

        if (totalOffsetX == 0.0 && totalOffsetY == 0.0 && totalOffsetZ == 0.0)
        {
            lines.add("0 Name: " + bricks.getBricksName());
            lines.add("0 Comment: Generated from brk_bricks");
        } else {
            lines.add("");
        }

        List<BrkBricksBrick> customGroupBricks = new ArrayList<>();
        if (replaceCustomGroup && "hair".equals(bricks.getCategory()))
        {
            BrkBricks customModel = selectBrkBricksByBricksId(100000000L);
            if (customModel != null && customModel.getBricks() != null)
            {
                double ref3022X = 0.0, ref3022Y = 0.0, ref3022Z = 0.0;
                double refQzX = 0.0, refQzY = 0.0, refQzZ = 0.0;
                boolean found3022 = false, foundQz = false;

                for (BrkBricksBrick cb : customModel.getBricks())
                {
                    if (cb.getPartNumber() != null && !found3022 && "3022".equals(cb.getPartNumber()))
                    {
                        ref3022X = cb.getX() != null ? cb.getX().doubleValue() : 0.0;
                        ref3022Y = cb.getY() != null ? cb.getY().doubleValue() : 0.0;
                        ref3022Z = cb.getZ() != null ? cb.getZ().doubleValue() : 0.0;
                        found3022 = true;
                    }
                }

                for (BrkBricksBrick ob : bricks.getBricks())
                {
                    if (ob.getPartNumber() != null && !foundQz && "qz98701".equals(ob.getPartNumber()))
                    {
                        refQzX = ob.getX() != null ? ob.getX().doubleValue() : 0.0;
                        refQzY = ob.getY() != null ? ob.getY().doubleValue() : 0.0;
                        refQzZ = ob.getZ() != null ? ob.getZ().doubleValue() : 0.0;
                        foundQz = true;
                    }
                }

                if (found3022 && foundQz)
                {
                    double newPosX = refQzX - ref3022X;
                    double newPosY = refQzY - ref3022Y;
                    double newPosZ = refQzZ - ref3022Z;
                    double translateX = newPosX - ref3022X;
                    double translateY = newPosY - ref3022Y;
                    double translateZ = newPosZ - ref3022Z;

                    for (BrkBricksBrick customBrick : customModel.getBricks())
                    {
                        BrkBricksBrick translated = new BrkBricksBrick();
                        BeanUtils.copyBeanProp(translated, customBrick);
                        if (translated.getX() != null) translated.setX(translated.getX().add(java.math.BigDecimal.valueOf(translateX)));
                        if (translated.getY() != null) translated.setY(translated.getY().add(java.math.BigDecimal.valueOf(translateY)));
                        if (translated.getZ() != null) translated.setZ(translated.getZ().add(java.math.BigDecimal.valueOf(translateZ)));
                        customGroupBricks.add(translated);
                    }
                }
                else
                {
                    customGroupBricks.addAll(customModel.getBricks());
                }
            }
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
                if (partNumber.contains("_"))
                {
                    continue;
                }
            }

            if (replaceCustomGroup)
            {
                if (processedBrick.getPartNumber() != null &&
                    ("qz98701".equals(processedBrick.getPartNumber()) || "qz98702".equals(processedBrick.getPartNumber())))
                {
                    continue;
                }
            }

            double x = (brick.getX() != null ? brick.getX().doubleValue() : 0.0) + totalOffsetX;
            double y = (brick.getY() != null ? brick.getY().doubleValue() : 0.0) + totalOffsetY;
            double z = (brick.getZ() != null ? brick.getZ().doubleValue() : 0.0) + totalOffsetZ;

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

        if (!customGroupBricks.isEmpty())
        {
            for (BrkBricksBrick customBrick : customGroupBricks)
            {
                double x = (customBrick.getX() != null ? customBrick.getX().doubleValue() : 0.0) + totalOffsetX;
                double y = (customBrick.getY() != null ? customBrick.getY().doubleValue() : 0.0) + totalOffsetY;
                double z = (customBrick.getZ() != null ? customBrick.getZ().doubleValue() : 0.0) + totalOffsetZ;

                String ldrLine = String.format("1 %s %.4f %.4f %.4f %.4f %.4f %.4f %.4f %.4f %.4f %.4f %.4f %.4f %s.dat",
                        customBrick.getColorId() != null ? customBrick.getColorId() : "0",
                        x, y, z,
                        customBrick.getM11() != null ? customBrick.getM11().doubleValue() : 1.0,
                        customBrick.getM12() != null ? customBrick.getM12().doubleValue() : 0.0,
                        customBrick.getM13() != null ? customBrick.getM13().doubleValue() : 0.0,
                        customBrick.getM21() != null ? customBrick.getM21().doubleValue() : 0.0,
                        customBrick.getM22() != null ? customBrick.getM22().doubleValue() : 1.0,
                        customBrick.getM23() != null ? customBrick.getM23().doubleValue() : 0.0,
                        customBrick.getM31() != null ? customBrick.getM31().doubleValue() : 0.0,
                        customBrick.getM32() != null ? customBrick.getM32().doubleValue() : 0.0,
                        customBrick.getM33() != null ? customBrick.getM33().doubleValue() : 1.0,
                        customBrick.getPartNumber() != null ? customBrick.getPartNumber() : "");

                lines.add(ldrLine);
            }
        }

        return lines;
    }

    @Override
    public int updatePreviewImg(Long bricksId, String previewImg)
    {
        BrkBricks brkBricks = new BrkBricks();
        brkBricks.setBricksId(bricksId);
        brkBricks.setPreviewImg(previewImg);
        brkBricks.setUpdateTime(DateUtils.getNowDate());
        return brkBricksMapper.updateBrkBricks(brkBricks);
    }
}
