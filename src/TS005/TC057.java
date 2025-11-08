package TS005;

import java.io.File;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

public class TC057 {

    public static void main(String[] args) {
        System.out.println("🔹 Step 1: Open browser and navigate to AskOmDch...");
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\ASUS\\eclipse-workspace\\learningSelenium\\Driver\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        String screenshotPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\";
        String screenshotName = "TC057-Screenshot-";

        try {
            driver.get("https://askomdch.com/");

            JavascriptExecutor js = (JavascriptExecutor) driver;

            // ⚡ Instantly scroll to bottom where icon section is
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

            // ✅ Locate the section containing all 4 icons (global shipping, best quality, etc.)
            WebElement iconSection = driver.findElement(
                    By.xpath("//div[@class=\"wp-block-columns\"]")
            );

            if (iconSection.isDisplayed()) {
                System.out.println("🔹 Step 2: Waiting 3 seconds before capturing screenshot...");
                Thread.sleep(3000); // Wait 3 seconds to allow rendering

                // Scroll section into center view
                js.executeScript("arguments[0].scrollIntoView({behavior:'instant', block:'center'});", iconSection);

                // ✅ Capture single screenshot for full 4-icon block
                File screenshot = iconSection.getScreenshotAs(OutputType.FILE);
                FileHandler.copy(screenshot, new File(screenshotPath + screenshotName + "Pass.png"));

                System.out.println("✅ TC057 Passed: All 4 icons (Global Shipping, Best Quality, Best Offers, Secure Payments) captured successfully.");
                System.out.println("📁 Saved at: " + screenshotPath + screenshotName + "Pass.png");
            } else {
                throw new Exception("Icon section not visible!");
            }

        } catch (Exception e) {
            System.out.println("❌ TC057 Failed: " + e.getMessage());
            try {
                File failShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileHandler.copy(failShot,
                        new File(screenshotPath + screenshotName + "Fail.png"));
                System.out.println("📸 Screenshot captured for failure.");
            } catch (Exception io) {
                System.out.println("⚠️ Unable to capture failure screenshot: " + io.getMessage());
            }
        } finally {
            driver.quit(); // ⚡ Instant close
            System.out.println("🧹 Browser closed instantly.");
        }
    }
}
