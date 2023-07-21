package com.example.tomcat;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface WebServlet {
    String name() default "";
    String urlPattern() default "";




}
