/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eggNews.news;

import com.eggNews.news.servicios.NoticiaServicio;
import com.eggNews.news.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWeb extends WebSecurityConfigurerAdapter {
    
    @Autowired
    public UsuarioServicio us;
    
    
    @Autowired
    public void ConfigureGlobal( AuthenticationManagerBuilder auth ) throws Exception{
        auth.userDetailsService(us) // cuando se registra el usuario, vamos a autenticarlo
                .passwordEncoder( new BCryptPasswordEncoder() ); // desp le pasamos el encriptador de contraseña
    }
    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/admin/*").hasRole("ADMIN")// SOLO PODRAN ENTRAR A /ADMIN LOS USUARIOS QUE CONTENGAN EL ROL DE ADMIN
                    .antMatchers("/css/*","/js/*","/img/*","/**")
                    .permitAll()
                .and().formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/logincheck") // para procesar este inicio de sesion vamos a utilizar
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/")   // si hay un error es a donde se va a dirigir 
                    .permitAll()
                .and().logout()// la salida de nuestro sistema
                    .logoutUrl("/logout")  // cuando un usuario ingrese a una url, se cierre la sesion
                    .logoutSuccessUrl("/login") // si es exitoso se va a esta url
                    .permitAll()
                .and().csrf()
                    .disable();
        
        
    }
    
    
    
}
