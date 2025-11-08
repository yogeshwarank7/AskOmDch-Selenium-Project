package TS004;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.File;
import java.time.Duration;
import org.apache.commons.io.FileUtils;

public class TC046 {

    public static void main(String[] args) {
        // Set ChromeDriver path
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

        WebDriver driver = null;

        try {
            // Step 1: Open browser
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Step 2: Navigate to homepage
            driver.get("https://askomdch.com/");
            System.out.println("✅ Navigated to homepage.");

            // Step 3: Scroll to product section
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 800)");

            // Step 4: Click on the first “Add to Cart” button
            WebElement addToCartButton = driver.findElement(By.cssSelector("a.button.product_type_simple.add_to_cart_button"));
            addToCartButton.click();
            System.out.println("🛒 Clicked 'Add to Cart' button.");

            // Step 5: Wait for "View cart" link to appear
            Thread.sleep(3000); // Small wait for the cart update

            WebElement viewCartLink = driver.findElement(By.cssSelector("a.added_to_cart"));
            if (viewCartLink.isDisplayed()) {
                System.out.println("✅ TS046 Passed - Product successfully added to cart.");
                takeScreenshot(driver, "TC046-Screenshot-Pass");
            } else {
                throw new Exception("❌ 'View Cart' link not visible. Product might not be added.");
            }

        } catch (Exception e) {
            System.out.println("⚠️ TS046 Failed: " + e.getMessage());
            takeScreenshot(driver, "TC046-Screenshot-Fail");

        } finally {
            // Step 6: Close browser
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
