```
gradle shadowJar
gradle publishToMavenLocal

<dependency>
    <groupId>me.endcrystal.plugins</groupId>
    <artifactId>PvPSwitch</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <scope>provided</scope>
</dependency>

import static me.endcrystal.pvpswitch.TimerSystem.isSwitchActive;
compileOnly("me.endcrystal.plugins:PvPSwitch:1.0.0-SNAPSHOT")
if (!isSwitchActive(player)) return;
depend: [PvPSwitch]
```
