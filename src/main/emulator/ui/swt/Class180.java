package emulator.ui.swt;

import org.eclipse.swt.events.*;
import emulator.*;

final class Class180 extends KeyAdapter
{
    private final Property aClass38_1450;
    
    Class180(final Property aClass38_1450) {
        super();
        this.aClass38_1450 = aClass38_1450;
    }
    
    public final void keyPressed(final KeyEvent keyEvent) {
        keyEvent.keyCode &= 0xFEFFFFFF;
        if (Property.method376(this.aClass38_1450).getSelectionIndex() == 0 && Keyboard.keyToString(String.valueOf(keyEvent.keyCode)) != null) {
            Property.method407(this.aClass38_1450).setText(Keyboard.keyToString(String.valueOf(keyEvent.keyCode)));
            Property.method365()[10] = Property.method407(this.aClass38_1450).getText().trim();
        }
    }
}
