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

package cherry.base.web.autoconfigure;

import cherry.base.web.app.healthcheck.HealthcheckController;
import cherry.base.web.app.healthcheck.HealthcheckService;
import cherry.base.web.interceptor.TraceInterceptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.util.function.Supplier;

@AutoConfiguration
public class BaseWebAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "traceInterceptor")
    public TraceInterceptor traceInterceptor(
            @Value("${traceInterceptor.useDynamicLogger}") boolean useDynamicLogger,
            @Value("${traceInterceptor.hideProxyClassNames}") boolean hideProxyClassNames,
            @Value("${traceInterceptor.logExceptionStackTrace}") boolean logExceptionStackTrace,
            @Value("${traceInterceptor.enterMessage}") String enterMessage,
            @Value("${traceInterceptor.exitMessage}") String exitMessage,
            @Value("${traceInterceptor.exceptionMessage}") String exceptionMessage
    ) {
        return new TraceInterceptor(
                useDynamicLogger,
                hideProxyClassNames,
                logExceptionStackTrace,
                enterMessage,
                exitMessage,
                exceptionMessage
        );
    }

    static class Healthcheck {

        @Bean
        @ConditionalOnMissingBean(name = "healthcheckController")
        public HealthcheckController healthcheckController(
                @Qualifier("healthcheckService") Supplier<Boolean> healthcheckService
        ) {
            return new HealthcheckController(healthcheckService);
        }

        @Bean
        @ConditionalOnMissingBean(name = "healthcheckService")
        public Supplier<Boolean> healthcheckService() {
            return new HealthcheckService();
        }
    }
}
