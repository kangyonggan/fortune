package com.kangyonggan.app.fortune.biz.asm;

import org.objectweb.asm.*;

/**
 * @author kangyonggan
 * @since 5/17/17
 */
public class BeforeClassAdapter extends ClassAdapter {

    private String methodName;
    private String className;

    public BeforeClassAdapter(ClassVisitor cv, String methodName) {
        super(cv);
        this.methodName = methodName;
    }

    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.className = name;
        super.visit(version, access, name, signature, name, interfaces);
    }

    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);

        if (name.equals("<init>")) {
            mv = new ChangeToChildConstructorMethodAdapter(mv, className);
        } else if (name.equals(methodName)) {
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/kangyonggan/app/fortune/biz/asm/LogAsm", "before", "()V");
        }

        return mv;
    }

    class ChangeToChildConstructorMethodAdapter extends MethodAdapter {
        private String superClassName;

        public ChangeToChildConstructorMethodAdapter(MethodVisitor mv, String superClassName) {
            super(mv);
            this.superClassName = superClassName;
        }

        public void visitMethodInsn(int opcode, String owner, String name, String desc) {
            if (opcode == Opcodes.INVOKESPECIAL && name.equals("<init>")) {
                owner = superClassName;
            }

            super.visitMethodInsn(opcode, owner, name, desc);
        }
    }

}
