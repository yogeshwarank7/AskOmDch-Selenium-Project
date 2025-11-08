package TS003;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.time.Duration;

public class TC033 {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

        WebDriver driver = null;

        try {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

            // Step 1: Open homepage
            driver.get("https://askomdch.com/");

            // Step 2: Scroll quickly to Secure Payments section
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 2500)");

            // Step 3: Wait 3 seconds before capturing
            Thread.sleep(3000);

            // Step 4: Locate Secure Payments section
            WebElement securePaymentsSection = driver.findElement(
                    By.xpath("//div[@class='wp-block-cover alignfull has-background-dim']"));

            // Step 5: Take screenshot of that section
            File screenshot = securePaymentsSection.getScreenshotAs(OutputType.FILE);
            File destination = new File(
                    "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\TC033_Screenshot_Pass.png");
            FileUtils.copyFile(screenshot, destination);

            System.out.println("✅ TC033 Passed - Screenshot captured successfully!");
            System.out.println("📸 Saved at: " + destination.getAbsolutePath());

        } catch (Exception e) {
            System.out.println("❌ TC033 Failed - " + e.getMessage());
            takeFailScreenshot(driver);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    // Capture Fail screenshot
    public static void takeFailScreenshot(WebDriver driver) {
        try {
            if (driver != null) {
                File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File dest = new File(
                        "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\TC033_Screenshot_Fail.png");
                FileUtils.copyFile(src, dest);
                System.out.println("📸 Fail Screenshot saved at: " + dest.getAbsolutePath());
            }
        } catch (Exception ex) {
            System.out.println("⚠️ Failed to capture fail screenshot: " + ex.getMessage());
        }
    }
}
