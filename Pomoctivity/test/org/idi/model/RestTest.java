package org.idi.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class RestTest {

    
    @Test
    public void aShortRestHas5MinutesRemainingWhenCreated() {
        
        Rest rest = new ShortRest();
        assertThat(rest.getRemainingTime(), equalTo(5));
    }
    
    @Test
    public void aLongRestHas15MinutesRemainingWhenCreated() {
        
        LongRest rest = new LongRest();
        assertThat(rest.getRemainingTime(), equalTo(15));
    }
    
    @Test
    public void aRestShouldBeStoppedWhenStarted() {
        
        ShortRest shortRest = new ShortRest();
        assertThat(shortRest.isStopped(), equalTo(true));
    
        LongRest longRest = new LongRest();
        assertThat(longRest.isStopped(), equalTo(true));
    }
    
}
