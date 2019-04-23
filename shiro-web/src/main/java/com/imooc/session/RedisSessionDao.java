package com.imooc.session;

import com.imooc.util.JedisUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.SerializationUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by  OSCAR on 2018/12/19
 */
public class RedisSessionDao extends AbstractSessionDAO {

    @Autowired
    private JedisUtil jedisUtil;

    private final String SHIRO_SESSION_PRIFIX = "imooc-session";

    private byte[] getKey(String key){
        return (SHIRO_SESSION_PRIFIX+key).getBytes();
    }

    //此方法是为了保存session
    private void saveSession(Session session){
        if (session!=null && session.getId()!=null){
            byte[] key = getKey(session.getId().toString());
            byte[] value = SerializationUtils.serialize(session);
            jedisUtil.set(key,value);//存入redis
            jedisUtil.expire(key,600);//设置超时时间600秒，10分钟
        }
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);//返回值session 的序列化对象
        assignSessionId(session,sessionId);
        saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        System.out.println("read session");//读了很多次，性能很低，优化，自定义sessionManager（抽空看看sessionmanager源码）
        if (serializable==null){
            return null;
        }
        byte[] key = getKey(serializable.toString());
        byte[] value = jedisUtil.get(key);
        return (Session) SerializationUtils.deserialize(value);//反序列化为session对象
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
         saveSession(session);
    }

    @Override
    public void delete(Session session) {
         if (session==null || session.getId()==null){
             return ;
         }
         byte[] key = getKey(session.getId().toString());
         jedisUtil.delete(key);
    }

    //获取存活的session
    @Override
    public Collection<Session> getActiveSessions() {
        Set<byte[]> keys = jedisUtil.keys(SHIRO_SESSION_PRIFIX);
        Set<Session> sessions = new HashSet<>();
        if (CollectionUtils.isEmpty(keys)){
            return sessions;
        }
        for (byte[] key : keys){
            Session session = (Session) SerializationUtils.deserialize(jedisUtil.get(key));
            sessions.add(session);
        }
        return sessions;
    }



}
