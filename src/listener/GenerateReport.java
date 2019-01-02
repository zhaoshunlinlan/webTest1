package listener;  
  
import java.io.File;  
import java.io.IOException;  
import java.nio.file.Files;  
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.testng.ITestContext;
import org.testng.ITestResult;  
import org.testng.TestListenerAdapter;

import common.Log4jUtil;

/**
 * 生成测试报告.
 * 
 * @author betty.shi
 * 
 */
public class GenerateReport extends TestListenerAdapter{  
	
/*	  private Collection<ITestResult> m_passedTests = new ConcurrentLinkedQueue<>();
	  private Collection<ITestResult> m_failedTests = new ConcurrentLinkedQueue<>();
	  private Collection<ITestResult> m_skippedTests = new ConcurrentLinkedQueue<>();*/

    private static String reportPath;
    private static int pass = 0;
    private static int fail = 0;
    private static int skip = 0;
    private static int all;
    private static float successRate;
    private static StringBuilder s1;
    private static StringBuilder s2;
    private static Date begin;
    private static Date finish;
    private static long duration;
    static List<String> screenshotPaths = new ArrayList<String>();
  
    @Override  
    public void onStart(ITestContext context) {  
        File htmlReportDir = new File("test-output/report");    
        if (!htmlReportDir.exists()) {    
            htmlReportDir.mkdirs();    
        }    
        String reportName = formateDate()+".html";    
        reportPath = htmlReportDir+"/"+reportName;    
        File report = new File(htmlReportDir,reportName);    
        if(report.exists()){    
            try {    
                report.createNewFile();    
            } catch (IOException e) {    
                e.printStackTrace();    
            }    
        }    
        s1 = new StringBuilder("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"  
                + "<title >UI自动化测试报告</title></head><body>"  
                + "<div id=\"top\" align=\"center\"><p style=\"font-weight:bold;font-size:150%\">Test Results Report</p>"   
                + "<table width=\"90%\" height=\"80\" border=\"1\" align=\"center\" cellspacing=\"0\" rules=\"all\" style=\"table-layout:relative;\">"
                + "<thead style=\"background-color:#ADD8E6;\">"
                + "<tr>"
                + "<th>Overview</th>"
                + "<th>Tests</th>"
                + "<th>Passed</th>"
                + "<th>Skipped</th>"
                + "<th>Failed</th>"
                + "<th>Success rate</th>"
                + "<th>Duration(s)</th>"
                + "</tr>"
                + "</thead>");  
        s2 = new StringBuilder("<p style=\"font-weight:bold;\">详情列表</p>"
        		+ "<table width=\"90%\" height=\"80\" border=\"1\" align=\"center\" cellspacing=\"0\" rules=\"all\" style=\"table-layout:relative;\">"  
                + "<thead style=\"background-color:#ADD8E6;\">"  
                + "<tr>"  
                + "<th>测试用例</th>"  
                + "<th>测试用例结果</th>"  
                + "<th>Duration(s)</th>" 
                + "</tr>"  
                + "</thead>"  
                + "<tbody style=\"word-wrap:break-word;font-weight:bold;\" align=\"center\">"); 
        begin = new Date();

        
    }  
      
    @Override  
    public void onTestSuccess(ITestResult result) {  
        StringBuilder sb2 = new StringBuilder("<tr><td align=\"center\">");  
        sb2.append((result.getMethod().getRealClass()+"."+result.getMethod().getMethodName()).substring(16));  
        sb2.append("</td><td align=\"center\"><font color=\"green\">Passed</font></td>");         
        long time =
                (result.getEndMillis() - result.getStartMillis())/1000;
        sb2.append("</td><td align=\"center\">" + time + "</td></tr>");       
        s2.append(sb2);
        pass = pass+1;
    }  
  
    @Override  
    public void onTestSkipped(ITestResult result) {  
        StringBuilder sb2 = new StringBuilder("<tr><td align=\"center\">");  
        sb2.append((result.getMethod().getRealClass()+"."+result.getMethod().getMethodName()).substring(16));  
        sb2.append("</td><td align=\"center\"><font color=\"#DAA520\">Skipped</font><br>");
        sb2.append(result.getMethod().getMethodName());
        sb2.append("<p align=\"center\">测试用例<font color=\"red\">跳过</font></p></td>");  
/*        sb2.append("<br><a style=\"background-color:#DAA520;\">");  
        Throwable throwable = result.getThrowable();   
                sb2.append(throwable.getMessage());  
                sb2.append("</a></p></td>");  */     
        long time =
                (result.getEndMillis() - result.getStartMillis())/1000;
        sb2.append("</td><td align=\"center\">" + time + "</td></tr>");       
        s2.append(sb2);
        skip = skip+1;
    }  
      
    @Override  
    public void onTestFailure(ITestResult result) {  
    	String error;
    	try {    		
        StringBuilder sb2 = new StringBuilder("<tr><td align=\"center\">");    
        sb2.append((result.getMethod().getRealClass()+"."+result.getMethod().getMethodName()).substring(16));    
        sb2.append("</td><td align=\"center\"><font color=\"red\">Failed</font><br>"); 
        sb2.append(result.getTestName());
        sb2.append("<p align=\"center\">用例执行<font color=\"red\">失败</font>，原因：<br>");  
        sb2.append("<br><a style=\"background-color:#CCCCCC;\">");          
        Throwable throwable = result.getThrowable();    
        if(throwable.getMessage().indexOf("Session info")!=-1) 
        {
        	int endIndex = throwable.getMessage().indexOf("(Session info");     
            error = throwable.getMessage().substring(0, endIndex);
         }
/*        else if(throwable.getMessage()==null)
        {
            error = "login faied";
            System.out.println(error);

        }*/
        else 
        {
            error = throwable.getMessage();//断言失败只打印断言
        }
        sb2.append(error);
        sb2.append("</a></p></td>");  
       /*         sb2.append(name);

        ITestNGMethod Method = result.getMethod();
        sb2.append(Method.getMethodName());*/
        
        long time =
                (result.getEndMillis() - result.getStartMillis())/1000;
        sb2.append("</td><td align=\"center\">" + time + "</td></tr>");       
        s2.append(sb2);
        fail = fail+1;
        String classname = result.getTestClass().getName();
        String methodname = result.getMethod().getMethodName();
        TakeScreenshot shot = new TakeScreenshot();
        String screenshotPath = shot.takeScreenShot(classname, methodname);
        screenshotPaths.add(screenshotPath);
        
    	}
    	catch(Exception e) {
    		//没有Throwable的时候，比如login或logout的时候挂了，exception被catch住了就没有Throwable抛出来
    		StringBuilder sb2 = new StringBuilder("<tr><td align=\"center\">");    
            sb2.append((result.getMethod().getRealClass()+"."+result.getMethod().getMethodName()).substring(16));    
            sb2.append("</td><td align=\"center\"><font color=\"red\">Failed</font><br>"); 
            sb2.append(result.getTestName());
            sb2.append("<p align=\"center\">用例执行<font color=\"red\">失败</font>，原因：<br>");  
            sb2.append("<br><a style=\"background-color:#CCCCCC;\">"); 
            sb2.append(e);
            sb2.append("</a></p></td>");
            long time =
                    (result.getEndMillis() - result.getStartMillis())/1000;
            sb2.append("</td><td align=\"center\">" + time + "</td></tr>");       
            s2.append(sb2);
            fail = fail+1;
            String classname = result.getTestClass().getName();
            String methodname = result.getMethod().getMethodName();
            TakeScreenshot shot = new TakeScreenshot();
            String screenshotPath = shot.takeScreenShot(classname, methodname);
            screenshotPaths.add(screenshotPath);
            Log4jUtil.error(e);
    	}
    }  
  
    @Override  
    public void onFinish(ITestContext testContext) {
/*    	XmlSuite xmls = new XmlSuite();
    	System.out.println(xmls.getName());
    	System.out.println(xmls.getTest());
    	XmlTest xml = new XmlTest(xmls);
    	xml.setName("all");
    	System.out.println(xml.getName());*/
    	
/*    	String lalala = toString();
    	System.out.println(lalala);*/
    	all = fail + pass + skip;
    	successRate = (float)pass/(float)all*100;
    	finish = new Date();
    	duration = (finish.getTime()-begin.getTime())/1000;
    	StringBuilder sb1 = new StringBuilder("<tbody style=\"word-wrap:break-word;font-weight:bold;\" align=\"center\">"
                + "<tr>"
                + "<td align=\"center\">Smoke</td>"
                + "<td align=\"center\">" + all + "</td>"
                + "<td align=\"center\">" + pass + "</td>"
                + "<td align=\"center\">" + skip + "</td>"
                + "<td align=\"center\">" + fail + "</td>"
                + "<td align=\"center\">" + String.format("%.2f", successRate) + "%" + "</td>"
                + "<td align=\"center\">" + duration + "</td>"
                + "</tr>"
                + "</tbody>"
                + "</table>");

        StringBuilder sb2 = new StringBuilder("</tbody></table><a href=\"#top\">返回顶部</a></div></body>");  
        sb2.append("</html>");  
        s1.append(sb1);
        String res = s1.toString();
        String res2 = s2.toString();
        String msg = sb2.toString();
        try {    
            Files.write((Paths.get(reportPath)),res.getBytes("utf-8")); 
            Files.write((Paths.get(reportPath)),res2.getBytes("utf-8"),StandardOpenOption.APPEND); 
            Files.write((Paths.get(reportPath)),msg.getBytes("utf-8"),StandardOpenOption.APPEND);  
        } catch (IOException e) {    
            e.printStackTrace();    
        }   
      
    }  
  
    public static String formateDate(){  
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");  
        Calendar cal = Calendar.getInstance();  
        Date date = cal.getTime();  
        return sf.format(date);  
    }  
    
    public static String returnResult() {
    	StringBuilder s = s1.append(s2);
    	String res = s.toString();
		return res;  	
    }
    
    public static String returnReportPath() {
		return reportPath;  	
    }
    
    public static List<String> returnScreenshotPath() {   	
		return screenshotPaths;  	
    }
/*    public List<ITestResult> getPassedTests() {
        return new ArrayList<>(m_passedTests);
      }
    public List<ITestResult> getFailedTests() {
        return new ArrayList<>(m_failedTests);
      }
    public List<ITestResult> getSkippedTests() {
        return new ArrayList<>(m_skippedTests);
      }
    public String toString(){
    	return Objects.toStringHelper(getClass())
    	        .add("passed", getPassedTests().size())
    	        .add("failed", getFailedTests().size())
    	        .add("skipped", getSkippedTests().size())
    	        .toString();
    	  
    }
*/
    
}  

