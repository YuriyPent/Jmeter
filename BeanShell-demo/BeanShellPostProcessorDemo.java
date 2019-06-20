try{
        //First take the custom variable, and when you use it, you can't find the definition.
        String secretKey=${secretKey}+"";
        String deviceId=${deviceId}+"";
        //The public configuration property returned by the server is saved.
        String fileName=${configFile}+"";
        log.info("====*fileName*******************************************************************"+fileName);

        //Processing of returned json data
        String response_data=prev.getResponseDataAsString();
        JSONObject parseObject=JSON.parseObject(response_data);
        log.info("====********************************************************************"+parseObject);
        String data=parseObject.getString("data");

        //This is encrypted data processing
        if(data!=null){

        log.info("====PostProcessor********************************************************************"+data);
        //Decrypt
        String reqDencryptJSON=AESUtis.appAESDencrypt(data,deviceId,secretKey);

        log.info("===PostProcessor**********************************responseBody*****************************"+reqDencryptJSON);

        //The decrypted data is written back to the body
        prev.setResponseData(reqDencryptJSON.getBytes("UTF-8"));

        //Save user attributes for later use
        JSONObject resultJson=JSON.parseObject(reqDencryptJSON);
        JSONObject result=resultJson.getJSONObject("result");
        if(result!=null){
        log.info("===PostProcessor*********************************result *******===************");
        String accessKey=result.getString("accessKey");

        if(accessKey!=null){
        String accountId=result.getString("accountId");
        String token=result.getString("token");

        //Configuration. Set header and public information

        String sign=MD5Utils.md5To32LowerCaseHexString(secretKey+accountId+deviceId+secretKey);

        //The file read is put here, the file content will be overwritten, not the append mode, so the place where the
        // content cannot be actually saved is declared FileOutputStream
        Properties properties=new Properties();

        FileOutputStream outputStream=new FileOutputStream(fileName);

        properties.setProperty("accountId",accountId);
        properties.setProperty("accessKey",accessKey);
        properties.setProperty("sign",sign);
        properties.setProperty("token",token);
        properties.store(outputStream,"");//
        log.info("===PostProcessor**********************************properties.store*******===************");

        //Resource release
        if(outputStream!=null){
        outputStream.close();
        }
        }
        }
        }

        }catch(Exception ex){

        log.info("Script execution failed================PostProcessor=========================",ex);
        }


