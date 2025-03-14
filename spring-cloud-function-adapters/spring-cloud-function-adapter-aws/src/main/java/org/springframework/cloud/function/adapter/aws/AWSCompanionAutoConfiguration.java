/*
 * Copyright 2021-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.function.adapter.aws;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.cloud.function.json.JacksonMapper;
import org.springframework.cloud.function.json.JsonMapper;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author Oleg Zhurakousky
 * @since 3.2
 *
 */
public class AWSCompanionAutoConfiguration implements ApplicationContextInitializer<GenericApplicationContext> {

	@Override
	public void initialize(GenericApplicationContext applicationContext) {
		applicationContext.registerBean("awsTypesMessageConverter", AWSTypesMessageConverter.class,
				() -> {
					if (CollectionUtils.isEmpty(applicationContext.getBeansOfType(JsonMapper.class).values())) {
						return new AWSTypesMessageConverter(new JacksonMapper(new ObjectMapper()));
					}
					else {
						return new AWSTypesMessageConverter(applicationContext.getBean(JsonMapper.class));
					}
				});
	}
}
