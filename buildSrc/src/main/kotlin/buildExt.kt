import org.gradle.api.Project
import java.io.File

fun File.findLineStartsWith(value: String) = inputStream().bufferedReader()
    .lines()
    .filter { it.startsWith(value) }
    .findAny()
    .get()

fun Project.localProperties(key: String): Lazy<String> = lazy {
    file("local.properties").findLineStartsWith(key)
        .substringAfter('=')
}

fun String.quoted() = "\"$this\""
