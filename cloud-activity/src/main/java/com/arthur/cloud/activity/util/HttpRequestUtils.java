package com.arthur.cloud.activity.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;


public class HttpRequestUtils {

    private static CloseableHttpClient httpClient = HttpClients.createDefault();
    private static HttpClientContext context = new HttpClientContext();

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);

//
//    /**
//     * 传入要下载的图片的url列表，将url所对应的图片下载到本地
//     *
//     * @param urlList
//     */
//    public static List<String> downloadPicture(List<String> urlList, List<String> mediaId) {
//
//        URL url = null;
//        int imageNumber = 0;
//        List<String> list=new ArrayList();
//        int i=0;
//        for (String urlString : urlList) {
//            try {
//                url = new URL(urlString);
//                DataInputStream dataInputStream = new DataInputStream(url.openStream());
//
//                String fileAbsPath = WechatAccessUtils.WECHAT_QR_CODE + mediaId.get(i) + ".jpg";
//
//                File file = new File(fileAbsPath);
//                FileOutputStream fileOutputStream = new FileOutputStream(file);
//
//                byte[] buffer = new byte[1024];
//                int length;
//
//                while ((length = dataInputStream.read(buffer)) > 0) {
//                    fileOutputStream.write(buffer, 0, length);
//                }
//                list.add(fileAbsPath);
//                dataInputStream.close();
//                fileOutputStream.close();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            i++;
//        }
//        return list;
//    }
//
//    /**
//     * download
//     *
//     * @param urlList
//     * @param mediaId
//     */
//    public static void downloadVoice(List<String> urlList, String mediaId) {
//
//        URL url = null;
//        int imageNumber = 0;
//
//        for (String urlString : urlList) {
//            try {
//                url = new URL(urlString);
//                DataInputStream dataInputStream = new DataInputStream(url.openStream());
//
//                String fileAbsPath = WechatAccessUtils.WECHAT_QR_CODE + mediaId + ".amr";
//
//                File file = new File(fileAbsPath);
//                FileOutputStream fileOutputStream = new FileOutputStream(file);
//
//                byte[] buffer = new byte[1024];
//                int length;
//
//                while ((length = dataInputStream.read(buffer)) > 0) {
//                    fileOutputStream.write(buffer, 0, length);
//                }
//
//                dataInputStream.close();
//                fileOutputStream.close();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {

        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            result = sendGet(urlNameString);
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static String sendGet(String url) {

        CloseableHttpResponse response = null;
        String content = null;
        try {
            HttpGet get = new HttpGet(url);
            response = httpClient.execute(get, context);
            HttpEntity entity = response.getEntity();
            content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return content;
    }


    /**
     * 获取当前服务器完整路径的方法
     */
    public static String getServerUrl(HttpServletRequest rq) {

        String requestUrl = rq.getScheme() //当前链接使用的协议
                + "://" + rq.getServerName()//服务器地址
                //端口号
                + rq.getContextPath() //应用名称，如果应用名称为
                + rq.getServletPath() //请求的相对url
                + "?" + rq.getQueryString(); //请求参数
        return requestUrl;
    }


    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url 发送请求的 URL
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String, ?> paramMap) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";

        String param = "";
        Iterator<String> it = paramMap.keySet().iterator();

        while(it.hasNext()) {
            String key = it.next();
            param += key + "=" + paramMap.get(key) + "&";
        }

        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        //使用finally块来关闭输出流、输入流
        finally{
            closeInput(out, in);
        }
        return result;
    }

    public static void closeInput(PrintWriter out, BufferedReader in) {
        try{
            if(out!=null){
                out.close();
            }
            if(in!=null){
                in.close();
            }
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }

}