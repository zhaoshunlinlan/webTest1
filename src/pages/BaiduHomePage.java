package pages;

import org.openqa.selenium.By;
import util.BaseFunction;

public class BaiduHomePage extends BaseFunction{
	//Ìù°É	
	public By tieba = By.name("tj_trtieba");

	/**
	* @description µã»÷Ìù°É
	* 
	*/
	public void clickTieba() {
		click(tieba); 
		waitForElementPresent(10, new BaiduTiebaPage().searchButton);
	}
}
