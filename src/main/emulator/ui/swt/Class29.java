package emulator.ui.swt;

import emulator.*;
import org.eclipse.swt.dnd.*;

final class Class29 extends DropTargetAdapter {
    Class29(final EmulatorScreen class93) {
        super();
    }

    public final void dragEnter(final DropTargetEvent dropTargetEvent) {
        if (dropTargetEvent.detail == 16) {
            dropTargetEvent.detail = (((dropTargetEvent.operations & 0x1) != 0x0) ? 1 : 0);
        }
    }

    public final void dragOver(final DropTargetEvent dropTargetEvent) {
        dropTargetEvent.feedback = 9;
    }

    public final void drop(final DropTargetEvent dropTargetEvent) {
        final String[] array;
        if (FileTransfer.getInstance().isSupportedType(dropTargetEvent.currentDataType) && (array = (String[]) dropTargetEvent.data).length > 0 && (array[0].toLowerCase().endsWith(".jar") || array[0].toLowerCase().endsWith(".jad"))) {
            Emulator.loadGame(array[0], Settings.g2d, Settings.g3d, false);
        }
    }
}
