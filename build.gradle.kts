// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
}

val detekt: Configuration by configurations.creating

val detektTask = tasks.register<JavaExec>("detekt") {
    mainClass = "io.gitlab.arturbosch.detekt.cli.Main"
    classpath = detekt

    val input = projectDir
    val config = "$projectDir/config/detekt/detekt.yml"
    val exclude = ".*/build/.*,.*/resources/.*"
    val report = "html:reports/detekt.html"
    val plugins = "plugins/detekt-twitter-compose-0.0.26-all.jar"
    val params = listOf("-i", input, "-c", config, "-ex", exclude, "-r", report, "-p", plugins)

    args(params)
}

dependencies {
    detekt(libs.detekt.cli)
    detekt(libs.detekt.formatting)
}
