package TS005;

import java.io.File;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class TC056 {

    public static void main(String[] args) {
        System.out.println("🔹 Step 1: Open browser and navigate to site...");
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\ASUS\\eclipse-workspace\\learningSelenium\\Driver\\chromedriver.exe");

        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        try {
            driver.get("https://askomdch.com/");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            System.out.println("🔹 Step 2: Scrolling to bottom of page...");
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(1000);

            // ✅ Correct and stable locator for "Secure Payments" section
            By securePaymentsLocator = By.xpath("//div[@class=\"wp-block-column\"][4]");

            WebElement securePayments = wait.until(ExpectedConditions.visibilityOfElementLocated(securePaymentsLocator));

            System.out.println("🔹 Step 3: Scrolling element into view...");
            js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", securePayments);
            Thread.sleep(800);

            System.out.println("🔹 Step 4: Capturing screenshot...");
            File screenshot = securePayments.getScreenshotAs(OutputType.FILE);
            FileHandler.copy(screenshot, new File(
                    "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\TC056_SecurePayments.png"));

            System.out.println("✅ TC056 Passed: Screenshot of 'Secure Payments' captured successfully!");

        } catch (Exception e) {
            System.out.println("❌ TC056 Failed: " + e.getMessage());
        } finally {
            driver.quit();
            System.out.println("🧹 Browser closed successfully.");
        }
    }
}
