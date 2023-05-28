package cn.edu.nenu.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Collects {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int userId;
    private int postId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "Collects{" +
                "id=" + id +
                ", userId=" + userId +
                ", postId=" + postId +
                '}';
    }
}
