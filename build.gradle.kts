import java.time.LocalDate
import java.time.format.DateTimeFormatter

plugins {
	id("com.gtnewhorizons.retrofuturagradle") version "1.4.0"
}

group = "com.github.hummel"
version = LocalDate.now().format(DateTimeFormatter.ofPattern("yy.MM.dd"))

dependencies {
	implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(8)
	}
}

minecraft {
	mcVersion = "1.7.10"
	username = "Hummel009"
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
