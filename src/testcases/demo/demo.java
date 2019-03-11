package testcases.demo;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import pages.BaiduHomePage;
import pages.BaiduTiebaPage;
import util.BaseTestCase;


public class demo extends BaseTestCase{
	
	/**
	 * @TestCase  打开百度首页进入贴吧
	 */	
	@Test
	public void openTieba() {
		testName="打开百度首页进入贴吧";
		new BaiduHomePage().clickTieba();
		//断言
		assertTrue(getElement(new BaiduTiebaPage().searchButton).isDisplayed());
		
	}
}
