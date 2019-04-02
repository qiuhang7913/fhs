package com.self.framework.config;

import com.self.framework.http.CsrfSecurityRequestMatcher;
import com.self.framework.spring.extend.security.Md5PasswordEncoder;
import com.self.framework.ucenter.service.CustomUserDetailsService;
import com.self.framework.ucenter.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启方法权限控制
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private MenuService menuService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder  auth) throws  Exception{
//		auth.userDetailsService(customUserDetailsService())
//				.passwordEncoder(passwordEncoder());
		auth.userDetailsService(customUserDetailsService());
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		//解决静态资源被拦截的问题
		web.ignoring().antMatchers("/api/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		RequestMatcher requestMatcher = new CsrfSecurityRequestMatcher();
//		((CsrfSecurityRequestMatcher) requestMatcher).setAllowRes(Arrays.asList("/druid/*"));
//		http.csrf().requireCsrfProtectionMatcher(requestMatcher);
		http.csrf().ignoringAntMatchers("/druid/*");
		http.csrf().ignoringAntMatchers("/api/*");
		http.authorizeRequests()
				// 所有用户均可访问的资源
				.antMatchers( "/favicon.ico",
								"/plugin/**",
								"/api/**",
								"/css/**",
								"/img/**",
								"/common/**",
								"/js/**",
								"/captcha.jpg",
								"/login",
								"/test/**",
								"/druid/**",
								"/doLogin").permitAll()
				// 任何尚未匹配的URL只需要验证用户即可访问
				.anyRequest().authenticated()
				.and()
				.formLogin().loginPage("/login").successForwardUrl("/index").failureForwardUrl("/login?error=0")
				.and()
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout", RequestMethod.GET.name())).logoutSuccessUrl("/login").invalidateHttpSession(true)
				.and()
				.headers().frameOptions().disable()
				.and()
				//权限拒绝的页面
				.exceptionHandling().accessDeniedPage("/403");

		//http.logout().logoutSuccessUrl("/login").invalidateHttpSession(true);
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(customUserDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder()); // 设置密码加密方式
		return authenticationProvider;
	}

	/**
	 * 设置用户密码的加密方式
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();   // 使用 BCrypt 加密
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

	public static void main(String[] args) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		// 加密
		String encodedPassword = passwordEncoder.encode("111111");
		System.out.println(encodedPassword);
	}
}
