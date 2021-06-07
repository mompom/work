package net.ib.paperless.spring.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan(basePackages = "net.ib.paperless.spring.repository_meritz", sqlSessionFactoryRef="sqlSession_meritz")
@EnableTransactionManagement
public class DatabaseConfig2 {

	@Bean(name="dataSource2")
	@ConfigurationProperties(prefix = "spring.datasource2")
	public DataSource dataSource2() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name="sessionFactory2")
	public SqlSessionFactory sessionFactory2(@Qualifier("dataSource2") DataSource dataSource2, ApplicationContext applicationContext)
			throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource2);
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:dao_meritz/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}

	@Bean(name="sqlSessionTemplate2")
	public SqlSessionTemplate sqlSessionTemplate2(@Qualifier("sessionFactory2") SqlSessionFactory sqlSessionFactory2) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory2);
	}
}