package TS005;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC055 {

    public static void main(String[] args) {
        WebDriver driver = null;
        String screenshotPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\";
        String screenshotName = "TC055-Screenshot-";

        try {
            System.out.println("🔹 Step 1: Open browser and navigate to AskOmDch...");
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\ASUS\\eclipse-workspace\\learningSelenium\\Driver\\chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.get("https://askomdch.com/");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Wait for the main content to load
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

            System.out.println("🔹 Step 2: Locating 'BEST OFFERS' icon section...");
            // Locate the full BEST OFFERS block (icon + heading + paragraph)
            WebElement bestOffersBlock = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@class='wp-block-column'][3]")));

            // Scroll smoothly to the section
            js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", bestOffersBlock);

            System.out.println("⏳ Waiting 3 seconds before capturing screenshot...");
            Thread.sleep(3000);

            // ✅ Take screenshot of the icon + text section only
            File screenshot = bestOffersBlock.getScreenshotAs(OutputType.FILE);
            FileHandler.copy(screenshot, new File(screenshotPath + screenshotName + "Pass.png"));

            System.out.println("✅ TC055 Passed: 'BEST OFFERS' icon and text are visible and aligned.");
            System.out.println("📸 Screenshot saved at: " + screenshotPath + screenshotName + "Pass.png");

        } catch (Exception e) {
            System.out.println("❌ TC055 Failed: " + e.getMessage());
            try {
                if (driver != null) {
                    File failShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                    FileHandler.copy(failShot, new File(screenshotPath + screenshotName + "Fail.png"));
                    System.out.println("📸 Screenshot saved at: " + screenshotPath + screenshotName + "Fail.png");
                }
            } catch (IOException ioEx) {
                System.out.println("⚠️ Failed to capture failure screenshot: " + ioEx.getMessage());
            }
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("🧹 Browser closed successfully.");
            }
        }
    }
}
