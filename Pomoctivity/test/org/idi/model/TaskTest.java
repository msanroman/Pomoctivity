package org.idi.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class TaskTest {

    @Test
    public void shouldHaveADescriptiveNameAndAPomodoriEstimation() {
        
        String taskName = "This is my first task in Pomoctivity, so exciting!";
        int estimatedPomodori = 5;
        Task task = new Task(taskName, estimatedPomodori);
        
        assertThat(task.getRemainingPomodori(), equalTo(estimatedPomodori));
        assertThat(task.getName(), equalTo(taskName));
    }
    
    @Test
    public void shouldNotBeCompletedIfThereAreRemainingPomodori() {
        
        String taskName = "This task is not completed yet";
        int estimatedPomodori = 1;
        Task task = new Task(taskName, estimatedPomodori);
        
        assertThat(task.isCompleted(), equalTo(false));
    }
    
    @Test
    public void shouldBeCompletedWhenThereAreNoRemainingPomodori() {
        
        String taskName = "This task is already completed";
        int estimatedPomodori = 0;
        Task task = new Task(taskName, estimatedPomodori);
        
        assertThat(task.isCompleted(), equalTo(true));
    }

}
