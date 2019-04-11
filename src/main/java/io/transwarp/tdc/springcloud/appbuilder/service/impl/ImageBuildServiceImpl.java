package io.transwarp.tdc.springcloud.appbuilder.service.impl;

import com.github.dockerjava.api.DockerClient;
import io.transwarp.tdc.springcloud.appbuilder.persistence.model.ImageBuild;
import io.transwarp.tdc.springcloud.appbuilder.persistence.mapper.ImageBuildMapper;
import org.springframework.beans.factory.annotation.Autowired;
import io.transwarp.tdc.springcloud.appbuilder.service.ImageBuildService;

import java.util.List;

/**
 * Author:CZD
 * Question:ImageBuildServiceImpl
 * Time:19-4-9 下午3:30
 */

public class ImageBuildServiceImpl implements ImageBuildService {


    @Autowired
    ImageBuildMapper mapper;



    @Override
    public void createImageBuild(ImageBuild imageBuild) {
        mapper.insertImageBuild(imageBuild);
    }

    @Override
    public ImageBuild getImageBuild(String buildId) {
        return mapper.getImageBuild(buildId);
    }

    @Override
    public List<ImageBuild> listImageBuilds() {
        return mapper.listImageBuilds();
    }

    @Override
    public void updateImageBuild(String buildId,ImageBuild imageBuild) {
        mapper.updateImageBuild(buildId,imageBuild);
    }

    @Override
    public void deleteImageBuild(String buildId) {
        mapper.deleteImageBuild(buildId);

    }
}
