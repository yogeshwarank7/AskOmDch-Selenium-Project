package TS004;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.File;
import java.time.Duration;
import org.apache.commons.io.FileUtils;

public class TC047 {

    public static void main(String[] args) {
        // Set ChromeDriver path
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

        WebDriver driver = null;

        try {
            // Step 1: Launch browser
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Step 2: Navigate to homepage
            driver.get("https://askomdch.com/");
            System.out.println("✅ Navigated to homepage.");

            // Step 3: Scroll to product section
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 800)");

            // Step 4: Click on first product image or name
            WebElement productLink = driver.findElement(By.cssSelector("ul.products li a.woocommerce-LoopProduct-link"));
            String productName = productLink.getAttribute("aria-label");
            productLink.click();
            System.out.println("🖱️ Clicked on product: " + productName);

            // Step 5: Verify redirection to product detail page
            Thread.sleep(3000); // Wait for navigation
            String currentUrl = driver.getCurrentUrl();

            if (currentUrl.contains("product")) {
                System.out.println("✅ TS047 Passed - Redirected to product detail page successfully.");
                takeScreenshot(driver, "TC047-Screenshot-Pass");
            } else {
                throw new Exception("❌ Not redirected to product detail page. Current URL: " + currentUrl);
            }

        } catch (Exception e) {
            System.out.println("⚠️ TS047 Failed: " + e.getMessage());
            takeScreenshot(driver, "TC047-Screenshot-Fail");

        } finally {
            // Step 6: Close browser
            if (driver != null) {
                driver.quit();
                System.out.println("🔒 Browser closed successfully.");
            }
        }
    }

    // 📸 Screenshot Utility
    public static void takeScreenshot(WebDriver driver, String fileName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String filePath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\" + fileName + ".png";
            File dest = new File(filePath);
            FileUtils.copyFile(src, dest);
            System.out.println("📸 Screenshot saved at: " + filePath);
        } catch (Exception e) {
            System.out.println("⚠️ Failed to capture screenshot: " + e.getMessage());
        }
    }
}
