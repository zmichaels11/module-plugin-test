
module animals.barn {
    requires animals.core;

    exports animals.barn;

    provides animals.Animal
            with
                    animals.barn.Cow,
                    animals.barn.Sheep;
}