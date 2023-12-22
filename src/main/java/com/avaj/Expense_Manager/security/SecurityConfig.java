//package com.avaj.Expense_Manager.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;
//import org.springframework.security.provisioning.UserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//import javax.sql.DataSource;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@Configuration
//public class SecurityConfig {
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource){
//        JdbcUserDetailsManager theUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//        theUserDetailsManager
//                .setUsersByUsernameQuery("select username,password,enabled from loginDetails where username=?");
//        theUserDetailsManager
//                .setAuthoritiesByUsernameQuery("select role from roles where username=?");
//
//        return theUserDetailsManager;
//    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http.authorizeHttpRequests(configurer->
//                configurer
//                        .requestMatchers(HttpMethod.GET,"/") .hasAnyRole("USER","ADMIN")
//                        .requestMatchers(HttpMethod.DELETE,"/user/delete" ) .hasRole("ADMIN")
//                        .anyRequest().authenticated());
////                        .formLogin(form->form.loginPage("/createUser").loginProcessingUrl("/authenticateTheUser").permitAll()) .logout(logout->logout.permitAll()
//        return http.build();
//    }
//
//}
//
////{bcrypt}$2a$12$jq2C5gr3gNPTWeDYVyloDu/3mc1Ho1wTnwPSi0PLEV5qVhgtHGRF6