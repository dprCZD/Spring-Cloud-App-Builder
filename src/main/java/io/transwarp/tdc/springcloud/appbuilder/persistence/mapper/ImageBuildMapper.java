package io.transwarp.tdc.springcloud.appbuilder.persistence.mapper;
import io.transwarp.tdc.springcloud.appbuilder.persistence.model.ImageBuild;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * Author:CZD
 * Question:ImageBuildMapper
 * Time:19-4-9 下午3:22
 */
@Mapper
public interface ImageBuildMapper {

    ImageBuild getImageBuild(@Param("buildId")String buildId);

    void insertImageBuild(@Param("imageBuild")ImageBuild imageBuild);

    void updateImageBuild(@Param("buildId")String buildId,@Param("imageBuild")ImageBuild imageBuild);

    void updateImageBuildStatus(@Param("buildId")String buildId,@Param("status")String status);

    List<ImageBuild> listImageBuilds();

    void deleteImageBuild(@Param("buildId")String buildId);




}
