package com.cos.blog2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
@ Configuration //빈등록 (IoC 관리)
@EnableWebSecurity //시큐리티 필터가 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻.
public class SecurityConfig{
	
	@Bean // Ioc가 된다
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable()  //csrf 토큰 비활성화 (테스트시 걸어두는 게 좋음)
			.authorizeRequests() //리퀘스트가 들어오면
			.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**") //  /auth/**로 경로가 들어오는 것들은 전부 허용
			.permitAll()
			.anyRequest() //이게 아닌 다른 요청들은
			.authenticated() //인증이 되어야한다.
		.and()
			.formLogin()
			.loginPage("/auth/loginForm"); //인증이 필요한 것들은 자동으로 /auth/loginForm 페이지로 이동한다.
		return http.build();
		
	}
}
