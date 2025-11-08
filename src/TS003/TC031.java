package TS003;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC031 {

    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            // ✅ Set ChromeDriver path
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

            driver = new ChromeDriver();
            driver.manage().window().maximize();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            // ✅ Step 1: Open homepage
            driver.get("https://askomdch.com/");
            System.out.println("✅ Homepage opened successfully.");

            // ✅ Step 2: Click on “SHOP NOW”
            WebElement shopNowBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(text(),'Shop Now')]")));
            shopNowBtn.click();
            System.out.println("✅ Clicked on 'SHOP NOW'.");

            // ✅ Step 3: Add first product to the cart
            WebElement addToCart = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//a[contains(text(),'Add to cart')])[1]")));
            addToCart.click();
            System.out.println("✅ Added product to cart.");

            // ✅ Step 4: Click on “View cart” or cart icon
            try {
                WebElement viewCart = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(text(),'View cart')]")));
                viewCart.click();
                System.out.println("✅ Clicked on 'View Cart'.");
            } catch (Exception e) {
                WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[@class='cart-contents']")));
                cartIcon.click();
                System.out.println("✅ Clicked on cart icon.");
            }

            // ✅ Step 5: Click on “PROCEED TO CHECKOUT”
            WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(text(),'Proceed to checkout') or contains(text(),'CHECK OUT')]")));
            checkoutBtn.click();
            System.out.println("✅ Clicked on 'CHECK OUT' button.");

            // ✅ Step 6: Wait 3 seconds for checkout page to load
            Thread.sleep(3000);

            // ✅ Step 7: Take screenshot of checkout page
            takeScreenshot(driver, "TC031-Screenshot_Pass_CHECKOUT");
            System.out.println("📸 Screenshot taken for checkout page.");

        } catch (Exception e) {
            System.out.println("❌ TS031 FAIL: Exception occurred - " + e.getMessage());
            try {
                if (driver != null) takeScreenshot(driver, "TC031-Screenshot_Fail_CHECKOUT");
            } catch (Exception ex) {
                System.out.println("⚠️ Unable to take screenshot: " + ex.getMessage());
            }

        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("🔒 Browser closed successfully.");
            }
        }
    }

    // 📸 Screenshot utility
    public static void takeScreenshot(WebDriver driver, String fileName) throws IOException {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String filePath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\"
                + fileName + ".png";
        FileHandler.copy(srcFile, new File(filePath));
        System.out.println("📸 Screenshot saved at: " + filePath);
    }
}
