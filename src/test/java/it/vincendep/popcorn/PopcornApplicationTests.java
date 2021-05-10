package it.vincendep.popcorn;

import it.vincendep.popcorn.batch.JobLauncher;
import it.vincendep.popcorn.config.Profile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles(Profile.DEVELOPMENT)
class PopcornApplicationTests {

	@Autowired
	private ApplicationContext ctx;

	@Test
	void contextLoads() {
	}

	@Test
	void onlyRequiredBeans() {
		assertThat(ctx.getBean(PopcornApplication.class)).isNotNull();
		assertThrows(NoSuchBeanDefinitionException.class, () -> ctx.getBean(JobLauncher.class));
	}
}
