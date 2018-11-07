package com.avides.springboot.testcontainer.awss3mock;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.Protocol;
import com.amazonaws.services.s3.AmazonS3;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public abstract class AbstractIT
{
    protected DockerClient dockerClient = DockerClientBuilder.getInstance().build();

    @Autowired
    protected ConfigurableEnvironment environment;

    protected AmazonS3 amazonS3;

    @Before
    public void init()
    {
        amazonS3 = AmazonS3Helper.buildAmazonS3(environment.getProperty("embedded.container.awss3mock.endpoint.http.url"), Protocol.HTTP);
    }
}
