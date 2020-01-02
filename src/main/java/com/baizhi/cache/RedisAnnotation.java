package com.baizhi.cache;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName RedisAnnotation
 * @Discription
 * @Author
 * @Date 2020/1/2 15:56
 **/
@Configuration
@Aspect
public class RedisAnnotation {
    @Autowired
    private RedisTemplate redisTemplate;

    @Around("@annotation(com.baizhi.annotation.AddCache)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        StringBuilder stringBuilder = new StringBuilder();
        //获取类名
        String name = joinPoint.getTarget().getClass().getName();
        //获取方法名
        String name1 = joinPoint.getSignature().getName();
        stringBuilder.append(name1);
        //获取方法参数
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            stringBuilder.append(arg);
        }
        String s = stringBuilder.toString();
        Boolean aBoolean = redisTemplate.hasKey(name);
        Object result = null;
        if (aBoolean) {
            result = redisTemplate.opsForHash().get(name, s);
        } else {
            Map<String, Object> map = new HashMap<>();
            result = joinPoint.proceed();
            map.put(s, result);
            redisTemplate.opsForHash().putAll(name, map);
        }
        return result;
    }

    @After("@annotation(com.baizhi.annotation.deleteCache)")
    public void deleteCache(JoinPoint joinPoint) {
        String name = joinPoint.getTarget().getClass().getName();
        redisTemplate.delete(name);
    }
}
