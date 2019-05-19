package cn.bounter.common.model;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 应用日志切面
 */
@Aspect
@Component
public class AppLogAspect {

//    @Autowired
//    private ActionLogService actionLogService;

    @Pointcut("@annotation(cn.bounter.common.model.AppLog)")
    public void appLogPointCut() {
    }

    @AfterReturning("appLogPointCut()")
    public void addActionLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        //获取注解
        AppLog appLog = method.getAnnotation(AppLog.class);
        if (appLog == null) {
            return;
        }

        //保存数据库
//        actionLogService.save(
//                new ActionLog()
//                .setSubject(appLog.subject().getValue())
//                .setContent(appLog.content())
//                .setCreateBy(ShiroUtils.getUser())      //获取当前登录的用户
//                .setCreateTime(LocalDateTime.now())
//        );
    }

}
