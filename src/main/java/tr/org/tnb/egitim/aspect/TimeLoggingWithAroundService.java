package tr.org.tnb.egitim.aspect;

import java.math.BigDecimal;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

@Service
public class TimeLoggingWithAroundService {
	public void logExecTime(ProceedingJoinPoint jp) throws Throwable {
		long startTime = System.nanoTime();
		String className = jp.getTarget().getClass().getCanonicalName();
		String methodName = jp.getSignature().getName();
		jp.proceed();
		long elapsedTime = System.nanoTime() - startTime;
		System.out.println("Execution of " + className + "#" + methodName
				+ " ended in "
				+ new BigDecimal(elapsedTime).divide(new BigDecimal(1000000))
				+ " milliseconds");
	}
}
