package badoken.ksy;

import badoken.ksy.page.Page;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.fail;

public class SearchApproachTest {

    @Test
    public void twoSearchesAndInvocationAfterWillThrowException() {
        // given
        var page = new Page(new HashMap<>() {{
            put("delete", 2);
        }});

        // when
        var button1 = page.searchForButton("delete");
        var button2 = page.searchForButton("delete");
        button1.invoke();
        try {
            button2.invoke();
        } catch (AssertionError error) {
            // then
            return;
        }
        fail("Had to throw exception");
    }

    @Test(expected = AssertionError.class)
    public void buttonNotExistsThenExceptionWillBeThrown() {
        // given
        var page = new Page(new HashMap<>() {{
            put("delete", 2);
        }});

        // when
        page.searchForButton("cancel");
    }

    @Test
    public void twoButtonsCreatedThenThirdSearchWillThrowException() {
        // given
        var page = new Page(new HashMap<>() {{
            put("delete", 2);
        }});

        // when
        page.searchForButton("delete");
        page.searchForButton("delete");

        try {
            page.searchForButton("delete");
        } catch (AssertionError error) {
            // then
            return;
        }
        fail("Had to throw exception");
    }
}