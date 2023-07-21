package com.example.tomcat;

import java.util.Map;

public class HttpMessageParse {

    /**
     * 解析http包,放在request的map中
     * @param bytes
     * @param request
     */

    static void parser(byte [] bytes, Request request){
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes) {
            if(aByte != 0){
                System.out.print((char) aByte);
                result.append((char)aByte);
            }

        }


        String [] array = result.toString().split("\\r\\n");



        Map map = request.getInformation();

        // 请求行处理
        String requestLine = array[0];
        String [] arrLine = requestLine.split(" ");

        map.put("method", arrLine[0]);
        map.put("uri", arrLine[1]);
        map.put("protocol", arrLine[2]);
        request.setMethod(arrLine[0]);
        request.setUri(arrLine[1]);
        request.setProtocol(arrLine[2]);

        // 第二行处理
        String hostLine = array[1];
        int count = 0;
        int index = 0;

        for(int i = 0;i<hostLine.length();i++){
            if(hostLine.charAt(i)==':'){
                count++;
            }
            if(count==1){
                index = i;
                break;
            }
        }
        map.put(hostLine.substring(0, index), hostLine.substring(index+1, hostLine.length()));



        for(int i=2; i<array.length; i++){
            String [] arrI =  array[i].replaceAll("\\s+", "").split(":");
            map.put(arrI[0], arrI[1]);
        }


    }


}
