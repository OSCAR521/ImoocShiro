package com.imooc.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * Created by  OSCAR on 2018/12/19
 * 仅读取配置文件，不读取数据库
 */
public class IniRealmTest {

    @Test
    public void testAuthentication(){

        IniRealm iniRealm = new IniRealm("classpath:user.ini");

        //1构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);

        //2主题提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("Mark","123456");
        subject.login(token);

        System.out.println("isAuthenticated:"+subject.isAuthenticated());//返回是否验证

        //检验是否存在此角色
        subject.checkRole("admin");

        //检验是否拥有此权限
        subject.checkPermission("user:delete");
        subject.checkPermission("user:update");
    }
}
