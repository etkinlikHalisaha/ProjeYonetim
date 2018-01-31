package tr.org.tnb.egitim.aspect;

import java.math.BigDecimal;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AspectsWithAnnotationsService {
	@Before("execution(public * *(..))")
	public void before(JoinPoint joinPoint) {
		String className = joinPoint.getClass().getCanonicalName();
		String methodName = joinPoint.getSignature().getName();
		System.out.println("Before " + className + "#" + methodName);
	}
	
	@Pointcut("bean(*Controller)")
	public void anyControllerMethod() {
	}
	
	@Pointcut("within(tr.org.tnb.egitim.projeyonetimi.department..*)")
	public void anyBeansAnyMethodInDepartmentPackage() {
	}
	
	@After(value = "anyControllerMethod() && anyBeansAnyMethodInDepartmentPackage()")
	public void afterWithMultiplePointcut(JoinPoint joinPoint) {
		String className = joinPoint.getClass().getCanonicalName();
		String methodName = joinPoint.getSignature().getName();
		System.out.println("After " + className + "#" + methodName);
	}	

	@Around("execution(public * *(..))")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.nanoTime();
		String className = joinPoint.getTarget().getClass().getCanonicalName();
		String methodName = joinPoint.getSignature().getName();
		Object output = joinPoint.proceed();
		long elapsedTime = System.nanoTime() - startTime;
		System.out.println("Execution of " 
				+ className + "#" + methodName
				+ " ended in " 
				+ new BigDecimal(elapsedTime).divide(new BigDecimal(1000000)) 
				+ " milliseconds");
		return output;	
	}
	
}
