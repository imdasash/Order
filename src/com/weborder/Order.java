package com.weborder;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Order {

	public static String returnFive(int generateNumber) {
		String returnedString = "";
		Random randomNumber = new Random();
		for (int i = 0; i < generateNumber; i++) {
			returnedString = returnedString + randomNumber.nextInt(10);
		}
		return returnedString;
	}

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver",
				"/Users/oleksandrdanylchuk/Documents/selenium dependencies/drivers/chromedriver");

		WebDriver driver = new ChromeDriver();
		// Where we going
		driver.navigate().to(" http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");

		// Login
		driver.findElement(By.name("ctl00$MainContent$username")).sendKeys("Tester");
		driver.findElement(By.name("ctl00$MainContent$password")).sendKeys("test");

		driver.findElement(By.cssSelector("input[type='submit']")).click();

		driver.findElement(By.linkText("Order")).click();

		Random rand = new Random();
		int num = rand.nextInt(100);

		driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtQuantity")).sendKeys("" + num);

		// Name + Middle name
		String chars = "abcdefghijklmnopqrstuvwxyz";
		StringBuilder mid = new StringBuilder();
		Random rnd = new Random();
		while (mid.length() < 5) {
			int index = (int) (rnd.nextFloat() * chars.length());
			mid.append(chars.charAt(index));
		}
		String Middle = mid.toString();

		driver.findElement(By.name("ctl00$MainContent$fmwOrder$txtName")).sendKeys("John " + Middle + " Smith");

		// Street
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox2")).sendKeys("123 Any st");

		// City
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox3")).sendKeys("Rockville");

		// State
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox4")).sendKeys("Virginia");

		// Zip Code
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox5")).sendKeys(returnFive(5));

		// Select Card Type

		int cardNum = rand.nextInt(3);

		switch (cardNum) {
		case 0:
			driver.findElement(By.id("ctl00_MainContent_fmwOrder_cardList_0")).click();

			driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox6")).sendKeys("4"+returnFive(15));

			break;
		case 1:
			driver.findElement(By.id("ctl00_MainContent_fmwOrder_cardList_1")).click();

			driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox6")).sendKeys("5"+returnFive(15));

			break;
		case 2:
			driver.findElement(By.id("ctl00_MainContent_fmwOrder_cardList_2")).click();

			driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox6")).sendKeys("3"+returnFive(14));

			break;

		}
		
		//Expiration date
		
		LocalDateTime ldt = LocalDateTime.now();
		ldt = ldt.plusYears(5);
		String validDate=DateTimeFormatter.ofPattern("MM/yy", Locale.ENGLISH).format(ldt);
		driver.findElement(By.name("ctl00$MainContent$fmwOrder$TextBox1")).sendKeys(validDate);
		
		//Process click
		driver.findElement(By.id("ctl00_MainContent_fmwOrder_InsertButton")).click();
		
		//Verify
		
		String actual = driver.findElement(By.xpath("//*[@id='ctl00_MainContent_fmwOrder']/tbody/tr/td/div/strong")).getText();
		
		if(actual.contains("New order has been successfully added.")) {
			System.out.println("pass");
		}else {
			System.out.println("fail");
			
		}

	}

}
