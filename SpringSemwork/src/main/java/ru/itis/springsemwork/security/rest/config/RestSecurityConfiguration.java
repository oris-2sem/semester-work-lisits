//package ru.itis.springsemwork.security.rest.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import ru.itis.springsemwork.security.BothSecurityConfiguration;
//import ru.itis.springsemwork.security.rest.filters.JwtAuthenticationFilter;
//import ru.itis.springsemwork.security.rest.filters.JwtAuthorizationFilter;
//
//@Configuration
//@Order(2)
//@RequiredArgsConstructor
//public class RestSecurityConfiguration extends BothSecurityConfiguration {
//
//    private final PasswordEncoder passwordEncoder;
//
//    private final UserDetailsService userAccountDetailsService;
//
//    private final AuthenticationProvider refreshTokenAuthenticationProvider;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
//                                                   JwtAuthenticationFilter jwtAuthenticationFilter,
//                                                   JwtAuthorizationFilter jwtAuthorizationFilter) throws Exception {
//
////        httpSecurity.csrf().disable();
////        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        httpSecurity.authorizeRequests()
//                .antMatchers("/itemsList/**").hasAuthority("ADMIN")
//                .antMatchers("/swagger-ui.html/**").permitAll();
//
//        httpSecurity.addFilter(jwtAuthenticationFilter);
//        httpSecurity.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return httpSecurity.build();
//    }
//
//    @Autowired
//    public void bindUserDetailsServiceAndPasswordEncoder(AuthenticationManagerBuilder builder) throws Exception {
//        builder.authenticationProvider(refreshTokenAuthenticationProvider);
//        builder.userDetailsService(userAccountDetailsService).passwordEncoder(passwordEncoder);
//    }
//
//}
