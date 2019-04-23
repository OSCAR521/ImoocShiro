package com.imooc.cache;

import com.imooc.util.JedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;


import java.util.Collection;
import java.util.Set;

/**
 * Created by  OSCAR on 2018/12/19
 * 自定义缓存的增删改查
 * 第一次：会先从redis中获取数据，然后找不到就会从数据库中查找数据，兵把数据库中的数据存入redis
 * 第二次：redis中就会有数据
 *
 * 最优秀的是先读取本地缓存->然后redis->然后数据库
 */
@Component
public class RedisCache<K,V> implements Cache<K,V> {

    @Autowired
    private JedisUtil jedisUtil;

    private final String CACHE_PREFIX = "imooc-cache";

    private byte[] getKey(K k){
        if (k instanceof String){
            return (CACHE_PREFIX+k).getBytes();
        }
        return SerializationUtils.serialize(k);
    }

    @Override
    public V get(K k) throws CacheException {
        System.out.println("从redis中获取权限数据");
        byte[] value = jedisUtil.get(getKey(k));
        if (value != null){
            return (V) SerializationUtils.deserialize(value);
        }
        return null;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        byte[] key = getKey(k);
        byte[] value = SerializationUtils.serialize(v);
        jedisUtil.set(key,value);
        jedisUtil.expire(key,600);//设置超时时间
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        byte[] key = getKey(k);
        byte[] value = jedisUtil.get(key);
        jedisUtil.delete(key);
        if (value != null){
            return (V) SerializationUtils.deserialize(value);
        }
        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
}
