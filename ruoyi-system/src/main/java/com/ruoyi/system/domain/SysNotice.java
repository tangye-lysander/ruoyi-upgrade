package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.xss.Xss;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 通知公告表 sys_notice
 *
 * @author 棠野·Lysander
 */
@Schema(description = "通知公告对象")
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class SysNotice extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 公告ID
     */
    @Schema(description = "公告编号")
    private Long noticeId;

    /**
     * 公告标题
     */
    @Schema(description = "公告标题（列表查询条件）")
    @Xss(message = "公告标题不能包含脚本字符")
    @NotBlank(message = "公告标题不能为空")
    @Size(min = 0, max = 50, message = "公告标题不能超过50个字符")
    private String noticeTitle;

    /**
     * 公告类型（1通知 2公告）
     */
    @Schema(description = "公告类型（1通知 2公告，列表查询条件）")
    private String noticeType;

    /**
     * 公告内容
     */
    @Schema(description = "公告内容（富文本）")
    private String noticeContent;

    /**
     * 公告状态（0正常 1关闭）
     */
    @Schema(description = "状态（0正常 1关闭，列表查询条件）")
    private String status;

    /**
     * 是否已读（Lombok 对 boolean isRead 字段会生成 isRead()/setRead()，
     * 与 MyBatis/Jackson 依赖的 getIsRead()/setIsRead() 不一致，故排除并保留手写方法）
     */
    @Schema(description = "当前用户是否已读")
    @JsonProperty("isRead")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private boolean isRead;

    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }
}
