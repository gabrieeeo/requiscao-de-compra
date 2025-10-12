package me.gabriel.requisicaodecompra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import me.gabriel.requisicaodecompra.service.user.UsuarioDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private UsuarioDetailsService usuarioDetailsService;
    

    public SecurityConfig(UsuarioDetailsService usuarioDetailsService) {
        this.usuarioDetailsService = usuarioDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UsuarioDetailsService usuarioDetailsService() {
        return this.usuarioDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
        //---------Autorizações----------
        .authorizeHttpRequests(auth -> auth

        //Recursos público/gerais
        .requestMatchers("/css/**", "/js/**", "").permitAll()
        .requestMatchers("/login/**", "/error/**").permitAll()
        //Apenas admnistradores do sistema (ADMIN)
        .requestMatchers("/usuarios/**").hasRole("ADMIN")
        //(COMPRADOR e ADMIN)
        .requestMatchers("/requisicoes", "/dashboard/**").hasAnyRole("ADMIN", "COMPRADOR")
        //Todos os usuários autenticados (ADMIN, COMPRADOR e USUARIO)
        .requestMatchers("/requisicoes/criar/**", "/requisicoes/detalhes/**").authenticated()
        //Qualquer outra requisição, deve estar autenticada
        .anyRequest().authenticated()
        )
        //---------Login e Logout----------
        .formLogin(form -> form
        .loginPage("/login")
        .permitAll()
        )
        .logout(logout -> logout
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login?logout=true")
        .deleteCookies("JSESSIONID")
        .invalidateHttpSession(true)
        .clearAuthentication(true)
        .permitAll()
        );
        

        return http.build();
    }
}
