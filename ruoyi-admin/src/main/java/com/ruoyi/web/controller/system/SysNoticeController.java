package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysNotice;
import com.ruoyi.system.service.ISysNoticeReadService;
import com.ruoyi.system.service.ISysNoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公告 信息操作处理
 *
 * @author 棠野·Lysander
 */
@Tag(name = "通知公告", description = "系统通知公告管理")
@RestController
@RequestMapping("/system/notice")
@RequiredArgsConstructor
public class SysNoticeController extends BaseController {
    private final ISysNoticeService noticeService;

    private final ISysNoticeReadService noticeReadService;

    /**
     * 获取通知公告列表
     */
    @Operation(summary = "获取通知公告列表", description = "分页查询，支持按标题、类型、状态等条件过滤")
    @PreAuthorize("@ss.hasPermi('system:notice:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysNotice notice) {
        startPage();
        List<SysNotice> list = noticeService.selectNoticeList(notice);
        return getDataTable(list);
    }

    /**
     * 根据通知公告编号获取详细信息
     */
    @Operation(summary = "获取通知公告详细信息")
    @GetMapping(value = "/{noticeId}")
    public AjaxResult getInfo(
            @Parameter(description = "公告编号", required = true) @PathVariable Long noticeId) {
        return success(noticeService.selectNoticeById(noticeId));
    }

    /**
     * 新增通知公告
     */
    @Operation(summary = "新增通知公告")
    @PreAuthorize("@ss.hasPermi('system:notice:add')")
    @Log(title = "通知公告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysNotice notice) {
        notice.setCreateBy(getUsername());
        return toAjax(noticeService.insertNotice(notice));
    }

    /**
     * 修改通知公告
     */
    @Operation(summary = "修改通知公告")
    @PreAuthorize("@ss.hasPermi('system:notice:edit')")
    @Log(title = "通知公告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysNotice notice) {
        notice.setUpdateBy(getUsername());
        return toAjax(noticeService.updateNotice(notice));
    }

    /**
     * 首页顶部公告列表（返回全部正常公告，带当前用户已读标记，最多5条）
     */
    @Operation(summary = "首页顶部公告列表", description = "返回最新 5 条正常公告，带当前用户已读标记及未读数 unreadCount")
    @GetMapping("/listTop")
    @ResponseBody
    public AjaxResult listTop() {
        Long userId = getUserId();
        List<SysNotice> list = noticeReadService.selectNoticeListWithReadStatus(userId, 5);
        long unreadCount = list.stream().filter(n -> !n.getIsRead()).count();
        AjaxResult result = AjaxResult.success(list);
        result.put("unreadCount", unreadCount);
        return result;
    }

    /**
     * 标记公告已读
     */
    @Operation(summary = "标记公告已读")
    @PostMapping("/markRead")
    @ResponseBody
    public AjaxResult markRead(
            @Parameter(description = "公告编号", required = true) Long noticeId) {
        Long userId = getUserId();
        noticeReadService.markRead(noticeId, userId);
        return success();
    }

    /**
     * 批量标记已读
     */
    @Operation(summary = "批量标记公告已读", description = "ids 为逗号分隔的公告编号")
    @PostMapping("/markReadAll")
    @ResponseBody
    public AjaxResult markReadAll(
            @Parameter(description = "公告编号集合（逗号分隔）", required = true) String ids) {
        Long userId = getUserId();
        Long[] noticeIds = Convert.toLongArray(ids);
        noticeReadService.markReadBatch(userId, noticeIds);
        return success();
    }

    /**
     * 已读用户列表数据
     */
    @Operation(summary = "已读用户列表", description = "分页查询指定公告的已读用户")
    @PreAuthorize("@ss.hasPermi('system:notice:list')")
    @GetMapping("/readUsers/list")
    @ResponseBody
    public TableDataInfo readUsersList(
            @Parameter(description = "公告编号", required = true) Long noticeId,
            @Parameter(description = "搜索关键词（用户名/昵称）") String searchValue) {
        startPage();
        List<?> list = noticeReadService.selectReadUsersByNoticeId(noticeId, searchValue);
        return getDataTable(list);
    }

    /**
     * 删除通知公告
     */
    @Operation(summary = "删除通知公告", description = "支持批量，路径参数为逗号分隔的公告编号")
    @PreAuthorize("@ss.hasPermi('system:notice:remove')")
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @DeleteMapping("/{noticeIds}")
    public AjaxResult remove(
            @Parameter(description = "公告编号集合", required = true) @PathVariable Long[] noticeIds) {
        noticeReadService.deleteByNoticeIds(noticeIds);
        return toAjax(noticeService.deleteNoticeByIds(noticeIds));
    }
}
