/*******************************************************************************
 * Copyright (C) 2018 Than Htike Aung
 * myapp-ui-backend - LoggingAspect.java
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.github.cataclysmuprising.myapp.ui.backend.common.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.cataclysmuprising.myapp.ui.backend.common.annotation.Loggable;

@Component
@Aspect
@Order(0)
public class LoggingAspect extends BaseAspect {
    private static final Logger applicationLogger = LogManager.getLogger("applicationLogs." + LoggingAspect.class.getName());

    @Before(value = "classAnnotatedWithLoggable(loggable) && publicMethod() && !initBinderMethod()")
    public void beforeMethod(JoinPoint joinPoint, Loggable loggable) throws Throwable {
        // get RequestMapping annotation of target class
        Object[] arguments = joinPoint.getArgs();
        applicationLogger.info(LOG_BREAKER_OPEN);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // get RequestMapping annotation of target class
        RequestMapping rootMapper = joinPoint.getTarget().getClass().getAnnotation(RequestMapping.class);
        // skip for non-servlet request methods.
        if (rootMapper == null) {
            return;
        }
        // get class level request mapping URL
        String rootMappingURL = rootMapper.value()[0];
        // get Class Name
        String className = joinPoint.getTarget().getClass().getSimpleName();

        String mappingURL = rootMappingURL;

        Annotation[] annotations = method.getAnnotations();
        String requestMethod = RequestMethod.GET.name();
        for (Annotation annotation : annotations) {
            if (annotation instanceof GetMapping) {
                GetMapping autualMapper = (GetMapping) annotation;
                if (autualMapper.value().length > 0) {
                    mappingURL += autualMapper.value()[0];
                }
                // set the request method
                requestMethod = RequestMethod.GET.name();
                break;
            }
            if (annotation instanceof PostMapping) {
                PostMapping autualMapper = (PostMapping) annotation;
                if (autualMapper.value().length > 0) {
                    mappingURL += autualMapper.value()[0];
                }
                // set the request method
                requestMethod = RequestMethod.POST.name();
                break;
            }
            if (annotation instanceof PutMapping) {
                PutMapping autualMapper = (PutMapping) annotation;
                if (autualMapper.value().length > 0) {
                    mappingURL += autualMapper.value()[0];
                }
                // set the request method
                requestMethod = RequestMethod.PUT.name();
                break;
            }
            if (annotation instanceof DeleteMapping) {
                DeleteMapping autualMapper = (DeleteMapping) annotation;
                if (autualMapper.value().length > 0) {
                    mappingURL += autualMapper.value()[0];
                }
                // set the request method
                requestMethod = RequestMethod.DELETE.name();
                break;
            }
        }

        // create for method String
        String methodName = joinPoint.getSignature().getName();
        StringBuffer sbMethod = new StringBuffer();
        sbMethod.append(" '" + methodName + "'");
        sbMethod.append(" Method [");
        sbMethod.append("mappingURL ('" + mappingURL + "') ,");
        sbMethod.append("requestMethod ('" + requestMethod + "') ");
        sbMethod.append("] ");
        applicationLogger.info(LOG_PREFIX + "Client request for" + sbMethod.toString() + " of '" + className + "' class." + LOG_SUFFIX);
        if (applicationLogger.isDebugEnabled()) {
            applicationLogger.debug("==================== Request parameters ===========================");
            if (arguments.length > 0) {
                for (Object arg : arguments) {
                    if (arg != null) {
                        applicationLogger.debug(LOG_PREFIX + arg.toString() + LOG_SUFFIX);
                    }
                }
            } else {
                applicationLogger.debug(LOG_PREFIX + "[EMPTY Request parameters]" + LOG_SUFFIX);
            }
            applicationLogger.debug("===================================================================");
        }
    }

    @AfterReturning(value = "classAnnotatedWithLoggable(loggable) && publicMethod() && !initBinderMethod()", returning = "serverResponse")
    public void afterReturnMethod(JoinPoint joinPoint, Loggable loggable, Object serverResponse) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // get RequestMapping annotation of target class
        RequestMapping rootMapper = joinPoint.getTarget().getClass().getAnnotation(RequestMapping.class);
        // skip for non-servlet request methods.
        if (rootMapper == null) {
            return;
        }
        // get class level request mapping URL
        String rootMappingURL = rootMapper.value()[0];
        // get Class Name
        String className = joinPoint.getTarget().getClass().getSimpleName();

        String mappingURL = rootMappingURL;

        Annotation[] annotations = method.getAnnotations();
        String requestMethod = RequestMethod.GET.name();
        for (Annotation annotation : annotations) {
            if (annotation instanceof GetMapping) {
                GetMapping autualMapper = (GetMapping) annotation;
                if (autualMapper.value().length > 0) {
                    mappingURL += autualMapper.value()[0];
                }
                // set the request method
                requestMethod = RequestMethod.GET.name();
                break;
            }
            if (annotation instanceof PostMapping) {
                PostMapping autualMapper = (PostMapping) annotation;
                if (autualMapper.value().length > 0) {
                    mappingURL += autualMapper.value()[0];
                }
                // set the request method
                requestMethod = RequestMethod.POST.name();
                break;
            }
            if (annotation instanceof PutMapping) {
                PutMapping autualMapper = (PutMapping) annotation;
                if (autualMapper.value().length > 0) {
                    mappingURL += autualMapper.value()[0];
                }
                // set the request method
                requestMethod = RequestMethod.PUT.name();
                break;
            }
            if (annotation instanceof DeleteMapping) {
                DeleteMapping autualMapper = (DeleteMapping) annotation;
                if (autualMapper.value().length > 0) {
                    mappingURL += autualMapper.value()[0];
                }
                // set the request method
                requestMethod = RequestMethod.DELETE.name();
                break;
            }
        }
        // create for method String
        String methodName = joinPoint.getSignature().getName();
        StringBuffer sbMethod = new StringBuffer();
        sbMethod.append(" '" + methodName + "'");
        sbMethod.append(" Method [");
        sbMethod.append("mappingURL ('" + mappingURL + "') ,");
        sbMethod.append("requestMethod ('" + requestMethod + "') ");
        sbMethod.append("] ");
        applicationLogger.info(LOG_PREFIX + "Servlet Request : ==> " + sbMethod.toString() + " of '" + className + "' class has been successfully initiated from server and navigate to new Tiles view [" + serverResponse + "]" + LOG_SUFFIX);
        applicationLogger.info(LOG_BREAKER_CLOSE);
    }

    @Pointcut("@within(loggable)")
    public void classAnnotatedWithLoggable(Loggable loggable) {
    }
}
