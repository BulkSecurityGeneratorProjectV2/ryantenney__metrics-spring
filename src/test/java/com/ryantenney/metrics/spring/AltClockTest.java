/*
 * Copyright 2012 Ryan W Tenney (http://ryan.10e.us)
 *            and Martello Technologies (http://martellotech.com)
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
package com.ryantenney.metrics.spring;

import java.lang.reflect.Field;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yammer.metrics.core.Clock;
import com.yammer.metrics.core.MetricsRegistry;
import com.yammer.metrics.core.Clock.CpuTimeClock;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

public class AltClockTest {

	@Test
	public void testOverriddenClock() throws Throwable {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:alt-clock.xml");
		MetricsRegistry registry = ctx.getBean(MetricsRegistry.class);

		assertThat(getClockField().get(registry), instanceOf(CpuTimeClock.class));
	}

	protected Field getClockField() throws NoSuchFieldException {
		for (Field f : MetricsRegistry.class.getDeclaredFields()) {
			if (f.getType() == Clock.class) {
				f.setAccessible(true);
				return f;
			}
		}
		throw new NoSuchFieldException();
	}

}
