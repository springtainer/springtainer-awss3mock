package com.avides.springboot.springtainer.awss3mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.amazonaws.Protocol;
import com.amazonaws.services.s3.AmazonS3;
import com.github.dockerjava.api.DockerClient;
import com.avides.springboot.springtainer.common.util.DockerClients;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext
public abstract class AbstractIT
{
    protected DockerClient dockerClient = DockerClients.build();

    @Autowired
    protected ConfigurableEnvironment environment;

    protected AmazonS3 amazonS3;

    @BeforeEach
    public void init()
    {
        amazonS3 = AmazonS3Helper.buildAmazonS3(environment.getProperty("embedded.container.awss3mock.endpoint.http.url"), Protocol.HTTP);
    }
}
