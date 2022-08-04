package com.app.pages;



import com.app.utilities.Driver;
import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.functions.ExpectedCondition;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    Driver driverInstance=Driver.getInstanceOfAppiumDriverFactory();
   AppiumDriver driver=driverInstance.get();
    WebDriverWait wait = new WebDriverWait(driver, 10);
    TouchAction t=new TouchAction(driver);

    public BasePage() {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public static void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static MobileElement waitForVisibility(MobileElement element, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(Driver.getInstanceOfAppiumDriverFactory().get(), timeToWaitInSec);
        return (MobileElement) wait.until(ExpectedConditions.visibilityOf(element));
    }
    /*public void waitForVisibility(MobileElement e) {
        wait.until(ExpectedConditions.visibilityOf(e));
    }

     */

    public void scroolOneTime(){
        Dimension size=driver.manage().window().getSize();
        int startX=size.width/2;
        int endx=startX;
        int startY= (int) (size.height*0.8);
        int endY= (int) (size.height*0.2);

            t.press(PointOption.point(startX,startY))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(endx,endY))
                    .release()
                    .perform();


    }

    public MobileElement scrollToElement(MobileElement element, String direction) throws Exception {
        Dimension size = driver.manage().window().getSize();
        int startX = (int) (size.width * 0.5);
        int endX = (int) (size.width * 0.5);
        int startY = 0;
        int endY = 0;
        boolean isFound = false;

        switch (direction) {
            case "up":
                endY = (int) (size.height * 0.3);
                startY = (int) (size.height * 0.7);
                break;

            case "down":
                endY = (int) (size.height * 0.8);
                startY = (int) (size.height * 0.2);
                break;
        }

        for (int i = 0; i < 15; i++) {
            if (find(element, 1)) {
                isFound = true;
                break;
            } else {
                swipe(startX, startY, endX, endY, 1);
            }
        }
        if(!isFound){
            throw new Exception("Element not found");
        }
        return element;
    }


    public void swipe(int startX, int startY, int endX, int endY, int seconds)
            throws InterruptedException {
        TouchAction t = new TouchAction(driver);
        t.press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(seconds)))
                .moveTo(PointOption.point(endX, endY)).release()
                .perform();
    }

    public boolean find(MobileElement element, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            return wait.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    if (element.isDisplayed()) {
                        return true;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            return false;
        }
    }
    public void clear(MobileElement e) {
        waitForVisibility(e,10);
        e.clear();
    }
    public void click(MobileElement e) {
        waitForVisibility(e,10);
        e.click();
    }
    public String getAttribute(MobileElement e, String attribute) {
        waitForVisibility(e,10);
        return e.getAttribute(attribute);
    }
    public void closeApp() {
        ((InteractsWithApps) driver).closeApp();
    }

    public void launchApp() {
        ((InteractsWithApps) driver).launchApp();
    }
    public MobileElement andScrollToElementUsingUiScrollable(String childLocAttr, String childLocValue) {
        return (MobileElement) ((FindsByAndroidUIAutomator) driver).findElementByAndroidUIAutomator(
                "new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView("
                        + "new UiSelector()."+ childLocAttr +"(\"" + childLocValue + "\"));");
    }

    public void scrollFromElementToElement(MobileElement startElement,MobileElement endElement){
        t.press(ElementOption.element(startElement))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(ElementOption.element(endElement))
                .release()
                .perform();
    }

    public void press(MobileElement e){
        t.press(ElementOption.element(e))
             .waitAction(WaitOptions.waitOptions(Duration.ofMillis(5000))).release().perform();
    }

    public void longPress(MobileElement e){
        t.longPress(ElementOption.element(e))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(5000))).release().perform();
    }

public void lockDevice(){
    ((AndroidDriver) driver).lockDevice();
}

public void unlockDevice(){
        waitFor(3);
    ((AndroidDriver) driver).unlockDevice();
}


    public void navigateTo(String option){
        String path="//android.widget.TextView[contains(@text,'"+option+"')]";
        MobileElement element= (MobileElement) driver.findElementByXPath(path);
        element.click();
    }

}
