package animals.barn;

import animals.Animal;

public class Sheep implements Animal {
    @Override
    public String say() {
        return "Beep";
    }
}
