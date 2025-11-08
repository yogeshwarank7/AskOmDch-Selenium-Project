package TS003;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC032 {

    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

            driver = new ChromeDriver();
            driver.manage().window().maximize();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            Actions actions = new Actions(driver);

            // Step 1: Open homepage
            driver.get("https://askomdch.com/");
            System.out.println("✅ Homepage opened successfully.");

            // Step 2: Hover over "SHOP NOW" button
            WebElement shopNowBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//a[contains(text(),'Shop Now')]")));
            actions.moveToElement(shopNowBtn).perform();
            Thread.sleep(3000); // wait for hover animation

            // Screenshot SHOP NOW
            takeScreenshot(shopNowBtn, "TC032-Screenshot_Pass_SHOP_NOW");
            System.out.println("📸 Screenshot taken for SHOP NOW hover.");

            // Step 3: Click SHOP NOW → Add to Cart → View Cart
            shopNowBtn.click();

            WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//a[contains(text(),'Add to cart')])[1]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartBtn);
            Thread.sleep(2000);

            WebElement viewCartBtn = driver.findElement(By.xpath("//a[contains(text(),'View cart')]"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", viewCartBtn);
            Thread.sleep(1000);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewCartBtn);
            Thread.sleep(3000); // wait for cart page to load

            // Step 4: Hover over CHECK OUT button
            WebElement checkoutBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//a[contains(text(),'Proceed to checkout')]")));
            actions.moveToElement(checkoutBtn).perform();
            Thread.sleep(3000);

            // Screenshot CHECK OUT
            takeScreenshot(checkoutBtn, "TC032-Screenshot_Pass_CHECKOUT");
            System.out.println("📸 Screenshot taken for CHECK OUT hover.");

            System.out.println("🎉 TS032 PASS: Hover effects verified successfully.");

        } catch (Exception e) {
            System.out.println("❌ TS032 FAIL: Exception occurred - " + e.getMessage());
            try {
                if (driver != null)
                    takeScreenshotFullPage(driver, "TC032-Screenshot_Fail");
            } catch (Exception ex) {
                System.out.println("⚠️ Unable to take fail screenshot: " + ex.getMessage());
            }
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("🔒 Browser closed successfully.");
            }
        }
    }

    // Screenshot WebElement
    public static void takeScreenshot(WebElement element, String fileName) throws IOException {
        File srcFile = element.getScreenshotAs(OutputType.FILE);
        String filePath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\"
                + fileName + ".png";
        FileHandler.copy(srcFile, new File(filePath));
        System.out.println("📸 Element screenshot saved at: " + filePath);
    }

    // Full-page screenshot
    public static void takeScreenshotFullPage(WebDriver driver, String fileName) throws IOException {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String filePath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\"
                + fileName + ".png";
        FileHandler.copy(srcFile, new File(filePath));
        System.out.println("📸 Full-page screenshot saved at: " + filePath);
    }
}
