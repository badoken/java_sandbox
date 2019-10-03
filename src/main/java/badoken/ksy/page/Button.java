package badoken.ksy.page;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

public final @Data class Button {
    private @Getter(PRIVATE) boolean broken;
    private @Setter(PRIVATE) boolean invoked;
    private @Setter(PACKAGE) Button next;
    private final @NonNull String name;

    public void invoke() {
        if (broken) throw new AssertionError("Button is broken");
        System.out.println("Button " + name + " clicked");
        invoked = true;
        if (next != null) next.setBroken(true);
    }
}
