package com.ruoyi.system.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 角色和部门关联 sys_role_dept
 *
 * @author 棠野·Lysander
 */
@Getter
@Setter
@ToString
public class SysRoleDept {
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 部门ID
     */
    private Long deptId;
}
