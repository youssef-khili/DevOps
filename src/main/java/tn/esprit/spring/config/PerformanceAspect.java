package tn.esprit.spring.config;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PerformanceAspect {
	


	@Around("execution(int tn.esprit.spring.services.*.*(..))")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
            return pjp.proceed();
    }

}
