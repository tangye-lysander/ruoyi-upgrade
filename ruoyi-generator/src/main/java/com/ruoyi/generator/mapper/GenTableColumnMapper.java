package com.ruoyi.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.generator.domain.GenTableColumn;

import java.util.List;

/**
 * 业务字段 数据层
 * <p>
 * gen_table_column 的常规 CRUD 走 MyBatis-Plus 内置方法（BaseMapper），
 * 这里只保留 information_schema.columns 的元数据查询。
 *
 * @author 棠野·Lysander
 */
public interface GenTableColumnMapper extends BaseMapper<GenTableColumn> {
    /**
     * 根据表名称查询列信息
     *
     * @param tableName 表名称
     * @return 列信息
     */
    public List<GenTableColumn> selectDbTableColumnsByName(String tableName);
}
