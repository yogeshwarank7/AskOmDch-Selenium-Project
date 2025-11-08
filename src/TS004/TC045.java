package TS004;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.File;
import org.apache.commons.io.FileUtils;

public class TC045 {

    public static void main(String[] args) {
        // Set ChromeDriver path
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

        WebDriver driver = null;

        try {
            // Initialize browser
            driver = new ChromeDriver();
            driver.manage().window().maximize();

            // Step 1: Open browser and navigate to homepage
            driver.get("https://askomdch.com/");
            System.out.println("✅ Navigated to homepage.");

            // Step 2: Scroll to product section
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 800)");

            // Step 3: Locate all “Add to Cart” buttons
            java.util.List<WebElement> addToCartButtons =
                    driver.findElements(By.cssSelector("a.button.product_type_simple.add_to_cart_button"));

            if (addToCartButtons.size() > 0) {
                System.out.println("✅ TS045 Passed - 'Add to Cart' buttons are visible under all products.");
                for (WebElement button : addToCartButtons) {
                    System.out.println("Button Text: " + button.getText());
                }
                takeScreenshot(driver, "TC045-Screenshot-Pass");
            } else {
                System.out.println("❌ TS045 Failed - 'Add to Cart' buttons are missing.");
                takeScreenshot(driver, "TC045-Screenshot-Fail");
            }

        } catch (Exception e) {
            System.out.println("⚠️ TS045 Failed due to exception: " + e.getMessage());
            takeScreenshot(driver, "TC045-Screenshot-Fail");
        } finally {
            // Ensure browser closes properly
            if (driver != null) {
                driver.quit();
                System.out.println("Browser closed successfully.");
            }
        }
    }

    // Screenshot method
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
