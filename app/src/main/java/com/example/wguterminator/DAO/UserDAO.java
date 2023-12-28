package com.example.wguterminator.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wguterminator.Entities.Assessment;
import com.example.wguterminator.Entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);
    @Update
    void update(User user);
    @Delete
    void delete(User user);
    @Query("SELECT * FROM users ORDER BY userId ASC")
    List<User> getAllUsers();
    @Query("SELECT * FROM users WHERE userId = :userId ORDER BY userId ASC")
    List<User> getAllAssociatedUsers(int userId);

}
