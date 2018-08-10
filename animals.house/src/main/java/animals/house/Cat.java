package animals.house;

import animals.Animal;

public class Cat implements Animal {

    @Override
    public String say() {
        return "Meow";
    }
}
