package com.devops.api2.security;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class URLConnTest {

//    boolean isChatBotAccessUser = false;
//	if( ( "01".equals(_user.getEtcparam4()) || "02".equals(_user.getEtcparam4()) ) || ("sjkim@goodcen.com".equals(_user.getLoginId())
//            || "jyshin1@goodcen.com".equals(_user.getLoginId())
//            || "hspark@goodcen.com".equals(_user.getLoginId())
//            || "mckim365@cloit.com".equals(_user.getLoginId())
//            || "kimmi@cloit.com".equals(_user.getLoginId())
//            || "hshong@cloit.com".equals(_user.getLoginId())
//            || "mjkim4@cloit.com".equals(_user.getLoginId())
//            || "chosa@cloit.com".equals(_user.getLoginId())
//            || "hjcho@cloit.com".equals(_user.getLoginId())
//            || "alshd39@cloit.com".equals(_user.getLoginId())
//            || "lhyeon@sicc.co.kr".equals(_user.getLoginId())
//            || "hshong@itcen.co.kr".equals(_user.getLoginId()) )
//            ){
//        isChatBotAccessUser = true;
//    }
//
//
//    boolean isChatBotAccessUser = false;
//	if( 	"sjkim@goodcen.com".equals(_user.getLoginId())
//            || "jyshin1@goodcen.com".equals(_user.getLoginId())
//            || "hspark@goodcen.com".equals(_user.getLoginId())
//            || "mckim365@cloit.com".equals(_user.getLoginId())
//            || "kimmi@cloit.com".equals(_user.getLoginId())
//            || "hshong@cloit.com".equals(_user.getLoginId())
//            || "mjkim4@cloit.com".equals(_user.getLoginId())
//            || "chosa@cloit.com".equals(_user.getLoginId())
//            ){
//        isChatBotAccessUser = true;
//    }
//    PrintWriter postReq = null;
//    BufferedReader postRes = null;
//    String resultJson = null;
//    StringBuilder resultBuffer = new StringBuilder();
//
//    //static String url = "http://121.254.178.229";
//    //static String url = "http://esdev.cengroup.co.kr";
//    static String url = "http://192.168.63.72";
//    static String json = "{\"BP_USR_ID\":\"210010\",\"USR_ID\":\"210010\",\"CPLD_RDM_KEY\":\"d2150de7-9e93-4034-a2b2-e660fcdc5784\",\"SW_CRTC_KEY\":\"b4119b77-9cfe-e504-b11d-c28f3628c1d9\"}";
//
//    static HttpURLConnection httpURLConnection = null;
//    private static String connectDev(String url, String json) throws Exception {
//        PrintWriter postReq = null;
//        BufferedReader postRes = null;
//        String resultJson = null;
//        StringBuilder resultBuffer = new StringBuilder();
//        try{
//            URL connectUrl = new URL(url);
//            httpURLConnection = (HttpURLConnection)connectUrl.openConnection();
//            httpURLConnection.setRequestMethod("POST");
//            httpURLConnection.setDoOutput(true);
//            httpURLConnection.setUseCaches(false);
//            httpURLConnection.setConnectTimeout(10000); //TIMEOUT 10초 설정
//
////            System.out.println("~~~> doOutPut : " + httpURLConnection.getOutputStream());
//
//            // JSONArray 전송
//            postReq = new PrintWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8"));
//            postReq.write(json);
//            postReq.flush();
//
//            // JSONObject 수신
//            postRes = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
//            System.out.println("~~~~~~~~~~~> gis : " + postRes.readLine());
//            while ((resultJson = postRes.readLine()) != null){
//                resultBuffer.append(resultJson);
//            }
//        }catch(SocketTimeoutException e){
//            e.printStackTrace();
//            throw e;
//        }catch(Exception e){
//            e.printStackTrace();
//            throw e;
//        }finally {
//            try{
//                if(httpURLConnection != null){
//                    httpURLConnection.disconnect();
//                }
//            }catch(Exception e){
//            }
//        }
//        return resultBuffer.toString();
//    }
//
//    public static void main(String[] args) throws Exception {
//        String result = connectDev(url, json);
//        System.out.println("~~~~~~~~> Result  : " + result);
//    }
//
//}
//
//
//
//    private String connectProd(String url , String json) throws Exception {
//        PrintWriter postReq = null;
//        BufferedReader postRes = null;
//        String resultJson = null;
//        StringBuilder resultBuffer = new StringBuilder();
//        HttpsURLConnection httpCon = null;
//        try {
//            URL connectUrl = new URL(url);
//            httpCon = (HttpsURLConnection)connectUrl.openConnection();
//            httpCon.setRequestMethod("POST");
//            httpCon.setDoOutput(true);
//            httpCon.setUseCaches(false);
//            httpCon.setConnectTimeout(5000); //TIMEOUT 5초 설정
//
//            // JSONArray 전송
//            postReq = new PrintWriter(new OutputStreamWriter(httpCon.getOutputStream(), "UTF-8"));
//            postReq.write(json);
//            postReq.flush();
//
//            // JSONObject 수신
//            postRes = new BufferedReader(new InputStreamReader(httpCon.getInputStream(), "UTF-8"));
//            while ((resultJson = postRes.readLine()) != null){
//                resultBuffer.append(resultJson);
//            }
//        }catch(SocketTimeoutException e){
//            e.printStackTrace();
//            throw e;
//        }catch(Exception e){
//            e.printStackTrace();
//            throw e;
//        }finally {
//            try{
//                if(httpCon != null){
//                    httpCon.disconnect();
//                }
//            }catch(Exception e){
//            }
//        }
//        return resultBuffer.toString();

    //[accept-encoding:"gzip", user-agent:"ReactorNetty/1.1.8", host:"127.0.0.1:8080", accept:"*/*", Internal-Route-Request:"true", Authorization:"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwdXJjaGFzZSIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE3MDE2NTM5NDF9.7Wl3XLNtYzfyTPxGKcAmmdZ4zluanWZ07YkQg7uhGXacjR9qya5_p6lFq8EhNradZ_ZfrbS92zXgChgeAm_Vnw"]
    }