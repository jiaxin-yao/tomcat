package com.example.tomcat;

public interface Servlet {

    void doGet(Request request, Response response);

    void doPost(Request request, Response response);


}
