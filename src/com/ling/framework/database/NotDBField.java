package com.ling.framework.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识不是数据库读写的字段
 */
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.METHOD) 
public @interface NotDBField {

}
