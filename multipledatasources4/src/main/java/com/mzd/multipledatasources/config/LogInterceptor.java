package com.mzd.multipledatasources.config;

import com.mzd.multipledatasources.datasource.DataSourceType;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * @author zhouyue01
 * @date 2021/8/31
 * 1. 打印mysql完整的执行语句
 * 2. 打印mysql语句执行时间
 * 这里我们拦截Executor里面的query和update方法
 */
@Intercepts({
        @Signature(
                method = "query",
                type = Executor.class,
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
        ),//MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class
        @Signature(
                method = "query",
                type = Executor.class,
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}
        )
})
@Component
public class LogInterceptor implements Interceptor {

    /**
     * 是否显示语句的执行时间
     */
    public static final String PROPERTIES_KEY_ENABLE_EXECUTOR_TIME = "enableExecutorTIme";
    public static final String ENABLE_EXECUTOR_TIME = "0"; // 显示

    private boolean enableExecutorTime = false;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 获取执行方法的MappedStatement参数,不管是Executor的query方法还是update方法，第一个参数都是MappedStatement
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        if (mappedStatement.getSqlCommandType().equals(SqlCommandType.SELECT)) {
            DataSourceType.setDataBaseType((DataSourceType.DataBaseType.TEST02));
        }
        Object result;
        try {
            result = invocation.proceed();
        } finally {
            DataSourceType.clearDataBaseType();
        }
        return result;
    }

    /**
     * 通过该方法决定要返回的对象是目标对象还是对应的代理
     * 不要想的太复杂，一般就两种情况：
     * <p>
     * 1. return target;  直接返回目标对象，相当于当前Interceptor没起作用，不会调用上面的intercept()方法
     * 2. return Plugin.wrap(target, this);  返回代理对象，会调用上面的intercept()方法
     *
     * @param target 目标对象
     * @return 目标对象或者代理对象
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 用于获取在Configuration初始化当前的Interceptor时时候设置的一些参数
     *
     * @param properties Properties参数
     */
    @Override
    public void setProperties(Properties properties) {
        if (properties != null) {
            String executorTImeValue = properties.getProperty(PROPERTIES_KEY_ENABLE_EXECUTOR_TIME);
            if (executorTImeValue != null) {
                enableExecutorTime = executorTImeValue.equals(ENABLE_EXECUTOR_TIME);
            }
        }
    }


}
