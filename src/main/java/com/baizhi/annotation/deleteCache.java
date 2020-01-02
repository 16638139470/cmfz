package com.baizhi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName deleteCache
 * @Discription
 * @Author
 * @Date 2020/1/2 15:57
 **/
//删除缓存
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface deleteCache {
}
