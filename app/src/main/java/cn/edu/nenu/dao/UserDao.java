package cn.edu.nenu.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cn.edu.nenu.entity.User;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Update
    int update(User user);

    @Query("SELECT * FROM User")
    List<User> queryAll();

    @Query("SELECT * FROM User WHERE account = :account ORDER BY id LIMIT 1")
    User queryByAccount(String account);

    @Query("SELECT * FROM User WHERE account = :account and password = :password")
    User queryByAcc_Pwd(String account, String password);
}
