package cn.edu.nenu.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import cn.edu.nenu.entity.Post;

@Dao
public interface PostDao {

    @Insert
    void insert(Post post);

    @Delete
    void delete(Post post);

    @Query("SELECT * FROM Post ORDER BY time DESC")
    List<Post> queryAll();

    @Query("SELECT * FROM Post WHERE author = :author ORDER BY time DESC")
    List<Post> queryByAuthor(String author);

    @Query("SELECT * FROM Post WHERE title like :content or content like :content ORDER BY time DESC")
    List<Post> queryByContent(String content);
}
