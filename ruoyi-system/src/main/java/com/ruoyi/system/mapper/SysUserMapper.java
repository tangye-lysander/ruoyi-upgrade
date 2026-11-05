package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ruoyi.common.core.domain.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 用户表 数据层
 *
 * @author 棠野·Lysander
 */
public interface SysUserMapper {
    /**
     * 根据条件查询用户列表（分页由 MyBatis-Plus 插件处理，page 传 null 则不分页）
     *
     * @param page   分页对象
     * @param sysUser 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUserPage(IPage<SysUser> page, @Param("user") SysUser sysUser);

    /**
     * 根据条件查询已配用户角色列表（分页由 MyBatis-Plus 插件处理，page 传 null 则不分页）
     *
     * @param page 分页对象
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectAllocatedPage(IPage<SysUser> page, @Param("user") SysUser user);

    /**
     * 根据条件查询未分配用户角色列表（分页由 MyBatis-Plus 插件处理，page 传 null 则不分页）
     *
     * @param page 分页对象
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUnallocatedPage(IPage<SysUser> page, @Param("user") SysUser user);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByUserName(String userName);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Long userId);

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(SysUser user);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(SysUser user);

    /**
     * 修改用户头像
     *
     * @param userId 用户ID
     * @param avatar 头像地址
     * @return 结果
     */
    public int updateUserAvatar(@Param("userId") Long userId, @Param("avatar") String avatar);

    /**
     * 修改用户状态
     *
     * @param userId 用户ID
     * @param status 状态
     * @return 结果
     */
    public int updateUserStatus(@Param("userId") Long userId, @Param("status") String status);

    /**
     * 更新用户登录信息（IP和登录时间）
     *
     * @param userId    用户ID
     * @param loginIp   登录IP地址
     * @param loginDate 登录时间
     * @return 结果
     */
    public int updateLoginInfo(@Param("userId") Long userId, @Param("loginIp") String loginIp, @Param("loginDate") Date loginDate);

    /**
     * 重置用户密码
     *
     * @param userId   用户ID
     * @param password 密码
     * @return 结果
     */
    public int resetUserPwd(@Param("userId") Long userId, @Param("password") String password);

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserById(Long userId);

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    public int deleteUserByIds(Long[] userIds);

    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    public SysUser checkUserNameUnique(String userName);

    /**
     * 校验手机号码是否唯一
     *
     * @param phonenumber 手机号码
     * @return 结果
     */
    public SysUser checkPhoneUnique(String phonenumber);

    /**
     * 校验email是否唯一
     *
     * @param email 用户邮箱
     * @return 结果
     */
    public SysUser checkEmailUnique(String email);
}
