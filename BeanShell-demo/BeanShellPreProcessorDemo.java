import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import com.xx.x.util.*;
import org.apache.jmeter.protocol.http.sampler.*;
import org.apache.jmeter.samplers.*;
import org.apache.jmeter.config.*;
import org.apache.jmeter.protocol.http.sampler.*;
import org.apache.jmeter.protocol.http.util.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import com.doctor.commons.*;

try {
        //Custom variable
        //Read configuration. Set header and public information
        Properties properties = new Properties();
        String fileName = ${configFile}+"";
        FileInputStream inputStream = new FileInputStream(fileName);
        properties.load(inputStream);
        String accountId =  properties.getProperty("accountId");
        String sign = properties.getProperty("sign");
        String accessKey = properties.getProperty("accessKey");
        String token = properties.getProperty("token");
        vars.put("accountId",accountId);
        vars.put("sign",sign);
        vars.put("accessKey",accessKey);
        vars.put("token",token);
        if (inputStream != null) {
        inputStream.close();
        }
        String deviceId = ${deviceId}+"";
        String secretKey = ${secretKey}+"";
        //Request body reset encryption
        Arguments arguments =  sampler.getArguments();
        Argument arg = arguments.getArgument(0);
        String body = arg.getValue();
        log.info("PreProcessor==========================================="+ body);
        JSONObject parseObject = JSON.parseObject(body);
        log.info("====********************************************************************"+ parseObject);
        String data =   parseObject.getString("data");
        if(data !=null){
        log.info("====********************************************************************"+ data);
        //encryption
        String encryptData = AESUtis.appAESEncrypt(data, deviceId, secretKey);
        log.info("encryptData====********************************************************************"+ encryptData);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", encryptData);
        String  postData = jsonObject.toJSONString();
        log.info("postData====**************************************postData******************************"+ postData);
        //Set new post data
        arg.setValue(postData);
        }
        } catch (Exception ex) {
        log.info("Script execution failed===========================================", ex);
}