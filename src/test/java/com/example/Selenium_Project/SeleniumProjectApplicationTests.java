package com.example.Selenium_Project;

import ArticleScrapper.ArticleScraper;
import BrowserStack.BrowserStackTest;
import OccurenceAnalyzer.HeaderAnalyzer;
import Translation.Translator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
class SeleniumProjectApplicationTests {

//	@Test
//	public void testScrapingOnBrowserStack() {
//		ArticleScraper.scrapeArticles(driver, "https://elpais.com/");
//	}

@Test
public void testScrapeArticles() {
	// Mock WebDriver and WebElement
	WebDriver driver = Mockito.mock(WebDriver.class);
	WebDriverWait wait = Mockito.mock(WebDriverWait.class);
	WebElement opinionLink = Mockito.mock(WebElement.class);

	// Mock behavior
	when(wait.until(any())).thenReturn(opinionLink);

	// Mock elements for articles
	WebElement article = Mockito.mock(WebElement.class);
	when(driver.findElements(By.cssSelector("article"))).thenReturn(List.of(article));

	// Mock article structure
	WebElement titleElement = Mockito.mock(WebElement.class);
	WebElement contentElement = Mockito.mock(WebElement.class);
	when(article.findElement(By.cssSelector("h2"))).thenReturn(titleElement);
	when(article.findElement(By.cssSelector("p"))).thenReturn(contentElement);
	when(titleElement.getText()).thenReturn("Mock Title");
	when(contentElement.getText()).thenReturn("Mock Content");

	// Test method
	List<String> titles = ArticleScraper.scrapeArticles(driver, "https://elpais.com/");
	Assert.assertNotNull(titles);
	Assert.assertEquals(titles.size(), 1);
	Assert.assertEquals(titles.get(0), "Mock Title");
}

	@Test
	public void testAnalyzeTranslatedHeaders() {
		// Test data
		List<String> headers = List.of("Hello World", "Hello Everyone", "World Peace");

		// Execute method
		HeaderAnalyzer.analyzeTranslatedHeaders(headers);

	}

	@Test
	public void testAnalyzeEmptyHeaders() {
		HeaderAnalyzer.analyzeTranslatedHeaders(List.of());
		// Verify no repeated words output
	}

	@Test
	public void testTranslateTitle() {
		// Mock translation service
		String mockTitle = "Hola Mundo";
		String translated = Translator.translateTitle(mockTitle);

		Assert.assertEquals(translated, "Hello World", "Translation did not match expected output.");
	}

	@Test
	public void testOnBrowserStack() throws Exception {
		// This calls the BrowserStack test method that initiates the cross-browser testing
		BrowserStackTest.main(null);
	}

}
