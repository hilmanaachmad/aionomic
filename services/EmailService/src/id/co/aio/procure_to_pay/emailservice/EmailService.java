/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.emailservice;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;


import com.wavemaker.runtime.security.SecurityService;
import com.wavemaker.runtime.service.annotations.ExposeToClient;
import com.wavemaker.runtime.service.annotations.HideFromClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;


// for http request
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

// for request http
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;

// for json data
import org.json.JSONObject;
import org.json.JSONArray;

import org.apache.commons.text.StringEscapeUtils;

//import id.co.aio.procure_to_pay.emailservice.model.*;

/**
 * This is a singleton class with all its public methods exposed as REST APIs via generated controller class.
 * To avoid exposing an API for a particular public method, annotate it with @HideFromClient.
 *
 * Method names will play a major role in defining the Http Method for the generated APIs. For example, a method name
 * that starts with delete/remove, will make the API exposed as Http Method "DELETE".
 *
 * Method Parameters of type primitives (including java.lang.String) will be exposed as Query Parameters &
 * Complex Types/Objects will become part of the Request body in the generated API.
 *
 * NOTE: We do not recommend using method overloading on client exposed methods.
 */
@ExposeToClient
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    @Value("${app.environment.AIOApiHost}")
    String AIOApiHost;
    
    private final CloseableHttpClient httpClient = HttpClients
    .custom()
    .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
    .build();

    public static class SendEmailResponse {
        private Boolean status;
        private String message;
        public SendEmailResponse(Boolean status, String message) {
            this.status = status;
            this.message = message;
        }
        
        public Boolean getStatus() {
            return this.status;
        }
        
        public void setStatus(Boolean status) {
            this.status = status;
        }
        
        public String getMessage() {
            return this.message;
        }
        
        public void setMessage(String message) {
            this.message = message;
        }
    }
    /**
     * This is sample java operation that accepts an input from the caller and responds with "Hello".
     *
     * SecurityService that is Autowired will provide access to the security context of the caller. It has methods like isAuthenticated(),
     * getUserName() and getUserId() etc which returns the information based on the caller context.
     *
     * Methods in this class can declare HttpServletRequest, HttpServletResponse as input parameters to access the
     * caller's request/response objects respectively. These parameters will be injected when request is made (during API invocation).
     */
    public SendEmailResponse send(String to, String cc, String subject, String body, String text, HttpServletRequest request) {
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("to", to));
        params.add(new BasicNameValuePair("cc", cc));
        params.add(new BasicNameValuePair("subject", subject));
        params.add(new BasicNameValuePair("body", body));
        params.add(new BasicNameValuePair("text", text));
        
        String res = this.sendPost(AIOApiHost + "/service/email", params);
        
        logger.info("sendPostResponse :: " + res);
        
        JSONObject resJson;
        
        try {
            resJson = new JSONObject(res);   
        } catch (Exception e) {
            resJson = new JSONObject("{\"status\": false, \"data\": \"failed send email\"}");   
        }
        return new SendEmailResponse(resJson.getBoolean("status"), resJson.get("data").toString());
    }
    
    private String sendPost(String url, List<NameValuePair> params) {
        
        logger.info("sendPostURL :: " + url);

        HttpPost request = new HttpPost(url);

        // add request headers
        // request.addHeader("custom-key", "mkyong");
        // request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");
        
        // add params
        try {
            request.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        } catch (Exception e) {
            logger.info(e.toString());
            return "";
        }
        

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            // Get HttpResponse Status
            // System.out.println(response.getStatusLine().toString());

            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            // System.out.println(headers);

            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                // System.out.println(result);
                logger.info(result);
                return result;
            } else {
                return "";
            }

        } catch (Exception e) {
            logger.info(e.toString());
            return "";
        }

    }

}