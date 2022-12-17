package com.cos.blog2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.cos.blog2.config.auth.PrincipalDetailService;
import com.cos.blog2.handler.LoginFailHandler;

//빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
@ Configuration //빈등록 (IoC 관리)
@EnableWebSecurity //시큐리티 필터가 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻.
public class SecurityConfig{
	
	@Autowired     // DI 한다.
	private PrincipalDetailService principalDetailService;
	
	@Bean
    public LoginFailHandler loginFailHandler(){
        return new LoginFailHandler();
    }
	
//	@Bean
//	public AuthenticationManager authenticationManagerBean() throws Exception{
//		return authenticationManagerBean();
//	}

	@Bean // Ioc가 된다
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	//시큐리티가 대신 로그인해주는데 password를 가로채기를 하는데
	// 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
	// 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음.
//	@Bean
//    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder builder) throws Exception {
//        return builder.userDetailsService(principalDetailService).passwordEncoder(encodePWD()).and().build();
//    }
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
	
	@Bean
	 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable()  //csrf 토큰 비활성화 (테스트시 걸어두는 게 좋음)
			.authorizeRequests() //리퀘스트가 들어오면
			.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/images/**") //  /auth/**로 경로가 들어오는 것들은 전부 허용
			.permitAll()
			.anyRequest() //이게 아닌 다른 요청들은
			.authenticated() //인증이 되어야한다.
		.and()
			.formLogin()
			.loginPage("/auth/loginForm") //인증이 필요한 것들은 자동으로 /auth/loginForm 페이지로 이동한다.
			.loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인 해준다.
			.defaultSuccessUrl("/") // 로그인에 성공하면 http://localhost:8000/으로 이동
			.failureHandler(loginFailHandler()); // 로그인 실패 핸들러
		return http.build();	
	}
}
