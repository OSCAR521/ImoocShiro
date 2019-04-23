package com.imooc.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import javax.servlet.ServletRequest;
import java.io.Serializable;

/**
 * Created by  OSCAR on 2018/12/19
 * 先从request里面取，娶不到再从session取，然后设置到request里面
 * 不用默认的，会多次请求
 */
public class CustomSessionManager extends DefaultWebSessionManager {

    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {

        //把sessionKey存储在request对象里面，这样的话就会大大减少从redis中读取的次数
        Serializable sessionId = getSessionId(sessionKey);//根据sessionKey获得序列化后的sessionId
        ServletRequest request =null;
        if (sessionKey instanceof WebSessionKey){
            request = ((WebSessionKey)sessionKey).getServletRequest();
        }
        if (request!=null && sessionId !=null){
            Session session = (Session) request.getAttribute(sessionId.toString());
            if (session != null){
                return session;
            }
        }
        Session session = super.retrieveSession(sessionKey);
        if (request !=null && sessionId !=null){
            request.setAttribute(sessionId.toString(),session);
        }
        return session;
    }



}
