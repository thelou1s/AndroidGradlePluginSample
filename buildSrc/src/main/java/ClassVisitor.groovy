import groovyjarjarasm.asm.AnnotationVisitor
import groovyjarjarasm.asm.MethodVisitor

public abstract class ClassVisitor {
    /** The class visitor to which this visitor must delegate method calls. May be null. */
    protected ClassVisitor cv;

    public ClassVisitor(final int api, final ClassVisitor classVisitor) {
        if (api != Opcodes.ASM7 && api != Opcodes.ASM6 && api != Opcodes.ASM5 && api != Opcodes.ASM4) {
            throw new IllegalArgumentException("Unsupported api " + api);
        }
        this.api = api;
        this.cv = classVisitor;
    }

    public AnnotationVisitor visitAnnotation(final String descriptor, final boolean visible) {
        if (cv != null) {
            return cv.visitAnnotation(descriptor, visible);
        }
        return null;
    }

    public MethodVisitor visitMethod(
            final int access,
            final String name,
            final String descriptor,
            final String signature,
            final String[] exceptions) {
        if (cv != null) {
            return cv.visitMethod(access, name, descriptor, signature, exceptions);
        }
        return null;
    }

//    ...
}