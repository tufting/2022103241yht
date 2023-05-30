package cn.edu.nenu.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import cn.edu.nenu.entity.Collects;

@Dao
public interface CollectsDao {
    @Insert
    void insert(Collects... collects);

    @Delete
    void delete(Collects... collects);

    @Query("SELECT count(*) FROM Collects WHERE userId = :userId and postId = :postId")
    int queryByDoubleId(int userId, int postId);

    @Query("SELECT postId FROM Collects WHERE userId = :userId")
    List<Integer> queryByUserId(int userId);
}
