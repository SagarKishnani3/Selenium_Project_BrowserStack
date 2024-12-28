package BrowserStack;

import ArticleScrapper.ArticleScraper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class BrowserStackTest {
    // BrowserStack credentials

    public static void runOnBrowserStack(String os, String osVersion, String browser, String browserVersion, String device, String realMobile) throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (device == null) {
            // Desktop browsers
            capabilities.setCapability("os", os);
            capabilities.setCapability("os_version", osVersion);
            capabilities.setCapability("browser", browser);
            capabilities.setCapability("browser_version", browserVersion);
        } else {
            // Mobile browsers
            capabilities.setCapability("device", device);
            capabilities.setCapability("real_mobile", realMobile);
        }


        WebDriver driver = new ChromeDriver();

        try {
            // Call the scraping logic
            ArticleScraper.scrapeArticles(driver, "https://elpais.com/");
        } finally {
            driver.quit();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[5];

        // Configurations for 5 parallel threads
        threads[0] = new Thread(() -> {
            try {
                runOnBrowserStack("Windows", "10", "Chrome", "latest", null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        threads[1] = new Thread(() -> {
            try {
                runOnBrowserStack("OS X", "Ventura", "Safari", "latest", null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        threads[2] = new Thread(() -> {
            try {
                runOnBrowserStack("Windows", "11", "Edge", "latest", null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        threads[3] = new Thread(() -> {
            try {
                runOnBrowserStack(null, null, null, null, "iPhone 14 Pro", "true");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        threads[4] = new Thread(() -> {
            try {
                runOnBrowserStack(null, null, null, null, "Samsung Galaxy S23", "true");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Start threads
        for (Thread thread : threads) {
            thread.start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Cross-browser testing completed.");
    }
}
