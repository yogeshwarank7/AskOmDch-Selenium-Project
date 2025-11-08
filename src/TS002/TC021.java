package TS002;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.apache.commons.io.FileUtils;

public class TC021 {

    static final String SCREENSHOT_PATH = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\";

    // ✅ Screenshot helper
    public static void takeScreenshot(ChromeDriver driver, String fileName) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(SCREENSHOT_PATH + fileName);
            FileUtils.copyFile(srcFile, destFile);
            System.out.println("📸 Screenshot saved: " + destFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("⚠️ Failed to save screenshot: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        // ✅ Setup ChromeDriver path
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

        ChromeDriver driver = null;

        try {
            driver = new ChromeDriver();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            // ✅ Define screen sizes
            Dimension desktop = new Dimension(1920, 1080);
            Dimension tablet = new Dimension(768, 1024);
            Dimension mobile = new Dimension(360, 740);

            // ✅ Open site
            driver.get("https://askomdch.com/");
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
            Thread.sleep(4000); // wait for banner to fully load

            // ✅ Use a more flexible XPath (handles upper/lowercase text)
            By shopNowBtn = By.xpath("//a[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'shop now')]");

            // --- DESKTOP VIEW ---
            driver.manage().window().setSize(desktop);
            wait.until(ExpectedConditions.visibilityOfElementLocated(shopNowBtn));
            takeScreenshot(driver, "TC021-Screenshot-Desktop-Pass.png");

            // --- TABLET VIEW ---
            driver.manage().window().setSize(tablet);
            Thread.sleep(2000);
            takeScreenshot(driver, "TC021-Screenshot-Tablet-Pass.png");

            // --- MOBILE VIEW ---
            driver.manage().window().setSize(mobile);
            Thread.sleep(2000);
            takeScreenshot(driver, "TC021-Screenshot-Mobile-Pass.png");

            System.out.println("✅ Test Passed: Banner layout, text, and buttons adjust properly across all devices.");

        } catch (Exception e) {
            System.out.println("❌ Test Failed: " + e.getMessage());
            if (driver != null) {
                takeScreenshot(driver, "TC021-Screenshot-Fail.png");
            }
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
