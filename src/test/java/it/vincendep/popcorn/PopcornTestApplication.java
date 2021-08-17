package it.vincendep.popcorn;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(PopcornApplication.TEST)
public class PopcornTestApplication {}