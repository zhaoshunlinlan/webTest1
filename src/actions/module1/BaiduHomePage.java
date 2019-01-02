package actions.module1;

import common.BaseFunction;
import elements.module1.BaiduHomePageElements;
import elements.module2.BaiduTiebaPageElements;

public class BaiduHomePage extends BaseFunction{
	
	/**
	* @description µã»÷Ìù°É
	* 
	*/
	public void clickTieba() {
		click(new BaiduHomePageElements().getTieba()); 
		waitForElementPresent(10, new BaiduTiebaPageElements().getSearchButtonLocator());
	}
}
