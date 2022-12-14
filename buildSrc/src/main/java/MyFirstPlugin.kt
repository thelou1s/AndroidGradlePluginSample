import extensions.android
import extensions.variants
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
//import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.gradle.AppExtension
import groovyjarjarasm.asm.ClassReader

internal class MyFirstPlugin : Plugin<Project> {

    lateinit var reader: ClassReader

    companion object {
        // All clients of this plugin will need to use this specific file name
        private const val COLOR_FILE_NAME = "my_colors.txt"
    }

    override fun apply(project: Project) {
        println("#thelou1s, apply")

        val ccw = CustomClassWriter()
        ccw.addField()

        project.pluginManager.withPlugin("com.android.application") {
            println("#thelou1s, withPlugin, it:$it")
            val androidExtension = project.extensions.getByType(AppExtension::class.java)
            //val androidComponentsExtension = project.extensions.getByType(AndroidComponentsExtension::class.java)

            androidExtension.registerTransform(MethodTimeTransform())
        }

        project.android().variants().all { variant ->
            println("#thelou1s, variants:$variant")

            val colorTaskName = "generateColors${variant.name.capitalize()}"
            val outputPath = "${project.buildDir}/generated/res"

            project.tasks.register(colorTaskName, ColorsTask::class.java) { colorTask ->
                colorTask.group = "MyPluginTasks"

                val colorsFileLocations =
                    getAllPossibleFileLocationsByVariantDirectory(variant.dirName)

                // Check that whether the file declared in any of the relevant folders and throw an exception otherwise
                val colorsFile = colorsFileLocations.asSequence()
                    .map { project.file("$it/$COLOR_FILE_NAME") }
                    .firstOrNull { it.isFile }
                    ?: throw GradleException(
                        "No $COLOR_FILE_NAME file found in any of the following locations: " +
                                "\n${colorsFileLocations.joinToString("\n")}"
                    )

                val outputDirectory =
                    File("$outputPath/${variant.dirName}").apply { mkdir() }
                colorTask.outputFile = File(outputDirectory, "values/generated_colors.xml")

                // Finally set the file on the Task object
                colorTask.inputFile = colorsFile

                variant.registerGeneratedResFolders(
                    project.files(outputDirectory).builtBy(
                        colorTask
                    )
                )
            }
        }
    }
}