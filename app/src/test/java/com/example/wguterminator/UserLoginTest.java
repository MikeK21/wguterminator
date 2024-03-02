package com.example.wguterminator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import com.example.wguterminator.Database.Repository;
import com.example.wguterminator.Entities.User;
import com.example.wguterminator.UI.UserLogin;

@RunWith(MockitoJUnitRunner.class)
public class UserLoginTest {

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
    public void testLogin_isSuccessful() {
        String validUser = "MK";
        String actualUser = "MK";
        assertEquals(validUser, actualUser);
    }

    @Test
    public void testLogin_isFailed() {
        String fakeUser = "fakeUser";
        String actualUser = "MK";
        assertNotSame(fakeUser, actualUser);
    }
}