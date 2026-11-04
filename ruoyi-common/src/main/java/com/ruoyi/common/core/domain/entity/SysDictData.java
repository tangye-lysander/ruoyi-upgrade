package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 字典数据表 sys_dict_data
 *
 * @author 棠野·Lysander
 */
@Schema(description = "字典数据对象")
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class SysDictData extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字典编码
     */
    @Excel(name = "字典编码", cellType = ColumnType.NUMERIC)
    @Schema(description = "字典数据编号")
    private Long dictCode;

    /**
     * 字典排序
     */
    @Excel(name = "字典排序", cellType = ColumnType.NUMERIC)
    @Schema(description = "显示顺序")
    private Long dictSort;

    /**
     * 字典标签
     */
    @Excel(name = "字典标签")
    @Schema(description = "字典标签（列表查询条件）")
    @NotBlank(message = "字典标签不能为空")
    @Size(min = 0, max = 100, message = "字典标签长度不能超过100个字符")
    private String dictLabel;

    /**
     * 字典键值
     */
    @Excel(name = "字典键值")
    @Schema(description = "字典键值")
    @NotBlank(message = "字典键值不能为空")
    @Size(min = 0, max = 100, message = "字典键值长度不能超过100个字符")
    private String dictValue;

    /**
     * 字典类型
     */
    @Excel(name = "字典类型")
    @Schema(description = "所属字典类型键值（列表查询条件）")
    @NotBlank(message = "字典类型不能为空")
    @Size(min = 0, max = 100, message = "字典类型长度不能超过100个字符")
    private String dictType;

    /**
     * 样式属性（其他样式扩展）
     */
    @Schema(description = "样式属性")
    @Size(min = 0, max = 100, message = "样式属性长度不能超过100个字符")
    private String cssClass;

    /**
     * 表格字典样式
     */
    @Schema(description = "表格回显样式（default/primary/success/info/warning/danger）")
    private String listClass;

    /**
     * 是否默认（Y是 N否）
     */
    @Excel(name = "是否默认", readConverterExp = "Y=是,N=否")
    @Schema(description = "是否默认（Y是 N否）")
    private String isDefault;

    /**
     * 状态（0正常 1停用）
     */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    @Schema(description = "状态（0正常 1停用，列表查询条件）")
    private String status;

    public boolean getDefault() {
        return UserConstants.YES.equals(this.isDefault);
    }
}
