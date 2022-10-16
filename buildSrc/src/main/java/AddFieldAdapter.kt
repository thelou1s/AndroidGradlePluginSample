import groovyjarjarasm.asm.ClassVisitor
import groovyjarjarasm.asm.FieldVisitor
import groovyjarjarasm.asm.Opcodes

class AddFieldAdapter(fieldName: String, fieldAccess: Int, cv: ClassVisitor) : ClassVisitor(Opcodes.ASM4, cv) {
    private val fieldName: String
    private val fieldType: String? = null
    private val fieldDefault: String? = null
    private var access = org.objectweb.asm.Opcodes.ACC_PUBLIC
    private var isFieldPresent = false

    init {
        this.cv = cv
        this.fieldName = fieldName
        access = fieldAccess
    }

    override fun visitField(
        access: Int, name: String, desc: String, signature: String, value: Any
    ): FieldVisitor {
        if (name == fieldName) {
            isFieldPresent = true
        }
        return cv.visitField(access, name, desc, signature, value)
    }

    override fun visitEnd() {
        if (!isFieldPresent) {
            val fv: FieldVisitor = cv.visitField(
                access, fieldName, fieldType, null, null
            )
            if (fv != null) {
                fv.visitEnd()
            }
        }
        cv.visitEnd()
    }
}