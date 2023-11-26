package com.integration.auth.common.config.db;

import com.mongodb.Block;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.connection.ConnectionPoolSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Objects;

@Configuration
@EnableMongoRepositories(basePackages = { "com.integration" })
public class MongoDataSourceConfiguration extends AbstractMongoClientConfiguration {

	private static final Logger LOG = LoggerFactory.getLogger(MongoDataSourceConfiguration.class);

	private MongoProperties mongoProperties;

	private Environment env;

	public MongoDataSourceConfiguration(MongoProperties mongoProperties, Environment env) {
		this.mongoProperties = mongoProperties;
		this.env = env;
	}

	@Override
	protected String getDatabaseName() {
		return mongoProperties.getDatabase();
	}

	@Override
	public MongoClient mongoClient() {
		LOG.info(" Initializing mongo client ");
		return MongoClients.create(
				MongoClientSettings.builder().applyConnectionString(new ConnectionString(mongoProperties.getUri()))
						.applyToConnectionPoolSettings(connectionPoolSettings()).build());
	}

	private Block<ConnectionPoolSettings.Builder> connectionPoolSettings() {
		String minSize = env.getProperty("threadPool.connectionPool.minimumIdle");
		String maxSize = env.getProperty("threadPool.connectionPool.maximumPoolSize");
		return builder -> {
			if (Objects.nonNull(minSize)) {
				builder.minSize(Integer.parseInt(minSize));
			}
			if (Objects.nonNull(maxSize)) {
				builder.maxSize(Integer.parseInt(maxSize));
			}
		};
	}

}
