import com.github.jrubygradle.api.core.RepositoryHandlerExtension
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

plugins {
    id("org.asciidoctor.jvm.revealjs") version "3.3.2"
    id("org.asciidoctor.jvm.gems") version "3.3.2"
    id("org.kordamp.gradle.livereload") version "0.4.0"
}

repositories {
    gradlePluginPortal()
    // https://github.com/jruby-gradle/jruby-gradle-plugin/issues/407
    this as ExtensionAware
    the<RepositoryHandlerExtension>().gems()
}

dependencies {
    dependencies {
        asciidoctorGems("rubygems:asciidoctor-revealjs:4.1.0")
    }
}

tasks.asciidoctorRevealJs {
    sourceDir("src/main/slides")
    sources {
        include("index.adoc")
    }
    setOutputDir("build/slides")
    resources {
        from("src/main/resources") {
            include("**")
        }
    }
    asciidoctorj {
        modules {
            diagram.setVersion("2.2.1")
        }
    }
}

tasks.liveReload {
    setDocRoot(tasks.asciidoctorRevealJs.get().outputDir.absolutePath)
}