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

import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.Optional;

class ProceedingJoinPointMethodInvocation implements MethodInvocation {
    private final ProceedingJoinPoint joinPoint;

    ProceedingJoinPointMethodInvocation(ProceedingJoinPoint joinPoint) {
        this.joinPoint = joinPoint;
    }

    @Override
    public Object proceed() throws Throwable {
        return joinPoint.proceed();
    }

    @Override
    public Object[] getArguments() {
        return joinPoint.getArgs();
    }

    @Override
    public Object getThis() {
        return joinPoint.getThis();
    }

    @Override
    public Method getMethod() {
        return Optional.of(joinPoint).map(ProceedingJoinPoint::getSignature)
                .filter(MethodSignature.class::isInstance).map(MethodSignature.class::cast)
                .map(MethodSignature::getMethod)
                .get();
    }

    @Override
    public AccessibleObject getStaticPart() {
        return getMethod();
    }
}
