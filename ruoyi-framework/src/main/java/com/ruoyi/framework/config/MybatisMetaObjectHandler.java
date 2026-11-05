package com.ruoyi.framework.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * MyBatis-Plus 字段自动填充处理器
 * <p>
 * 仅负责时间类字段的填充，语义等价于原先各 Mapper XML 中手写的 sysdate()：
 * 插入时补齐 createTime / updateTime，更新时补齐 updateTime。
 * 创建者、更新者（createBy / updateBy）仍由业务 Service 显式赋值，此处不介入，
 * 避免定时任务等无 HTTP 上下文的线程取不到登录用户。
 * <p>
 * 注意：自动填充只对走 MyBatis-Plus 内置方法的 SQL 生效；
 * quartz 调度表等保留自定义 XML 语句的映射不经过这里，其时间列仍由 sysdate() 提供。
 *
 * @author 棠野·Lysander
 */
@Component
public class MybatisMetaObjectHandler implements MetaObjectHandler {

    /**
     * 创建时间属性名
     */
    private static final String CREATE_TIME = "createTime";

    /**
     * 更新时间属性名
     */
    private static final String UPDATE_TIME = "updateTime";

    /**
     * 插入填充：字段为空才填，保持与原 XML「显式传值则用传值」一致的行为
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Date now = new Date();
        strictInsertFill(metaObject, CREATE_TIME, Date.class, now);
        strictInsertFill(metaObject, UPDATE_TIME, Date.class, now);
    }

    /**
     * 更新填充：仅补齐更新时间
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        strictUpdateFill(metaObject, UPDATE_TIME, Date.class, new Date());
    }
}
