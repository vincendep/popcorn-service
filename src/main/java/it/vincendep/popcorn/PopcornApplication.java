package it.vincendep.popcorn;

import it.vincendep.popcorn.batch.JobLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import static org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE;

@SpringBootApplication
@ComponentScan(
		basePackageClasses = {PopcornApplication.class},
		excludeFilters = {@ComponentScan.Filter(type = ASSIGNABLE_TYPE, value = JobLauncher.class)})
public class PopcornApplication {

	public static void main(String[] args) {
		SpringApplication.run(PopcornApplication.class, args);
	}
}
