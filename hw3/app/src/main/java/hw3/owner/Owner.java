package owner;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Owner {
    private final long ownerId;
    private final String name;
    private final String lastName;
    private final int age;
}
