package badoken.ksy;

import badoken.ksy.page.Button;
import badoken.ksy.page.Page;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Spliterator.ORDERED;
import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.stream.StreamSupport.stream;

@RequiredArgsConstructor
public class Handler {
    private final @NonNull Page page;
    private final @NonNull List<String> buttonNames;

    public void handle() {
        buttonNames.stream()
                .flatMap(this::retrieveFromPage2)
                .forEach(Button::invoke);
    }

    private Stream<Button> retrieveFromPage(String buttonName) {
        return Stream.generate(() -> getButton(buttonName)).takeWhile(Optional::isPresent).map(Optional::get);
    }

    private Stream<Button> retrieveFromPage2(String buttonName) {
        var iterator = new Iterator<Button>() {
            private Optional<Button> next = Optional.empty();

            @Override public boolean hasNext() {
                next = getButton(buttonName);
                return next.isPresent();
            }

            @Override public Button next() { return next.get(); }
        };

        return stream(spliteratorUnknownSize(iterator, ORDERED), false);
    }

    private Optional<Button> getButton(String buttonName) {
        try {
            return Optional.of(page.searchForButton(buttonName));
        } catch (AssertionError error) {
            return Optional.empty();
        }
    }
}
