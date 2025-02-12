package cn.edu.nenu.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cn.edu.nenu.entity.Post;

@Dao
public interface PostDao {

    @Insert
    void insert(Post post);

    @Query("DELETE FROM Post WHERE id = :id")
    void delete(int id);

    @Query("DELETE FROM Post WHERE author = :author")
    void deleteByAuthor(String author);    /* author is user primary key */

    @Update
    int update(Post post);

    /* 收藏数加一或者减一 */
    @Query("UPDATE Post SET collects_num = collects_num + :num WHERE id = :id")
    int collectModifyOne(int id, int num);

    @Query("SELECT * FROM Post ORDER BY time DESC")
    List<Post> queryAll();

    @Query("SELECT * FROM Post WHERE author = :author ORDER BY time DESC")
    List<Post> queryByAuthor(String author);

    @Query("SELECT * FROM Post WHERE title like :content or content like :content ORDER BY time DESC")
    List<Post> queryByContent(String content);

    @Query("SELECT * FROM Post WHERE id in (SELECT postId FROM Collects WHERE userId = :userId) ORDER BY time DESC")
    List<Post> queryByCollectUserId(int userId);

    @Query("SELECT * FROM Post WHERE block = :block ORDER BY time DESC")
    List<Post> queryByBlock(String block);
}
