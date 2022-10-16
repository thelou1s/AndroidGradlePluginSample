import groovyjarjarasm.asm.ClassReader
import groovyjarjarasm.asm.ClassWriter
import groovyjarjarasm.asm.Opcodes

class CustomClassWriter {
    var reader: ClassReader
    var writer: ClassWriter
    var addFieldAdapter: AddFieldAdapter? = null

    init {
        reader = ClassReader(className)
        writer = ClassWriter(reader, 0)
    }

    //...
    fun addField(): ByteArray {
        println("#thelou1s, CustomClassWriter.addField")

        addFieldAdapter = AddFieldAdapter(
            "aNewBooleanField",
            Opcodes.ACC_PUBLIC,
            writer
        )
        reader.accept(addFieldAdapter, 0)
        return writer.toByteArray()
    }

    companion object {
        var className = "java.lang.Integer"
        var cloneableInterface = "java/lang/Cloneable"
    }
}