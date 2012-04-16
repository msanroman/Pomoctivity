package org.idi.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class PomodoroTest {

    private Pomodoro pomodoro;

    @Before
    public void setUp() {
        
        pomodoro = new Pomodoro();
    }
    
    @Test
    public void shouldHave25MinutesRemainingWhenCreated() {
        
        assertThat(pomodoro.getRemainingMinutes(), equalTo(25));
    }
    
    @Test
    public void shouldBeStoppedWhenCreated() {
        
        assertThat(pomodoro.isStopped(), equalTo(true));
        assertThat(pomodoro.isStarted(), equalTo(false));
    }
    
    @Test
    public void shouldNotBeStoppedWhenStarted() {
        
        pomodoro.start();
        assertThat(pomodoro.isStopped(), equalTo(false));
        assertThat(pomodoro.isStarted(), equalTo(true));
    }
}
