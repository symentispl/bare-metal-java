plugins {
    id("org.asciidoctor.jvm.revealjs") version "4.0.4"
    id("org.asciidoctor.jvm.gems") version "4.0.4"
    id("org.asciidoctor.jvm.convert") version "4.0.4"
    id("org.kordamp.gradle.livereload") version "0.4.0"
}

val revealjsVersion = "5.1.0"

repositories {
    mavenCentral()
    ruby.gems()
}

dependencies {
    asciidoctorGems("rubygems:asciidoctor-revealjs:${revealjsVersion}")
}

// hacky hackery to get the reveal.js template in a shape expected by asciidoctorRevealJs
revealjs {
    templateGitHub {
        setOrganisation("hakimel")
        setRepository("reveal.js")
        setTag("${revealjsVersion}")
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