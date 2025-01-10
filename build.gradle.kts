// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.dependency.check)
}

val detekt: Configuration by configurations.creating

val detektTask = tasks.register<JavaExec>("detekt") {
    mainClass = "io.gitlab.arturbosch.detekt.cli.Main"
    classpath = detekt

    val input = projectDir
    val config = "$projectDir/config/detekt/detekt.yml"
    val exclude = ".*/build/.*,.*/resources/.*"
    val report = "html:reports/detekt.html,sarif:reports/detekt.sarif"
    val plugins = "plugins/detekt-twitter-compose-0.0.26-all.jar"
    val params = listOf("-i", input, "-c", config, "-ex", exclude, "-r", report, "-p", plugins)

    args(params)
}

dependencies {
    detekt(libs.detekt.cli)
    detekt(libs.detekt.formatting)
}

dependencyCheck {
    nvd {
        apiKey = "62cf5ed5-84f8-4992-9c9e-dbd0e1f4bb02"
    }
    failBuildOnCVSS = 7.0F // Fail for vulnerabilities with CVSS score >= 7
//    suppressionFile = "dependency-check-suppressions.xml" // Optional suppression file for known false positives
    analyzers {
        archiveEnabled = false // Disable archive analyzer to improve performance
        assemblyEnabled = false // Disable .NET assembly analyzer
        composerEnabled = false // Disable PHP Composer analyzer
        cocoapodsEnabled = false // Disable iOS Cocoapods analyzer
        nodePackage {
            enabled = false // Disable Node.js package analyzer
        }
        pyDistributionEnabled = false // Disable Python distribution analyzer
        pyPackageEnabled = false // Disable Python package analyzer
        bundleAuditEnabled = false // Disable Ruby bundle audit analyzer
    }
    formats = listOf("HTML", "SARIF") // Generate HTML and SARIF reports
    outputDirectory = layout.buildDirectory.dir("dependency-check-report").get().asFile.absolutePath
}