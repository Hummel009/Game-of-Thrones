import java.time.LocalDate
import java.time.format.DateTimeFormatter

plugins {
	id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.7"
	id("com.gtnewhorizons.retrofuturagradle") version "1.3.24"
}

group = "hummel"
version = "v" + LocalDate.now().format(DateTimeFormatter.ofPattern("yy.MM.dd"))

dependencies {
	implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

minecraft {
	mcVersion.set("1.7.10")
	username.set("Hummel009")
}

tasks {
	jar {
		manifest {
			attributes(
				mapOf(
					"FMLCorePlugin" to "got.coremod.GOTLoadingPlugin", "FMLCorePluginContainsFMLMod" to "true"
				)
			)
		}
	}
	withType<JavaCompile>().configureEach {
		options.encoding = "UTF-8"
	}
}
