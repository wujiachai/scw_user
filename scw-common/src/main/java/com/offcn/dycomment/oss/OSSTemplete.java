package com.offcn.dycomment.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

//OSS模板类
@Data
public class OSSTemplete {
    private String endpoint;  //固定地址
    private String bucketDomain;  //  返回的图片中间固定地址
    private String accessKeyId;  //AccessKey ID
    private String accessKeySecret;  //accessKeySecret
    private String bucketName;   //zhongchou01

    public String uplode(InputStream fileStream, String fileName){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String folderName = simpleDateFormat.format(new Date());
        fileName = UUID.randomUUID().toString().replace("-", "")+fileName;
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, "pic/"+folderName+"/"+fileName, fileStream);
        ossClient.putObject(putObjectRequest);

// 关闭OSSClient。
        ossClient.shutdown();
        try {
            fileStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "https://"+bucketDomain+"/pic/"+folderName+"/"+fileName;
    }
}
