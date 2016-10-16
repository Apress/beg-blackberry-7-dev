package com.beginningblackberry.fileconnection;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.container.PopupScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class FileNameScreen extends PopupScreen implements FieldChangeListener {
    private EditField fileNameField;
    private ButtonField okButton;
    public FileNameScreen() {
        super(new VerticalFieldManager());

        fileNameField = new EditField("New Filename:", "");
        add(fileNameField);
        okButton = new ButtonField("OK", ButtonField.CONSUME_CLICK | Field.FIELD_HCENTER);
        okButton.setChangeListener(this);
        add(okButton);
    }

    public String getFilename() {
        return fileNameField.getText();
    }

    public void fieldChanged(Field field, int context) {
        if (field == okButton) {
            close();
        }
    }
}
