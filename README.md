# Java 10 Modules as Plugins Demo
Minimal reference project for using Java Modules for runtime application
extensions.

This project defines the modules:
__animals.core__ An "API module" extended by plugins and referenced by the application

__animals.house__ An extension of __animals.core__ that supplies _Cat_ and _Dog_

__animals.barn__ An extension of __animals.core__ that supplies _Sheep_ and _Cow_

__petshop__ An application that loads __animals.house__ and __animals.barn__ at 
*runtime* and demonstrates retrieving the *Class* as well as initialize via *SPI*