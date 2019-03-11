## Environment Setup

+ Install JDK

 * 配置环境变量
 ```
   JAVA_HOME=jdk目录
   ClassPath=".;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar;"
   PATH="%JAVA_HOME%\bin;"
 ```
+ Install Eclipse
 * https://www.eclipse.org/downloads/
+ Install testng plug-in
 * https://jingyan.baidu.com/article/86f4a73ea6116f37d6526980.html


+ Clone and run testcases in local 
```
git clone https://github.com/zhaoshunlinlan/webTest1.git
open eclipse and import the project
```

+ Modify properties in local
```
change chrome.path in file env.properties to your local chrome path
config TestNg for the project (right-click on project -> click properties -> click TestNg -> check Disable default listeners -> Apply and Close)
```

+ Run testcases in local 
```
right-click on testng.xml -> run as -> TestNG Suite
```
