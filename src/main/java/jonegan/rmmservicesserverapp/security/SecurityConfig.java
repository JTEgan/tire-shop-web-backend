package jonegan.rmmservicesserverapp.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/signup/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/signup/**").permitAll()
                .antMatchers("/customers/**").hasRole("CUSTOMER")
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().csrf().disable();
    }
}
//disable SerializationFeature.FAIL_ON_EMPTY_BEANS