package com.imooc.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * Created by  OSCAR on 2018/12/19
 * 使用自己的sql设置权限
 */
public class SQLRealm {

    DruidDataSource dataSource = new DruidDataSource();
    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("123321xlb");
    }

    @Test
    public void testAuthentication(){

        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setPermissionsLookupEnabled(true);//默认false，不可以查看数据库权限数据

        //在实际使用中不太可能使用JdbcRealm的源码
        //使用自己的sql
        String sql = "select password from test_user where username = ?";
        jdbcRealm.setAuthenticationQuery(sql);//验证语句查询

        String roleSql = "select rolename from test_user_role where username = ?";
        jdbcRealm.setUserRolesQuery(roleSql);

        //1构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);

        //2主题提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("OSCAR","123321xlb");
        subject.login(token);

        System.out.println("isAuthenticated:"+subject.isAuthenticated());//返回是否验证

        subject.checkRole("user");

        /*subject.checkRole("admin");
        subject.checkRoles("admin","user");
        subject.checkPermission("user:select");*/


    }
}
