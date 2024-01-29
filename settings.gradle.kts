pluginManagement {
	repositories {
		mavenLocal()
		mavenCentral()
		gradlePluginPortal()
		maven {
			name = "GTNH Maven"
			url = uri("http://jenkins.usrv.eu:8081/nexus/content/groups/public/")
			isAllowInsecureProtocol = true
			mavenContent {
				includeGroup("com.gtnewhorizons")
				includeGroup("com.gtnewhorizons.retrofuturagradle")
			}
		}
	}
}

dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
	repositories {
		mavenLocal()
		mavenCentral()
		gradlePluginPortal()
		maven {
			name = "GTNH Maven"
			url = uri("http://jenkins.usrv.eu:8081/nexus/content/groups/public/")
			isAllowInsecureProtocol = true
			mavenContent {
				includeGroup("com.gtnewhorizons")
				includeGroup("com.gtnewhorizons.retrofuturagradle")
			}
		}
	}
}

plugins {
	id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
