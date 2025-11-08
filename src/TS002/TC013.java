package TS002;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class TC013 {

    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            // Step 1: Setup ChromeDriver
            System.setProperty("webdriver.chrome.driver",
                    "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();

            // Step 2: Open the website
            driver.get("https://askomdch.com/");
            System.out.println("✅ Opened https://askomdch.com/ successfully.");

            // Step 3: Scroll down slightly to ensure banner loads fully
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 300);");
            Thread.sleep(2000);

            // Step 4: Locate the banner heading and buttons
            WebElement bannerHeading = driver.findElement(By.xpath("//h1[contains(text(),'Raining Offers for Hot Summer!')]"));
            WebElement shopNowBtn = driver.findElement(By.xpath("//a[text()='Shop Now']"));
            WebElement findMoreBtn = driver.findElement(By.xpath("//a[text()='Find More']"));

            boolean isBannerDisplayed = bannerHeading.isDisplayed();
            boolean isTextCorrect = bannerHeading.getText().trim().equalsIgnoreCase("Raining Offers for Hot Summer!");
            boolean areButtonsVisible = shopNowBtn.isDisplayed() && findMoreBtn.isDisplayed();

            // Step 5: Locate the entire banner container for screenshot
            WebElement bannerSection = driver.findElement(By.xpath(
                "//div[@class='wp-block-column is-vertically-aligned-center'][.//h1[@class='alignwide has-text-align-center has-white-color has-text-color has-huge-font-size']]"
            ));

            // ✅ Scroll banner section into full view before screenshot
            js.executeScript("arguments[0].scrollIntoView(true);", bannerSection);
            Thread.sleep(1500);

            if (isBannerDisplayed && isTextCorrect && areButtonsVisible) {
                System.out.println("✅ Banner and buttons are visible with correct text.");

                // Optional: Check button functionality
                shopNowBtn.click();
                Thread.sleep(1500);
                driver.navigate().back();
                Thread.sleep(1500);

                findMoreBtn.click();
                Thread.sleep(1500);
                driver.navigate().back();
                Thread.sleep(1500);

                System.out.println("✅ Both 'Shop Now' and 'Find More' buttons are functional.");

                // ✅ Take screenshot of entire banner div (your given XPath)
                takeElementScreenshot(bannerSection, "TC013-Screenshot-Pass");

                System.out.println("✅ Test Passed: Banner section and buttons verified successfully.");
            } else {
                System.out.println("❌ Test Failed: Banner text or buttons missing.");
                takeFullScreenshot(driver, "TC013-Screenshot-Fail");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Exception occurred: " + e.getMessage());
            if (driver != null) {
                takeFullScreenshot(driver, "TC013-Screenshot-Fail");
            }
        } finally {
            if (driver != null) {
                driver.quit();
                System.out.println("🔒 Browser closed after execution.");
            }
        }
    }

    // ✅ Screenshot of specific element (banner div)
    public static void takeElementScreenshot(WebElement element, String fileName) {
        try {
            File src = element.getScreenshotAs(OutputType.FILE);
            String folderPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\";
            new File(folderPath).mkdirs();
            File dest = new File(folderPath + fileName + ".png");
            FileHandler.copy(src, dest);
            System.out.println("📸 Element screenshot saved: " + dest.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("⚠️ Error saving element screenshot: " + e.getMessage());
        }
    }

    // ✅ Fallback: full-page screenshot
    public static void takeFullScreenshot(WebDriver driver, String fileName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String folderPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\";
            new File(folderPath).mkdirs();
            File dest = new File(folderPath + fileName + ".png");
            FileHandler.copy(src, dest);
            System.out.println("📸 Full-page screenshot saved: " + dest.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("⚠️ Error saving full screenshot: " + e.getMessage());
        }
    }
}
