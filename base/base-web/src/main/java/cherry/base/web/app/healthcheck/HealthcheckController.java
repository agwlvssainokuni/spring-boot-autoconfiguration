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

package cherry.base.web.app.healthcheck;

import cherry.base.web.annotation.TraceInterceptor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Supplier;

@RestController
@TraceInterceptor
public class HealthcheckController {

    private final Supplier<Boolean> healthcheckService;

    public HealthcheckController(Supplier<Boolean> healthcheckService) {
        this.healthcheckService = healthcheckService;
    }

    @RequestMapping({"/healthcheck"})
    public String healthcheck() {
        if (healthcheckService.get()) {
            return "OK";
        } else {
            return "FAIL";
        }
    }
}
