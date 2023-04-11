package shop.mtcoding.securityappex.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // 1. CSRF
        http.csrf().disable(); // postman 접근해야 함. - CSR 할 때만 !!

        // 2. Form 로그인 설정
        http.formLogin()
                .loginPage("/loginForm")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/login") // POST + x-www-form-urlencoded
//                .defaultSuccessUrl("/")
                .successHandler((request, response, authentication) -> {
                    System.out.println("디버그 : 로그인이 완료되었습니다.");
                    response.sendRedirect("/");
                })
                .failureHandler((request, response, exception) -> {
                    System.out.println("디버그 : 로그인 실패->" + exception.getMessage());
                });

        // 3. 인증, 권한 필터 설정
        http.authorizeRequests(
            (authorize)-> { authorize.antMatchers("/users/**").authenticated()
                    .antMatchers("/manager/**").access("hasRole('ADMIN') or hasRole('MANAGER')")
                    .antMatchers("/admin/**").access("hasRole('ADMIN')")
                    .anyRequest().permitAll();
        });



        return http.build();
    }
}
