module animals.house {
    requires animals.core;
    exports animals.house;

    provides animals.Animal with animals.house.Cat, animals.house.Dog;
}