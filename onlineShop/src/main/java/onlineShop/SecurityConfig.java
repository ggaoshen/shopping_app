package onlineShop;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.context.annotation.Bean;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // csrf是一个网络缺陷，我们代码不能handle，所以就先disable掉
                .formLogin()
                .loginPage("/login"); // specify login page的url
        http
                .authorizeRequests()
                .antMatchers("/cart/**").hasAuthority("ROLE_USER") // match ROLE_USER这种url才能有权限
                .antMatchers("/get*/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/admin*/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().permitAll();
        // *匹配/之后当前层任何string，**匹配/之后subtree任何string（/cart/a/b/blah也可以匹配/cart/**）

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication().withUser("stefanlaioffer@gmail.com").password("123").authorities("ROLE_ADMIN");
        // 设置admin权限，不用注册就可以登录
        // in memory可以不用访问数据库
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT emailId, password, enabled FROM users WHERE emailId=?") // 这个不能hibernate，只能sql
                .authoritiesByUsernameQuery("SELECT emailId, authorities FROM authorities WHERE emailId=?"); // 根据前端发来的用户名，concat到sql语句

    }

    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() { // 没有pw加密的功能。可以自己加上加密功能
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}
