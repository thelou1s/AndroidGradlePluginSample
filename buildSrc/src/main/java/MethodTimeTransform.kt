import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
//import com.android.utils.FileUtils

class MethodTimeTransform : Transform() {
    override fun getName(): String {
        return "MethodTimeTransform"
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    override fun isIncremental(): Boolean {
        return true
    }


    override fun transform(transformInvocation: TransformInvocation) /*throws TransformException, InterruptedException, IOException*/ {
        super.transform(transformInvocation)

        //TransformOutputProvider管理输出路径,如果消费型输入为空,则outputProvider也为空
        val outputProvider = transformInvocation.outputProvider

        //transformInvocation.inputs的类型是Collection<TransformInput>,可以从中获取jar包和class文件夹路径。需要输出给下一个任务
        transformInvocation.inputs.forEach { input -> //这里的input是TransformInput

            input.jarInputs.forEach { jarInput ->
                println("#thelou1s, jarInputs, jarInput:$jarInput -----")
                //处理jar
                processJarInput(jarInput, outputProvider)
            }

            input.directoryInputs.forEach { directoryInput ->
                //处理源码文件
                processDirectoryInput(directoryInput, outputProvider)
            }
        }
    }

    private fun processJarInput(jarInput: JarInput, outputProvider: TransformOutputProvider) {
        val dest = outputProvider.getContentLocation(
            jarInput.file.absolutePath,
            jarInput.contentTypes,
            jarInput.scopes,
            Format.JAR
        )
        //将修改过的字节码copy到dest,就可以实现编译期间干预字节码的目的
        println("#thelou1s, processJarInput, 拷贝文件 $dest -----")
//        FileUtils.copyFile(jarInput.file, dest)
    }

    private fun processDirectoryInput(
        directoryInput: DirectoryInput,
        outputProvider: TransformOutputProvider
    ) {
        val dest = outputProvider.getContentLocation(
            directoryInput.name,
            directoryInput.contentTypes,
            directoryInput.scopes,
            Format.DIRECTORY
        )
        //将修改过的字节码copy到dest,就可以实现编译期间干预字节码的目的
        println("#thelou1s, processDirectoryInput, 拷贝文件夹 $dest -----")
//        FileUtils.copyDirectory(directoryInput.file, dest)
    }
}
