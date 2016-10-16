import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.component.LabelField;


class HelloWorldMainScreen extends MainScreen {
	private LabelField labelField;
    public HelloWorldMainScreen(boolean isAlternateEntry) {
        if (isAlternateEntry) {
            labelField = new LabelField("Goodbye World!");
        }
        else {
            labelField = new LabelField("Hello World!");
        }
        add(labelField);
    }

    public void appendLabelText(String text) {
        labelField.setText(labelField.getText() + "\n" + text);
    }
    
}
