package io.transwarp.tdc.springcloud.appbuilder.service.impl;

import com.github.dockerjava.api.DockerClient;
import io.transwarp.tdc.springcloud.appbuilder.persistence.mapper.ImageBuildMapper;
import io.transwarp.tdc.springcloud.appbuilder.persistence.model.ImageBuild;
import io.transwarp.tdc.springcloud.appbuilder.service.FileOperationService;
import io.transwarp.tdc.springcloud.appbuilder.service.ThreadSource.UploadFileCallable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Author:CZD
 * Question:FileOperationServiceImpl
 * Time:19-4-10 上午10:54
 */
public class FileOperationServiceImpl implements FileOperationService {

    private static final Logger logger = LoggerFactory.getLogger(FileOperationService.class);

    ExecutorService executor= Executors.newSingleThreadExecutor();

    @Autowired
    ImageBuildMapper mapper;

    @Autowired
    DockerClient docker;


    @Override
    public Boolean isJarFile(String fileName) {
        if(fileName.contains("."))
        {
            String spilitFileName[]=fileName.split("\\.");
            if(spilitFileName.length==0||!spilitFileName[spilitFileName.length-1].equals("jar"))
            {
                return false;
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public Boolean createAndUploadFiles(MultipartFile file, String fileName, ImageBuild build,String baseFilePath) throws ExecutionException, InterruptedException {
        Future<Boolean> result=executor.submit(new UploadFileCallable(file,fileName,mapper,build,baseFilePath,docker));
        return true;
    }
}
