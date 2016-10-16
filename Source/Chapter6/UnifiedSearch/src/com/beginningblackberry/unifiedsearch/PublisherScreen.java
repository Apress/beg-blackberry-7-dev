package com.beginningblackberry.unifiedsearch;

import java.util.*;
import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.container.*;

public class PublisherScreen extends MainScreen implements FieldChangeListener
{    
    private ObjectListField _listField;    
    private ButtonField _publishButton;
    private Vector _dataObjects;    
    private Publisher _publisher;   
      
    public PublisherScreen(Publisher publisher) {      
        super(NO_VERTICAL_SCROLL);
               
        _publisher = publisher;
        _dataObjects = new Vector();           
        
        setTitle("Unified Search");        
        
        _listField = new ObjectListField();                
        
        _publishButton = new ButtonField("Add and Publish Data", ButtonField.CONSUME_CLICK);                
        _publishButton.setChangeListener(this);       
        
        VerticalFieldManager vfm = new VerticalFieldManager(VERTICAL_SCROLL);               
        vfm.add(_publishButton);
        vfm.add(new SeparatorField());
        vfm.add(_listField);
        
        add(vfm);        
    } 
    
    protected boolean onSavePrompt() {        
        if(_dataObjects.size() == 0) {
            return true;
        }        
        
        return super.onSavePrompt(); 
    }   
    
    public void save() {
    	if (_dataObjects.size()==0) {
            _dataObjects.addElement(new Customer("ABC Corp", "111 One St, New York, NY"));
            _dataObjects.addElement(new Customer("ACME LTD", "1 Acme St, Acme City"));
            
            Object[] elementArray = new Object[_dataObjects.size()];
            _dataObjects.copyInto(elementArray);
            _listField.set(elementArray);       

            _publisher.insertData(_dataObjects);      
    	}
        
        setDirty(false);            
    }  

    public void fieldChanged(Field field, int context) {        
        if (field == _publishButton) {
        	save();
        }               
    }
}
