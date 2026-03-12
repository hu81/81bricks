package com.ruoyi.bricks.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.bricks.domain.BrkFace;
import com.ruoyi.bricks.service.IBrkFaceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 表情Controller
 * 
 * @author ruoyi
 * @date 2026-03-12
 */
@RestController
@RequestMapping("/bricks/face")
public class BrkFaceController extends BaseController
{
    @Autowired
    private IBrkFaceService brkFaceService;

    /**
     * 查询表情列表
     */
    @PreAuthorize("@ss.hasPermi('bricks:face:list')")
    @GetMapping("/list")
    public TableDataInfo list(BrkFace brkFace)
    {
        startPage();
        List<BrkFace> list = brkFaceService.selectBrkFaceList(brkFace);
        return getDataTable(list);
    }

    /**
     * 导出表情列表
     */
    @PreAuthorize("@ss.hasPermi('bricks:face:export')")
    @Log(title = "表情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BrkFace brkFace)
    {
        List<BrkFace> list = brkFaceService.selectBrkFaceList(brkFace);
        ExcelUtil<BrkFace> util = new ExcelUtil<BrkFace>(BrkFace.class);
        util.exportExcel(response, list, "表情数据");
    }

    /**
     * 获取表情详细信息
     */
    @PreAuthorize("@ss.hasPermi('bricks:face:query')")
    @GetMapping(value = "/{faceId}")
    public AjaxResult getInfo(@PathVariable("faceId") Long faceId)
    {
        return success(brkFaceService.selectBrkFaceByFaceId(faceId));
    }

    /**
     * 新增表情
     */
    @PreAuthorize("@ss.hasPermi('bricks:face:add')")
    @Log(title = "表情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BrkFace brkFace)
    {
        return toAjax(brkFaceService.insertBrkFace(brkFace));
    }

    /**
     * 修改表情
     */
    @PreAuthorize("@ss.hasPermi('bricks:face:edit')")
    @Log(title = "表情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BrkFace brkFace)
    {
        return toAjax(brkFaceService.updateBrkFace(brkFace));
    }

    /**
     * 删除表情
     */
    @PreAuthorize("@ss.hasPermi('bricks:face:remove')")
    @Log(title = "表情", businessType = BusinessType.DELETE)
	@DeleteMapping("/{faceIds}")
    public AjaxResult remove(@PathVariable Long[] faceIds)
    {
        return toAjax(brkFaceService.deleteBrkFaceByFaceIds(faceIds));
    }
}
