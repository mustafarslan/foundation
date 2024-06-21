package com.foundation.core.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.foundation.core.repository")
@ComponentScan(basePackages = { "com.foundation.core.service" })
public class ElasticSearchConfiguration extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.url}")
    private String elasticsearchUrl;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder().connectedTo(elasticsearchUrl).build();
    }
}
