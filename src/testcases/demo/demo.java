package testcases.demo;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

import actions.module1.BaiduHomePage;
import common.BaseTestCase;
import elements.module2.BaiduTiebaPageElements;


public class demo extends BaseTestCase{
	
	/**
	 * @TestCase  打开百度首页进入贴吧
	 */	
	@Test
	public void openTieba() {
		testName="打开百度首页进入贴吧";
		new BaiduHomePage().clickTieba();
		//断言
		assertTrue(new BaiduTiebaPageElements().getSearchButton().isDisplayed());
		
	}
}
