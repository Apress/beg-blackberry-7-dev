package com.beginningblackberry.unifiedsearch;

import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;

public class UnifiedSearchApp extends UiApplication
{
    public static void main(String[] args)
    {
        UnifiedSearchApp theApp = new UnifiedSearchApp();       
        theApp.enterEventDispatcher();
    }
    
    public UnifiedSearchApp()
    {        
        try {
			pushScreen(new PublisherScreen(new Publisher()));
		} catch (final Exception e) {
            UiApplication.getUiApplication().invokeLater(new Runnable()
            {
                public void run()
                {
                    Dialog.alert(e.getMessage());
                    System.exit(0);            
                } 
            }); 
		}
    }
    
    public static void displayMessage(final String message)
    {
        UiApplication.getUiApplication().invokeLater(new Runnable()
        {
            public void run()
            {
                Dialog.alert(message);
            } 
        });
    }
}
