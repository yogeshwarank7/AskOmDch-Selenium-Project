package TS005;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class TC053 {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\ASUS\\eclipse-workspace\\learningSelenium\\Driver\\chromedriver.exe");

        WebDriver driver = null;
        String screenshotPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\";
        String screenshotName = "TC053-Screenshot-";

        try {
            // Step 1: Launch browser
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.get("https://askomdch.com/");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Step 2: Locate “Global Shipping” full column section
            WebElement globalShippingSection = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@class='wp-block-column'][1]")));

            // Step 3: Scroll smoothly to it
            js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", globalShippingSection);

            // Wait 3 seconds before screenshot
            Thread.sleep(3000);

            // Step 4: Verify visibility and take screenshot
            if (globalShippingSection.isDisplayed()) {
                System.out.println("✅ TC053 Passed - 'Global Shipping' icon and text are displayed properly.");

                // Capture only that section (icon + text)
                File src = globalShippingSection.getScreenshotAs(OutputType.FILE);
                FileHandler.copy(src, new File(screenshotPath + screenshotName + "Pass.png"));
                System.out.println("📸 Screenshot saved: " + screenshotPath + screenshotName + "Pass.png");

            } else {
                throw new Exception("'Global Shipping' section not visible.");
            }

        } catch (Exception e) {
            System.out.println("❌ TC053 Failed: " + e.getMessage());
            try {
                if (driver != null) {
                    File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                    FileHandler.copy(src,
                            new File("C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\"
                                    + screenshotName + "Fail.png"));
                    System.out.println("📸 Screenshot saved (Fail): " + screenshotName + "Fail.png");
                }
            } catch (IOException ioEx) {
                System.out.println("⚠️ Failed to capture screenshot: " + ioEx.getMessage());
            }
        } finally {
            // Step 5: Close browser
            if (driver != null) {
                driver.quit();
                System.out.println("🧹 Browser closed successfully.");
            }
        }
    }
}
