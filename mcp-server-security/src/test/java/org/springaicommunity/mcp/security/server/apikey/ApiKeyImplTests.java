/*
 * Copyright 2025-2026 the original author or authors.
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

package org.springaicommunity.mcp.security.server.apikey;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Tests for {@link ApiKeyImpl}.
 *
 * @author Anil Kumar Veldurthi
 */
class ApiKeyImplTests {

	@Test
	void fromParsesIdAndSecret() {
		var apiKey = ApiKeyImpl.from("id.secret");

		assertThat(apiKey.getId()).isEqualTo("id");
		assertThat(apiKey.getSecret()).isEqualTo("secret");
	}

	@Test
	void fromPreservesDotsInSecret() {
		// Regression guard for the dots-in-secret fix: the secret is everything
		// after the first '.', so a secret that itself contains '.' must not be
		// truncated.
		var apiKey = ApiKeyImpl.from("id.sec.ret");

		assertThat(apiKey.getId()).isEqualTo("id");
		assertThat(apiKey.getSecret()).isEqualTo("sec.ret");
	}

	@Test
	void fromWithoutSeparatorThrows() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> ApiKeyImpl.from("no-dot"));
	}

	@Test
	void fromBlankThrows() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> ApiKeyImpl.from(""));
	}

	@Test
	@SuppressWarnings("NullAway") // intentionally passing null to verify it is rejected
	void fromNullThrows() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> ApiKeyImpl.from(null));
	}

}
