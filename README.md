# Asciidoctor RevealJS Slides

This repository contains presentation slides created using Asciidoctor RevealJS. The slides are located in the `slides` submodule of this project and are built using Gradle.

## Getting Started

### Prerequisites

- [Java JDK](https://adoptopenjdk.net/) (8 or newer)
- [Gradle](https://gradle.org/install/) (included as wrapper in the project)

### Setup

1. Clone this repository:

```bash
git clone https://github.com/yourusername/yourrepository.git
```

2. Navigate to the slides directory:

```bash
cd slides
```

## Working with Slides

### Directory Structure

The slides are organized with the following structure:

```
slides/
├── src/main/slides/       # Source AsciiDoc files
├── src/main/resources/    # Resources used in presentations
└── build/slides/          # Generated HTML presentations
```

### Creating and Editing Slides

1. Create or edit AsciiDoc files in the `slides/src/main/slides` directory.

2. The main presentation file is `index.adoc`. You can modify this file or create additional AsciiDoc files.

3. Here's a basic template for an AsciiDoc slide:

```asciidoc
= My Presentation Title
:author: Your Name
:email: your.email@example.com
:revealjs_theme: white
:revealjs_history: true
:revealjs_transition: slide
:source-highlighter: highlightjs

== First Slide

Content for the first slide

== Second Slide

Content for the second slide

=== Sub-slide

Content for a sub-slide
```

### Building Slides

To build the slides using Gradle:

```bash
./gradlew asciidoctorRevealJs
```

This will generate the HTML presentation in the `build/slides` directory.

### Viewing Slides

You can view the slides using the built-in live reload server:

```bash
./gradlew liveReload
```

This will start a local server and automatically reload the presentation when changes are detected. Open your browser and navigate to `http://localhost:35729` to view the slides.

Alternatively, you can open the generated HTML files directly in your browser:

```bash
open build/slides/index.html
```

## Customizing Slides

### Themes

You can change the theme by setting the `:revealjs_theme:` attribute in your AsciiDoc file. Available themes include:
- black (default)
- white
- league
- beige
- sky
- night
- serif
- simple
- solarized
- blood
- moon

### Adding Resources

Place any additional resources (images, CSS, JavaScript) in the `slides/src/main/resources` directory. They will be automatically copied to the output directory during the build process.

### Configuration

The project uses RevealJS version `5.1.0` as specified in the `build.gradle.kts` file. You can modify the configuration in this file to update the RevealJS version or change other build settings.

## Advanced Usage

### Diagrams

The project includes the Asciidoctor Diagram module (version 2.2.1), which allows you to include various types of diagrams in your slides:

```asciidoc
[plantuml]
----
@startuml
Alice -> Bob: Hello
@enduml
----
```

### Custom CSS and JavaScript

You can add custom CSS and JavaScript by placing the files in the `slides/src/main/resources` directory and referencing them in your AsciiDoc file:

```asciidoc
:customcss: custom.css
:customjs: custom.js
```

## Continuous Integration

This project uses GitHub Actions to automatically build the slides whenever changes are pushed to the repository. The workflow checks out the code, sets up Java, and runs the Gradle build process.

You can see the build status in the Actions tab of the GitHub repository. The workflow configuration is located in the `.github/workflows/gradle-build.yml` file.

## Additional Resources

- [Asciidoctor RevealJS Documentation](https://docs.asciidoctor.org/reveal.js-converter/latest/)
- [RevealJS Documentation](https://revealjs.com/)
- [AsciiDoc Syntax Quick Reference](https://docs.asciidoctor.org/asciidoc/latest/syntax-quick-reference/)
- [Gradle Asciidoctor Plugin Documentation](https://asciidoctor.github.io/asciidoctor-gradle-plugin/)

## License

This project is licensed under the MIT License - see the LICENSE file for details.
