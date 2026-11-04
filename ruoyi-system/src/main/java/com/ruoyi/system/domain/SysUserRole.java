package com.ruoyi.system.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户和角色关联 sys_user_role
 *
 * @author 棠野·Lysander
 */
@Getter
@Setter
@ToString
public class SysUserRole {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;
}
