package com.mt.android.message.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MsgColumn {
	String field();//Ó³Éä¶ÔÓ¦µÄ×Ö¶Î
}
