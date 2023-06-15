package nl.novi.automate.security;

import nl.novi.automate.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig  {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public SecurityConfig(JwtService service, UserRepository userRepos) {
        this.jwtService = service;
        this.userRepository = userRepos;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder encoder, UserDetailsService udService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(udService)
                .passwordEncoder(encoder)
                .and()
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService(this.userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//        http
//                .httpBasic().disable()
//                .authorizeRequests()
//                .antMatchers("/**").permitAll() // Alle endpoints zijn open voor iedereen
//                .and()
//                .addFilterBefore(new JwtRequestFilter(jwtService, userDetailsService()), UsernamePasswordAuthenticationFilter.class)
//                .csrf().disable().cors().and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        return http.build();
//
//
////als alles gelukt is weer overschakelen naar hieronder

        http
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.POST, "/roles").permitAll()
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .antMatchers(HttpMethod.DELETE, "/rides/{rideId}/users/{username}").hasAuthority("PASSAGIER")
                .antMatchers(HttpMethod.DELETE, "/rides/{id}").hasAuthority("BESTUURDER")
                .antMatchers(HttpMethod.POST, "/rides").hasAuthority("BESTUURDER")
                .antMatchers(HttpMethod.GET, "/rides").hasAuthority("PASSAGIER")
                .antMatchers(HttpMethod.POST, "/rides/**").hasAnyAuthority("BESTUURDER", "PASSAGIER")
                .antMatchers(HttpMethod.DELETE, "/cars/**").hasAuthority("BESTUURDER")
                .antMatchers(HttpMethod.GET, "/cars/**").hasAnyAuthority("BESTUURDER", "PASSAGIER")
                .antMatchers(HttpMethod.POST, "/cars").hasAuthority("BESTUURDER")
                .antMatchers(HttpMethod.PUT, "/users/{username}/{carId}").hasAuthority("BESTUURDER")
                .antMatchers("/users/**").hasAnyAuthority("PASSAGIER", "BESTUURDER")
                .antMatchers("/notifications/**").hasAnyAuthority("PASSAGIER", "BESTUURDER")
                .antMatchers("/messages/**").hasAnyAuthority("PASSAGIER", "BESTUURDER")
//                .antMatchers("/users").hasAnyAuthority("PASSAGIER", "BESTUURDER")
                .and()
                .addFilterBefore(new JwtRequestFilter(jwtService, userDetailsService()), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable().cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    //                .antMatchers("/rides").hasAuthority("BESTUURDER")
//                .antMatchers("/cars").hasAuthority("BESTUURDER")
//                .antMatchers("/bestuurder").hasAuthority("BESTUURDER")
//                .antMatchers("/**").hasAnyAuthority("PASSAGIER", "BESTUURDER")
}
