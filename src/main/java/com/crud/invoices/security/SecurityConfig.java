package com.crud.invoices.security;

import com.crud.invoices.security.filter.CustomAuthorizationFilter;
import com.crud.invoices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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


    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfig(UserService userService, CustomAuthorizationFilter customAuthorizationFilter,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.customAuthorizationFilter = customAuthorizationFilter;
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
        http.authorizeRequests().antMatchers(   "/v1/invoice/register/**", "/v1/invoice/login/**",
                        "/v1/invoice/token/refresh/**", ("/v1/invoice/logout/**")).permitAll()
                .antMatchers(HttpMethod.OPTIONS,"/**").permitAll();
        //appUser
        http.authorizeRequests().antMatchers("/v1/invoice/getAppUserByUsername/**").permitAll();//hasAnyRole("USER","ADMIN")
        //invoice
        http.authorizeRequests().antMatchers("/v1/invoice/createInvoice/**").permitAll()//hasAnyRole("USER","ADMIN")
                .antMatchers("/v1/invoice/createInvoiceWithoutContractor/**").permitAll()//hasAnyRole("USER","ADMIN")
                .antMatchers("/v1/invoice/getInvoice/**").permitAll()//hasAnyRole("USER","ADMIN")
                .antMatchers("/v1/invoice/getInvoices/**").permitAll()//hasAnyRole("USER","ADMIN")
                .antMatchers("/v1/invoice/updateInvoice/**").permitAll()//hasAnyRole("USER","ADMIN")
                .antMatchers("/v1/invoice/deleteInvoice/**").permitAll()//hasAnyRole("ADMIN")
                .antMatchers("/v1/invoice/printer/generateInvoice/**").permitAll()//hasAnyRole("ADMIN")

                //contractor
                .antMatchers("/v1/contractor/getContractor/**").permitAll()//hasAnyRole("USER","ADMIN")
                .antMatchers("/v1/contractor/getContractorWithSpecifiedName/**").permitAll()//hasAnyRole("USER","ADMIN")
                .antMatchers("/v1/contractor/getContractorByName/**").permitAll()//hasAnyRole("USER","ADMIN")
                .antMatchers("/v1/gus/getContractorFromGus/**").permitAll()//hasAnyRole("USER","ADMIN")
                .antMatchers("/v1/contractor/getContractors/**").permitAll()//hasAnyRole("USER","ADMIN")

                .antMatchers("/v1/contractor/deleteContractor/**").permitAll()//hasAnyRole("USER","ADMIN")
                .antMatchers("/v1/contractor/updateContractor/**").permitAll()//hasAnyRole("USER","ADMIN")
                .antMatchers("/v1/contractor/createContractor/**").permitAll()//hasAnyRole("USER","ADMIN")
                //seller
                .antMatchers("/v1/seller/getSeller/**").permitAll()//hasAnyRole("USER","ADMIN")
                .antMatchers("/v1/seller/getSellerWithSpecifiedName/**").permitAll()//hasAnyRole("USER","ADMIN")
                .antMatchers("/v1/seller/getSellerByName/**").permitAll()//hasAnyRole("USER","ADMIN")
                .antMatchers("/v1/seller/getSellerByAppUser/**").permitAll()//hasAnyRole("USER","ADMIN")
                .antMatchers("/v1/seller/getSellerByVatIdentificationNumber/**").permitAll()
                .antMatchers("/v1/gus/getSellerFromGus/**").permitAll()
                .antMatchers("/v1/seller/deleteSeller/**").permitAll()//hasAnyRole("USER","ADMIN")
                .antMatchers("/v1/seller/updateSeller/**").permitAll()//hasAnyRole("USER","ADMIN")
                .antMatchers("/v1/seller/createSeller/**").permitAll()//hasAnyRole("USER","ADMIN")
                //item
                .antMatchers("/v1/item/createItem/**").permitAll()//hasAnyRole("USER","ADMIN")
                .antMatchers("/v1/item/updateItem/**").permitAll()//hasAnyRole("USER","ADMIN")
                .antMatchers("/v1/item/deleteItem/**").permitAll()//hasAnyRole("USER","ADMIN")
                .antMatchers("/v1/item/deleteItem/**").permitAll()//hasAnyRole("USER","ADMIN")
                .antMatchers("/v1/item/getItem/**").permitAll()//hasAnyRole("USER","ADMIN")
                .antMatchers("/v1/item/getItems/**").permitAll()//hasAnyRole("USER","ADMIN")
                .anyRequest().authenticated()

               .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class );
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*AppUser user = new AppUser("Anna", bCryptPasswordEncoder.encode("123")
        , "martyna.prawdzik@gmail.com", AppUserRole.USER);
        AppUser admin = new AppUser("Grzegorz", bCryptPasswordEncoder.encode("456"),
                "test-v5v1jt5rk@srv1.mail-tester.com",
        AppUserRole.ADMIN);
        */
        auth.userDetailsService(userService).passwordEncoder((bCryptPasswordEncoder));
        //userService.saveUser(user);
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
