package com.xizi.config;

import com.xizi.pojo.User;
import com.xizi.service.UserServiceImpl;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

//自定义
public class UserRealm  extends AuthorizingRealm {

    @Autowired
    UserServiceImpl userService;

    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行了授权");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //拿到当前登录用户的这个对象
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();

        //添加当前用户的角色权限，用于判断可以访问哪些功能
        info.addStringPermission(currentUser.getPerms());

        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了认证");

        //用户名 密码，数据库取
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;

        User user = userService.queryUserByName(userToken.getUsername());


        Subject currentSubject = SecurityUtils.getSubject();
        Session session =  currentSubject.getSession();
        session.setAttribute("loginUser",user);
        session.setAttribute("UserName", user.getName());

      if (user==null){//没有这个人
          return null;
      }
        //密码认证 shiro做
        return new SimpleAuthenticationInfo(user,user.getPwd(),"");


    }
}
