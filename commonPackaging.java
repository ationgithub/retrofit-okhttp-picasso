        Map<String, RequestBody> files = new HashMap<>();
        final FlaskClient service = ServiceGenerator.createService(FlaskClient.class);
        for (int i = 0; i < imagesList.size(); i++) {
            File file = new File(imagesList.get(i).path);
            files.put("file" + i + "\"; filename=\"" + file.getName(), RequestBody.create(MediaType.parse(imagesList.get(i).mimeType), file));
        }
        Call<UploadResult> call = service.uploadMultipleFiles(files);
        call.enqueue(new Callback<UploadResult>() {
            @Override
            public void onResponse(Call<UploadResult> call, Response<UploadResult> response) {
                if (response.isSuccessful() && response.body().code == 1) {
                    Toast.makeText(MainActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    Log.i("orzangleli", "---------------------上传成功-----------------------");
                    Log.i("orzangleli", "基础地址为：" + ServiceGenerator.API_BASE_URL);
                    Log.i("orzangleli", "图片相对地址为：" + listToString(response.body().image_urls,','));
                    Log.i("orzangleli", "---------------------END-----------------------");
                }
            }

            @Override
            public void onFailure(Call<UploadResult> call, Throwable t) {
                Toast.makeText(MainActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
            }
        });
