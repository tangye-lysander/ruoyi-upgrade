package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 参数配置表 sys_config
 *
 * @author 棠野·Lysander
 */
@Schema(description = "参数配置对象")
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class SysConfig extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 参数主键
     */
    @Excel(name = "参数主键", cellType = ColumnType.NUMERIC)
    @Schema(description = "参数编号")
    private Long configId;

    /**
     * 参数名称
     */
    @Excel(name = "参数名称")
    @Schema(description = "参数名称（列表查询条件）")
    @NotBlank(message = "参数名称不能为空")
    @Size(min = 0, max = 100, message = "参数名称不能超过100个字符")
    private String configName;

    /**
     * 参数键名
     */
    @Excel(name = "参数键名")
    @Schema(description = "参数键名（列表查询条件）")
    @NotBlank(message = "参数键名长度不能为空")
    @Size(min = 0, max = 100, message = "参数键名长度不能超过100个字符")
    private String configKey;

    /**
     * 参数键值
     */
    @Excel(name = "参数键值")
    @Schema(description = "参数键值")
    @NotBlank(message = "参数键值不能为空")
    @Size(min = 0, max = 500, message = "参数键值长度不能超过500个字符")
    private String configValue;

    /**
     * 系统内置（Y是 N否）
     */
    @Excel(name = "系统内置", readConverterExp = "Y=是,N=否")
    @Schema(description = "系统内置（Y是 N否）")
    private String configType;
}
