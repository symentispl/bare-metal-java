plugins {
    id("org.asciidoctor.jvm.convert") version "3.3.0"
    id("org.asciidoctor.jvm.gems") version "3.3.0"
}

repositories {
    gradlePluginPortal()
}

dependencies {
    asciidoctorGems("rubygems:asciidoctor-revealjs:4.1.0")
}


val TARGET_SLIDES = "target/slides"
val REVEALJSDIR = "https://cdn.jsdelivr.net/npm/reveal.js@3.9.2"
val RESOURCES_SRC = "src/main/resources"
val SLIDES_SRC = "src/main/slides"

task<Copy>("copySlidesResources") {
    description = "copy slides resource"
    from(RESOURCES_SRC)
    into(TARGET_SLIDES)
}

task<Exec>("buildSlides") {
    description = "builds slides"
    dependsOn("copySlidesResources")
    commandLine("asciidoctor-revealjs",
            "-a", "revealjsdir=$REVEALJSDIR",
            "-r", "asciidoctor-diagram",
            "-D", TARGET_SLIDES,
            "$SLIDES_SRC/*.adoc")
}