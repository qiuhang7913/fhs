package com.self.framework.ucenter.config;

import com.self.framework.spring.extend.security.Md5PasswordEncoder;
import com.self.framework.ucenter.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启方法权限控制
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder  auth) throws  Exception{
		auth.userDetailsService(customUserDetailsService())
				.passwordEncoder(passwordEncoder());
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// 所有用户均可访问的资源
				.antMatchers( "/favicon.ico",
								"/plugin/**",
								"/css/**",
								"/img/**",
								"/js/**",
								"/captcha.jpg",
								"/login",
								"/doLogin").permitAll()
				// 任何尚未匹配的URL只需要验证用户即可访问
				.anyRequest().authenticated()
				.and()
				.formLogin().loginPage("/login").successForwardUrl("/index").failureForwardUrl("/login?error=1")
				.and()
				//权限拒绝的页面
				.exceptionHandling().accessDeniedPage("/403");

		http.logout().logoutSuccessUrl("/login");
	}

	/**
	 * 设置用户密码的加密方式
	 * @return
	 */
	@Bean
	public Md5PasswordEncoder passwordEncoder() {
		return new Md5PasswordEncoder();

	}

	/**
	 * 自定义UserDetailsService，授权
	 * @return
	 */
	@Bean
	public CustomUserDetailsService customUserDetailsService(){
		return new CustomUserDetailsService();
	}

	/**
	 * AuthenticationManager
	 * @return
	 * @throws Exception
	 */
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
