package io.transwarp.tdc.springcloud.appbuilder.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.transwarp.tdc.springcloud.appbuilder.controller.pojo.ImageBuildCreateConfig;
import io.transwarp.tdc.springcloud.appbuilder.persistence.model.ImageBuild;
import io.transwarp.tdc.springcloud.appbuilder.service.FileOperationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.transwarp.tdc.springcloud.appbuilder.service.ImageBuildService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Author:CZD
 * Description:AppBuilderController
 * Time:19-4-2 下午5:20
 */
@Api(value = "Spring-Cloud-App-Builder", description = "SpringCloudApp打包服务")
@RestController
@RequestMapping("/api/v1")
public class AppBuilderController {

    private static final Logger logger = LoggerFactory.getLogger(AppBuilderController.class);

    @Autowired
    private ImageBuildService imageBuildService;

    @Autowired
    private FileOperationService fileOperationService;

    @Value("${registry.location}")
    private String registryLocation;

    @Value("${Resources.baseDir}")
    String baseFilePath;

    @Value("${Resources.App.name}")
    String appFileName;



    @ApiOperation(value = "jar包转换镜像至仓库", notes = "jar包转换镜像至仓库", produces = "application/json")
    @ApiImplicitParams({
            //@ApiImplicitParam(name = "config", value = "ImageBuildCreateConfig", dataType = "ImageBuildCreateConfig", paramType = "body"),
    })
    @RequestMapping(path = "/uploadApp", method = RequestMethod.POST,produces = "application/json")
    public String getSpringCloudAppJar(MultipartFile file,@RequestParam("createUser")String createUser,
                                       @RequestParam("tenantName")String tenantName,@RequestParam("imageName")String imageName )
    {
        if(!file.isEmpty()) {
            try {
                String fileName=file.getOriginalFilename();
                //判断文件格式是否为jar
                if(!fileOperationService.isJarFile(fileName))
                    throw new IOException("file type error:"+fileName);
                //创建build持久化对象
                ImageBuild build=new ImageBuild();
                build.setBuildId(UUID.randomUUID().toString());
                build.setCreateTime(System.currentTimeMillis());
                build.setCreateUser(createUser);
                build.setImageName(registryLocation+imageName);
                build.setTenantName(tenantName);
                build.setStatus("uploading");
                imageBuildService.createImageBuild(build);
                logger.info("create ImageBuild ["+build.getBuildId()+"]sucess");
                //创建临时文件夹并上传文件
                fileOperationService.createAndUploadFiles(file,appFileName,build,baseFilePath);
                logger.info("operating origin file:"+fileName);
            } catch (IOException | ExecutionException | InterruptedException e) {
               logger.error(e.getMessage());
                return e.getMessage();
            }
        }
        return "upLoadSuccess!";
    }

    @ApiOperation(value = "获取Build对象", notes = "获取Build对象", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "buildId", value = "buildId", dataType = "String", paramType = "path"),

    })
    @RequestMapping(path = "/imageBuild/{buildId}", method = RequestMethod.GET,produces = "application/json")
    public ImageBuild getImageBuild(@PathVariable("buildId")String buildId)
    {
        ImageBuild temp=imageBuildService.getImageBuild(buildId);
        return imageBuildService.getImageBuild(buildId);
    }

    @ApiOperation(value = "更新Build对象", notes = "更新Build对象", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "buildId", value = "buildId", dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "imageBuild", value = "Build对象", dataType = "ImageBuild", paramType = "body"),
    })
    @RequestMapping(path = "/imageBuild/{buildId}", method = RequestMethod.POST,produces = "application/json")
    public String updateImageBuild(@PathVariable("buildId")String buildId,@RequestBody ImageBuild imageBuild)
    {
        imageBuild=imageBuildService.getImageBuild(buildId);
        imageBuild.setUpdateTime(System.currentTimeMillis());
        imageBuild.setUpdateUser("czddd1");
        imageBuildService.updateImageBuild(buildId,imageBuild);
        return "success";
    }

    @ApiOperation(value = "列表Build对象", notes = "列表Build对象", produces = "application/json")
    @RequestMapping(path = "/imageBuild", method = RequestMethod.GET,produces = "application/json")
    public List<ImageBuild> listtImageBuilds()
    {
        List<ImageBuild> imageBuilds = imageBuildService.listImageBuilds();
        return imageBuilds;
    }


    @ApiOperation(value = "删除Build对象", notes = "删除Build对象", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "buildId", value = "buildId", dataType = "String", paramType = "path"),

    })
    @RequestMapping(path = "/imageBuild/{buildId}", method = RequestMethod.DELETE,produces = "application/json")
    public String deletetImageBuilds(@PathVariable("buildId")String buildId)
    {
        imageBuildService.deleteImageBuild(buildId);
        return "success";
    }



}
