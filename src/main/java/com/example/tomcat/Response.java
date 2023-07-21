package com.example.tomcat;




public class Response {
    private String protocol = "HTTP/1.1";

    private String statusCode = "200";

    private String message = "OK";


    private String type = "text/html";

    private String charset = "UTF-8";

    private String length = "1024";


    private String body = "Hello JiaXin TOMCAT";


    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public String getCharset() {
        return charset;
    }

    public String getLength() {
        return length;
    }

    public String getBody() {
        return body;
    }



    public String getProtocol() {
        return protocol;
    }


    public Response setProtocol(String protocol) {
        this.protocol = protocol;

        return this;
    }



    public String toString() {

        String response = protocol + " "+ statusCode + "" + message + "\n" +
                "Content-Type: " + type + "; charset=" + charset + "\n" +
                "Content-Length: " + length + "\n" + "\n" +
                body;
        return response;



//        return ("HTTP/1.1 200 OK\n" +
//                "Content-Type: text/html; charset=UTF-8\n" +
//                "Content-Length: 110\n" +
//                "\n" +
//                "<!DOCTYPE html>\n" +
//                "<html>\n" +
//                "<head>\n" +
//                "    <title>Sample HTTP Response</title>\n" +
//                "</head>\n" +
//                "<body>\n" +
//                "    <h1>Hello, World!</h1>\n" +
//                "</body>\n" +
//                "</html>");

    }


}
