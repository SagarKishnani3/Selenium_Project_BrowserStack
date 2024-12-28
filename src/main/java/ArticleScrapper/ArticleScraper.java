package ArticleScrapper;

import OccurenceAnalyzer.HeaderAnalyzer;
import Translation.Translator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ArticleScraper {
    public static List<String> scrapeArticles(WebDriver driver, String url) {
        driver.get(url);

        // Wait for and click the 'Opinión' link using XPath
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement opinionLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='csw']/div[1]/nav/div/a[2]")));
        opinionLink.click();

        // Scrape articles from the 'Opinión' section (modify this part as needed)
        List<String> articleTitles = new ArrayList<>();

        List<String> translatedTitles = new ArrayList<>();
        List<WebElement> articles = driver.findElements(By.cssSelector("article"));
        for (int i = 0; i < Math.min(articles.size(), 5); i++) {
            WebElement article = articles.get(i);
            String title = article.findElement(By.cssSelector("h2")).getText();
            String content = article.findElement(By.cssSelector("p")).getText();
            System.out.println("Title: " + title);
            System.out.println("Content: " + content);
            if(title!=null){
                articleTitles.add(title);
                // Translate title and print original and translated versions
                String translatedTitle = Translator.translateTitle(title);
                System.out.println("Original: " + title);
                System.out.println("Translated: " + translatedTitle);
                translatedTitles.add(translatedTitle);
            }
            String imageUrl = getCoverImageUrl(article);
            if (imageUrl != null) {
                saveImage(imageUrl, title);
            }
        }
        HeaderAnalyzer.analyzeTranslatedHeaders(translatedTitles);
        return articleTitles;
    }

    private static String getCoverImageUrl(WebElement article) {
        try {
            WebElement imageElement = article.findElement(By.tagName("img"));
            return imageElement.getAttribute("src");
        } catch (Exception e) {
            System.err.println("No cover image found for this article.");
            return null;
        }
    }

    private static void saveImage(String imageUrl, String title) {
        try {
            String sanitizedTitle = title.replace(" ", "_").replaceAll("[^a-zA-Z0-9_]", "");
            File directory = new File("images");
            if (!directory.exists()) {
                directory.mkdir();
            }

            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            try (InputStream inputStream = connection.getInputStream();
                 FileOutputStream outputStream = new FileOutputStream(new File(directory, sanitizedTitle + ".jpg"))) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            System.out.println("Image saved for article: " + title);
        } catch (Exception e) {
            System.err.println("Failed to save image: " + e.getMessage());
        }
    }


}

