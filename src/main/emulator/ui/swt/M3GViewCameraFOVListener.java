package emulator.ui.swt;

import org.eclipse.swt.events.*;
import emulator.*;

final class M3GViewCameraFOVListener extends SelectionAdapter {
    private final M3GViewUI aClass90_625;

    M3GViewCameraFOVListener(final M3GViewUI aClass90_625) {
        super();
        this.aClass90_625 = aClass90_625;
    }

    public final void widgetSelected(final SelectionEvent selectionEvent) {
        final Class30 class30;
        (class30 = new Class30(M3GViewUI.method499(this.aClass90_625), 0)).setText(UILocale.get("M3G_VIEW_CAMEAR_FIELD_OF_VIEW", "Field of View"));
        class30.method337(M3GViewUI.method517(this.aClass90_625));
        class30.method340();
        if (class30.aFloat603 > 0.0f) {
            if (M3GViewUI.method500(this.aClass90_625) == 0) {
                if (class30.aFloat603 < 180.0f) {
                    M3GViewUI.method518(this.aClass90_625, class30.aFloat603);
                }
            } else {
                M3GViewUI.method518(this.aClass90_625, class30.aFloat603);
            }
            M3GViewUI.method252(this.aClass90_625);
        }
    }
}
