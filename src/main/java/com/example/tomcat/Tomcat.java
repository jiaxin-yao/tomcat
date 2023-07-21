package com.example.tomcat;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.*;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.*;
import java.lang.annotation.Annotation;
import java.net.ServerSocket;
import java.net.Socket;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;


public class Tomcat {

    public int getPort() {
        return port;
    }

    private final int port;

    public List<String> getServletScanPath() {
        return servletScanPath;
    }

    public HashMap<String, Servlet> getServletContext() {
        return servletContext;
    }

    private final List<String> servletScanPath = new ArrayList<>();

    private final HashMap<String, Servlet> servletContext = new HashMap<>();


    Tomcat() {

        String userDir = System.getProperty("user.dir");

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(userDir + "/target/classes/application.yaml");
            int character;
            StringBuilder stringBuilder = new StringBuilder();
            while ((character = fileReader.read()) != -1) {
                char c = (char) character;
                stringBuilder.append(c);
                System.out.print(c);
            }

            String result = stringBuilder.toString().replaceAll("\\s", "").split("=")[1];
            port = Integer.parseInt(result);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void start() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        ServerSocket server = new ServerSocket(port);
        List<String> servletUrl = findServletUrl();

        // 初始化Servlet并放入容器
        initServletContext(servletUrl);


        while (true) {
            final Socket socket;
            socket = server.accept();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    byte[] bytes = new byte[1024];
                    OutputStream outputStream;
                    try {
                        socket.getInputStream().read(bytes);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    Request request = new Request();
                    Response response = new Response();
                    HttpMessageParse.parser(bytes, request);

                    // 按照路径匹配servlet
                    try {
                        handleRequestMatchServlet(request, response,socket.getOutputStream());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                }
            }).start();


        }

    }

    private void handleRequestMatchServlet(Request request, Response response, OutputStream outputStream) throws IOException {
        Servlet servlet = this.servletContext.get(request.getUri());
        if(servlet != null){
            if(request.getMethod().equals("GET")){
                servlet.doGet(request,response);
                outputStream.write(response.toString().getBytes());
                outputStream.flush();
                outputStream.close();

            } else if (request.getMethod().equals("POST")) {
                servlet.doPost(request,response);
                outputStream.write(response.toString().getBytes());
                outputStream.flush();
                outputStream.close();
            }else {
                System.out.println("have no pattern method");
            }
        }


    }

    List<String> findServletUrl(){
        List<String> servletUrl = new ArrayList<>();
        String packageName = "com.example.tomcat";
        List<Class<?>> classes = getClassesInPackage(packageName);

        for (Class<?> clazz : classes) {
            System.out.println("Class: " + clazz.getName());
            for(Annotation annotation : clazz.getAnnotations()){
                if (annotation instanceof WebServlet){
                    Class<?>[] interfaces = clazz.getInterfaces();
                    if (interfaces.length > 0) {
                        System.out.println("Implements interfaces:");
                        for (Class<?> intf : interfaces) {
                            if(intf.getName().equals("com.example.tomcat.Servlet")){
                                servletUrl.add(clazz.getName());
                            }
                        }
                    } else {
                        System.out.println("Does not implement any interfaces.");
                    }
                }


            }


        }

        return servletUrl;


    }

    public static List<Class<?>> getClassesInPackage(String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try {
            Enumeration<URL> resources = classLoader.getResources(path);
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                File file = new File(resource.getFile());
                if (file.isDirectory()) {
                    classes.addAll(findClassesInDirectory(packageName, file));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }

    private static List<Class<?>> findClassesInDirectory(String packageName, File directory) {
        List<Class<?>> classes = new ArrayList<>();
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    classes.addAll(findClassesInDirectory(packageName + "." + file.getName(), file));
                } else if (file.getName().endsWith(".class")) {
                    String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                    try {
                        Class<?> clazz = Class.forName(className);
                        classes.add(clazz);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return classes;
    }


    void initServletContext(List<String> servletUrl) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        for(String url : servletUrl){

            Class c = Class.forName(url);
            Servlet servlet = (Servlet) c.newInstance();
            WebServlet annotation = (WebServlet)servlet.getClass().getAnnotation(WebServlet.class);
            String urlPattern = annotation.urlPattern();
            this.servletContext.put(urlPattern, servlet);
        }

    }
}
