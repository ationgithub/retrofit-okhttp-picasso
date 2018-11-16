package com.shtoone.zjsjwzgl.utils;
import android.content.Context;
import android.util.Log;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import org.json.JSONObject;
import java.util.HashMap;

/**
 * jsonObjectRequest队列
 */
public class VolleyDemo {

    String TAG = "volleyDemo";
    private Gson g = new Gson();
    private static RequestQueue requestQueue = null;
    private static VolleyDemo volleyDemo;

    public static VolleyDemo getVolleyDemo(Context context) {
        if (volleyDemo == null)
            volleyDemo = new VolleyDemo();
        requestQueue = Volley.newRequestQueue(context);
        return volleyDemo;
    }

    /**
     * Post请求 url,HashMap,volleyInterface,Class
     * 以实体类形式返回，然后进行强转
     */
    public void SendVolleyPostBean(String url, HashMap<?, ?> hashMap, final VolleyInterface volleyInterface, final Class<?> aClass) {
        Log.i(TAG, "开始请求");

        JSONObject jsonObject = new JSONObject(hashMap);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                Object userBean = (Object) g.fromJson(jsonObject.toString(), aClass);
                volleyInterface.ResponseResult(userBean);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyInterface.ResponError(volleyError);
                Log.i(TAG, "请求错误");
            }
        });

        AddrequestQueue(jsonObjectRequest, true);
    }

    /**
     * GET请求 url,volleyInterface,Class
     * 以实体类形式返回，
     * **/
    public void SendVolleyGetBean(String url, final VolleyInterface volleyInterface,final Class<?> aClass) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Object userBean = (Object) g.fromJson(jsonObject.toString(),aClass);
                volleyInterface.ResponseResult(userBean);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyInterface.ResponError(volleyError);
                Log.i(TAG, "请求错误");
            }
        });
        AddrequestQueue(jsonObjectRequest, true);
    }

    /**
     * POST请求 url,volleyInterface,Class
     * 以JSON形式返回，
     **/
    public void SendVolleyPostJsonobject(String url, final VolleyInterface volleyInterface, final Class<?> aClass) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                volleyInterface.ResponseResult(jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyInterface.ResponError(volleyError);
                Log.i(TAG, "请求错误");
            }
        });
        AddrequestQueue(jsonObjectRequest, true);
    }

    /**
     * GET请求 url,volleyInterface,Class
     * 以JSON形式返回，然后进行强转 为JSONobject
     **/
    public void SendVolleyGetJsonobject(String url, final VolleyInterface volleyInterface, final Class<?> aClass) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                volleyInterface.ResponseResult(jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyInterface.ResponError(volleyError);
                Log.i(TAG, "请求错误");
            }
        });
//        加入队列 及设置 请求时间
        AddrequestQueue(jsonObjectRequest, true);
    }

    //    此方法是 Volley配置方法
    public void AddrequestQueue(JsonObjectRequest req, boolean issave) {
        // 设置超时时间
        req.setRetryPolicy(new DefaultRetryPolicy(3 * 1000, 1, 1.0f));
        // 是否开启缓存；
        req.setShouldCache(issave);
        // 将请求加入队列
        requestQueue.add(req);
        // 开始发起请求
        requestQueue.start();
    }
}

