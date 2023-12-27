package com.example.wguterminator.Database;

import android.app.Application;
import android.util.Log;

import com.example.wguterminator.DAO.AssessmentDAO;
import com.example.wguterminator.DAO.CourseDAO;
import com.example.wguterminator.DAO.TermDAO;
import com.example.wguterminator.DAO.UserDAO;
import com.example.wguterminator.Entities.Assessment;
import com.example.wguterminator.Entities.Course;
import com.example.wguterminator.Entities.Term;
import com.example.wguterminator.Entities.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Repository {
    private AssessmentDAO mAssessmentDAO;
    private CourseDAO mCourseDAO;
    private TermDAO mTermDAO;
    private UserDAO mUserDAO;

    private List<Assessment> mAllAssessments;
    private List<Course> mAllCourses;
    private List<Term> mAllTerms;
    private List<User> mAllUsers;
    private List<Course> mAllAssocCourses;
    private List<Course> mAllAssocCoursesById;
    private List<Term> mTermNameById;



    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        TerminatorDatabaseBuilder db = TerminatorDatabaseBuilder.getDatabase(application);

        mTermDAO = db.termDAO();
        mCourseDAO = db.courseDAO();
        mAssessmentDAO = db.assessmentDAO();
        mUserDAO = db.userDAO();
    }

    public List<Term> getmAllTerms() {
        databaseExecutor.execute(() ->{
            mAllTerms = mTermDAO.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public List<Term> getmTermByTermId(int termId) {
        databaseExecutor.execute(() ->{
            mTermNameById = mTermDAO.getTermNameById(termId);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mTermNameById;
    }

    public List<Course> getmAllCourseWithAssocTerm(Term term) {
        databaseExecutor.execute(() ->{
            mAllAssocCourses = mCourseDAO.getAllAssociatedCourses(term.getTermId());
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssocCourses;
    }

    public List<Course> getmAllCourseWithAssocTermById(int termId) {
        databaseExecutor.execute(() ->{
            mAllAssocCoursesById = mCourseDAO.getAllAssociatedCourses(termId);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssocCoursesById;
    }

    public int getmAllAssessForACourse(int courseId) {
        List<Assessment> assocAssessments = mAssessmentDAO.getCountAssessmentsForCourse(courseId);
        if (!assocAssessments.isEmpty()) {
            return assocAssessments.size() + 1;
        }
        {
            return 0;
        }
    }

    public void insert(Term term) {
        databaseExecutor.execute(()->{
            mTermDAO.insert(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Term term) {
        databaseExecutor.execute(()->{
            mTermDAO.update(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Term term, List<Course> courseList) {

       //List<Course> assocCourseList = mCourseDAO.getAllAssociatedCourses(term.getTermId())
       boolean flagNoDelete = false;
       for (Course course : courseList ) {
           if (course.getTermId() == term.getTermId()) {
               Log.e("ERROR","Unable to delete Term - in use for Course: "
               + course.getTermId());
               flagNoDelete = true;
           }
       }
       if (flagNoDelete) {
           Log.e("ERROR","There was a course - cannot delete");
       }
       if (!flagNoDelete) {
           databaseExecutor.execute(() -> {
               mTermDAO.delete(term);
           });
       }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Course> getmAllCourses() {
        databaseExecutor.execute(() ->{
            mAllCourses = mCourseDAO.getAllCourses();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }

    public void insert(Course course) {
        databaseExecutor.execute(()->{
            mCourseDAO.insert(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Course course) {
        databaseExecutor.execute(()->{
            mCourseDAO.update(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Course course) {
        databaseExecutor.execute(()->{
            mCourseDAO.delete(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public List<Assessment> getmAllAssessments() {
        databaseExecutor.execute(() ->{
            mAllAssessments = mAssessmentDAO.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    public void insert(Assessment assessment) {
        /*
        boolean flagNoDelete = false;
        List<Assessment> assocAssessments = mAssessmentDAO.getCountAssessmentsForCourse(assessment.getCourseId());
            if (assocAssessments.size() >= 5) {
                flagNoDelete = true;
            }
        if (flagNoDelete) {
            Log.e("ERROR","There are already 5 assessments - cannot add");
        }
        if (!flagNoDelete) {
                    databaseExecutor.execute(() -> {
                mAssessmentDAO.insert(assessment);
            });
        }

         */
        databaseExecutor.execute(() -> {
            mAssessmentDAO.insert(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Assessment assessment) {
        databaseExecutor.execute(()->{
            mAssessmentDAO.update(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Assessment assessment) {
        databaseExecutor.execute(()->{
            mAssessmentDAO.delete(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<User> getmAllUsers() {
        databaseExecutor.execute(() ->{
            mAllUsers = mUserDAO.getAllUsers();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllUsers;
    }

    public void insert(User user) {
        databaseExecutor.execute(()->{
            mUserDAO.insert(user);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(User user) {
        databaseExecutor.execute(()->{
            mUserDAO.update(user);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(User user) {
        databaseExecutor.execute(()->{
            mUserDAO.delete(user);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
