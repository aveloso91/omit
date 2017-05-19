package com.omit.config;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

import javax.annotation.Resource;

/**
 * Created by Alejandro on 5/5/17.
 */

@Configuration
@EnableSolrRepositories(basePackages={"com.omit.repository"}, multicoreSupport=true)
public class SolrConfiguration {
    static final String SOLR_HOST = "spring.data.solr.host";

    @Resource
    private Environment environment;

    @Bean
    public HttpSolrServer solrClient() {
        String solrHost = environment.getRequiredProperty(SOLR_HOST);
        return new HttpSolrServer(solrHost);
    }

}
