package com.ruoyi.system.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 公告已读记录表 sys_notice_read
 *
 * @author 棠野·Lysander
 */
@Getter
@Setter
@ToString
public class SysNoticeRead {
    /**
     * 主键
     */
    private Long readId;

    /**
     * 公告ID
     */
    private Long noticeId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 阅读时间
     */
    private Date readTime;
}
