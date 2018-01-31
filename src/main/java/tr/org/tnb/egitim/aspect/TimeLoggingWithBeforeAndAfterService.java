package tr.org.tnb.egitim.aspect;

import java.lang.reflect.Method;
import java.math.BigDecimal;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

public class TimeLoggingWithBeforeAndAfterService 
	implements MethodBeforeAdvice, AfterReturningAdvice {

	private long start = 0;
	
	@Override
	public void before(Method method, Object[] args, Object target)
			throws Throwable {
		start = System.nanoTime();
	}

	@Override
	public void afterReturning(Object returnValue, Method method, 
			Object[] args, Object target) throws Throwable {
		
		long elapsedTime = System.nanoTime() - start;
		String className = target.getClass().getCanonicalName();
		String methodName = method.getName();
		System.out.println("Execution of " 
				+ className + "#" + methodName
				+ " ended in " 
				+ new BigDecimal(elapsedTime).divide(new BigDecimal(1000000)) 
				+ " milliseconds");		
	}

	

}
