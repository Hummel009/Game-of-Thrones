import java.time.LocalDate
import java.time.format.DateTimeFormatter

plugins {
	id("com.gtnewhorizons.retrofuturagradle") version "latest.release"
}

group = "com.github.hummel"
version = LocalDate.now().format(DateTimeFormatter.ofPattern("yy.MM.dd"))

dependencies {
	implementation(rfg.deobf(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar")))))
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
	register("copyForgeUnpacked") {
		doLast {
			val userHome = System.getProperty("user.home")
			val gradleUserHome = System.getenv("GRADLE_USER_HOME") ?: "$userHome/.gradle"
			val sourceDir = File("$gradleUserHome/caches/minecraft/net/minecraftforge/forge/1.7.10-10.13.4.1614-1.7.10")
			val targetDir =
				File("$userHome/.gradle/caches/minecraft/net/minecraftforge/forge/1.7.10-10.13.4.1614-1.7.10")
			targetDir.mkdirs()
			sourceDir.copyRecursively(targetDir, overwrite = true)
		}
	}
	jar {
		manifest {
			attributes(
				mapOf(
					"FMLCorePlugin" to "got.coremod.GOTLoadingPlugin", "FMLCorePluginContainsFMLMod" to "true"
				)
			)
		}
	}
	runClient {
		dependsOn("copyForgeUnpacked")
	}
	withType<JavaCompile>().configureEach {
		options.encoding = "UTF-8"
	}
}
