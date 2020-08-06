package com.github.gleans.aop;

import com.github.gleans.bean.ResultBean;
import com.github.gleans.exception.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;

import java.util.Arrays;

/**
 * 处理和包装异常
 *
 * @author 晓风轻 https://github.com/xwjie/PLMCodeTemplate
 */
@Slf4j
@Aspect
public class ControllerAOP {

    //切点方法
    public Object handlerControllerMethod(ProceedingJoinPoint pjp) {
        long startTime = System.currentTimeMillis();

        ResultBean<?> result;
        try {
            result = (ResultBean<?>) pjp.proceed();
            log.info(pjp.getSignature() + "use time:" + (System.currentTimeMillis() - startTime));
        } catch (Throwable e) {
            result = handlerException(pjp, e);
        }

        return result;
    }

    /**
     * 封装异常信息，注意区分已知异常（自己抛出的）和未知异常
     */
    private ResultBean<?> handlerException(ProceedingJoinPoint pjp, Throwable e) {
        ResultBean<?> result = new ResultBean<>();
        StringBuilder stringBuilder = new StringBuilder();

        if (e instanceof AuthException) {
            result.setMsg(e.getMessage());
            result.setCode(((AuthException) e).getCode());
        } else {
            stringBuilder.append("错误信息[").append(e.getLocalizedMessage()).append("]错误详细信息===》").append(Arrays.toString(e.getStackTrace()));
            result.setMsg(stringBuilder.toString());
            result.setCode(ResultBean.FAIL);
        }

        log.error("异常信息：{}", stringBuilder.toString());
        return result;
    }
}