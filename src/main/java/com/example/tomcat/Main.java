package com.example.tomcat;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Tomcat tomcat = new Tomcat();
        tomcat.start();



    }
}
