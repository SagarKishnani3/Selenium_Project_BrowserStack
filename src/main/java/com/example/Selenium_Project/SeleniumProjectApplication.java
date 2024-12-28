package com.example.Selenium_Project;

import ArticleScrapper.ArticleScraper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SeleniumProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeleniumProjectApplication.class, args);
		WebDriver driver = new ChromeDriver();
		try {
			List<String> articles = ArticleScraper.scrapeArticles(driver, "https://elpais.com/");
			for (String article : articles) {
				System.out.println(article);
			}
		} finally {
			driver.quit();
		}
	}

}
