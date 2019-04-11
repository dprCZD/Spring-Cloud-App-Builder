package io.transwarp.tdc.springcloud.appbuilder.service;


import io.transwarp.tdc.springcloud.appbuilder.persistence.model.ImageBuild;

import java.util.List;

/**
 * Author:CZD
 * Question:ImageBuildService
 * Time:19-4-9 下午3:29
 */

public interface ImageBuildService {

    void createImageBuild(ImageBuild imageBuild);

    ImageBuild getImageBuild(String buildId);

    List<ImageBuild> listImageBuilds();

    void updateImageBuild(String buildId,ImageBuild imageBuild);

    void deleteImageBuild(String buildId);


}
