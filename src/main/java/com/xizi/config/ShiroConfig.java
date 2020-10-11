package com.xizi.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        //创建一个shiro过滤的工厂bean对象
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
       //设置SecurityManager安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);


        //设置登录跳转页面
        bean.setLoginUrl("/toLogin");
        //设置未授权提示页面
        bean.setUnauthorizedUrl("/unAuth");

        //添加shiro的内置过滤器
        /*
            anno:无需认证就可以访问
            authc:必须认证才能访问
            user: 必须有 记住我功能才能使用
            perms:拥有对某个资源的权限才能访问
            role:拥有某个角色权限才能访问
         */

        //拦截
        Map<String, String> filterMap = new LinkedHashMap<>();

        //权限
        filterMap.put("/user/add","perms[user:add]"); //拥有user:add权限才能访问
//        filterMap.put("/user/add","authc");
        filterMap.put("/user/update","perms[user:update]");

        filterMap.put("/logout","logout");
        //添加到bean中
        bean.setFilterChainDefinitionMap(filterMap);

        return bean;
    }


    //DafaultWebSecurityManager
//    注入 securityManager
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //创建 realm对象
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }


    //整合ShiroDialect:用来整合shiro thymeleaf
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

}
