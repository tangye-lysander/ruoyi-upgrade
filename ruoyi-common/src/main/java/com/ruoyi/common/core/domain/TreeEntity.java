package com.ruoyi.common.core.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Tree基类
 *
 * @author 棠野·Lysander
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class TreeEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 父菜单名称
     */
    private String parentName;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 子部门
     */
    private List<?> children = new ArrayList<>();
}
