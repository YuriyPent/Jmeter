# Jmeter scripts
* Start dt-app
* Start server-agent
* Execute test_scripts
## JMeter-jmx-BeanShellCode
* Dynamically set the request body with custom variables.
* The value of the request header is reasonable to use the custom variable to take the value.
* Custom variable value script settings (read settings from file).
* Use String fileName = `${configFile} +" ";` to get the value of the custom variable configFile.
* Use `vars.put("accountId",accountId);` to set the accountId value of the custom variable.
* Modify the request json data content with the following code pattern:
```groovy
 Arguments arguments =  sampler.getArguments();
 Argument arg = arguments.getArgument(0);
 arg.setValue("postData");
 ```
 Use the following code pattern to modify the rest response content:
 ```groovy
  String response_data = prev.getResponseDataAsString();
  prev.setResponseData("reqDencryptJSON".getBytes("UTF-8"));
 ```
 Print information using logs
 ```groovy
 log.info("Script execution failed=========PostProcessor==================", exception);
 log.info("Script execution failed=========PostProcesso===================");
  ```
 ### Run JMeter tests with Maven
 * To be able to run JMeter tests with Maven, you'll need to set up a Maven module that has just the following:
 ```xml
 src/
   test/
     jmeter/
   pom.xml
 ```
 * At first you need only a blank pom.xml, mine looks like this:
 ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://Maven.apache.org/POM/4.0.0"
 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 xsi:schemaLocation="http://Maven.apache.org/POM/4.0.0 http://Maven.apache.org/xsd/Maven-4.0.0.xsd">
     <modelVersion>4.0.0</modelVersion>
     <groupId>com.koant.rwj</groupId>
     <artifactId>01-run-with-Maven</artifactId>
     <version>1.0-SNAPSHOT</version>
     <packaging>pom</packaging>
   </project>
   ```
   * To be able to run the JMeter jmx files with Maven, you'll need to use the jmeter-Maven-plugin. Add the following to the pom.xml:
   ```xml
     <build>
       <plugins>
         <plugin>
           <groupId>com.lazerycode.jmeter</groupId>
           <artifactId>jmeter-Maven-plugin</artifactId>
           <version>1.9.0</version>
          <executions>
            <execution>
              <id>jmeter-tests</id>
              <phase>verify</phase>
              <goals>
                <goal>jmeter</goal>
              </goals>
            </execution>
          </executions>
        </plugin>
       </plugins>
     </build>
 ```    
* What this achieves is that when you run `mvn verify`