package com.example.wguterminator.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.wguterminator.DAO.AssessmentDAO;
import com.example.wguterminator.DAO.CourseDAO;
import com.example.wguterminator.DAO.TermDAO;
import com.example.wguterminator.DAO.UserDAO;
import com.example.wguterminator.Entities.Assessment;
import com.example.wguterminator.Entities.Course;
import com.example.wguterminator.Entities.Term;
import com.example.wguterminator.Entities.User;

//Increment everytime you make a change to entities, otherwise it wont build with changes
@Database(entities = {Assessment.class, Course.class, Term.class, User.class}, version=15, exportSchema = false)
public abstract class TerminatorDatabaseBuilder extends RoomDatabase {
        public abstract AssessmentDAO assessmentDAO();
        public abstract CourseDAO courseDAO();
        public abstract TermDAO termDAO();
        public abstract UserDAO userDAO();

        private static volatile TerminatorDatabaseBuilder INSTANCE;

        static TerminatorDatabaseBuilder getDatabase(final Context context){
            if(INSTANCE == null) {
                synchronized (TerminatorDatabaseBuilder.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),TerminatorDatabaseBuilder.class, "MyTerminatorDatabase3.db")
                                .fallbackToDestructiveMigration()
                               // .allowMainThreadQueries()
                                .build();
                    }
                }
            }
            return INSTANCE;
        }

}
