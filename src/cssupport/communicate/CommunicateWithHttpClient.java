/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cssupport.communicate;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 *
 * @author babji
 */
public class CommunicateWithHttpClient {

    private String serverPath = "";
    private String hostName = "";
    private int port = 80;
    private String postData = "";
    private String responseData = "";
    private boolean connectionProblems = false;
    private String contentType = "application/x-www-form-urlencoded";
    private Logger logger = Logger.getLogger("global");

    public CommunicateWithHttpClient() {
    }

    public CommunicateWithHttpClient(String host, int port, String serverPath, String postData) {
        setHostName(host);
        setPort(port);
        setServerPath(serverPath);
        setPostData(postData);
    }

    public void setServerPath(String path) {
        serverPath = path;
    }

    public String getServerPath() {
        return serverPath;
    }

    public void setHostName(String name) {
        hostName = name;
    }

    public String getHostName() {
        return hostName;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setPostData(String postData) {
        this.postData = postData;
    }

    public void doHandShakeWithHTTPClient() {
        try {
            System.out.println("Debug @ stmt Do HandShake hostName:port " + hostName + ":" + port + "");
            System.out.println("Debug @ stmt Do HandShake hostName:" + serverPath);

            HttpClient httpClient = new HttpClient();
//              HttpClient httpClient = null;
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println("Debug @ stmt Do HandShake::");
            String protocol = (port == 8443) ? "https" : "http";
            System.out.println("protocol::" + protocol);
            URL url = new URL(protocol, hostName, port, serverPath);
            HttpMethod httpPost = new PostMethod(url.toString());
            httpPost.setRequestHeader("Host", hostName);
            httpPost.setRequestHeader("Content-Length", "" + postData.length());
            // httpPost.setRequestHeader("Content-Type", ""+getContentType());
            httpPost.setRequestHeader("Connection", "close");
            httpPost.setRequestHeader("Accept-Encoding", "gzip");
            httpPost.setRequestHeader("Accept-Charset", "utf-8");
            httpPost.setQueryString(postData);
          
            int statusCode = httpClient.executeMethod(httpPost);
            if (statusCode == HttpStatus.SC_OK) {
                byte[] buffer = new byte[8192];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(baos);
                int bytesRead = 0;
                System.out.println("Debug @ stmt :: getResponseBodyAsStream " + new Date());
                InputStream is = httpPost.getResponseBodyAsStream();
                Header encoding = httpPost.getResponseHeader("Content-Encoding");
                System.out.println("Debug @ stmt :: BeforeReading " + new Date());
                boolean flag = true;
                if (encoding != null && encoding.getValue().equalsIgnoreCase("gzip")) {
                    GZIPInputStream gzipIS = new GZIPInputStream(is);
                    while ((bytesRead = gzipIS.read(buffer)) > -1) {
                        bos.write(buffer, 0, bytesRead);
                        if (flag) {
                            System.out.println("Debug @ stmt :: In reading " + new Date());
                            flag = false;
                        }
                    }
                    gzipIS.close();
                } else {
                    while ((bytesRead = is.read(buffer)) > -1) {
                        bos.write(buffer, 0, bytesRead);
                        if (flag) {
                            System.out.println("Debug @ stmt :: In reading " + new Date());
                            flag = false;
                        }
                    }
                }
                System.out.println("Debug @ stmt :: AfterReading " + new Date());
                bos.flush();
                byte[] responseBinaryData = baos.toByteArray();
                setResponseData(new String(responseBinaryData));
                bos.close();
                baos.close();
                is.close();
                httpPost.releaseConnection();
            } else {
                setConnectionProblems(true);
            }
        } catch (Exception ex) {
            setConnectionProblems(true);
            ex.printStackTrace();
            System.out.println("Failed making a connection to Server!(" + hostName + ":" + port);
        }
    }

    
      public void doHandShakeWithHTTPClient1() {
        try {
            System.out.println("Debug @ stmt Do HandShake hostName:port " + hostName + ":" + port + "" + new Date());
            System.out.println("Debug @ stmt Do HandShake hostName:" + serverPath);

            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setParameter("http.useragent", "Test Client");
//              HttpClient httpClient =HttpClientBuilder.create().build();
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println("Debug @ stmt Do HandShake::");
            String protocol = (port == 8443) ? "https" : "http";
            System.out.println("protocol::" + protocol);
            URL url = new URL(protocol, hostName, port, serverPath);
            System.out.println("urlurl::"+url.toString());
            HttpMethod httpPost = new PostMethod(url.toString());
            
            httpPost.setRequestHeader("Host", hostName);
            httpPost.setRequestHeader("Content-Length", "" + postData.length());
            // httpPost.setRequestHeader("Content-Type", ""+getContentType());
            httpPost.setRequestHeader("Connection", "close");
            httpPost.setRequestHeader("Accept-Encoding", "gzip");
            httpPost.setRequestHeader("Accept-Charset", "utf-8");
            httpPost.setQueryString(postData);
            System.out.println("POSTDATA>>::::"+postData);
          
            int statusCode = httpClient.executeMethod(httpPost);
            if (statusCode == HttpStatus.SC_OK) {
                byte[] buffer = new byte[8192];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(baos);
                int bytesRead = 0;
                System.out.println("Debug @ stmt :: getResponseBodyAsStream " + new Date());
                InputStream is = httpPost.getResponseBodyAsStream();
                Header encoding = httpPost.getResponseHeader("Content-Encoding");
                System.out.println("Debug @ stmt :: BeforeReading " + new Date());
                boolean flag = true;
                if (encoding != null && encoding.getValue().equalsIgnoreCase("gzip")) {
                    GZIPInputStream gzipIS = new GZIPInputStream(is);
                    while ((bytesRead = gzipIS.read(buffer)) > -1) {
                        bos.write(buffer, 0, bytesRead);
                        if (flag) {
                            System.out.println("Debug @ stmt :: In reading " + new Date());
                            flag = false;
                        }
                    }
                    gzipIS.close();
                } else {
                    while ((bytesRead = is.read(buffer)) > -1) {
                        bos.write(buffer, 0, bytesRead);
                        if (flag) {
                            System.out.println("Debug @ stmt :: In reading " + new Date());
                            flag = false;
                        }
                    }
                }
                System.out.println("Debug @ stmt :: AfterReading " + new Date());
                bos.flush();
                byte[] responseBinaryData = baos.toByteArray();
                setResponseData(new String(responseBinaryData));
                bos.close();
                baos.close();
                is.close();
                httpPost.releaseConnection();
            } else {
                setConnectionProblems(true);
            }
        } catch (Exception ex) {
            setConnectionProblems(true);
            ex.printStackTrace();
            System.out.println("Failed making a connection to Server!(" + hostName + ":" + port);
        }
    }

      
    public void doHandShakeWithHttp() {
        HttpURLConnection httpCon = null;
        OutputStreamWriter wr = null;
        StringBuffer respBuff = null;
        URL url = null;
        try {

//            URL url = new URL("https://cultswitch.cultuzz.de:8443/cultswitch/processOTA");
            String protocol = (port == 8443) ? "https" : "http";
            url = new URL(protocol, hostName, port, serverPath);
//            url = new URL(hostName + ":" + port + serverPath);
            httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setRequestMethod("POST");
            httpCon.setDoOutput(true);
            httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//application/x-www-form-urlencoded
            httpCon.setRequestProperty("Content-Length", String.valueOf(postData.length()));
            httpCon.setReadTimeout(60000);
            wr = new OutputStreamWriter(httpCon.getOutputStream());
            wr.write(postData);
            wr.flush();
            wr.close();

            InputStreamReader isr = new InputStreamReader(httpCon.getInputStream());

            BufferedReader rd = new BufferedReader(isr);
            String line;
            respBuff = new StringBuffer();

            while ((line = rd.readLine()) != null) {
                respBuff.append(line);
            }

            responseData = respBuff.toString();

        } catch (Exception e) {
            e.printStackTrace();
            responseData = "Exception doHandShakeWithHttp " + e;
//            org.apache.log4j.Logger.getLogger(Communicate.class).error("### in doHandShakeWithHttp Exception " + e);

        } finally {
            url = null;
            httpCon = null;
            if (wr != null) {
                try {
                    wr.close();
                } catch (Exception e) {
                }
                wr = null;
            }
            respBuff = null;

        }
    }

    public boolean isConnectionProblems() {
        return connectionProblems;
    }

    public void setConnectionProblems(boolean connectionProblems) {
        this.connectionProblems = connectionProblems;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getResponseData() {
        return responseData;
    }

    public static String encodeData(String postData) {
        try {
            postData = URLEncoder.encode(postData, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            postData = URLEncoder.encode(postData);
            ex.printStackTrace();
        }
        return postData;
    }
}
