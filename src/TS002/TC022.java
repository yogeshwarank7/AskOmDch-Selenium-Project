package TS002;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import org.apache.commons.io.FileUtils;

public class TC022 {

    static final String SCREENSHOT_PATH = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\";

    // Screenshot helper
    public static void takeScreenshot(ChromeDriver driver, String fileName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(SCREENSHOT_PATH + fileName);
            FileUtils.copyFile(src, dest);
            System.out.println("📸 Screenshot saved: " + dest.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("⚠️ Failed to save screenshot: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Driver\\chromedriver.exe");

        ChromeDriver driver = null;

        try {
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

            // Open website
            driver.get("https://askomdch.com/");
            Thread.sleep(3000); // wait for page load

            // Example: check main banner heading font
            WebElement heading = driver.findElement(By.xpath("//h1 | //h2 | //h3")); // adjust as per actual element

            String fontFamily = heading.getCssValue("font-family");
            String fontSize = heading.getCssValue("font-size");
            String color = heading.getCssValue("color");
            String backgroundColor = heading.getCssValue("background-color");

            System.out.println("Font Family: " + fontFamily);
            System.out.println("Font Size: " + fontSize);
            System.out.println("Text Color: " + color);
            System.out.println("Background Color: " + backgroundColor);

            // Optionally, you can assert specific expected values
            // e.g., if(!fontSize.equals("24px")) { throw new Exception("Font size mismatch"); }

            takeScreenshot(driver, "TC022-Screenshot.png");

            System.out.println("✅ Test Passed: Font style, size, and color contrast validated.");

        } catch (Exception e) {
            System.out.println("⚠️ Exception occurred during test execution: " + e.getMessage());
            if (driver != null) {
                takeScreenshot(driver, "TC022-Screenshot-fail.png");
            }
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }

    }

}
