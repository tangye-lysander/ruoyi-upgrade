package com.ruoyi.quartz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.quartz.domain.SysJob;

import java.util.List;

/**
 * 调度任务信息 数据层
 * <p>
 * sys_job 的物理主键是 (job_id, job_name, job_group) 复合主键，MyBatis-Plus 只支持单列主键，
 * 因此本表的全部语句仍走 XML 自定义 SQL；继承 BaseMapper 仅为统一数据层形态并提供
 * selectCount / Wrapper 查询等附加能力。
 *
 * @author 棠野·Lysander
 */
public interface SysJobMapper extends BaseMapper<SysJob> {
    /**
     * 查询调度任务日志集合
     *
     * @param job 调度信息
     * @return 操作日志集合
     */
    public List<SysJob> selectJobList(SysJob job);

    /**
     * 查询所有调度任务
     *
     * @return 调度任务列表
     */
    public List<SysJob> selectJobAll();

    /**
     * 通过调度ID查询调度任务信息
     *
     * @param jobId 调度ID
     * @return 角色对象信息
     */
    public SysJob selectJobById(Long jobId);

    /**
     * 通过调度ID删除调度任务信息
     *
     * @param jobId 调度ID
     * @return 结果
     */
    public int deleteJobById(Long jobId);

    /**
     * 批量删除调度任务信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteJobByIds(Long[] ids);

    /**
     * 修改调度任务信息
     *
     * @param job 调度任务信息
     * @return 结果
     */
    public int updateJob(SysJob job);

    /**
     * 新增调度任务信息
     *
     * @param job 调度任务信息
     * @return 结果
     */
    public int insertJob(SysJob job);
}
