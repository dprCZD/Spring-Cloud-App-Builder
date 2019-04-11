package io.transwarp.tdc.springcloud.appbuilder.persistence.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Author:CZD
 * Question:ImageBuild
 * Time:19-4-9 下午2:49
 */
public class ImageBuild implements Serializable {

    private int id;

    private String buildId;

    private String imageName;

    private String tenantName;

    private String status;

    private Long createTime;

    private String createUser;

    private Long updateTime;

    private String updateUser;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageBuild that = (ImageBuild) o;
        return id == that.id &&
                buildId.equals(that.buildId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, buildId);
    }

    @Override
    public String toString() {
        return "ImageBuild{" +
                "id=" + id +
                ", buildId='" + buildId + '\'' +
                ", imageName='" + imageName + '\'' +
                ", tenantName='" + tenantName + '\'' +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                ", updateTime=" + updateTime +
                ", updateUser='" + updateUser + '\'' +
                '}';
    }
}
