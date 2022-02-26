package com.api.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "USER_PROFILE_IMAGE_TB", schema = "toy_chat")
public class UserProfileImageTbEntity {
    @Basic
    @Id
    @Column(name = "USER_SQ")
    private int userSq;
    @Basic
    @Column(name = "ORIGINAL_NAME")
    private String originalName;
    @Basic
    @Column(name = "FILE_NAME")
    private String fileName;
    @Basic
    @Column(name = "FILE_URL")
    private String fileUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserProfileImageTbEntity that = (UserProfileImageTbEntity) o;

        if (userSq != that.userSq) return false;
        if (originalName != null ? !originalName.equals(that.originalName) : that.originalName != null) return false;
        if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) return false;
        if (fileUrl != null ? !fileUrl.equals(that.fileUrl) : that.fileUrl != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userSq;
        result = 31 * result + (originalName != null ? originalName.hashCode() : 0);
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (fileUrl != null ? fileUrl.hashCode() : 0);
        return result;
    }
}
