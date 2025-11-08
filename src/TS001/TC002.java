package TS001;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class TC002 {

    public static void main(String[] args) {

        WebDriver driver = null;

        try {
            // Step 1: Set up WebDriver
            System.setProperty("webdriver.chrome.driver", 
                "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

            driver = new ChromeDriver();
            driver.manage().window().maximize();

            // Step 2: Navigate to the website
            driver.get("https://askomdch.com");
            System.out.println("Navigated to https://askomdch.com");

            // Step 3: Locate and click on the logo
            WebElement logo = driver.findElement(By.xpath("//a[@class='custom-logo-link']//img"));
            logo.click();
            System.out.println("Clicked on the 'AskOmDch' logo.");

            // Step 4: Validate redirection to Home page
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.equals("https://askomdch.com/")) {
                System.out.println("✅ Clicking the logo redirects successfully to the homepage.");
                System.out.println("Test Case Passed ✅");
            } else {
                throw new Exception("❌ Logo click did not redirect to the homepage. Current URL: " + currentUrl);
            }

        } catch (Exception e) {
            System.out.println("❌ Test Case Failed - " + e.getMessage());

            // Screenshot on failure
            try {
                if (driver != null) {
                    TakesScreenshot ts = (TakesScreenshot) driver;
                    File src = ts.getScreenshotAs(OutputType.FILE);

                    // ✅ Create folder if not exists
                    String folderPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots";
                    File folder = new File(folderPath);
                    if (!folder.exists()) {
                        folder.mkdirs();
                        System.out.println("📁 Created folder: " + folderPath);
                    }

                    // ✅ Save screenshot with file name
                    String destPath = folderPath + "\\TC002_Screenshot.png";
                    FileUtils.copyFile(src, new File(destPath));
                    System.out.println("📸 Screenshot saved at: " + destPath);
                }
            } catch (IOException ioException) {
                System.out.println("⚠️ Failed to capture screenshot: " + ioException.getMessage());
            }

        } finally {
            // Step 5: Close browser
            if (driver != null) {
                driver.quit();
                System.out.println("Browser closed successfully.");
            }
        }
    }
}
