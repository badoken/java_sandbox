package badoken.ksy.page;

import lombok.NonNull;
import lombok.Value;

import java.util.*;

public @Value class Page {
    @NonNull Map<String, Integer> buttons;
    Map<String, Button> previousButtons = new HashMap<>();
    List<Button> allGenerated = new ArrayList<>();

    public Button searchForButton(@NonNull final String name) {
        return Optional.ofNullable(buttons.get(name))
                .filter(counter -> counter > 0)
                .map(counter -> {
                    var button = new Button(name + "[" + counter + "]");
                    allGenerated.add(button);
                    var previousButton = previousButtons.get(name);
                    buttons.put(name, --counter);
                    previousButtons.put(name, button);
                    if (previousButton == null) return button;
                    if (!previousButton.isInvoked()) button.setBroken(true);
                    return button;
                })
                .orElseThrow(() -> new AssertionError("Button " + name + " was not found"));
    }
}
