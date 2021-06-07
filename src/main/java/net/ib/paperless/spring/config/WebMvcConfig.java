package net.ib.paperless.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import net.ib.paperless.spring.support.OpenApiUserSessionInterceptor;
import net.ib.paperless.spring.support.UserSessionInterceptor;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	@Autowired
	private UserSessionInterceptor userSessionInterceptor;
	
	//작엄 내용 : open api 용 인터셉터를 따로 만들엇음. 작업자 : 김범래, 작업일 : 2017.06.12
	@Autowired
	private OpenApiUserSessionInterceptor openApiUserSessionInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		
		registry.addInterceptor(userSessionInterceptor)
		//.excludePathPatterns("/**/admin/**")
		//.excludePathPatterns("/**/admin_mng/**")
		.excludePathPatterns("/**/open_api/**") //작엄 내용 : 기존 인터셉터에 제외 로직.  작업자 : 김범래, 작업일 : 2017.06.12
		.excludePathPatterns("/**/open_api/login") //작엄 내용 : 기존 인터셉터에 제외 로직.  작업자 : 김범래, 작업일 : 2017.06.12
		.excludePathPatterns("/open_api/error/token") //작엄 내용 : 에러 페이지(json)  작업자 : 김범래, 작업일 : 2017.06.20
		.excludePathPatterns("/open_api/error/loanid") //작엄 내용 : 에러 페이지(json)  작업자 : 김범래, 작업일 : 2017.06.20
		.excludePathPatterns("/**/stats/**")
		.excludePathPatterns("/**/status/**")
		.excludePathPatterns("/**/user_mng/**")
		.excludePathPatterns("/**/api/checkLogin")
		.excludePathPatterns("/login")
		.excludePathPatterns("/login_process")
		.excludePathPatterns("/");
		
		//작엄 내용 : open api 용 인터셉터를 따로 만들엇음. 작업자 : 김범래, 작업일 : 2017.06.12
		registry.addInterceptor(openApiUserSessionInterceptor)
		.addPathPatterns("/**/open_api/**") 
		.addPathPatterns("/**/open_api/login")
		.excludePathPatterns("/open_api/error/token") //작엄 내용 : 에러 페이지(json)  작업자 : 김범래, 작업일 : 2017.06.20
		.excludePathPatterns("/open_api/error/loanid") //작엄 내용 : 에러 페이지(json)  작업자 : 김범래, 작업일 : 2017.06.20
		;
		
	}
	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}
	
	@Bean
	public TilesConfigurer tilesConfigurer() {
		final TilesConfigurer configurer = new TilesConfigurer();
		configurer.setDefinitions("WEB-INF/views/tiles.xml");
		configurer.setCheckRefresh(true);
		return configurer;
	}
	
	@Bean
	public TilesViewResolver tilesViewResolver() {
		final TilesViewResolver resolver = new TilesViewResolver();
		resolver.setViewClass(TilesView.class);
		return resolver;
	}
	@Bean
	public ViewResolver getViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
}