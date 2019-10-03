package badoken.ksy;

import badoken.ksy.page.Button;
import badoken.ksy.page.Page;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class HandlerTest {
    @Test
    public void threeDeleteAndTwoCancel() {
        // given
        var page = new Page(new HashMap<>() {{
            put("delete", 3);
            put("cancel", 2);
        }});

        var handler = new Handler(page, List.of("delete", "cancel"));
        // when
        handler.handle();

        // then
        var buttons = page.getAllGenerated();
        assertThat(buttons, hasSize(5));
        assertTrue(buttons.stream().allMatch(Button::isInvoked));
    }

    @Test
    public void threeDeleteAndTwoCancelAndOneDontClickMe() {
        // given
        var page = new Page(new HashMap<>() {{
            put("delete", 3);
            put("cancel", 2);
            put("don't", 1);
        }});

        var handler = new Handler(page, List.of("delete", "cancel"));
        // when
        handler.handle();

        // then
        var buttons = page.getAllGenerated();
        assertThat(buttons, hasSize(5));
        assertTrue(buttons.stream().allMatch(Button::isInvoked));
        assertTrue(buttons.stream().map(Button::getName).noneMatch("don't"::equals));
    }

    @Test
    public void oneDelete() {
        // given
        var page = new Page(new HashMap<>() {{
            put("delete", 1);
        }});

        var handler = new Handler(page, List.of("delete", "cancel"));
        // when
        handler.handle();

        // then
        var buttons = page.getAllGenerated();
        assertThat(buttons, hasSize(1));
        assertTrue(buttons.get(0).isInvoked());
    }

    @Test
    public void noButtons() {
        // given
        var page = new Page(new HashMap<>());

        var handler = new Handler(page, List.of("delete", "cancel"));
        // when
        handler.handle();

        // then
        // Nothing happend
    }
}