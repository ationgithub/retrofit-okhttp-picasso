package com.shtoone.zjsjwzgl.utils;

import com.android.volley.VolleyError;

public interface  VolleyInterface {
    void ResponseResult(Object jsonObject);

    void ResponError(VolleyError volleyError);
}
