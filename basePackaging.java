        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .connectTimeout(INTERNET_TIMEOUT , TimeUnit.SECONDS)
                .writeTimeout(INTERNET_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(INTERNET_TIMEOUT, TimeUnit.SECONDS)
                .build();



        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//表单类型
                .addFormDataPart("tiaomacode", tiaomacode)
                .addFormDataPart("qianshouren", qianshouren)
                .addFormDataPart("qianshouremark", qianshouremark)
                .addFormDataPart("state","0")
                .addFormDataPart("guobangleibie", guobangleibie);
				
				 if (chepainame!=null&&!TextUtils.isEmpty(chepainame))
        {
            file1 = new File(getExternalCacheDir(), chepainame);
            try {
                file1 = new Compressor(this).setQuality(30).compressToFile(file1);
                RequestBody imageBody3 = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
                builder.addFormDataPart("file", file1.getName(), imageBody3);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Bitmap  bitmap = BitmapFactory.decodeResource(getApplication().getResources(), R.drawable.addpic);
            final File file = saveInternal(getApplicationContext(), bitmap, file1);
            RequestBody imageBody3 = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart("file", file.getName(), imageBody3);

        }


        if (fachedanname!=null&&!TextUtils.isEmpty(fachedanname))
        {
            file2 = new File(getExternalCacheDir(), fachedanname);
            try {
                file2 = new Compressor(this).setQuality(30).compressToFile(file2);
                RequestBody imageBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
                builder.addFormDataPart("file", file1.getName(), imageBody1);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else{
            Bitmap  bitmap = BitmapFactory.decodeResource(getApplication().getResources(), R.drawable.addpic);
            final File file = saveInternal(getApplicationContext(), bitmap, file2);
            RequestBody imageBody2 = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart("file", file.getName(), imageBody2);

        }
		

		 List<MultipartBody.Part> parts = builder.build().parts();


        new Retrofit.Builder()
                .baseUrl(URL.BaseURL)
                .addConverterFactory(GsonConverterFactory.create())//配置gson转换
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//配置rxjava转换
                .client(client)
                .build()
                .create(ApiService.class)
                .uploadBhzChuchang(parts)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<UpLoadBean>() {


                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull UpLoadBean upLoadBean) {
                        Log.e(TAG, upLoadBean.toString());
                        Log.e(TAG, "upLoadBean:" + upLoadBean.getData());
                        response = upLoadBean.getData();
                        if (upLoadBean.isSuccess()) {
						
                            handler.sendEmptyMessage(1);  // 上传成功 发送消息到 handler 关闭详情页并提示上传成功
                            //Toast.makeText(getApplicationContext(),upLoadBean.getData(),Toast.LENGTH_SHORT).show();
                        } else {
						
                            handler.sendEmptyMessage(2);  // 上传失败 则什么都不做 停留在此页面
                            Log.e(TAG, upLoadBean.toString());
                            //Toast.makeText(getApplicationContext(),upLoadBean.getData(),Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        progressDialog.dismiss();
                        Log.e(TAG, "onError:");
                        Log.e(TAG, "e:" + e);
                        //handler.sendEmptyMessage(3);  // 上传失败 则什么都不做 停留在此页面
                        //TastyToast.makeText(getApplicationContext(), "上传失败，请重试！", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                        if (e instanceof SocketTimeoutException) {
                            //Toast.makeText(getApplicationContext(), "网络请求超时，请重试", Toast.LENGTH_SHORT).show();
                            handler.sendEmptyMessage(3);
                        } else if (e instanceof ConnectException) {
                            //Toast.makeText(getApplicationContext(), "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
                            handler.sendEmptyMessage(4);
                        } else if (e instanceof UnknownHostException) {
                            //Toast.makeText(getApplicationContext(), "网络错误，请检查您的网络后重试", Toast.LENGTH_SHORT).show();
                            handler.sendEmptyMessage(6);
                        } else {
                            //            Toast.makeText(mContext, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getApplicationContext(), "系统错误，请重试", Toast.LENGTH_SHORT).show();
                            handler.sendEmptyMessage(7);
                        }
                    }


                });
