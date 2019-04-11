package io.transwarp.tdc.springcloud.appbuilder.service.ThreadSource;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.BuildImageCmd;
import com.github.dockerjava.api.exception.DockerClientException;
import com.github.dockerjava.core.command.BuildImageResultCallback;
import com.github.dockerjava.core.command.PushImageResultCallback;
import io.transwarp.tdc.springcloud.appbuilder.controller.AppBuilderController;
import io.transwarp.tdc.springcloud.appbuilder.persistence.mapper.ImageBuildMapper;
import io.transwarp.tdc.springcloud.appbuilder.persistence.model.ImageBuild;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Author:CZD
 * Question:UploadFileCallble
 * Time:19-4-10 上午11:27
 */
public class UploadFileCallable implements Callable<Boolean> {

    private static final Logger logger = LoggerFactory.getLogger(UploadFileCallable.class);

    private ImageBuildMapper mapper;



    ImageBuild build;

    private MultipartFile file;

    private  String fileName;

    String filePath="";

    String baseFilePath;

    DockerClient docker;



    public  UploadFileCallable(MultipartFile file, String fileName,ImageBuildMapper mapper,ImageBuild build,String baseFilePath,DockerClient docker)
    {
        this.file=file;
        this.fileName=fileName;
        this.mapper=mapper;
        this.build=build;
        this.baseFilePath=baseFilePath;
        this.docker=docker;
        filePath=baseFilePath+build.getBuildId()+"/";
    }
    @Override
    public Boolean call() throws Exception {
            if(createFile())
            {
                try
                {
                    buildImage();
                    return true;
                }
                catch (DockerClientException e)
                {
                    logger.error("Docker Error:"+e.getMessage());
                }
            }
            return false;
    }

    private Boolean createFile() {
        File dir=new File(filePath);
        if(!dir.exists())
            dir.mkdirs();
        File targetFile=new File(filePath+fileName);
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            logger.error("create targetfile error:"+e.getMessage());
        }
        if(targetFile.exists())
        {
            setBuildStatus("uploaded");
            copyFile(baseFilePath+"Dockerfile",filePath+"Dockerfile");
            logger.info("upload file ["+fileName+"] success");
            return true;
        }
        logger.error("upload file failure:"+fileName);
        setBuildStatus("upload failure");
        return false;

    }
    private Boolean buildImage()
    {
        setBuildStatus("building");
        //创建镜像
        File baseDir = new File(filePath);
        BuildImageResultCallback callback=new BuildImageResultCallback();
        BuildImageCmd buildImageCmd = docker.buildImageCmd(baseDir);
        Set<String>tagSet=new HashSet<>();
        tagSet.add(build.getImageName());
        buildImageCmd.withTags(tagSet);
        String imageId=buildImageCmd.exec(callback).awaitImageId();
        try {
        if(imageId==null)
            throw new InterruptedException("build image: ["+build.getImageName()+"] failed");
        logger.info("build image ["+build.getImageName()+"] success,ImageId: ["+imageId+"]");
        if(imageId==null)
            throw new DockerClientException("create image failed:"+fileName);
        //push镜像
            if(!docker.pushImageCmd(build.getImageName()).exec(new PushImageResultCallback()).awaitCompletion(5, TimeUnit.MINUTES))
                throw new InterruptedException("time out!push image: ["+build.getImageName()+"] failed");
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            return false;
        }
        logger.info("push image ["+build.getImageName()+"] success");
        delFile(filePath);
        setBuildStatus("builded");
        return true;
    }
//    private Boolean buildImage()
//    {
//        setBuildStatus("building");
//        String bashCommand = "sh "+ filePath+"AutoImage.sh APP_NAME="+fileName +" "+build.getImageName();
//        ProcessBuilder processBuilder= new ProcessBuilder(bashCommand);
//        processBuilder.directory(new File(filePath));
//        Process pro = null;
//        int status=-1;
//        try {
//            pro = processBuilder.start();
//            status = pro.waitFor();
//            int times=2;
//            while(times!=0&&status!=0)
//            {
//                pro = processBuilder.start();
//                status = pro.waitFor();
//                times--;
//            }
//            if(status!=0)
//            {
//                setBuildStatus("build failure");
//                throw new Exception("build image failure! status code:"+status);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        setBuildStatus("builded");
//        return true;
//    }
    private void setBuildStatus(String status)
    {
        build.setStatus(status);
        mapper.updateImageBuildStatus(build.getBuildId(),build.getStatus());
    }

    private  void  copyFile(String  oldPath,  String  newPath)  {
        try  {
            int  byteread  =  0;
            File  oldfile  =  new  File(oldPath);
            if  (oldfile.exists())  {  //文件存在时
                InputStream inStream  =  new FileInputStream(oldPath);  //读入原文件
                FileOutputStream  fs  =  new FileOutputStream(newPath);
                byte[]  buffer  =  new  byte[1444];
                while  (  (byteread  =  inStream.read(buffer))  !=  -1)  {
                    fs.write(buffer,  0,  byteread);
                }
                inStream.close();
            }
        }
        catch  (Exception  e)  {
            setBuildStatus("upload failure");
            logger.error(e.getMessage());

        }

    }
    private void delFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            logger.warn("delete file failure: "+filename+"is not exist!");
        }

        if (file.isFile()) {
            if(file.delete())
                logger.info("delete file success:"+filename);
            else
                logger.warn("delete file failure: "+filename);
        } else {
            String[] filenames = file.list();
            for (String f : filenames) {
                delFile(filePath+f);
            }
            if(file.delete())
                logger.info("delete file success:"+filename);
            else
                logger.warn("delete file failure: "+filename);
        }
    }

}
