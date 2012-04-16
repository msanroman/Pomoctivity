package org.idi.pomoctivity;

import org.idi.pomoctivity.*;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class PomoctivityActivityTest {

    @Test
    public void shouldHaveHappySmiles() throws Exception {
        String hello = new PomoctivityActivity().getResources().getString(R.string.hello);
        assertThat(hello, equalTo("Hello World, PomoctivityActivity!"));
    }
}


