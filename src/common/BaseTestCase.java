package common;

import common.BaseFunction;
import listener.GenerateReport;
import org.testng.annotations.BeforeMethod;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.testng.ITest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;

/**
 * The common cases.
 * 
 * @author betty.shi
 * 
 */
public class BaseTestCase extends BaseFunction implements ITest{
	
	public String testName;
	
	/**
	 * 1.初始化 2. login 
	 * @throws IOException
	 * @author betty.shi
	 */
	@BeforeMethod
	public static void setUp() throws IOException {
        try {
		initialize();
		login(getProperty("login.account", ".\\resource\\env.properties"), getProperty("login.password", ".\\resource\\env.properties"));
        }
        catch(Exception e) {
        	Log4jUtil.error(e);
        }
	}

	/**
	 * 登录
	 * @throws IOException
	 * @author betty.shi
	 */
	public static void login(String account, String password) throws IOException {
		openAnURL(getProperty("login.url", ".\\resource\\env.properties"));
		sleep(2000);
		//省略login流程
	}


	@AfterMethod
	public static void tearDown() throws InterruptedException {
		try {
		logout();
		quitBrowser();
		}
		catch(Exception e) {
			Log4jUtil.error(e);
		}
	}

	/**
	 * 登出
	 * @author betty.shi
	 */
	public static void logout() {
		sleep(2000);
		//省略logout流程
	}

	/**
	 * 退出browser
	 * @author betty.shi
	 */
	public static void quitBrowser() {
		driver.quit();
	}
	/**
	 * 实现ITest接口，重写getTestName方法，在GenerateReport类中，用例执行失败或用例跳过时打印testName
	 * @author betty.shi
	 */
	@Override
	public String getTestName() {
		// TODO Auto-generated method stub
		return testName;
	}
	
	@AfterSuite
	public static void sendEmail() {
		try {
			String userName = ""; // 发件人邮箱  
	        String password = ""; // 发件人密码  
	        String smtpHost = ""; // 邮件服务器    
  
	        String to = ""; // 收件人，多个收件人以半角逗号分隔  
	        String cc = ""; // 抄送，多个抄送以半角逗号分隔  
	        String subject = ""; // 主题
	        String body = GenerateReport.returnResult(); // 正文，可以用html格式的哟  
	        // 附件的路径，多个附件也不怕  
	        List<String> ReportPath = Arrays.asList(GenerateReport.returnReportPath());
	        List<String> screenshotPath = GenerateReport.returnScreenshotPath();
	        List<String> attachments = new ArrayList<String>();
	        attachments.addAll(ReportPath);
	        attachments.addAll(screenshotPath);
	        
	        SendEmail email = SendEmail.entity(smtpHost, userName, password, to, cc, subject, body, attachments);  
	  
	        email.send(); // 发送！     
		}
		catch(Exception e) {
			Log4jUtil.error(e);
		}
	}
	
}
