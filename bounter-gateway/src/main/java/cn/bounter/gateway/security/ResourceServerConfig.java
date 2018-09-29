package cn.bounter.gateway.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 资源服务器权限控制
 */
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/simon/api/simon/name").hasRole("ADMIN")        //应用自定义授权
                .antMatchers("/*/api/**").authenticated();                    //下游服务以api开头的都需要认证
    }
}
