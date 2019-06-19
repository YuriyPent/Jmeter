# Jmeter scripts
## JMeter-jmx-BeanShellCode
Dynamically set the request body with custom variables
The value of the request header is reasonable to use the custom variable to take the value
Custom variable value script settings (read settings from file)
Use String fileName = `${configFile}+""`; to get the value of the custom variable configFile
Use vars.put("accountId",accountId); to set the accountId value of the custom variable.
Modify the request json data content with the following code pattern:
```
 Arguments arguments =  sampler.getArguments();
 Argument arg = arguments.getArgument(0);
 arg.setValue("postData");
 ```
 Use the following code pattern to modify the rest response content:
 ```
  String response_data = prev.getResponseDataAsString();
  prev.setResponseData("reqDencryptJSON".getBytes("UTF-8"));
 ```
 Print information using logs
 ```
 log.info("Script execution failed=========PostProcessor==================", exception);
 log.info("Script execution failed=========PostProcesso===================");
  ```
  Use mvn dependency:copy-dependencies -DoutputDirectory=D:/lib -DincludeScope=compile to copy the custom lib dependencies