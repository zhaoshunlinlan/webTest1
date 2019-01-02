package listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer{
	//private static Logger logger = Logger.getLogger(retry.class); 
    private int retryCount = 1;  
    private static int maxRetryCount = 1;  
   
    public boolean retry(ITestResult result) {   
        if (retryCount < maxRetryCount) { 
            retryCount++; 
            return true; 
        } 
        return false; 
    }

}
