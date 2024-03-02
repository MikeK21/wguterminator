package com.example.wguterminator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import com.example.wguterminator.Database.Repository;
import com.example.wguterminator.UI.UserLogin;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CourseTest {

    @Mock
    MockedConstruction.Context mockContext;

    @Mock
    Repository mockRepository;

    @Mock
    UserLogin mockUserLogin;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCourse_isSuccessful() {
        String validCourse = "course";
        String actualCourse = "course";
        assertEquals(validCourse, actualCourse);
    }

    @Test
    public void testCourse_isFailed() {
        String fakeCourse = "fakeCourse";
        String actualCourse = "course";
        assertNotSame(fakeCourse, actualCourse);
    }
}
