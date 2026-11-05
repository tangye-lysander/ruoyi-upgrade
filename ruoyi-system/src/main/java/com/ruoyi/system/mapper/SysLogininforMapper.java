package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.system.domain.SysLogininfor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统访问日志情况信息 数据层
 *
 * @author 棠野·Lysander
 */
public interface SysLogininforMapper {
    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    public void insertLogininfor(SysLogininfor logininfor);

    /**
     * 查询系统登录日志集合（分页由 MyBatis-Plus 插件处理，page 传 null 则不分页）
     *
     * @param page       分页对象
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    public List<SysLogininfor> selectLogininforPage(IPage<SysLogininfor> page, @Param("logininfor") SysLogininfor logininfor);

    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return 结果
     */
    public int deleteLogininforByIds(Long[] infoIds);

    /**
     * 清空系统登录日志
     *
     * @return 结果
     */
    public int cleanLogininfor();
}
