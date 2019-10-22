package com.process.zuul.core.config.database.tenant.interceptor;


import cn.ocoop.framework.sql.TC;
import cn.ocoop.framework.sql.tenant.MySqlDeleteTenantOptimizer;
import cn.ocoop.framework.sql.tenant.MySqlInsertTenantOptimizer;
import cn.ocoop.framework.sql.tenant.MySqlUpdateTenantOptimizer;
import com.process.zuul.core.config.database.tenant.optimizer.MySqlSelectTenantOptimizer;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author Lynn
 * @since 2019-09-03
 */
@Slf4j
@Component
@Intercepts(
        {
                @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}),
        }
)
@RefreshScope
@EnableConfigurationProperties(TenantProperties.class)
@RequiredArgsConstructor
public class TenantIntercept implements Interceptor {

    private final TenantProperties tenantProperties;

    public Object realTarget(Object target) {
        if (Proxy.isProxyClass(target.getClass())) {
            MetaObject metaObject = SystemMetaObject.forObject(target);
            return realTarget(metaObject.getValue("h.target"));
        }
        return target;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (!tenantProperties.isEnabled()) {
            log.info("tenant is not enabled");
            return invocation.proceed();
        }
        if (TC.get() == null) {
            TC.set(tenantProperties.getTenantId());
        }
        try {
            StatementHandler statementHandler = (StatementHandler) realTarget(invocation.getTarget());
            MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
            MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
            BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
            String optimizedSql = boundSql.getSql();
            String tenantColumn = tenantProperties.getTenantColumn();
            String tenantColumnType = tenantProperties.getTenantColumnType();
            if (tenantProperties.isTenantInsertEnabled() && SqlCommandType.INSERT.equals(mappedStatement.getSqlCommandType())) {
                optimizedSql = new MySqlInsertTenantOptimizer(tenantColumn, tenantColumnType).optimize(optimizedSql);
            } else if (tenantProperties.isTenantDeleteEnabled() && SqlCommandType.DELETE.equals(mappedStatement.getSqlCommandType())) {
                optimizedSql = new MySqlDeleteTenantOptimizer(tenantColumn, tenantColumnType).optimize(optimizedSql);
            } else if (tenantProperties.isTenantUpdateEnabled() && SqlCommandType.UPDATE.equals(mappedStatement.getSqlCommandType())) {
                optimizedSql = new MySqlUpdateTenantOptimizer(tenantColumn, tenantColumnType).optimize(optimizedSql);
            } else if (tenantProperties.isTenantSelectEnabled() && SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
                optimizedSql = new MySqlSelectTenantOptimizer(tenantColumn, tenantColumnType).optimize(optimizedSql);
            }
            metaObject.setValue("delegate.boundSql.sql", optimizedSql);
        } catch (Exception e) {
            throw new SQLException(e.getLocalizedMessage());
        } finally {
            TC.clear();
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

}
