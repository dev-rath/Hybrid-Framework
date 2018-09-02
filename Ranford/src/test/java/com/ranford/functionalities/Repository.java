package com.ranford.functionalities;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Excel.Excel_Class;
import PageLibrary.AdminPage;
import PageLibrary.BranchesPage;
import PageLibrary.GenericPage;
import PageLibrary.LoginPage;
import TestBase.Base;
import Utility.Screenshot;

public class Repository extends Base{
	
	WebDriver driver;
	public ExtentReports extentreport;
	public ExtentTest extenttest;
	public void launch_Application()
	{
		Report_Extent();
		extenttest=extentreport.startTest("Start");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Carrygo\\Desktop\\chromedriver.exe");
		driver=new ChromeDriver();
		driver.get(read_testdata("sitUrl"));
		log.info("Chrome Browser launched");
		extenttest.log(LogStatus.PASS,"Launch Success");
		log.info("Url entered"+read_testdata("sitUrl"));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		log.info("Maximise the browser");
		String strTitle = driver.getTitle();
		if(strTitle.equals("KeXiM BANK"))
		{
			System.out.println("Title displayed correctly as :"+strTitle);
			log.info("Title displayed correctly as: "+strTitle);
			extenttest.log(LogStatus.PASS,"Unsuccessful Title:"+strTitle);
		}
		else
		{
			Screenshot.CaptureScreenShot("VerifyTitle");
			String path=Screenshot.CaptureScreenShot("VerifyTitle");
			extenttest.addScreenCapture(path);
			System.out.println("Incorrect Title Displayed:"+strTitle);
			log.info("Incorrect Title Displayed:"+strTitle);
			extenttest.log(LogStatus.FAIL,"Title displayed coreectly as:"+strTitle);
		}
		
		//Assert.assertEquals(driver.getTitle(), "HDFC BANK");
	}
	
	public void login_Application()
	{
		LoginPage.username_textfield(driver).sendKeys(read_testdata("username"));
		LoginPage.password_textfield(driver).sendKeys(read_testdata("password"));
		LoginPage.login_button(driver).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		boolean blnLogout = AdminPage.logout_button(driver).isDisplayed();
		if(blnLogout)
		{
			Assert.assertTrue(true, "Login Successful");
		}
		else
		{
			Assert.assertTrue(true, "Login failed");
		}
	}
	
	public void clickbranches()
	{
		AdminPage.branches_button(driver).click();
	}
	
		
		
	/*public void createNewBranch()
	{
		BranchesPage.newBranch_btn(driver).click();
		BranchesPage.branchName_txt(driver).sendKeys(read_testdata("branchname"));
		BranchesPage.branchAddress1_txt(driver).sendKeys(read_testdata("address"));
		BranchesPage.zipcode_txt(driver).sendKeys(read_testdata("zipcode"));
		//GenericPage.dropDownSelection(driver, By.id(read_OR("branch_country"))).selectByValue(read_testdata("country"));
		GenericPage.dropDownSelection(driver, getlocator("branch_country")).selectByValue(read_testdata("country"));
		//GenericPage.dropDownSelection(driver, By.id(read_OR("branch_state"))).selectByValue(read_testdata("state"));
		GenericPage.dropDownSelection(driver, getlocator("branch_state")).selectByValue(read_testdata("state"));
		//GenericPage.dropDownSelection(driver, By.id(read_OR("branch_city"))).selectByValue(read_testdata("city"));
		GenericPage.dropDownSelection(driver, getlocator("branch_city")).selectByValue(read_testdata("city"));
		BranchesPage.cancel_btn(driver).click();
	}*/
	
	public void createBranch(String bname, String address, String zip, String country, String state, String city)
	{
		BranchesPage.newBranch_btn(driver).click();
		BranchesPage.branchName_txt(driver).sendKeys(bname);
		BranchesPage.branchAddress1_txt(driver).sendKeys(address);
		BranchesPage.zipcode_txt(driver).sendKeys(zip);
		GenericPage.dropDownSelection(driver, getlocator("branch_country")).selectByValue(country);
		GenericPage.dropDownSelection(driver, getlocator("branch_state")).selectByValue(state);
		GenericPage.dropDownSelection(driver, getlocator("branch_city")).selectByValue(city);
		BranchesPage.cancel_btn(driver).click();
	}
	
	
	public Object[][] excelContent(String fileName, String sheetName) throws IOException
	{
		Excel_Class.excelconnection(fileName, sheetName);
		int rc = Excel_Class.rcount();
		int cc = Excel_Class.ccount();
		
		String[][] data=new String[rc-1][cc];
		
		for(int r=1;r<rc;r++)
		{
			for(int c=0;c<cc;c++)
			{
				data[r-1][c] = Excel_Class.readdata(c, r);
			}
		}
		
		
		return data;
		
		
	}
	public void logout_Application()
	{
		AdminPage.branches_button(driver).click();
		driver.close();
		extentreport.endTest(extenttest);
		extentreport.flush();
	}
	public void Report_Extent()
	{
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		String timestamp= df.format(date);
		extentreport = new ExtentReports("C:\\Users\\Carrygo\\Downloads\\Ranford\\Ranford\\Reports"+"ExtentReportResults"+timestamp+".html",false);
	}
	

}
