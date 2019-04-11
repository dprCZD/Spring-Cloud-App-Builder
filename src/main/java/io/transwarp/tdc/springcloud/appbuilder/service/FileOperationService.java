package io.transwarp.tdc.springcloud.appbuilder.service;

import io.transwarp.tdc.springcloud.appbuilder.persistence.model.ImageBuild;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Author:CZD
 * Question:FileOperationService
 * Time:19-4-10 上午10:53
 */
public interface FileOperationService {



     Boolean isJarFile(String fileName);

     Boolean createAndUploadFiles(MultipartFile file, String fileName, ImageBuild build,String baseFilePath) throws IOException, ExecutionException, InterruptedException;
}
