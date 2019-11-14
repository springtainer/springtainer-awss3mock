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

import com.amazonaws.Protocol;
import com.amazonaws.services.s3.AmazonS3;
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

    public class AwsS3MockContainer extends AbstractBuildingEmbeddedContainer<AwsS3MockProperties>
    {

        public AwsS3MockContainer(String service, ConfigurableEnvironment environment, AwsS3MockProperties properties)
        {
            super(service, environment, properties);
        }

        @Override
        protected boolean isContainerReady(AwsS3MockProperties props)
        {
            AmazonS3 amazonS3 = AmazonS3Helper.buildAmazonS3(generateProtocolEndpoint(Protocol.HTTP), Protocol.HTTP);
            amazonS3.createBucket("testbucket");
            amazonS3.deleteBucket("testbucket");
            return true;
        }

        @Override
        protected Map<String, Object> providedProperties()
        {
            Map<String, Object> provided = new HashMap<>();
            provided.put("embedded.container.awss3mock.endpoint.http.url", generateProtocolEndpoint(Protocol.HTTP));
            provided.put("embedded.container.awss3mock.endpoint.https.url", generateProtocolEndpoint(Protocol.HTTPS));
            return provided;
        }

        private String generateProtocolEndpoint(Protocol protocol)
        {
            return getContainerHost() + ":" + (protocol == Protocol.HTTP ? getContainerPort(properties
                    .getEndpointHttpPort()) : getContainerPort(properties.getEndpointHttpsPort()));
        }
    }
}
