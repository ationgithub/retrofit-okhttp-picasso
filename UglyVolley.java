2016年已经停用
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//        MultipartRequest multipartRequest = new MultipartRequest(URL.DATA_UPLOAD, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String string) {
//                //string为服务器返回的字符串
////                Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
//                Log.e(TAG, string);
//                finish();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
////                Toast.makeText(getApplicationContext(), "网络异常", Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
//                Log.e(TAG, "失败");
//                TastyToast.makeText(getApplicationContext(), "服务器异常！", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
//            }
//        });
//        // 添加header
//        multipartRequest.addHeader("header-name", "value");
//        // 通过MultipartEntity来设置参数
//        MultipartEntity multi = multipartRequest.getMultiPartEntity();
////             //传文件(以图片为例)
////        multi.addFilePart("file", new File("/storage/emulated/0/Pictures/Screenshots/Screenshot_20180928-173621.jpg"),"image/jpg");
//        multi.addFilesPart("file",sImgPath,"image/*");
//        multi.addStringPart("data", jsonString);
//        queue.add(multipartRequest);