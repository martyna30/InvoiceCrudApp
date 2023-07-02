package com.crud.invoices.security;

import com.crud.invoices.security.filter.CustomAuthorizationFilter;
import com.crud.invoices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@CrossOrigin
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private final UserService userService;

    @Autowired
    private final CustomAuthorizationFilter customAuthorizationFilter;
    //@Autowired
    //private final AuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfig(UserService userService, CustomAuthorizationFilter customAuthorizationFilter,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.customAuthorizationFilter = customAuthorizationFilter;
        //this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("http://localhost:8080/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowedOrigins("*")
                        .allowCredentials(true);
            };
        };
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.httpBasic();
        http.authorizeRequests().antMatchers("/v1/invoice/**").permitAll();
        http.authorizeRequests().antMatchers("/v1/invoice/createInvoice/**").permitAll()
                //.and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class );
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //User librarian = new User("Martyna",
        //bCryptPasswordEncoder.encode("123")
        // , "martyna.prawdzik@gmail.com", "ROLE_LIBRARIAN");


        //User admin = new User("Piotr",
        //   bCryptPasswordEncoder.encode("456"),"test-v5v1jt5rk@srv1.mail-tester.com",
        //   "ROLE_ADMIN");


        auth.userDetailsService(userService).passwordEncoder((bCryptPasswordEncoder));
        //userService.saveUser(librarian);
        //userService.saveUser(admin);

        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder((bCryptPasswordEncoder));
        provider.setUserDetailsService(userService);
        return provider;
    }


    /*@Bean
    public CORSFilter corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*", "http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("content-type", "Access-Control-Allow-Origin", "*",
                "Access-Control-Allow-Headers","Access-Control-Request-Method",
                "Access-Control-Request-Headers","Origin","Cache-Control", "Content-Type", "Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
       return new CORSFilter(source);
    }

    @Bean
    public CORSFilter corsFilter() {
        CorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedMethod(HttpMethod.DELETE);
        config.addAllowedMethod(HttpMethod.GET);
        config.addAllowedMethod(HttpMethod.OPTIONS);
        config.addAllowedMethod(HttpMethod.PUT);
        config.addAllowedMethod(HttpMethod.POST);
        ((UrlBasedCorsConfigurationSource) source).registerCorsConfiguration("/**", config);
        return new CORSFilter(source);
    }*/


}
