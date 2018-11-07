package com.avides.springboot.testcontainer.awss3mock;

import static com.avides.springboot.testcontainer.awss3mock.AwsS3MockProperties.BEAN_NAME;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.avides.springboot.testcontainer.common.container.AbstractBuildingEmbeddedContainer;
import com.avides.springboot.testcontainer.common.container.EmbeddedContainer;

@Configuration
@ConditionalOnProperty(name = "embedded.container.awss3mock.enabled", matchIfMissing = true)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties(AwsS3MockProperties.class)
public class EmbeddedAwsS3MockContainerAutoConfiguration
{
    @ConditionalOnMissingBean(AwsS3MockContainer.class)
    @Bean(BEAN_NAME)
    public EmbeddedContainer awsS3MockContainer(ConfigurableEnvironment environment, AwsS3MockProperties properties)
    {
        return new AwsS3MockContainer("awss3mock", environment, properties);
    }

    @Bean
    public AmazonS3 amazonS3(ConfigurableEnvironment environment)
    {
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(environment
                        .getProperty("embedded.container.awss3mock.http.endpoint"), "us-east-1"))
                .withPathStyleAccessEnabled(true)
                .withClientConfiguration(new ClientConfiguration().withProtocol(Protocol.HTTP))
                .build();
    }

    public class AwsS3MockContainer extends AbstractBuildingEmbeddedContainer<AwsS3MockProperties>
    {

        public AwsS3MockContainer(String service, ConfigurableEnvironment environment, AwsS3MockProperties properties)
        {
            super(service, environment, properties);
        }

        @Override
        protected boolean isContainerReady(AwsS3MockProperties properties)
        {
            var amazonS3 = AmazonS3ClientBuilder.standard()
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(generateProtocolEndpoint(Protocol.HTTP), "us-east-1"))
                    .withPathStyleAccessEnabled(true)
                    .withClientConfiguration(new ClientConfiguration().withProtocol(Protocol.HTTP))
                    .build();
            amazonS3.createBucket("testbucket");
            amazonS3.deleteBucket("testbucket");
            return true;
        }

        @Override
        protected Map<String, Object> providedProperties()
        {
            var provided = new HashMap<String, Object>();
            provided.put("embedded.container.awss3mock.http.endpoint", generateProtocolEndpoint(Protocol.HTTP));
            provided.put("embedded.container.awss3mock.https.endpoint", generateProtocolEndpoint(Protocol.HTTPS));
            return provided;
        }

        private String generateProtocolEndpoint(Protocol protocol)
        {
            return getContainerHost() + ":" + (protocol == Protocol.HTTP ? getContainerPort(properties
                    .getHttpPort()) : getContainerPort(properties.getHttpsPort()));
        }
    }
}
