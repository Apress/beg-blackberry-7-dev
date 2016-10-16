package com.beginningblackberry.uifun;

import net.rim.device.api.command.Command;
import net.rim.device.api.command.CommandHandler;
import net.rim.device.api.command.ReadOnlyCommandMetadata;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.component.CheckboxField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.ObjectChoiceField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.PasswordEditField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.util.StringProvider;

public class UiFunMainScreen extends MainScreen implements FieldChangeListener  {
    BitmapField bitmapField;
    EditField usernameField;
    PasswordEditField passwordField;
    ObjectChoiceField domainField;
    CheckboxField rememberCheckbox;
    ButtonField clearButton;
    ButtonField loginButton;
    LoginCommandHandler loginHandler = new LoginCommandHandler();

    public UiFunMainScreen() {
        Bitmap logoBitmap = Bitmap.getBitmapResource("apress_logo.png");
        bitmapField = new BitmapField(logoBitmap, Field.FIELD_HCENTER);
        add(bitmapField);
        add(new SeparatorField());
        add(new LabelField("Please enter your credentials:"));

        usernameField = new EditField("Username:", "");
        passwordField = new PasswordEditField("Password:", "");
        add(usernameField);
        add(passwordField);

        domainField = new ObjectChoiceField("Domain:", new String[] {"Home", "Work"});
        add(domainField);

        rememberCheckbox = new CheckboxField("Remember password:", false);
        add(rememberCheckbox);

        add(new SeparatorField());

        clearButton = new ButtonField("Clear", ButtonField.CONSUME_CLICK);
        loginButton = new ButtonField("Login", ButtonField.CONSUME_CLICK);
        loginButton.setCommand(new Command(loginHandler));
        
        clearButton.setChangeListener(this);
        //loginButton.setChangeListener(this);
        
        HorizontalFieldManager buttonManager = 
          new HorizontalFieldManager(Field.FIELD_RIGHT);
        buttonManager.add(clearButton);
        buttonManager.add(loginButton);
        add(buttonManager);

        
    }

    public void fieldChanged(Field field, int context) {
        if (field == clearButton) {
            clearTextFields();
        }
        else if (field == loginButton) {
            login();
        }
    }

    private void clearTextFields() {
        usernameField.setText("");
        passwordField.setText("");
   }

    private void login() {
        if (usernameField.getTextLength() == 0 || passwordField.getTextLength() == 0) {
            Dialog.alert("You must enter a username and password");
        }
        else {
            String username = usernameField.getText();
            String selectedDomain = 
              (String)domainField.getChoice(domainField.getSelectedIndex());
//        LoginSuccessScreen loginSuccessScreen = 
//                new LoginSuccessScreen(username, selectedDomain);
            UiApplication.getUiApplication().pushScreen(new Album());
        }
    }

    protected void makeMenu(Menu menu, int instance) {
        super.makeMenu(menu, instance);
        MenuItem loginMenu = new MenuItem(new StringProvider("Login"), 20, 10);
        loginMenu.setCommand(new Command(loginHandler));
        menu.add(loginMenu);
        
        /*
        menu.add(new MenuItem(new StringProvider("Login"), 20, 10) {
            public void run() {
                login();
            }
        });
        */
        
        menu.add(new MenuItem(new StringProvider("Clear"), 10, 20) {
            public void run() {
                clearTextFields();
            }
        });
    }
/*
    class LoginMenuItem extends MenuItem {
        public LoginMenuItem() {
            super(new StringProvider("Login"), 20, 10);
        }

        public void run() {
            login();
        }
    }

    class ClearMenuItem extends MenuItem {
        public ClearMenuItem() {
            super(new StringProvider("Clear"), 10, 20);
        }

        public void run() {
            clearTextFields();
        }
    }
  */  

    class LoginCommandHandler extends CommandHandler
    {
       public void execute(ReadOnlyCommandMetadata metadata, Object context)
       {
    	   login();
       }
    }
}
