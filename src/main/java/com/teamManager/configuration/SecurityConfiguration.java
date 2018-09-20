package com.teamManager.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;

	@Value("${spring.queries.users-query}")
	private String usersQuery;

	@Value("${spring.queries.roles-query}")
	private String rolesQuery;

	@Value("${test}")
	private String test;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().usersByUsernameQuery(usersQuery).authoritiesByUsernameQuery(rolesQuery)
				.dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if ("false".equals(test)) {
			http.cors().and().authorizeRequests().antMatchers("/registration").permitAll().antMatchers("/login")
					.permitAll().antMatchers("/admin/**").hasAuthority("ADMIN").antMatchers("/**").hasAuthority("STAFF")
					.anyRequest().authenticated().and().csrf().disable().formLogin().loginPage("/login")
					.failureUrl("/login?error=true").defaultSuccessUrl("/#/home", true).usernameParameter("email")
					.passwordParameter("password").and().logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").and()
					.exceptionHandling().accessDeniedPage("/access-denied").and()
					// enabling the basic authentication
					.httpBasic().and()
					// configuring the session on the server
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
					// disabling the CSRF - Cross Site Request Forgery
					.csrf().disable();
		} else {
			http.cors().and().authorizeRequests().antMatchers("/**").permitAll().and()
					// enabling the basic authentication
					.httpBasic().and()
					// configuring the session on the server
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
					// disabling the CSRF - Cross Site Request Forgery
					.csrf().disable();
		}
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/templates/**");
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
