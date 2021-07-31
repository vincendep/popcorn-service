package it.vincendep.popcorn;

import it.vincendep.popcorn.infrastructure.Profile;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(Profile.DEVELOPMENT)
class PopcornApplicationTests {

	@Test
	void contextLoads() {}
}
