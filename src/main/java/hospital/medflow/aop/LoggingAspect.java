package hospital.medflow.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* hospital.medflow.service.*.*(..))")
    public void serviceLayer() {}

    @Before("serviceLayer()")
    public void logBefore(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info(">>> Method start: {} | Args: {}", method, Arrays.asList(args));
    }

    @AfterReturning(pointcut = "serviceLayer()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("<<< Method end: {} | Result: {}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(pointcut = "serviceLayer()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
        log.error("Exception: {} | Method: {} | Message: {}",
                exception.getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                exception.getMessage());
    }

    @AfterThrowing(pointcut = "execution(* hospital.medflow.validator.*.*(..))", throwing = "e")
    public void logValidator(JoinPoint joinPoint, Exception e) {
        log.error("Validation error in: {} | Reason: {}", joinPoint.getSignature().getName(), e.getMessage());
    }

    @Around("serviceLayer()")
    public Object measureTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            long elapsed = System.currentTimeMillis() - start;

            if (elapsed > 1000) {
                log.warn("Method: {} working to slow - {}ms",
                        joinPoint.getSignature().getName(), elapsed);
            }
            return result;
        } catch (Exception e) {
            long elapsed = System.currentTimeMillis();
            log.error("Exception: {} - {}ms",
                    joinPoint.getSignature().getName(), elapsed);
            throw e;
        }
    }

}
