package com.ruoyi.bricks.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.bricks.domain.BrkSet;
import com.ruoyi.bricks.domain.BrkSetListDTO;
import com.ruoyi.bricks.domain.PreviewImgRequest;
import com.ruoyi.bricks.service.IBrkSetService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 套装Controller
 * 
 * @author ruoyi
 * @date 2026-03-16
 */
@RestController
@RequestMapping("/bricks/set")
public class BrkSetController extends BaseController
{
    @Autowired
    private IBrkSetService brkSetService;

    @PreAuthorize("@ss.hasPermi('bricks:set:list')")
    @GetMapping("/list")
    public TableDataInfo list(BrkSetListDTO dto)
    {
        startPage();
        List<BrkSet> list = brkSetService.selectBrkSetListWithSimpleCategories(dto, dto.isIncludeLdr());
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('bricks:set:query')")
    @GetMapping("/{setId}")
    public AjaxResult getInfo(@PathVariable("setId") Long setId)
    {
        return AjaxResult.success(brkSetService.selectBrkSetBySetId(setId));
    }

    @GetMapping("/ldr/{setId}")
    public AjaxResult getLdrContent(
            @PathVariable("setId") Long setId,
            @RequestParam(value = "replaceDefaultColor", defaultValue = "true") boolean replaceDefaultColor,
            @RequestParam(value = "useOriginalParts", defaultValue = "true") boolean useOriginalParts,
            @RequestParam(value = "removeAbnormalParts", defaultValue = "false") boolean removeAbnormalParts,
            @RequestParam(value = "includeHandheld", defaultValue = "false") boolean includeHandheld)
    {
        List<String> ldrContent = brkSetService.generateSetLdrContent(setId, replaceDefaultColor, useOriginalParts, removeAbnormalParts, includeHandheld);
        return AjaxResult.success("操作成功", ldrContent);
    }

    @PreAuthorize("@ss.hasPermi('bricks:set:edit')")
    @Log(title = "套装预览图", businessType = BusinessType.UPDATE)
    @PutMapping("/preview/{setId}")
    public AjaxResult updatePreviewImg(@PathVariable("setId") Long setId, @RequestBody PreviewImgRequest request)
    {
        return toAjax(brkSetService.updatePreviewImg(setId, request.getPreviewImg()));
    }
}
