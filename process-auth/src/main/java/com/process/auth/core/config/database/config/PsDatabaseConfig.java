package com.process.auth.core.config.database.config;

import com.process.common.database.utils.PsBatchService;
import com.process.common.database.utils.PsBatchServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.List;

/**
 * DB 配置
 *
 * @author Danfeng
 * @since 2018/12/7
 */
@Slf4j
@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties(DataSourceProperties.class)
@RequiredArgsConstructor
public class PsDatabaseConfig implements TransactionManagementConfigurer {

    private final DataSourceProperties dataSourceProperties;

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Primary
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(this.dataSource());
    }

    @Primary
    @Bean
    public SqlSessionFactory sqlSessionFactory(@Autowired(required = false) List<TypeHandler> typeHandlers, @Autowired(required = false) List<Interceptor> interceptors) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        if (typeHandlers != null && !typeHandlers.isEmpty()) {
            sessionFactoryBean.setTypeHandlers(typeHandlers.toArray(new TypeHandler[0]));
        }
        if (interceptors != null && !interceptors.isEmpty()) {
            sessionFactoryBean.setPlugins(interceptors.toArray(new Interceptor[0]));
        }
        sessionFactoryBean.setDataSource(this.dataSource());
        return sessionFactoryBean.getObject();
    }

    @Primary
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(final SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    @ConditionalOnMissingBean(PsBatchService.class)
    public PsBatchService psBatchService(final SqlSessionFactory sqlSessionFactory) {
        return new PsBatchServiceImpl(sqlSessionFactory);
    }

}
