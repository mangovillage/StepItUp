# StepItUp
Step Up, but updated!

The original mod is here: https://www.curseforge.com/minecraft/mc-mods/stepup.

The code for this mod is not copied from the above mod, but the functionality is mostly the same. This mod uses mixins
instead of event registration for very slightly better performance.

## Support

- Minecraft 26.2
- Fabric and NeoForge
- Java 25+

## Building

```sh
./gradlew build
```

Release jars are built at:

- `fabric/build/libs/stepitup-<version>-fabric.jar`
- `neoforge/build/libs/stepitup-<version>-neoforge.jar`
