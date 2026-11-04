package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 系统访问记录表 sys_logininfor
 *
 * @author 棠野·Lysander
 */
@Schema(description = "登录日志对象")
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class SysLogininfor extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Excel(name = "序号", cellType = ColumnType.NUMERIC)
    @Schema(description = "访问记录编号")
    private Long infoId;

    /**
     * 用户账号
     */
    @Excel(name = "用户账号")
    @Schema(description = "登录账号（列表查询条件）")
    private String userName;

    /**
     * 登录状态 0成功 1失败
     */
    @Excel(name = "登录状态", readConverterExp = "0=成功,1=失败")
    @Schema(description = "登录状态（0成功 1失败，列表查询条件）")
    private String status;

    /**
     * 登录IP地址
     */
    @Excel(name = "登录地址")
    @Schema(description = "登录 IP 地址（列表查询条件）")
    private String ipaddr;

    /**
     * 登录地点
     */
    @Excel(name = "登录地点")
    @Schema(description = "登录地点")
    private String loginLocation;

    /**
     * 浏览器类型
     */
    @Excel(name = "浏览器")
    @Schema(description = "浏览器类型")
    private String browser;

    /**
     * 操作系统
     */
    @Excel(name = "操作系统")
    @Schema(description = "操作系统")
    private String os;

    /**
     * 提示消息
     */
    @Excel(name = "提示消息")
    @Schema(description = "提示消息")
    private String msg;

    /**
     * 访问时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "访问时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "访问时间")
    private Date loginTime;
}
