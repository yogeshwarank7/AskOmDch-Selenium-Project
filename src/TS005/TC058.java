package TS005;

import java.io.File;
import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

public class TC058 {

    public static void main(String[] args) {
        System.out.println("🔹 Step 1: Open browser and navigate to AskOmDch...");
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\ASUS\\eclipse-workspace\\learningSelenium\\Driver\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        String screenshotPath = "C:\\Users\\ASUS\\eclipse-workspace\\AskOmDch_Selenium_Project\\Screenshots\\";
        String screenshotName = "TC058-Screenshot-";

        try {
            driver.get("https://askomdch.com/");
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // ⚡ Instantly scroll to the bottom where the icon section is
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

            // ✅ Locate all 4 icons and their corresponding texts
            List<WebElement> icons = driver.findElements(By.xpath("//div[@class='wp-block-columns']//div[@class='wp-block-column']//div[@class='wp-block-image']"));
            List<WebElement> texts = driver.findElements(By.xpath("//div[@class='wp-block-columns']//div[@class='wp-block-column']//p"));

            System.out.println("🔹 Step 2: Checking text alignment under each icon...");

            if (icons.size() == texts.size() && icons.size() > 0) {
                boolean allAligned = true;

                for (int i = 0; i < icons.size(); i++) {
                    WebElement icon = icons.get(i);
                    WebElement text = texts.get(i);

                    Point iconLoc = icon.getLocation();
                    Point textLoc = text.getLocation();

                    int iconCenter = iconLoc.getX();
                    int textCenter = textLoc.getX();
                    int difference = Math.abs(iconCenter - textCenter);

                    System.out.println("   🔸 Icon " + (i + 1) + " text alignment check → Difference: " + difference + " px");

                    if (difference > 50) { // tolerance limit
                        allAligned = false;
                        System.out.println("   ❌ Text under Icon " + (i + 1) + " is NOT horizontally centered.");
                    } else {
                        System.out.println("   ✅ Text under Icon " + (i + 1) + " is horizontally centered.");
                    }
                }

                // Wait before capturing screenshot
                System.out.println("🔹 Step 3: Waiting 3 seconds before capturing screenshot...");
                Thread.sleep(3000);

                // Scroll the section into view
                WebElement iconSection = driver.findElement(By.xpath("//div[@class='wp-block-columns']"));
                js.executeScript("arguments[0].scrollIntoView({behavior:'instant', block:'center'});", iconSection);

                // Take screenshot
                File screenshot = iconSection.getScreenshotAs(OutputType.FILE);
                String result = allAligned ? "Pass" : "Fail";
                FileHandler.copy(screenshot, new File(screenshotPath + screenshotName + result + ".png"));

                if (allAligned) {
                    System.out.println("✅ TC058 Passed: All icon texts are horizontally centered properly.");
                } else {
                    System.out.println("❌ TC058 Failed: One or more texts are not aligned properly.");
                }

                System.out.println("📁 Screenshot saved at: " + screenshotPath + screenshotName + result + ".png");

            } else {
                throw new Exception("Icon or text elements not found!");
            }

        } catch (Exception e) {
            System.out.println("❌ TC058 Failed due to exception: " + e.getMessage());
            try {
                File failShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileHandler.copy(failShot, new File(screenshotPath + screenshotName + "Fail.png"));
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
