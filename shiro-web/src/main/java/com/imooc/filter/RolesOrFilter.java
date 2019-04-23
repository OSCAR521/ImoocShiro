package com.imooc.filter;


import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

/**
 * 自定义 filter：如果传多个 roles 满足任何一个即可
 * 不好用，别理会，只能访问，不能屏蔽页面，设置为false，全部不能访问了，不管有没有权限
 * @author cheng
 *         2018/11/4 16:29
 */
public class RolesOrFilter extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(javax.servlet.ServletRequest servletRequest, javax.servlet.ServletResponse servletResponse, Object o) throws Exception {
        Subject subjecj = getSubject(servletRequest, servletResponse);
        String[] roles = (String[]) o;
        if(roles == null || roles.length == 0) {
            return true;
        }
            for(String role : roles) {
                if(subjecj.hasRole(role)){
                    return true;
                }else{
                    return false;
                }
            }

        return false;

    }
}
