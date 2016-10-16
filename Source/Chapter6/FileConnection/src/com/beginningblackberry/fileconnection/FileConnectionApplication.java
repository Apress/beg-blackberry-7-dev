package com.beginningblackberry.fileconnection;

import net.rim.device.api.ui.UiApplication;

public class FileConnectionApplication extends UiApplication {

    public FileConnectionApplication() {
        FileConnectionScreen screen = new FileConnectionScreen();
        pushScreen(screen);
    }

    public static void main(String[] args) {
        FileConnectionApplication app = new FileConnectionApplication();
        app.enterEventDispatcher();
    }

}
