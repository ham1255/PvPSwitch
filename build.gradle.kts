import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    `java-library`
    id("io.papermc.paperweight.userdev") version "1.3.11"
    id("xyz.jpenilla.run-paper") version "2.0.0"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("com.github.johnrengelman.shadow") version "7.1.2"

}

group = "me.endcrystal.plugins"
version = "1.0.0-SNAPSHOT"
description = "pvp-switch mode"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

dependencies {
    paperDevBundle("1.19.2-R0.1-SNAPSHOT")
    implementation("com.github.puregero", "multilib", "1.1.8")
    compileOnly("me.clip", "placeholderapi", "2.11.2")
}

repositories {
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/") // place holder api repo
    maven("https://repo.clojars.org/") // multipaper multilib
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }

    shadowJar {
        relocate( "com.github.puregero.multilib", "me.endcrystal.pvpswitch.multilib")
    }

}


bukkit {
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    main = "me.endcrystal.pvpswitch.PvPSwitchPlugin"
    apiVersion = "1.19"
    authors = listOf("EndCrystal.me")
    depend = listOf("PlaceholderAPI")
}
