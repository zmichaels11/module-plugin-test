package animals.barn;

import animals.Animal;

public class Cow implements Animal {
    @Override
    public String say() {
        return "Moo";
    }
}
