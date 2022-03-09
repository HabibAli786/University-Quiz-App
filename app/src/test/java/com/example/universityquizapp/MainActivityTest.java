package com.example.universityquizapp;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ReportFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ReportFragment.class })
public class MainActivityTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void onCreate() {
        // Mock some data
        mockStatic(ReportFragment.class);
        MainActivity activity = spy(new MainActivity());
        doNothing().when(activity).setContentView(R.layout.activity_register);
        doReturn(mock(AppCompatDelegate.class)).when(activity).getDelegate();

//        // Call the method
        activity.onCreate(null);

        // Verify that it worked
        verify(activity, times(1)).setContentView(R.layout.activity_register);
    }

    @Test
    public void onRequestPermissionsResult() {
    }

    @Test
    public void onActivityResult() {
    }

    @Test
    public void loginAct() {
    }
}