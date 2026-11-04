package com.ruoyi.system.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户和岗位关联 sys_user_post
 *
 * @author 棠野·Lysander
 */
@Getter
@Setter
@ToString
public class SysUserPost {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 岗位ID
     */
    private Long postId;
}
