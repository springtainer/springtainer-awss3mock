package com.avides.springboot.testcontainer.awss3mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

public class EmbeddedAwsS3MockContainerAutoConfigurationIT extends AbstractIT
{
    @Test
    public void testGeneratedProperties() throws Exception
    {
        assertThat(environment.getProperty("embedded.container.awss3mock.endpoint.http")).isNotEmpty();
        assertThat(environment.getProperty("embedded.container.awss3mock.endpoint.https")).isNotEmpty();

        System.out.println();
        System.out.println("Resolved properties:");
        System.out.println("Http endpoint url : " + environment.getProperty("embedded.container.awss3mock.endpoint.http"));
        System.out.println("Https endpoint url: " + environment.getProperty("embedded.container.awss3mock.endpoint.https"));
    }

    @Test
    public void testAmazonS3() throws Exception
    {
        amazonS3.createBucket("testbucket");
        assertEquals(0, amazonS3.listObjects("testbucket", "test/1").getObjectSummaries().size());
    }

    @Configuration
    @EnableAutoConfiguration
    static class TestConfiguration
    {
        // nothing
    }
}
