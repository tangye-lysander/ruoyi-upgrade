package com.ruoyi.system.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 角色和菜单关联 sys_role_menu
 *
 * @author 棠野·Lysander
 */
@Getter
@Setter
@ToString
public class SysRoleMenu {
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单ID
     */
    private Long menuId;
}
