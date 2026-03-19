package com.ruoyi.bricks.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.bricks.domain.BrkBricks;
import com.ruoyi.bricks.service.IBrkBricksService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 积木模型Controller
 * 
 * @author ruoyi
 * @date 2026-03-13
 */
@RestController
@RequestMapping("/bricks/model")
public class BrkBricksController extends BaseController
{
    @Autowired
    private IBrkBricksService brkBricksService;

    @PreAuthorize("@ss.hasPermi('bricks:list')")
    @GetMapping("/list")
    public TableDataInfo list(BrkBricks brkBricks)
    {
        startPage();
        List<BrkBricks> list = brkBricksService.selectBrkBricksListWithLdrData(brkBricks);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('bricks:query')")
    @GetMapping("/{bricksId}")
    public AjaxResult getInfo(@PathVariable("bricksId") Long bricksId)
    {
        return AjaxResult.success(brkBricksService.selectBrkBricksByBricksId(bricksId));
    }

    @PreAuthorize("@ss.hasPermi('bricks:add')")
    @Log(title = "积木模型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BrkBricks brkBricks)
    {
        return toAjax(brkBricksService.insertBrkBricks(brkBricks));
    }

    @PreAuthorize("@ss.hasPermi('bricks:edit')")
    @Log(title = "积木模型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BrkBricks brkBricks)
    {
        return toAjax(brkBricksService.updateBrkBricks(brkBricks));
    }

    @PreAuthorize("@ss.hasPermi('bricks:remove')")
    @Log(title = "积木模型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{bricksIds}")
    public AjaxResult remove(@PathVariable Long[] bricksIds)
    {
        return toAjax(brkBricksService.deleteBrkBricksByBricksIds(bricksIds));
    }

    @GetMapping("/ldr/{bricksId}")
    public AjaxResult getLdrContent(
            @PathVariable("bricksId") Long bricksId,
            @RequestParam(value = "replaceDefaultColor", defaultValue = "true") boolean replaceDefaultColor,
            @RequestParam(value = "useOriginalParts", defaultValue = "true") boolean useOriginalParts,
            @RequestParam(value = "removeAbnormalParts", defaultValue = "false") boolean removeAbnormalParts)
    {
        List<String> ldrContent = brkBricksService.generateLdrContent(bricksId, replaceDefaultColor, useOriginalParts, removeAbnormalParts);
        return AjaxResult.success("操作成功", ldrContent);
    }
}
