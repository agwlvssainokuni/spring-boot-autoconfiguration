/*
 * Copyright 2025 agwlvssainokuni
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cherry.base.web.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;

@Aspect
public class TraceInterceptor {

    private final CustomizableTraceInterceptor traceInterceptor;

    public TraceInterceptor(
            boolean useDynamicLogger,
            boolean hideProxyClassNames,
            boolean logExceptionStackTrace,
            String enterMessage,
            String exitMessage,
            String exceptionMessage
    ) {
        traceInterceptor = new CustomizableTraceInterceptor();
        traceInterceptor.setUseDynamicLogger(useDynamicLogger);
        traceInterceptor.setHideProxyClassNames(hideProxyClassNames);
        traceInterceptor.setLogExceptionStackTrace(logExceptionStackTrace);
        traceInterceptor.setEnterMessage(enterMessage);
        traceInterceptor.setExitMessage(exitMessage);
        traceInterceptor.setExceptionMessage(exceptionMessage);
    }

    @Around("""
            @annotation(cherry.base.web.annotation.TraceInterceptor)
            || @within(cherry.base.web.annotation.TraceInterceptor)
            """)
    public Object trace(ProceedingJoinPoint joinPoint) throws Throwable {
        return traceInterceptor.invoke(
                new ProceedingJoinPointMethodInvocation(joinPoint)
        );
    }

}
