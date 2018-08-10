package petshop;

import animals.Animal;

import java.lang.module.ModuleFinder;
import java.nio.file.Paths;
import java.util.ServiceLoader;
import java.util.Set;

public class DynamicAnimalTest {
    public static void main(String[] args) throws Exception {
        var finder = ModuleFinder.of(
                Paths.get("animals.barn", "build", "libs"),
                Paths.get("animals.house", "build", "libs"));

        var parent = ModuleLayer.boot();
        var cfg = parent.configuration().resolve(finder, ModuleFinder.of(), Set.of("animals.barn", "animals.house"));
        var pluginLayer = parent.defineModulesWithOneLoader(cfg, ClassLoader.getSystemClassLoader());

        var petLoader = ServiceLoader.load(pluginLayer, Animal.class);

        for (var animal : petLoader) {
            System.out.printf("The %s says %s%n", animal.getClass().getSimpleName(), animal.say());
        }
    }
}
