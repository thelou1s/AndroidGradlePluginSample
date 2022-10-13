import groovyjarjarasm.asm.ClassVisitor
import groovyjarjarasm.asm.MethodVisitor

class MyHelloClassVisitor extends ClassVisitor {

    MyHelloClassVisitor(ClassVisitor cv) {
        //这里需要指定一下版本Opcodes.ASM7
        super(Opcodes.ASM7, cv)
    }

    @Override
    MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        def methodVisitor = cv.visitMethod(access, name, descriptor, signature, exceptions)
        return new MyHelloClassVisitor(api, methodVisitor, access, name, descriptor)
    }

    //方法进入
    @Override
    protected void onMethodEnter() {
        super.onMethodEnter()
        //这里的mv是MethodVisitor
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("Hello World!");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
    }
}