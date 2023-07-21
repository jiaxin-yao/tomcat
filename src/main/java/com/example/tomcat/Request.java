package com.example.tomcat;

import java.util.HashMap;
import java.util.Map;

public class Request {

    public Map<String, String> getInformation() {
        return information;
    }

    final private Map<String, String> information = new HashMap<>();
    private String method;
    private String protocol;

    public String getMethod() {
        return method;
    }

    public Request setMethod(String method) {
        this.method = method;
        return this;
    }

    public String getProtocol() {
        return protocol;
    }

    public Request setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public String getUri() {
        return uri;
    }

    public Request setUri(String uri) {
        this.uri = uri;
        return this;
    }

    private String uri;
}
