# MobileAutomationTesting

## Overview
This is a lightweight API and Mobile Test Automation Framework in Java (JUnit5 + Appium + RestAssured + Allure Reporting).
There are two tests so far:
```YAML
# API Testing - Include one negative scenario (failed with assertion failure on user name)
src/test/java/testsuite/api/APIAddUpdateDeleteUserTest.java

# Appium Mobile Testing
src/test/java/testsuite/appium/AppiumAddNewWifiNameTest.java
```

## Usage
#### 1. Evironment Setup
Assume that you already have Java (mine is JDK 20), Appium, Android Studio (Android Emulator) installed

#### 2. Update config.properties
Please replace src/test/resources/config.properties file settings as per your own environment:
```YAML
# Please replace deviceName with your Android Emulator's name
deviceName=JaneEmulator

# Mobile APP is ready in project, but please replace the absolute path as per your machine
appPath=//Users//zhangying//IdeaProjects//Appium//src//test//resources//ApiDemos-debug.apk

# Appium JS Absolute Path on your machine
appiumJSPath=//usr//local//lib//node_modules//appium//build//lib//main.js
```

#### 3. Run Automation Testing along with generating Allure Report
Open Terminal / CMD tool, cd to your project directory, run below maven command
```java
mvn clean verify
```

#### 4. Automation Test Reporting
After all tests are finished, raw data for Allure Report will be generated under **target/allure-results**, please refer to https://allurereport.org/docs/gettingstarted-installation/ to install Allure cmd tool and generate report with below command
```
allure generate target/allure-results/ --clean -o allure-report
```
You will find the final report is generated under **<project_root>/allure-report/index.html**

<img width="1440" alt="image" src="https://github.com/bbzying/MobileAutomationTesting/assets/36399262/3f625d1f-540b-4520-bcd4-70ce1bdbf22d">
<img width="1428" alt="image" src="https://github.com/bbzying/MobileAutomationTesting/assets/36399262/c2ef959a-9e3c-4a42-8d6e-173441a93f1f">



