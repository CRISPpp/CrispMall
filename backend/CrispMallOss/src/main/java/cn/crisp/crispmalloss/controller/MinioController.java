package cn.crisp.crispmalloss.controller;

import cn.crisp.common.R;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Api("oss")
@RestController
@Slf4j
@RequestMapping("/oss")
public class MinioController {
    @Autowired
    MinioClient minioClient;

    @Value("${minio.endpoint}")
    String endpoint;

    @Value("${minio.bucketName}")
    String bucketName;
    /**
     * 文件上传
     */
    @SneakyThrows
    @ApiOperation("上传图片")
    @PostMapping("/upload")
    public R<String> uploadFile(@RequestBody MultipartFile file){
        if(file == null){
            return R.error("上传文件不能为空");
        }

        String originalFileName = file.getOriginalFilename();

        InputStream inputStream = file.getInputStream();

        String fileName = bucketName + System.currentTimeMillis() + originalFileName;
        //上传
        try {
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(inputStream,file.getSize(),-1).contentType(file.getContentType()).build());
            inputStream.close();
        }catch (Exception e){
            log.info(e.getMessage());
            return R.error("上传失败");
        }
        return R.success(endpoint + "/" + bucketName + "/" + fileName);
    }
}
