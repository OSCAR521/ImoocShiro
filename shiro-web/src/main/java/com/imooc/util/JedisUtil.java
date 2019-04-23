package com.imooc.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * Created by  OSCAR on 2018/12/19
 */
@Component
public class JedisUtil {

    @Autowired
    private JedisPool jedisPool;

    private Jedis getResource(){
        return jedisPool.getResource();
    }

    public byte[] set(byte[] key, byte[] value) {
        Jedis jedis = getResource();
        try {
            jedis.set(key, value);
            return value;
        }finally {
            jedis.close();
        }
    }

    public void expire(byte[] key, int i) {
        Jedis jedis = getResource();
        try{
            jedis.expire(key, i);
        }finally {
            jedis.close();
        }

    }

    public byte[] get(byte[] key) {
        Jedis jedis = getResource();
        try{
            return jedis.get(key);
        }finally {
            jedis.close();
        }
    }

    public void delete(byte[] key) {
        Jedis jedis = getResource();
        try{
            jedis.del(key);
        }finally {
            jedis.close();
        }
    }


    public Set<byte[]> keys(String SHIRO_SESSION_PRIFIX) {
        Jedis jedis = getResource();
        try{
           return jedis.keys((SHIRO_SESSION_PRIFIX+"*").getBytes());
        }finally {
            jedis.close();
        }
    }


}
