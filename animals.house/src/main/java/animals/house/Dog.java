package animals.house;

import animals.Animal;

public class Dog implements Animal {
    @Override
    public String say() {
        return "Bark";
    }
}
