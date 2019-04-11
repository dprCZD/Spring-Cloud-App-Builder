package io.transwarp.tdc.springcloud.appbuilder.controller.pojo;

/**
 * Author:CZD
 * Question:ImageBuildCreateConfig
 * Time:19-4-11 下午3:07
 */
public class ImageBuildCreateConfig {



    private String imageName;

    private String tenantName;

    private String createUser;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Override
    public String toString() {
        return "ImageBuildCreateConfig{" +
                "imageName='" + imageName + '\'' +
                ", tenantName='" + tenantName + '\'' +
                ", createUser='" + createUser + '\'' +
                '}';
    }
}
