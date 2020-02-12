package listener;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import core.Application;

// TODO: Auto-generated Javadoc
// Set runtime level to 1 (run configurations in eclipse) to reduce the stack trace size on failure

/**
 * The listener interface for receiving failedTest events.
 * The class that is interested in processing a failedTest
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addFailedTestListener<code> method. When
 * the failedTest event occurs, that object's appropriate
 * method is invoked.
 *
 * @see FailedTestEvent
 */
public class FailedTestListener extends TestListenerAdapter {
	
	/* (non-Javadoc)
	 * @see org.testng.TestListenerAdapter#onTestFailure(org.testng.ITestResult)
	 */
	@Override
	public void onTestFailure(ITestResult result){
		System.out.println("Test failure details:" );
		System.out.println("   Test method: "+result.getMethod().getMethodName()+"()");
		System.out.println("   Rule: "+Application .getCurrentRule());
		for (String s: Application .getCurrentRow())
			System.out.print(" "+s);
		    System.out.println();
	}
}