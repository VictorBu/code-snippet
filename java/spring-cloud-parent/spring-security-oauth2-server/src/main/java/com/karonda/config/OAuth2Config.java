package com.karonda.config;

import com.karonda.service.UserServiceDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.*;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer // 开启授权服务
@EnableResourceServer // 需要对外暴露获取和验证 Token 的接口，所以也是一个资源服务
public class OAuth2Config extends AuthorizationServerConfigurerAdapter{

//    @Autowired
//    private DataSource dataSource;

    @Autowired
    private AuthenticationManager authenticationManager;

//    @Autowired
//    private UserServiceDetail userServiceDetail;

    @Override
    // 配置客户端信息
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory() // 将客户端的信息存储在内存中
//                .withClient("browser") // 客户端 id, 需唯一
//                .authorizedGrantTypes("refresh_token", "password") // 认证类型为 refresh_token, password
//                .scopes("ui") // 客户端域
//                .and()
                .withClient("eureka-client") // 另一个客户端
                .secret("123456")  // 客户端密码
                .authorizedGrantTypes("client_credentials", "refresh_token", "password")
                .accessTokenValiditySeconds(3600) // 设置 token 过期时间
                .scopes("server");
    }

    @Override
    // 配置授权 token 的节点和 token 服务
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore()) // token 的存储方式
                .authenticationManager(authenticationManager) // 开启密码验证，来源于 WebSecurityConfigurerAdapter
//                .userDetailsService(userServiceDetail); // 读取验证用户的信息
                .tokenEnhancer(jwtTokenEnhancer());
    }

//    @Override
//    // 配置 token 节点的安全策略
//    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        security.tokenKeyAccess("permitAll()") // 获取 token 的策略
//                .checkTokenAccess("isAuthenticated()");
//    }

    @Bean
    public TokenStore tokenStore() {

//        return new InMemoryTokenStore();

//        return new JdbcTokenStore(dataSource);

        return new JwtTokenStore(jwtTokenEnhancer());
    }

    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer(){
        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(new ClassPathResource("spring-jwt.jks")
                        , "abc123".toCharArray()); // abc123 为 password
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("spring-jwt")); // spring-jwt 为 alias
        return converter;
    }
}
