package petshop;

import animals.Animal;

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
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

        System.out.println("Name of all plugins defined:");
        pluginLayer.modules().stream()
                .map(Module::getDescriptor)
                .map(ModuleDescriptor::provides)
                .flatMap(Set::stream)
                .map(ModuleDescriptor.Provides::providers)
                .flatMap(List::stream)
                .forEach(System.out::println);

        System.out.println();
        System.out.println("Initialize Plugin from Module");
        pluginLayer.findModule("animals.barn")
                .map(module -> Class.forName(module, "animals.barn.Sheep"))
                .flatMap(clazz -> {
                    try {
                        return Optional.of(clazz.getConstructor().newInstance());
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        return Optional.empty();
                    }
                })
                .flatMap(plugin -> {
                    if (plugin instanceof Animal) {
                        return Optional.of((Animal) plugin);
                    } else {
                        return Optional.empty();
                    }
                })
                .map(Animal::say)
                .ifPresent(theSheep -> System.out.printf("The sheep say %s%n", theSheep));


        var petLoader = ServiceLoader.load(pluginLayer, Animal.class);

        System.out.println();
        System.out.println("SPI loaded Plugins:");
        for (var animal : petLoader) {
            System.out.printf("The %s says %s%n", animal.getClass().getSimpleName(), animal.say());
        }
    }
}
