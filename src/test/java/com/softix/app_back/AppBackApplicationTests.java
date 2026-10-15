package com.softix.app_back;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"spring.flyway.enabled=false",
		"spring.jpa.hibernate.ddl-auto=update"
})
class AppBackApplicationTests {

	@Test
	void contextLoads() {
	}

}
