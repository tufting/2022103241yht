package cn.edu.nenu.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import cn.edu.nenu.entity.Comment;

@Dao
public interface CommentDao {
    @Insert
    void insert(Comment comment);

    @Query("SELECT * FROM Comment WHERE postId = :postId ORDER BY time DESC")
    List<Comment> queryByPostId(int postId);
}
