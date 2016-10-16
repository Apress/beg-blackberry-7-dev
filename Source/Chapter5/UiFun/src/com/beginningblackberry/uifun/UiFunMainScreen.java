package com.beginningblackberry.uifun;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.CheckboxField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ObjectChoiceField;
import net.rim.device.api.ui.component.PasswordEditField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.TransitionContext;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.UiEngineInstance;

public class UiFunMainScreen extends MainScreen implements FieldChangeListener {
    BitmapField bitmapField;
    EditField usernameField;
    PasswordEditField passwordField;
    ObjectChoiceField domainField;
    CheckboxField rememberCheckbox;
    CustomButtonField clearButton;
    CustomButtonField loginButton;
    


    public UiFunMainScreen() {
    	
        try {
	            FontFamily alphaSerifFamily = FontFamily.forName("BBAlpha Serif");
	            Font appFont = alphaSerifFamily.getFont(Font.PLAIN, 9, Ui.UNITS_pt);
	            setFont(appFont);
	    } catch (ClassNotFoundException e) {
	    }
	    

        Bitmap logoBitmap = Bitmap.getBitmapResource("apress_logo2.png");
        bitmapField = new BitmapField(logoBitmap, Field.FIELD_LEFT);
        HorizontalFieldManager hfmLabel = new HorizontalFieldManager(Field.USE_ALL_WIDTH);
        Background blackBackground = BackgroundFactory.createSolidBackground(Color.BLACK);
        hfmLabel.setBackground(blackBackground);

        hfmLabel.add(bitmapField);
        add(hfmLabel);
        
        add(new SeparatorField());
//        add(new LabelField("Please enter your credentials:"));
        Bitmap loginImage = Bitmap.getBitmapResource("login_arrow.gif");
        add(new CustomLabelField("Please enter your credentials:", Color.WHITE, 0x999966, loginImage, Field.USE_ALL_WIDTH));

        usernameField = new EditField("", "");
        LabelField usernameLabel = new LabelField("   Username:", Field.FIELD_RIGHT);
        usernameLabel.setMargin(0, 0, 0, 10);
        passwordField = new PasswordEditField("", "");
        LabelField passwordLabel = new LabelField("Password:", Field.FIELD_RIGHT);

        domainField = new ObjectChoiceField("", new String[] {"Home", "Work"});
        LabelField domainLabel = new LabelField("Domain:", Field.FIELD_RIGHT);

        GridFieldManager gridFieldManager = new GridFieldManager(2, 0);
        gridFieldManager.add(usernameLabel);
        gridFieldManager.add(usernameField);
        gridFieldManager.add(passwordLabel);
        gridFieldManager.add(passwordField);
        gridFieldManager.add(domainLabel);
        gridFieldManager.add(domainField);
        add(gridFieldManager);

        rememberCheckbox = new CheckboxField("Remember password", false);
        add(rememberCheckbox);
        
        add(new SeparatorField());
        clearButton = new CustomButtonField
        ("Clear", Color.WHITE, Color.LIGHTGRAY, Color.YELLOW, Color.GREEN, 0);
        clearButton.setChangeListener(this);
        loginButton = new CustomButtonField
        ("Login", Color.WHITE, Color.LIGHTGRAY, Color.YELLOW, Color.GREEN, 0);
        loginButton.setChangeListener(this);

        HorizontalFieldManager buttonManager = new HorizontalFieldManager(Field.FIELD_RIGHT);
        buttonManager.add(clearButton);
        buttonManager.add(loginButton);
        add(buttonManager);

    }

	private void clearTextFields() {
	     usernameField.setText("");
	    passwordField.setText("");
	}
    public void fieldChanged(Field field, int context) {
        if (field == clearButton) {
            clearTextFields();
        }
        else if (field == loginButton) {
            login();
        }
    }

    private void login() {
        if (usernameField.getTextLength() == 0 || passwordField.getTextLength() == 0) {
        	UiApplication.getUiApplication().pushModalScreen
            (new CustomDialog("You must enter a username and password"));
        }
        else {
            String username = usernameField.getText();
            String selectedDomain = 
              (String)domainField.getChoice(domainField.getSelectedIndex());
            
            LoginSuccessScreenAlternate loginSuccessScreen = 
	                new LoginSuccessScreenAlternate(username, selectedDomain);
	            
	        TransitionContext transition = new TransitionContext(TransitionContext.TRANSITION_SLIDE);
	        transition.setIntAttribute(TransitionContext.ATTR_DURATION, 500);
	        transition.setIntAttribute(TransitionContext.ATTR_DIRECTION, TransitionContext.DIRECTION_UP);
	        transition.setIntAttribute(TransitionContext.ATTR_STYLE, TransitionContext.STYLE_PUSH);
	        
	        UiEngineInstance engine = Ui.getUiEngineInstance();
	        engine.setTransition(null, loginSuccessScreen, UiEngineInstance.TRIGGER_PUSH, transition);
	        
	        UiApplication.getUiApplication().pushScreen(loginSuccessScreen);
  
        }
    }

	public class LoginSuccessScreen extends MainScreen implements Runnable{
		private int verticalOffset;
		private final static long animationTime = 300;
	    private long animationStart = 0;

	    public LoginSuccessScreen(String username, String domain) {
	        try {
	            FontFamily alphaSansFamily = FontFamily.forName("BBAlpha Serif");
	            Font appFont = alphaSansFamily.getFont(Font.PLAIN, 9, Ui.UNITS_pt);
	            setFont(appFont);
	        } catch (ClassNotFoundException e) {
	        }
	        add(new CustomLabelField("Logged In!", Color.WHITE, 0x999966, Field.USE_ALL_WIDTH));
	        add(new SeparatorField());
	        GridFieldManager gridFieldManager = new GridFieldManager(2, 0);
	        gridFieldManager.add
	          (new CustomLabelField("Username:", Color.BLACK, Color.WHITE, Field.FIELD_RIGHT));
	        gridFieldManager.add
	          (new CustomLabelField(username, Color.BLACK, Color.LIGHTGRAY, Field.USE_ALL_WIDTH));
	        gridFieldManager.add
	          (new CustomLabelField("Domain:", Color.BLACK, Color.WHITE, Field.FIELD_RIGHT));
	        gridFieldManager.add
	          (new CustomLabelField(domain, Color.BLACK, Color.LIGHTGRAY, Field.USE_ALL_WIDTH));
	        add(gridFieldManager);
	        
	        verticalOffset = Display.getHeight();

	        new Thread((Runnable) this).start();
	        
	    }

	    protected void sublayout(int width, int height) {
	        super.sublayout(width, height);
	        if (verticalOffset > 0) {
	            if (animationStart == 0) {
	                // start the animation
	                animationStart = System.currentTimeMillis();
	            }
	            else {
	                long timeElapsed = System.currentTimeMillis() - animationStart;
	                if (timeElapsed >= animationTime) {
	                    verticalOffset = 0;
	                }
	                else {
	                    float percentDone = (float)timeElapsed / (float)animationTime;
	                    verticalOffset = 
	                      Display.getHeight() - (int)(percentDone * Display.getHeight());
	                }
	            }
	        }
	        setPosition(0, verticalOffset);

	        if (verticalOffset > 0) {
	            UiApplication.getUiApplication().invokeLater(new Runnable() {
	                public void run() {
	                    updateLayout();
	                }
	            });
	        }
	    }

		public void run() {
		}
	    
	}

	public class LoginSuccessScreenAlternate extends MainScreen {
	    public LoginSuccessScreenAlternate(String username, String domain) {
	        try {
	            FontFamily alphaSansFamily = FontFamily.forName("BBAlpha Serif");
	            Font appFont = alphaSansFamily.getFont(Font.PLAIN, 9, Ui.UNITS_pt);
	            setFont(appFont);
	        } catch (ClassNotFoundException e) {
	        }
	        add(new CustomLabelField("Logged In!", Color.WHITE, 0x999966, Field.USE_ALL_WIDTH));
	        add(new SeparatorField());
	        GridFieldManager gridFieldManager = new GridFieldManager(2, 0);
	        gridFieldManager.add
	          (new CustomLabelField("Username:", Color.BLACK, Color.WHITE, Field.FIELD_RIGHT));
	        gridFieldManager.add
	          (new CustomLabelField(username, Color.BLACK, Color.LIGHTGRAY, Field.USE_ALL_WIDTH));
	        gridFieldManager.add
	          (new CustomLabelField("Domain:", Color.BLACK, Color.WHITE, Field.FIELD_RIGHT));
	        gridFieldManager.add
	          (new CustomLabelField(domain, Color.BLACK, Color.LIGHTGRAY, Field.USE_ALL_WIDTH));
	        add(gridFieldManager);
	        
	    }
	}
	
}
