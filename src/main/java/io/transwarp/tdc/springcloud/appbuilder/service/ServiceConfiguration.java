package io.transwarp.tdc.springcloud.appbuilder.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import io.transwarp.tdc.springcloud.appbuilder.service.impl.FileOperationServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.transwarp.tdc.springcloud.appbuilder.service.impl.ImageBuildServiceImpl;

/**
 * Author:CZD
 * Question:ServiceConfiguration
 * Time:19-4-9 下午4:14
 */
@Configuration
public class ServiceConfiguration {

    @Bean
    ImageBuildService getImageBuildService()
    {
        return new ImageBuildServiceImpl();
    }


    @Bean
    FileOperationService getFileOperationService(){ return new FileOperationServiceImpl(); }

    @Bean
    DockerClient getDockerClient(@Value("${docker.host}")String host,
                                 @Value("${docker.registryUrl}")String registryUrl,
                                 @Value("${docker.registryUsername}")String registryUsername,
                                 @Value("${docker.registryPassword}")String registryPassword){
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(host)
                .withRegistryUrl(registryUrl)
                .withRegistryUsername(registryUsername)
                .withRegistryPassword(registryPassword)
                .build();
        return DockerClientBuilder.getInstance(config).build();}
}
