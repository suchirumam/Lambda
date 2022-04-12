

import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.lang3.time.StopWatch;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;

public class demoMobile {

    public String username = "subrahmanyam.chirumamilla";
    public String accesskey = "xxBIoTPV5KGSVZNp5LzfSQMd3RCfECLYyCBa6jAC38zj6B8M6c";
    protected static WebDriverWait wdWait;
    public AppiumDriver driver;
    public String gridURL = "beta-hub.lambdatest.com";
    String status;
    String hub;
    SessionId sessionId;



    @org.testng.annotations.Parameters(value = {"browser", "platformVersion", "platform", "deviceName"})
    @BeforeTest
    public void setUp(String browser, String platformVersion, String platform, String deviceName) throws Exception {

            try {

                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability("name", "Mobile");
                capabilities.setCapability("build", "MobileDemo");
                capabilities.setCapability("deviceName", deviceName);
                capabilities.setCapability("platformVersion", platformVersion);
                capabilities.setCapability("platform", platform);
                capabilities.setCapability("isRealMobile", true);

                if (platform.equalsIgnoreCase("Android")) {
                    capabilities.setCapability("autoGrantPermissions", true);
                    capabilities.setCapability("app", "lt://APP100202491649690932874628");
                }else{
                    capabilities.setCapability("app", "lt://APP100202491649700900124712");
                }
                capabilities.setCapability("network", true);
                capabilities.setCapability("console", true);
                capabilities.setCapability("visual", true);

                StopWatch driverStart = new StopWatch();
                driverStart.start();

                hub = "https://" + username + ":" + accesskey + "@" + gridURL + "/wd/hub";
                driver = new AppiumDriver(new URL(hub), capabilities);

                Thread.sleep(2000);


        driver.findElementByXPath("//android.widget.TextView[starts-with(@text,'Skip all and view')]").click();
        driver.findElementByXPath("//android.widget.TextView[@text='OK']/..").click();
Thread.sleep(2000);
                driver.findElementByXPath("//android.widget.TextView[starts-with(@text,'SIGN')]").click();
                driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
                if(!driver.findElementsByXPath("//android.view.ViewGroup[@index='3']/android.widget.EditText").isEmpty()) {
                    driver.findElementByXPath("//android.view.ViewGroup[@index='3']/android.widget.EditText").clear();
                }
                driver.findElementByXPath("//android.widget.EditText[@text='Username']").sendKeys("140805");
                driver.findElementByXPath("//android.widget.EditText[@text='Password']").sendKeys("Copart@55");
                driver.findElementByAccessibilityId("signInSubmit").click();
                watchlist();

            } catch (
                    MalformedURLException e) {
                System.out.println("Invalid grid URL");
            } catch (Exception f) {
                System.out.println(f);

            }

        }


        public void watchlist() {
           try {
               int count, uCount;
               String text, lot_num;
               ArrayList<String> lotnum = new ArrayList<String>();
               Thread.sleep(2000);
               driver.findElementByAccessibilityId("Dashboard_Vehicle Finder").click();
               driver.findElementByXPath("//android.view.ViewGroup[@content-desc='searchSubmitButton']").click();
               Thread.sleep(200);
               if (!driver.findElements(By.xpath("//android.view.ViewGroup[@content-desc='searchSubmitButton']")).isEmpty()) {
                   driver.findElementByXPath("//android.widget.TextView[@text='Sorry, we were unable to find any vehicles that met your criteria. Please perform a new search.']");

               } else {
                   Thread.sleep(2000);

                   List<MobileElement> listOfLots = driver.findElements(By.xpath("//android.widget.TextView[contains(@text,'Lot')]"));
                   Random random = new Random();
                   int ran_val, count1 = 0;
                   ran_val = random.nextInt(listOfLots.size());
                   lot_num = listOfLots.get(ran_val).getText();
                   System.out.println("text" + lot_num);
                   lotnum.add((lot_num.split(":")[1]).split("/")[0].trim());
                   Thread.sleep(2000);
                   horizontalSwipeOnElement(driver, listOfLots.get(ran_val));

                   Thread.sleep(1000);



                   driver.findElement(By.xpath("//android.widget.TextView[@content-desc='lotItemNumber_lot_num']".replace("lot_num", lotnum.get(0)))).click();

                 driver.navigate().back();
                   driver.navigate().back();
                   driver.navigate().back();

                   Thread.sleep(3000);

                   driver.findElementByAccessibilityId("Dashboard_Watchlist").click();


                   //Remove lot from watchlist

                   horizontalSwipeOnElement(driver, (MobileElement) driver.findElement(By.xpath("//android.widget.TextView[@content-desc='lotItemNumber_lot_num']".replace("lot_num", lotnum.get(0)))));
                   Thread.sleep(2000);
                   driver.findElement(By.xpath("(//android.widget.TextView[@content-desc='lotItemNumber_lot_num']/../../../following::android.view.ViewGroup/android.widget.ImageView[@content-desc='addToWatchlist'])[1]".replace("lot_num", lotnum.get(0))));


               }
           }catch (Exception f) {
               System.out.println(f);

           }


        }

    public static void verticalSwipeByPercentages(AppiumDriver driver, double startPercentage, double endPercentage, double anchorPercentage) {
        Dimension size = driver.manage().window().getSize();
        int anchor = (int) (size.width * anchorPercentage);
        int startPoint = (int) (size.height * startPercentage);
        int endPoint = (int) (size.height * endPercentage);

        new TouchAction(driver)
                .press(point(anchor, startPoint))
                .waitAction(waitOptions(ofMillis(1000)))
                .moveTo(point(anchor, endPoint))
                .release().perform();

    }

    public static void horizontalSwipeOnElement(AppiumDriver driver, MobileElement ae) {
        int startX = ae.getLocation().getX() + (ae.getSize().getWidth() / 2);
        int startY = ae.getLocation().getY() + (ae.getSize().getHeight() / 2);
        int endX;
        if(startX+1000>driver.manage().window().getSize().getWidth() )
        {
            if (driver instanceof AndroidDriver) {
                endX = driver.manage().window().getSize().getWidth()-100;
                startX = 30;
            }
            else {
                endX = driver.manage().window().getSize().getWidth()-100;
            }
        }
        else
        {
            endX = startX+1000;
        }
        new TouchAction(driver)
                .press(point(startX,startY))
                .waitAction(waitOptions(ofMillis(100)))
                .moveTo(point(endX, startY))
                .release().perform();
    }



    @AfterTest
    public void tearDown() throws Exception {
        if (driver != null) {
            ((JavascriptExecutor) driver).executeScript("lambda-status=" + status);
            driver.quit();
        }
    }
}

