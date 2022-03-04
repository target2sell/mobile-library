import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    val kotlinVersion = "1.6.10"
    kotlin("multiplatform") version kotlinVersion
    `maven-publish`
    `signing`
    id("com.android.library")
    kotlin("plugin.serialization") version kotlinVersion
    id("com.chromaticnoise.multiplatform-swiftpackage") version "2.0.3"
}

group = "com.target2sell.library"
version = "0.0.3"

repositories {
    google()
    jcenter()
    mavenCentral()
}

kotlin {
    android {
        publishLibraryVariants("release")
    }
    val libraryName = "Target2sellSDK"
    val xcf = XCFramework(libraryName)
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = libraryName
            xcf.add(this)
        }
    }
    /*
        WARNING !!! current version doesn't generate arm simulator framework
        Jetbrain tool should be use to generate XCFramework :
        gradle->other->assembleTarget2sellSDKReleaseXCFramework
        then copy generated XCFramework to SPM
     */
    multiplatformSwiftPackage {
        packageName("Target2sellSDK")
        swiftToolsVersion("5.5.0")
        targetPlatforms {
            iOS { v("14") }
        }
    }

    ext["ossrhUsername"] = project.property("ossrhUsername")
    ext["ossrhPassword"] = project.property("ossrhPassword")

    fun getExtraString(name: String) = ext[name]?.toString()

    // Grabbing secrets from local.properties file or from environment variables, which could be used on CI
    publishing {
        // Configure maven central repository
        repositories {
            maven {
                name = "sonatype"
                setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = getExtraString("ossrhUsername")
                    password = getExtraString("ossrhPassword")
                }
            }
        }

        // Configure all publications
        publications.withType<MavenPublication> {

            // Provide artifacts information requited by Maven Central
            pom {
                name.set("Target2sell Mobile library")
                description.set("Target2sell Mobile library")
                url.set("https://github.com/target2sell/mobile-library.git")

                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("developers")
                        name.set("developers")
                        email.set("developers@target2sell.com")
                    }
                }
                scm {
                    url.set("https://github.com/target2sell/mobile-library.git")
                }

            }
        }
    }

    // Signing artifacts. Signing.* extra properties values will be used
    signing {
        sign(publishing.publications)
    }

    sourceSets {
        all {
            languageSettings.optIn("io.ktor.util.InternalAPI")
        }
        val ktorVersion = "1.6.7"
        val ktxVersion = "1.6.0"
        val jUnitVersion = "4.13.2"
        val mockkVersion = "1.11.0"
        val kermitVersion = "1.0.0-rc4"
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting

        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("co.touchlab:kermit:$kermitVersion")
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation("io.ktor:ktor-client-mock:$ktorVersion")
                implementation("io.mockk:mockk-common:$mockkVersion")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.core:core-ktx:$ktxVersion")
                implementation("io.ktor:ktor-client-android:$ktorVersion")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:$jUnitVersion")
                implementation("io.mockk:mockk:$mockkVersion")
            }
        }
        val iosMain by creating {
            dependencies {
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
            }
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    compileSdkVersion(31)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(31)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

}