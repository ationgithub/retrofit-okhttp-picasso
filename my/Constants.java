package com.shtoone.zjsjwzgl.common;

import com.shtoone.zjsjwzgl.BaseApplication;
import com.shtoone.zjsjwzgl.utils.DirectoryUtils;

import java.io.File;

public class Constants {
    //SD卡路径
    public static final String PATH_DATA = DirectoryUtils.getDiskCacheDirectory(BaseApplication.context, "data").getAbsolutePath();
    public static final String PATH_NET_CACHE = PATH_DATA + File.separator + "NetCache";

    //网络链接超时时间
//    public static final int DEFAULT_TIMEOUT = 5;
    public static final int DEFAULT_TIMEOUT = 20;

    public static final Boolean DEBUG = true;

}
