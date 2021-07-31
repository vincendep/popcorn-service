package it.vincendep.popcorn.infrastructure;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings.Builder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;

@Configuration
class MongoConfig  extends AbstractReactiveMongoConfiguration {

	@Value("${MONGO_CONNECTION_STRING}")
	private String connectionString;
	
	@Override
	protected void configureClientSettings(Builder builder) {
		builder.applyConnectionString(new ConnectionString(connectionString));
	}
	
    @Override
    protected String getDatabaseName() {
        return "popcorn";
    }
}
