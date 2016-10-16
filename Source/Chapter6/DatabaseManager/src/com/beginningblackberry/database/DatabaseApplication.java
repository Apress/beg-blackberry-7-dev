package com.beginningblackberry.database;

import net.rim.device.api.ui.UiApplication;

public class DatabaseApplication extends UiApplication
{
    public static void main(String[] args)
    {
        DatabaseApplication theApp = new DatabaseApplication();       
        theApp.enterEventDispatcher();
    }

    public DatabaseApplication()
    {        
        pushScreen(new DatabaseScreen());
    }    
}
