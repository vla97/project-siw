package it.uniroma3.siw.projectmanager.autenticazione;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static it.uniroma3.siw.projectmanager.model.Credenziali.ADMIN_ROLE;
@Configuration
@EnableWebSecurity
public class AutConfigurazione extends WebSecurityConfigurerAdapter{
	@Autowired
	DataSource dataSource;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/","/index", "/login", "/users/register").permitAll()
			.antMatchers(HttpMethod.POST, "/login", "/users/register").permitAll()
			.antMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
			.antMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
			.anyRequest().authenticated()
			.and().formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/login")
			.defaultSuccessUrl("/home")
			.and().logout()
			.logoutUrl("/logout")
			.clearAuthentication(true).permitAll()
			.logoutSuccessUrl("/index")
			.invalidateHttpSession(true);
			
			
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
				.dataSource(this.dataSource)
				.authoritiesByUsernameQuery("SELECT username, role FROM credenziali WHERE username=?")
				.usersByUsernameQuery("SELECT username, password, 1 as enable FROM credenziali WHERE username=?");
				
	}
	
	@Bean
	PasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
