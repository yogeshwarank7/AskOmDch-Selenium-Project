package TS005;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC054 {
    public static void main(String[] args) {
        WebDriver driver = null;
        String screenshotPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\";
        String screenshotName = "TC054-Screenshot-";

        try {
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\ASUS\\eclipse-workspace\\learningSelenium\\Driver\\chromedriver.exe");

            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.get("https://askomdch.com/");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // ✅ Locate the complete BEST QUALITY section (icon + text)
            WebElement bestQualitySection = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@class='wp-block-column'][2]")));

            // 🔹 Scroll smoothly and center the section
            js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", bestQualitySection);

            // ⏳ Wait 3 seconds before taking screenshot
            Thread.sleep(3000);

            if (bestQualitySection.isDisplayed()) {
                System.out.println("✅ TC054 Passed: 'BEST QUALITY' section (icon + text) is visible.");

                // 📸 Capture only this section
                File src = bestQualitySection.getScreenshotAs(OutputType.FILE);
                FileHandler.copy(src, new File(screenshotPath + screenshotName + "Pass.png"));

                System.out.println("📁 Screenshot saved at: " + screenshotPath + screenshotName + "Pass.png");
            } else {
                throw new Exception("'BEST QUALITY' section not visible.");
            }

        } catch (Exception e) {
            System.out.println("❌ TC054 Failed: " + e.getMessage());
            try {
                if (driver != null) {
                    File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                    FileHandler.copy(src, new File(screenshotPath + screenshotName + "Fail.png"));
                    System.out.println("📸 Screenshot saved (Fail case): " + screenshotPath + screenshotName + "Fail.png");
                }
            } catch (IOException ioEx) {
                System.out.println("⚠️ Failed to capture screenshot: " + ioEx.getMessage());
            }
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("🧹 Browser closed successfully.");
            }
        }
    }
}
