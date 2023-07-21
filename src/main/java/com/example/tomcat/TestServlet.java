package com.example.tomcat;




@WebServlet(name = "testServlet",urlPattern = "/test")
public class TestServlet implements Servlet{
    @Override
    public void doGet(Request request, Response response) {
         response.setBody("<!DOCTYPE html>\n" +
                 "<html>\n" +
                 "<head>\n" +
                 "    <title>Hello, World!</title>\n" +
                 "</head>\n" +
                 "<body>\n" +
                 "    <h1>Hello, Jiaxin World!</h1>\n" +
                 "</body>\n" +
                 "</html>\n");

    }

    @Override
    public void doPost(Request request, Response response) {

    }
}
