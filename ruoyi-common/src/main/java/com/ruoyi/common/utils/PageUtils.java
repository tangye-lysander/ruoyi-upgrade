package com.ruoyi.common.utils;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.utils.sql.SqlUtil;

/**
 * 分页工具类（基于 MyBatis-Plus）
 *
 * @author 棠野·Lysander
 */
public class PageUtils {

    private PageUtils() {
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
