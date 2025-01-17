package emulator.custom;

import emulator.Emulator;
import emulator.Settings;
import org.objectweb.asm.*;

public final class CustomClassAdapter extends ClassVisitor implements Opcodes {
    private String className;

    public final MethodVisitor visitMethod(int acc, final String name, String desc, final String sign, final String[] array) {
        Label_0037:
        {
            String s4;
            String s5;
            if (desc.contains("java/util/TimerTask")) {
                s4 = "java/util/TimerTask";
                s5 = "emulator/custom/subclass/SubTimerTask";
            } else if (desc.contains("java/util/Timer")) {
                s4 = "java/util/Timer";
                s5 = "emulator/custom/subclass/Timer";
            } else {
                break Label_0037;
            }
            desc = desc.replaceAll(s4, s5);
        }
        if ("paint".equals(name) && "(Ljavax/microedition/lcdui/Graphics;)V".equals(desc) && Settings.patchSynchronizedPaint) {
            if ((acc & Opcodes.ACC_SYNCHRONIZED) != 0) {
                acc = acc & (~Opcodes.ACC_SYNCHRONIZED);
                Emulator.getEmulator().getLogStream().println("Patched paint method: " + className);
            }
        }
        if ("playerUpdate".equals(name) && "(Ljavax/microedition/media/Player;Ljava/lang/String;Ljava/lang/Object;)V".equals(desc)) {
            if ((acc & Opcodes.ACC_SYNCHRONIZED) != 0) {
                acc = acc & (~Opcodes.ACC_SYNCHRONIZED);
                Emulator.getEmulator().getLogStream().println("Patched playerUpdate method: " + className);
            }
        }
        final MethodVisitor visitMethod;
        if ((visitMethod = super.visitMethod(acc, name, desc, sign, array)) != null) {
            return new CustomMethodAdapter(visitMethod, this.className, name, desc);
        }
        return null;
    }

    public CustomClassAdapter(final ClassVisitor classVisitor, final String aString1165) {
        super(Opcodes.ASM4, classVisitor);
        this.className = aString1165;
    }

    public final void visit(final int n, final int n2, final String s, final String s2, final String s3, final String[] array) {
        if (s3.equals("java/util/TimerTask")) {
            super.visit(n, n2, s, s2, "emulator/custom/subclass/SubTimerTask", array);
            return;
        }
        if (s3.equals("java/util/Timer")) {
            super.visit(n, n2, s, s2, "emulator/custom/subclass/Timer", array);
            return;
        }
        super.visit(n, n2, s, s2, s3, array);
    }

    public final FieldVisitor visitField(final int n, final String s, String s2, final String s3, final Object o) {
        String s4;
        if (s2.equals("Ljava/util/TimerTask;")) {
            s4 = "Lemulator/custom/subclass/SubTimerTask;";
        } else {
            if (!s2.equals("Ljava/util/Timer;")) {
//                if(className.equals("AppTHQwale"))
//                    System.out.println(s + " " + s2 + " " + s3 + " " + o);
                return super.visitField(n, s, s2, s3, o);
            }
            s4 = "Lemulator/custom/subclass/Timer;";
        }
        s2 = s4;
        return super.visitField(n, s, s2, s3, o);
    }
}
