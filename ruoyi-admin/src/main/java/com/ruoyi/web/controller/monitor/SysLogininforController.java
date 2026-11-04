package com.ruoyi.web.controller.monitor;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.service.SysPasswordService;
import com.ruoyi.system.domain.SysLogininfor;
import com.ruoyi.system.service.ISysLogininforService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统访问记录
 *
 * @author 棠野·Lysander
 */
@Tag(name = "登录日志", description = "系统登录日志监控")
@RestController
@RequestMapping("/monitor/logininfor")
@RequiredArgsConstructor
public class SysLogininforController extends BaseController {
    private final ISysLogininforService logininforService;

    private final SysPasswordService passwordService;

    @Operation(summary = "获取登录日志列表", description = "分页查询，支持按登录地址、用户名、状态、时间范围等条件过滤")
    @PreAuthorize("@ss.hasPermi('monitor:logininfor:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysLogininfor logininfor) {
        startPage();
        List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
        return getDataTable(list);
    }

    @Operation(summary = "导出登录日志列表", description = "按查询条件导出登录日志 Excel")
    @Log(title = "登录日志", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('monitor:logininfor:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysLogininfor logininfor) {
        List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
        ExcelUtil<SysLogininfor> util = new ExcelUtil<SysLogininfor>(SysLogininfor.class);
        util.exportExcel(response, list, "登录日志");
    }

    @Operation(summary = "删除登录日志", description = "支持批量，路径参数为逗号分隔的日志编号")
    @PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')")
    @Log(title = "登录日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{infoIds}")
    public AjaxResult remove(
            @Parameter(description = "登录日志编号集合", required = true) @PathVariable Long[] infoIds) {
        return toAjax(logininforService.deleteLogininforByIds(infoIds));
    }

    @Operation(summary = "清空登录日志")
    @PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')")
    @Log(title = "登录日志", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clean")
    public AjaxResult clean() {
        logininforService.cleanLogininfor();
        return success();
    }

    @Operation(summary = "账户解锁", description = "清除指定用户的密码错误锁定缓存")
    @PreAuthorize("@ss.hasPermi('monitor:logininfor:unlock')")
    @Log(title = "账户解锁", businessType = BusinessType.OTHER)
    @GetMapping("/unlock/{userName}")
    public AjaxResult unlock(
            @Parameter(description = "用户名", required = true) @PathVariable("userName") String userName) {
        passwordService.clearLoginRecordCache(userName);
        return success();
    }
}
