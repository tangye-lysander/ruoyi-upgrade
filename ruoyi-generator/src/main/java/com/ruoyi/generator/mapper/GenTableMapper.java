package com.ruoyi.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.generator.domain.GenTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 业务 数据层
 * <p>
 * gen_table 的常规 CRUD 走 MyBatis-Plus 内置方法（BaseMapper），
 * 这里保留的方法都是无法用条件构造器表达的语句：information_schema 元数据查询、
 * gen_table 与 gen_table_column 的 LEFT JOIN 组装查询，以及 DDL 执行。
 *
 * @author 棠野·Lysander
 */
public interface GenTableMapper extends BaseMapper<GenTable> {
    /**
     * 查询业务列表（分页由 MyBatis-Plus 插件处理，page 传 null 则不分页）
     *
     * @param page     分页对象
     * @param genTable 业务信息
     * @return 业务集合
     */
    public List<GenTable> selectGenTableList(IPage<GenTable> page, @Param("genTable") GenTable genTable);

    /**
     * 查询据库列表
     *
     * @param page     分页对象（page 传 null 则不分页）
     * @param genTable 业务信息
     * @return 数据库表集合
     */
    public List<GenTable> selectDbTableList(IPage<GenTable> page, @Param("genTable") GenTable genTable);

    /**
     * 查询据库列表
     *
     * @param tableNames 表名称组
     * @return 数据库表集合
     */
    public List<GenTable> selectDbTableListByNames(String[] tableNames);

    /**
     * 查询所有表信息
     *
     * @return 表信息集合
     */
    public List<GenTable> selectGenTableAll();

    /**
     * 查询表ID业务信息
     *
     * @param id 业务ID
     * @return 业务信息
     */
    public GenTable selectGenTableById(Long id);

    /**
     * 查询表名称业务信息
     *
     * @param tableName 表名称
     * @return 业务信息
     */
    public GenTable selectGenTableByName(String tableName);

    /**
     * 创建表
     *
     * @param sql 表结构
     * @return 结果
     */
    public int createTable(String sql);
}
