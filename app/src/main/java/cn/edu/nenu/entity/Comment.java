package cn.edu.nenu.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/* 评论表：time时刻，用户user在帖子post里发表了评论content */
@Entity
public class Comment {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String content;
    private String time;
    private int userId;
    private int postId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", userId=" + userId +
                ", postId=" + postId +
                '}';
    }
}
