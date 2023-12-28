package com.example.wguterminator.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wguterminator.Entities.Course;
import com.example.wguterminator.Entities.Term;

import java.util.List;

@Dao
public interface TermDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Term term);
    @Update
    void update(Term term);
    @Delete
    void delete(Term term);
    @Query("SELECT * FROM terms ORDER BY termId ASC")
    List<Term> getAllTerms();
    @Query("SELECT * FROM terms WHERE termId = :termId")
    List<Term> getTermNameById(int termId);
    @Query("SELECT * FROM terms WHERE termName = :termName")
    List<Term> getTermIdByName(String termName);
}
