package com.omit.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

import javax.annotation.Resource;

/**
 * Created by Alejandro on 5/5/17.
 */

@Configuration
@EnableSolrRepositories(
    basePackages = "com.omit.repository",
    multicoreSupport = true)
@ComponentScan
public class SolrConfiguration {
    static final String SOLR_HOST = "spring.data.solr.host";

    @Resource
    private Environment environment;

    @Bean
    public SolrClient solrClient() {
        return new HttpSolrClient(environment.getRequiredProperty(SOLR_HOST));
    }

    @Bean
    public SolrTemplate solrTemplate(SolrClient client) throws Exception {
        return new SolrTemplate(client);
    }
}
