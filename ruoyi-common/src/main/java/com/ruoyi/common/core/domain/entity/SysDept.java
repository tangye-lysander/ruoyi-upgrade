package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门表 sys_dept
 *
 * @author 棠野·Lysander
 */
@Schema(description = "部门对象")
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class SysDept extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    @Schema(description = "部门编号")
    private Long deptId;

    /**
     * 父部门ID
     */
    @Schema(description = "父部门编号")
    private Long parentId;

    /**
     * 祖级列表
     */
    @Schema(description = "祖级部门编号列表（逗号分隔）")
    private String ancestors;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称（列表查询条件）")
    @NotBlank(message = "部门名称不能为空")
    @Size(min = 0, max = 30, message = "部门名称长度不能超过30个字符")
    private String deptName;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    @NotNull(message = "显示顺序不能为空")
    private Integer orderNum;

    /**
     * 负责人
     */
    @Schema(description = "负责人")
    private String leader;

    /**
     * 联系电话
     */
    @Schema(description = "联系电话")
    @Size(min = 0, max = 11, message = "联系电话长度不能超过11个字符")
    private String phone;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    private String email;

    /**
     * 部门状态:0正常,1停用
     */
    @Schema(description = "状态（0正常 1停用，列表查询条件）")
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @Schema(description = "删除标志（0存在 2删除）")
    private String delFlag;

    /**
     * 父部门名称
     */
    @Schema(description = "父部门名称")
    private String parentName;

    /**
     * 子部门
     */
    @Schema(description = "子部门列表")
    private List<SysDept> children = new ArrayList<SysDept>();
}
