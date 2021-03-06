package com.wangyu.wx.web.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wangyu.wx.common.AccessTokenInfo;
import com.wangyu.wx.entry.AccessToken;
import com.wangyu.wx.util.NetWorkHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.concurrent.*;

/**
 * @ClassName AccessTokenServlet
 * @Description 用于获取accessToken的Servlet
 * @Author ChongqingWangYu
 * @DateTime 2019/2/26 21:17
 * @GitHub https://github.com/ChongqingWangYu
 */
@WebServlet(
        name = "AccessTokenServlet",
        urlPatterns = {"/AccessTokenServlet"},
        loadOnStartup = 1,
        initParams = {
                @WebInitParam(name = "appId", value = "wxbe4d433e857e8bb1"),
                @WebInitParam(name = "appSecret", value = "ccbc82d560876711027b3d43a6f2ebda")
        })
public class AccessTokenServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("启动WebServlet");
        super.init();

        final String appId = getInitParameter("appId");
        final String appSecret = getInitParameter("appSecret");
        //线程池
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        singleThreadPool.execute(()-> System.out.println(Thread.currentThread().getName()));
        singleThreadPool.shutdown();
        //开启一个新的线程
        ((ThreadPoolExecutor) singleThreadPool).getThreadFactory().newThread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        //获取accessToken
                        AccessTokenInfo.accessToken = getAccessToken(appId, appSecret);
                        //获取成功
                        if (AccessTokenInfo.accessToken != null) {
                            //获取到access_token 休眠7000秒,大约2个小时左右
                            Thread.sleep(7000 * 1000);
                            //Thread.sleep(10 * 1000);//10秒钟获取一次
                        } else {
                            //获取失败,获取的access_token为空 休眠3秒
                            Thread.sleep(1000 * 3);
                        }
                    } catch (Exception e) {
                        System.out.println("发生异常：" + e.getMessage());
                        e.printStackTrace();
                        try {
                            //发生异常休眠1秒
                            Thread.sleep(1000 * 10);
                        } catch (Exception e1) {

                        }
                    }
                }
            }
        }).start();
    }

    /**
     * 获取access_token
     *
     * @return AccessToken
     */
    private AccessToken getAccessToken(String appId, String appSecret) {
        NetWorkHelper netHelper = new NetWorkHelper();
        /**
         * 接口地址为https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET，其中grant_type固定写为client_credential即可。
         */
        String Url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", appId, appSecret);
        //此请求为https的get请求，返回的数据格式为{"access_token":"ACCESS_TOKEN","expires_in":7200}
        String result = netHelper.getHttpsResponse(Url, "");
        System.out.println("获取到的access_token="+result);
        //使用FastJson将Json字符串解析成Json对象
        JSONObject json = JSON.parseObject(result);
        AccessToken token = new AccessToken();
        token.setAccessToken(json.getString("access_token"));
        token.setExpiresin(json.getInteger("expires_in"));
        return token;
    }
}