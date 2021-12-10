plugins {
    val kotlinVersion = "1.5.31"
    kotlin("multiplatform") version kotlinVersion
    `maven-publish`
    `signing`
    id("com.android.library")
    kotlin("plugin.serialization") version kotlinVersion
}

group = "com.target2sell.library"
version = "0.0.2"

repositories {
    google()
    jcenter()
    mavenCentral()
}

kotlin {
    android {
        publishLibraryVariants("release")
    }
    iosX64("ios") {
        binaries {
            framework {
                baseName = "library"
            }
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
        val ktorVersion = "1.6.4"
        val ktxVersion = "1.6.0"
        val jUnitVersion = "4.13.2"
        val mockkVersion = "1.11.0"
        val kermitVersion = "1.0.0-rc4"

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
        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
            }
        }
        val iosTest by getting
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