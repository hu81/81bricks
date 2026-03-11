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
import com.ruoyi.bricks.domain.BrkResource;
import com.ruoyi.bricks.domain.LibraryInfo;
import com.ruoyi.bricks.service.IBrkResourceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 资源图片Controller
 * 
 * @author ruoyi
 * @date 2026-03-10
 */
@RestController
@RequestMapping("/bricks/resource")
public class BrkResourceController extends BaseController
{
    @Autowired
    private IBrkResourceService brkResourceService;

    /**
     * 查询资源图片列表
     */
    @PreAuthorize("@ss.hasPermi('bricks:resource:list')")
    @GetMapping("/list")
    public TableDataInfo list(BrkResource brkResource)
    {
        startPage();
        List<BrkResource> list = brkResourceService.selectBrkResourceList(brkResource);
        return getDataTable(list);
    }

    /**
     * 导出资源图片列表
     */
    @PreAuthorize("@ss.hasPermi('bricks:resource:export')")
    @Log(title = "资源图片", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BrkResource brkResource)
    {
        List<BrkResource> list = brkResourceService.selectBrkResourceList(brkResource);
        ExcelUtil<BrkResource> util = new ExcelUtil<BrkResource>(BrkResource.class);
        util.exportExcel(response, list, "资源图片数据");
    }

    /**
     * 获取资源图片详细信息
     */
    @PreAuthorize("@ss.hasPermi('bricks:resource:query')")
    @GetMapping(value = "/{resourceId}")
    public AjaxResult getInfo(@PathVariable("resourceId") Long resourceId)
    {
        return success(brkResourceService.selectBrkResourceByResourceId(resourceId));
    }

    /**
     * 新增资源图片
     */
    @PreAuthorize("@ss.hasPermi('bricks:resource:add')")
    @Log(title = "资源图片", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BrkResource brkResource)
    {
        return toAjax(brkResourceService.insertBrkResource(brkResource));
    }

    /**
     * 修改资源图片
     */
    @PreAuthorize("@ss.hasPermi('bricks:resource:edit')")
    @Log(title = "资源图片", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BrkResource brkResource)
    {
        return toAjax(brkResourceService.updateBrkResource(brkResource));
    }

    /**
     * 删除资源图片
     */
    @PreAuthorize("@ss.hasPermi('bricks:resource:remove')")
    @Log(title = "资源图片", businessType = BusinessType.DELETE)
	@DeleteMapping("/{resourceIds}")
    public AjaxResult remove(@PathVariable Long[] resourceIds)
    {
        return toAjax(brkResourceService.deleteBrkResourceByResourceIds(resourceIds));
    }

    /**
     * Parse and return library configuration from JSON file
     */
    @GetMapping("/parseLibrary")
    public AjaxResult parseLibrary(String url)
    {
        try
        {
            LibraryInfo libraryInfo = brkResourceService.parseLibraryJson(url);
            return success(libraryInfo);
        }
        catch (Exception e)
        {
            return error("Failed to parse library: " + e.getMessage());
        }
    }
}
