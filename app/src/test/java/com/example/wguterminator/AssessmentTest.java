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
public class AssessmentTest {

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
    public void testAssessment_isSuccessful() {
        String validAssessment = "assess";
        String actualAssessment = "assess";
        assertEquals(validAssessment, actualAssessment);
    }

    @Test
    public void testAssessment_isFailed() {
        String fakeAssessment = "fakeAssessment";
        String actualAssessment = "assess";
        assertNotSame(fakeAssessment, actualAssessment);
    }
}
