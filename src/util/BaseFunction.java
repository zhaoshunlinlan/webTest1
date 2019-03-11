package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * The common methods.
 * 
 * @author betty.shi
 * 
 */
public class BaseFunction {

	public static WebDriver driver;
	
	/**
	 * 从配置文件中取值
	 * @return
	 * @throws IOException
	 * @author betty.shi
	 */
	public static String getProperty(String key, String filePath) {
		Properties p = new Properties();
		try {		
     		InputStream ips = new FileInputStream(filePath);
			BufferedReader bf = new BufferedReader(new  InputStreamReader(ips,"UTF-8"));//解决读取properties文件中产生中文乱码的问题
			p.load(bf);
		}catch (Exception e) {
			Log4jUtil.error(e);
		}		
		return p.getProperty(key);		
	}
	
	/**
	 * 直接等待
	 * @author betty.shi
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 点击一个元素
	 * @author betty.shi
	 */
	public static void click(WebElement element) {
		element.click();
	}

	/**
	 * 向输入框输入值
	 * @author betty.shi
	 */
	public static void setText(WebElement webElement, String text) {
		webElement.clear();
		webElement.sendKeys(text);
	}
	
	/**
	 * 初始化chromedriver
	 * @throws IOException
	 * @author betty.shi
	 */
	public static void initialize() {
		String chDriver = getProperty("driver.path.chrome", ".\\resource\\env.properties");
		String chrome = getProperty("chrome.path", ".\\resource\\env.properties");
		System.setProperty("webdriver.chrome.driver", chDriver);
		System.setProperty("webdriver.chrome.bin", chrome);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
		
	/**
	 * 退出browser
	 * @author betty.shi
	 */
	public static void quitBrowser() {
		driver.quit();
	}

	/**
	 * open url
	 * @author betty.shi
	 */
	public static void openAnURL(String url) {
		try {
			driver.get(url);
		} catch(Exception e) {
			Log4jUtil.error(e);
		}		
	}

	/**
	 * get element
	 * @author betty.shi
	 */
	public static WebElement getElement(By locator) {
		return driver.findElement(locator);
	}
	
	/**
	 * get elements
	 * @author betty.shi
	 */
	public static List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}
	
	/**
	 * 点击一个元素
	 * @author betty.shi
	 */
	public static void click(By locator) {
		getElement(locator).click();
	}

	/**
	 * 向输入框输入值
	 * @author betty.shi
	 */
	public static void setText(By locator, String text) {
		getElement(locator).clear();
		getElement(locator).sendKeys(text);
	}
	
	/**
	 * 在输入框中上传文件
	 * @author betty.shi
	 * @parameter location 选择器,file 文件路径
	 */
	public static void setFile(By locator, String pathName) {
		String absolutePath=getAbsolutePath(new File(pathName));
		driver.findElement(locator).sendKeys(absolutePath);
	}
	
	/**
	 * 从一组元素中选取值为xxx的元素
	 * @param locator为这一组元素的定位
	 * @author betty.shi
	 */
	public static WebElement findElementByText(By locator, String text) {
		List<WebElement> elements = getElements(locator);
		WebElement element = null;
		for (WebElement e : elements) {
			if (e.getText().equals(text)) {
				element = e;
			}
		}
		return element;

	}

    /**
     * 从一组元素中选取值为xxx的元素并返回索引值
     * @author betty.shi
     */
    public static int findElementByTextReturnIndex(By locator, String text) {
    	List<WebElement> elements = getElements(locator);
		int index = 0 ;
		for (int i=0; i<elements.size(); i++) {
			if (elements.get(i).getText().contains(text)) {
				 index = i;
			}
		}
		return index;
    }
    
	/**
	 * 从一组元素中选取值包含xxx的元素
	 * @param locator为这一组元素的定位
	 * @author betty.shi
	 */
	public static WebElement findElementByContainsText(By locator, String text) {
		List<WebElement> elements = getElements(locator);
		WebElement element = null;
		for (WebElement e : elements) {
			if (e.getText().contains(text)) {
				element = e;
			}
		}
		return element;

	}
	
	/**
	 * 等待直到某元素的出现
	 * @author betty.shi
	 */
	public static boolean waitForElementPresent(int timeout, By locator) {
		boolean isPresent = false;
		try {
			new WebDriverWait(driver, timeout)
			.ignoring(StaleElementReferenceException.class, NoSuchElementException.class)
			.until(ExpectedConditions.visibilityOfElementLocated(locator));		
			isPresent = true;
		} catch (Exception e) {
			isPresent = false;
		}
		return isPresent;
	}
	
	/**
	 * 等待直到某元素的出现
	 * @author betty.shi
	 */
	public static boolean waitForElementPresent(int timeout, WebElement element) {
		boolean isPresent = false;
		try {
			new WebDriverWait(driver, timeout)
			.ignoring(StaleElementReferenceException.class, NoSuchElementException.class)
			.until(ExpectedConditions.visibilityOf(element));
			isPresent = true;
		} catch (Exception e) {
			isPresent = false;
		}
		return isPresent;
	}
	
	/**
	 * 等待直到包含某文本的元素的出现
	 * @author betty.shi
	 */
	public static boolean waitForElementContainsTextPresent(int timeout, By locator,String text) {
		boolean isPresent = false;
		try {
			new WebDriverWait(driver, timeout)
			.ignoring(StaleElementReferenceException.class, NoSuchElementException.class)
			.until(ExpectedConditions.visibilityOf(findElementByContainsText(locator, text)));		
			isPresent = true;
		} catch (Exception e) {
			isPresent = false;
		}
		return isPresent;
	}
	
	/**
	 * 等待某个位置的元素的文本为xxx
	 * @author betty.shi
	 */
	public static boolean waitForTextToBePresentInElementLocated(int timeout, By locator,String text) {
		boolean isPresent = false;
		try {
			new WebDriverWait(driver, timeout)
			.ignoring(StaleElementReferenceException.class, NoSuchElementException.class)
			.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));		
			isPresent = true;
		} catch (Exception e) {
			isPresent = false;
		}
		return isPresent;
	}
	
	/**
	 * 等待直到某元素消失
	 * @author betty.shi
	 */
	public static boolean waitForElementInvisibility(int timeout, By locator) {
		try {
			new WebDriverWait(driver, timeout)
			.ignoring(StaleElementReferenceException.class, NoSuchElementException.class)
			.until(ExpectedConditions.invisibilityOfElementLocated(locator));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 等待直到某元素可以点击
	 * @author betty.shi
	 */
	public static boolean waitForElementClickable(int timeout, By locator) {
		try {
			new WebDriverWait(driver, timeout)
			.ignoring(StaleElementReferenceException.class, NoSuchElementException.class)
			.until(ExpectedConditions.elementToBeClickable(locator));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
		
	
	public static int getNum(int start,int end) {
        return (int)(Math.random()*(end-start+1)+start);
    }
	
	/**
	 * 随机生成手机号
	 * @author betty.shi
	 */
    public static String getTel() {
    	String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
        int index=getNum(0,telFirst.length-1);
        String first=telFirst[index];
        String second=String.valueOf(getNum(1,888)+10000).substring(1);
        String third=String.valueOf(getNum(1,9100)+10000).substring(1);
        return first+second+third;
    }
    
    /**
     * 按下键盘回车键
     * @author betty.shi
     */
    public static void pressEnterKey(WebElement element) {
    	element.sendKeys(Keys.ENTER);    	
    }
    
    /**
     * 按下键盘回车键
     * @author betty.shi
     */
    public static void pressEnterKey(By locator) {
    	getElement(locator).sendKeys(Keys.ENTER);
    }
    
    /**
     * 判断元素是否存在
     * @author betty.shi
     */
    public static boolean doesWebElementExist(By locator) {
    	try 
    	{ 
    		getElement(locator); 
    		return true; 
            } 
    	catch (Exception e) { 
    		return false; 
            } 
    } 
    
    /**
     * 日期加n个月
     * @author sara.zhou
     * @throws ParseException 
     */
    public static String getDatePlusMonths(String date,int n) throws ParseException {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Date dt=dateFormat.parse(date);
    	Calendar rightNow=Calendar.getInstance();
    	rightNow.setTime(dt);
    	rightNow.add(Calendar.MONTH, n);
    	Date dateTime=rightNow.getTime();
    	String datePlusMonthsString = dateFormat.format(dateTime);
    	return datePlusMonthsString;
    }
    
	/**
     * 获取String类型的日期
     * @author sara.zhou
     */
     public static String getCurentDateString() {
     	//获取当前日期
     	Date date = new Date();
     	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
     	//获取String类型的日期
     	String currentDateString=dateFormat.format(date); 
     	return currentDateString;
     }
     
     /**
      * 获取String类型的时间
      * @author sara.zhou
      */
     public static String getCurentTimeString() {
     	//获取当前时间
     	Date date = new Date();
     	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
     	//获取String类型的时间
     	String currentTimeString = timeFormat.format(date);
     	return currentTimeString;
     }
     
     /**
      * 获取String类型的日期时间
      * @author sara.zhou
      */
     public static String getCurentDateTimeString() {
     	//获取当前时间
     	Date date = new Date();
     	SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
     	//获取String类型的时间
     	String dateTimeString = dateTimeFormat.format(date);
     	return dateTimeString;
     }
           
    /**
     * 鼠标移到某元素上
     * @author aimee.yang
     */ 
    
    public static void mouseOver(By locator) {   	
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(locator)).perform();		
	}
    
    /**
     * 根据相对路径获得绝对路径
     * @author aimee.yang
     */ 
    
    public static String getAbsolutePath(File file) {  
		 String fileAbsolutePath = file.getAbsolutePath();
		 return fileAbsolutePath;
    }
    
    /**
     * @description 刷新页面
     * @author aimee.yang
     */ 
    
    public static void refreshPage() {  
    	driver.navigate().refresh();
    }
}
