  @OnClick({R.id.action_btn})
    public void uploadFile() {
        String path1 = Environment.getExternalStorageDirectory() + File.separator + "test.png";
        String path2 = Environment.getExternalStorageDirectory() + File.separator + "test.jpg";
        ArrayList<String> pathList = new ArrayList<>();
        pathList.add(path1);
        pathList.add(path2);

        Map<String, RequestBody> bodyMap = new HashMap<>();
        if(pathList.size() > 0) {
            for (int i = 0; i < pathList.size(); i++) {
                File file = new File(pathList.get(i));
                bodyMap.put("file"+i+"\"; filename=\""+file.getName(), RequestBody.create(MediaType.parse("image/png"),file));
            }
        }

        APIWrapper.getInstance().uploadMultipleTypeFile("jdsjlzx",bodyMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResponse<List<String>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        TLog.error("onError " + e.toString());
                    }

                    @Override
                    public void onNext(HttpResponse<List<String>> response) {
                        TLog.error("onNext " + response.toString());

                    }
                });

    }