package yu.com.Wifi.wifiway;

import android.os.Looper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import yu.com.Wifi.MainInterface;


/**
 * 使用get方法获取Http服务器数据
 */

public class WebServiceGet {

    public static String executeHttpGet(String username, String password, String address){//用户密码
        HttpURLConnection connection = null;
        InputStream in = null;

        try{
            String Url = "http:/39.108.0.16/Web/" + address;
            String path = Url + "?username=" + username + "&password=" + password;
            Log.i("WebServiceGet", "Url="+Url);
            Log.i("WebServiceGet", "path="+path);
            try {
                URL url = new URL(path);
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);//建立连接超时
                connection.setReadTimeout(8000);//传递数据超时
                in = connection.getInputStream();
                return parseInfo(in);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //意外退出时，连接关闭保护
            if(connection != null){
                connection.disconnect();
            }
            if(in != null){
                try{
                    in.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    //得到字节输入流，将字节输入流转化为String类型
    public static String parseInfo(InputStream inputStream){
        BufferedReader reader = null;
        String line = "";
        StringBuilder response = new StringBuilder();

        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            while((line = reader.readLine()) != null){
                response.append(line);
            }
            Log.e("WebServiceGet", "response="+response.toString());
            return response.toString();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(reader != null){
                try{
                    reader.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static String appexecuteHttpGet(String appmessage,String address){//APP数据
        HttpURLConnection connection = null;
        InputStream in = null;
        Time mtime = new Time();
        MainInterface mmap = new MainInterface();
        String netType;
        switch (Login.networkState) {
            case 0:netType="null Internet";break;
            case 1:netType="wifi";Login.operatorName=Login.SSID;break;
            case 2:netType="2G";break;
            case 3:netType="3G";break;
            case 4:netType="4G";break;
            case 5:netType="traffic";break;
            default:netType="null";
        }
        try{
            String Url = "http://39.108.0.16/Web/" + address;
            String path;
            if(Login.user!=null){
                String time = Time.TimeOut();
                String location = MainInterface.loc;
                path = Url + "?user=" + URLEncoder.encode(Login.user, "UTF-8") +"&time=" + URLEncoder.encode(time, "UTF-8") +"&location=" + URLEncoder.encode(location, "UTF-8")+"&netType=" + URLEncoder.encode(netType, "UTF-8")+"&netmessage=" + URLEncoder.encode(Login.operatorName, "UTF-8") +"&appmessage=" + URLEncoder.encode(appmessage, "UTF-8") ;
            }else
               return null;
            Log.e("WebServiceGet", "Url="+Url);
            Log.e("WebServiceGet", "path="+path);
            try {
                URL url = new URL(path);
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);//建立连接超时
                connection.setReadTimeout(8000);//传递数据超时
                in = connection.getInputStream();
                //return parseInfo(in);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //意外退出时，连接关闭保护
            if(connection != null){
                connection.disconnect();
            }
            if(in != null){
                try{
                    in.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}