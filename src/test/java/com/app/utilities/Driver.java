package com.app.utilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Driver {
    //private static final JavaNetUriAccess URI ="http://ssh.redon.cf:4444/wd/hub\" ;
    private static Driver instanceOfAppiumDriver;
    private static AppiumDriver driver;
    private Driver() {
        String platformName=ConfigurationReader.get("platformName");
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
             caps.setCapability("newCommandTimeout", 300);
        String userName = "your username given from browserstack";
        String accessKey = "your accesskey for browserstack";



        switch(platformName){
            case "Android":
                //caps.setCapability("browserName", "android");
                //caps.setCapability("browserVersion", "10.0");
               // caps.setCapability("selenoid:options", Map.<String, Object>of(
                 //       "enableVNC", true,
                   //     "enableVideo", true
                //));
               // RemoteWebDriver driver = new RemoteWebDriver(
                 //      URI.create("http://ssh.redon.cf:4444/wd/hub").toURL(),
                   //     caps);
                caps.setCapability(MobileCapabilityType.DEVICE_NAME, ConfigurationReader.get("deviceName3"));
                caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
                //caps.setCapability(MobileCapabilityType.UDID, ConfigurationReader.get("udid2"));
                caps.setCapability("no-reset","false");
                caps.setCapability("full-reset","true");
                //               caps.setCapability(MobileCapabilityType.UDID, "803KPTM1660769");
                //       caps.setCapability("avd", "Pixel_3");
                //       caps.setCapability("avdLaunchTimeout", 180000);
                String androidAppUrl = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
                        + File.separator + "resources" + File.separator + "apps" + File.separator + ConfigurationReader.get("path");
                //caps.setCapability("appPackage", "io.appium.android.apis");
                //caps.setCapability("appActivity", "io.appium.android.apis.ApiDemos");
                caps.setCapability(MobileCapabilityType.APP, androidAppUrl);
               // caps.setCapability("appWaitActivity",ConfigurationReader.get("appWaitActivity"));
//                caps.setCapability("chromedriverExecutableDir", "/Users/Om/Downloads/chromedriver");
//                caps.setCapability("unlockType", "pattern");
//                caps.setCapability("unlockKey", "125478963");
               /* try {
                    driver= (AppiumDriver) new RemoteWebDriver(new URL("http://ssh.redon.cf:4444/wd/hub"), caps);
                }catch (MalformedURLException e){
                    e.printStackTrace();
                }

                */
                try {
                    driver= new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), caps);
                }catch (MalformedURLException e){
                    e.printStackTrace();
                }
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                break;
            case "BrowserStack":
                caps.setCapability("browserstack.user",userName);
                caps.setCapability("browserstack.key",accessKey);
                caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Samsung Galaxy S21");
                caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11.0");
                caps.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
                caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
                caps.setCapability("project", "My First Project");
                caps.setCapability("build", "My First Build");
                caps.setCapability("name", "Bstack-[Java] Sample Test");
                caps.setCapability(MobileCapabilityType.APP, "your application's bs://...");
                //caps.setCapability("browserstack.geoLocation","US");
                caps.setCapability("deviceOrientation", "portrait");
                try {
                    driver = new AndroidDriver<AndroidElement>(new URL("https://"+userName+":"+accessKey+"@hub-cloud.browserstack.com/wd/hub"), caps);
                }catch (MalformedURLException e){
                    e.printStackTrace();
                }
                break;

           /* case "iOS":
/                caps.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 11");
                caps.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 8");
                caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
//                caps.setCapability(MobileCapabilityType.UDID, "77F6B8F0-8877-4EDF-8C8C-99DBE64A93FF");
//                caps.setCapability(MobileCapabilityType.UDID, "913C24AE-C3B8-461E-B87D-30093CEAA006");
                caps.setCapability(MobileCapabilityType.UDID, "9527463259a2c083dfc8062535b699a52b9b3cf9");

                caps.setCapability("xcodeOrgId", "L8T9J4R323");
                caps.setCapability("xcodeSigningId", "iPhone Developer");
                String iOSAppUrl = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
                        + File.separator + "resources" + File.separator + "UIKitCatalog-iphonesimulator.app";
//                  caps.setCapability(MobileCapabilityType.APP, iOSAppUrl);
                caps.setCapability("simulatorStartupTimeout", 180000);
//                caps.setCapability("bundleId", "com.example.apple-samplecode.UICatalog");
                caps.setCapability("bundleId", "com.ubercab.UberClient");
                caps.setCapability("includeSafariInWebviews", true);
                caps.setCapability("webviewConnectTimeout", "90000");
//                caps.setCapability("safariLogAllCommunication", true);
//                caps.setCapability("fullContextList", true);
//                caps.setCapability("additionalWebviewBundleIds", "process-SafariViewService");
                return new IOSDriver(url, caps);

               */
        }

    }

    public static Driver getInstanceOfAppiumDriverFactory() {
        if (instanceOfAppiumDriver==null){
            System.out.println("New Driver Initilized!");
            instanceOfAppiumDriver=new Driver();
        }else {
            System.out.println("Driver already initilized!");
        }
        return instanceOfAppiumDriver;

    }

    public AppiumDriver get(){
        return driver;
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            instanceOfAppiumDriver=null;

        }
    }
}