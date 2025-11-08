package TS004;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

public class TC038 {

    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            // 🔹 Set ChromeDriver path
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

            // 🔹 Launch browser
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // 🔹 Step 1: Open homepage
            driver.get("https://askomdch.com/");
            System.out.println("✅ Step 1: Homepage opened successfully.");

            // 🔹 Step 2: Scroll down to Featured Products section
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 1200)");
            Thread.sleep(2000);
            System.out.println("✅ Step 2: Scrolled down to Featured Products section.");

            // 🔹 Step 3: Verify “Featured Products” heading
            WebElement featuredHeading = driver.findElement(By.xpath("//h2[contains(text(),'Featured Products')]"));
            if (featuredHeading.isDisplayed()) {
                System.out.println("✅ 'Featured Products' heading is visible.");
            } else {
                throw new Exception("❌ 'Featured Products' heading not found.");
            }

            // 🔹 Step 4: Verify featured product elements (image, name, price, add to cart)
            WebElement firstProduct = driver.findElement(By.cssSelector("ul.products li:first-child"));

            WebElement productImage = firstProduct.findElement(By.tagName("img"));
            WebElement productName = firstProduct.findElement(By.cssSelector(".woocommerce-loop-product__title"));
            WebElement productPrice = firstProduct.findElement(By.cssSelector(".woocommerce-Price-amount"));
            WebElement addToCartBtn = firstProduct.findElement(By.cssSelector("a.button"));

            if (productImage.isDisplayed() && productName.isDisplayed() && productPrice.isDisplayed()
                    && addToCartBtn.isDisplayed()) {
                System.out.println("🎉 TS038 PASS: Featured Products section and its elements are displayed properly.");
                takeScreenshot(driver, "TC038-Screenshot-pass");
            } else {
                System.out.println("❌ TS038 FAIL: Some product elements are missing.");
                takeScreenshot(driver, "TC038-Screenshot-fail");
            }

        } catch (Exception e) {
            System.out.println("❌ TS038 FAIL: Exception occurred - " + e.getMessage());
            try {
                if (driver != null) {
                    takeScreenshot(driver, "TC038-Screenshot-fail");
                }
            } catch (IOException ex) {
                System.out.println("⚠️ Unable to take screenshot: " + ex.getMessage());
            }

        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("🔒 Browser closed successfully.");
            }
        }
    }

    // 📸 Screenshot Utility
    public static void takeScreenshot(WebDriver driver, String fileName) throws IOException {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String filePath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\"
                + fileName + ".png";
        FileHandler.copy(srcFile, new File(filePath));
        System.out.println("📸 Screenshot saved at: " + filePath);
    }
}
