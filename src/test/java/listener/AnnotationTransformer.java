//package listener;
//
//import java.lang.reflect.Constructor;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import org.testng.IAnnotationTransformer;
//import org.testng.annotations.ITestAnnotation;
//
///**
// * @Description : This method will be invoked by TestNG to give you a chance to modify a TestNG annotation read from your test classes. 
// *    			  You can change the values you need by calling any of the setters on the ITest interface. Note that only one of the 
// *    			  three parameters testClass, testConstructor and testMethod will be non-null.
// * Parameters	: annotation - The annotation that was read from your test class.
// * Parameters	: testClass - If the annotation was found on a class, this parameter represents this class (null otherwise).
// * Parameters	: testConstructor - If the annotation was found on a constructor, this parameter represents this constructor (null otherwise).
// * Parameters	: testMethod - If the annotation was found on a method, this parameter represents this method (null otherwise).
// **/
//public class AnnotationTransformer implements IAnnotationTransformer 
//{
//	@SuppressWarnings({ "rawtypes"})
//	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod)
//	{ 
//
//    	ArrayList<String> ae = new ArrayList<String>();
//		
//    	try 
//    	{
//				ae = test.start();
//    	}
//    	catch (IllegalAccessException e) 
//    	{
//				e.printStackTrace();
//		}
//    	catch (IllegalArgumentException e) 
//		{
//				e.printStackTrace();
//		}
//    	catch (InvocationTargetException e) 
//    	{
//				e.printStackTrace();
//		}
//    	catch (NoSuchMethodException e) 
//    	{
//				e.printStackTrace();
//		}
//    	catch (SecurityException e) 
//    	{
//				e.printStackTrace();
//		}
//			
//		if (ae.contains(annotation.getTestName())) 
//		{
//		    annotation.setEnabled(true);
//		}
//	}
//}
