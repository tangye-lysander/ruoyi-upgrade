package com.ruoyi.common.utils;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.utils.sql.SqlUtil;

/**
 * 分页工具类
 *
 * @author 棠野·Lysander
 */
public class PageUtils extends PageHelper {
    /**
     * 设置请求分页数据
     */
    public static void startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
        Boolean reasonable = pageDomain.getReasonable();
        PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
    }

    /**
     * 清理分页的线程变量
     */
    public static void clearPage() {
        PageHelper.clearPage();
    }

    /**
     * 构建 MyBatis-Plus 分页对象（从请求参数读取分页与排序信息）
     */
    public static <T> Page<T> buildPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Page<T> page = new Page<>(pageDomain.getPageNum(), pageDomain.getPageSize());
        if (StringUtils.isNotEmpty(pageDomain.getOrderByColumn())) {
            String column = SqlUtil.escapeOrderBySql(StringUtils.toUnderScoreCase(pageDomain.getOrderByColumn()));
            if ("desc".equals(pageDomain.getIsAsc())) {
                page.addOrder(OrderItem.desc(column));
            } else {
                page.addOrder(OrderItem.asc(column));
            }
        }
        return page;
    }
}
