package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.system.domain.SysOperLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 操作日志 数据层
 *
 * @author 棠野·Lysander
 */
public interface SysOperLogMapper {
    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    public void insertOperlog(SysOperLog operLog);

    /**
     * 查询系统操作日志集合（分页由 MyBatis-Plus 插件处理，page 传 null 则不分页）
     *
     * @param page    分页对象
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    public List<SysOperLog> selectOperLogPage(IPage<SysOperLog> page, @Param("operLog") SysOperLog operLog);

    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    public int deleteOperLogByIds(Long[] operIds);

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    public SysOperLog selectOperLogById(Long operId);

    /**
     * 清空操作日志
     */
    public void cleanOperLog();
}
