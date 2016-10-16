package com.beginningblackberry.database;


import java.util.Enumeration;
import java.util.Hashtable;

import net.rim.device.api.database.Cursor;
import net.rim.device.api.database.Database;
import net.rim.device.api.database.DatabaseException;
import net.rim.device.api.database.DatabaseFactory;
import net.rim.device.api.database.DatabaseIOException;
import net.rim.device.api.database.Row;
import net.rim.device.api.database.Statement;
import net.rim.device.api.io.URI;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ObjectListField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;

public final class DatabaseScreen extends MainScreen implements FieldChangeListener
{
    private ButtonField addButton = new ButtonField("Add Records", ButtonField.CONSUME_CLICK | Field.FIELD_HCENTER);
    private ButtonField displayButton = new ButtonField("Refresh List", ButtonField.CONSUME_CLICK | Field.FIELD_HCENTER);
    private ButtonField deleteButton = new ButtonField("Delete Records", ButtonField.CONSUME_CLICK | Field.FIELD_HCENTER);
    private ObjectListField recordList = new ObjectListField();
    private Database db;

    public DatabaseScreen()
    {        
        LabelField title = new LabelField("Database Example",
                 LabelField.ELLIPSIS | 
                 LabelField.USE_ALL_WIDTH);
        
		setTitle(title);
		
		HorizontalFieldManager hfm = new HorizontalFieldManager();
        addButton.setChangeListener(this);
		displayButton.setChangeListener(this);
		deleteButton.setChangeListener(this);
        hfm.add(addButton);
        hfm.add(displayButton);
        hfm.add(deleteButton);
        
        add(hfm);
        
        add(recordList);
		
		if (openOrCreateDatabase()) {
			createDatabaseSchema();
		}
    }
    
    public void fieldChanged(Field field, int context) {
        if (field == displayButton) {
        	readTableData();
        } else if (field == addButton) {
        	insertTableData();        	
        	readTableData();
        } else if (field == deleteButton) {
        	if (deleteTableData()) {
        		clearList();
        	}
        }
    }    
    
    private void clearList() {
		for (int i=recordList.getSize()-1;i>=0;i--) {
    		recordList.delete(i);
		}
    }
    
    private boolean openOrCreateDatabase() {
		try {
			URI myURI = URI.create("/SDCard/BlackBerry/documents/Person.db"); 
			db = DatabaseFactory.openOrCreate(myURI);
		} catch (Exception e) {
			return false;
		}
    	return true;
    }
    
    private void closeDatabase() {
		try {
			db.close();
		} catch (DatabaseIOException e) {
		}
    }
    
    private void createDatabaseSchema() {
        Statement st = null;
		try {
			st = db.createStatement( "CREATE TABLE 'People' ( " +
			                    "'Name' TEXT, " +
			                    "'Age' INTEGER )" );
			st.prepare();
			st.execute();
		} catch ( Exception e ) {         
			System.out.println( e.getMessage() );
			e.printStackTrace();
		} finally {
			try {
				st.close();
			} catch (DatabaseException e) {
			}
		}
    }

    private void insertTableData() {
        Statement st = null;
        try {
        	st = db.createStatement("INSERT INTO People(Name,Age) VALUES (?,?)");
            st.prepare();

            Hashtable ht = new Hashtable(5);
            ht.put("Joe", new Integer(6));
            ht.put("Mike",  new Integer(7));
            ht.put("Marie",  new Integer(12));
            ht.put("Dennis",  new Integer(14));
            ht.put("Richard",  new Integer(12));

            Enumeration names = ht.keys();
            Enumeration ages  = ht.elements();

            while (names.hasMoreElements()) {
               String strName = (String)names.nextElement();
               Integer iAge   = (Integer)ages.nextElement();
               st.bind(1,strName);
               st.bind(2,iAge.intValue());
               st.execute();
               st.reset();
            }
        } catch ( Exception e ) {         
            e.printStackTrace();
		} finally {
			try {
				st.close();
			} catch (DatabaseException e) {
			}
        }    	
    }

    private boolean readTableData() {
    	if (db==null) {
            Dialog.alert("no database opened");
    		return false;
    	}

    	clearList();
    	
    	Statement st = null;
    	
        try {
            st = db.createStatement("SELECT Name,Age FROM People order by Name desc");
            st.prepare();
            Cursor c = st.getCursor();

            if (c==null) {
            	Dialog.alert("cursor is null");
            	return false;
            }
            
            Row r;
            int i = 0;
            while(c.next()) {
                r = c.getRow();
                i++;
				recordList.insert(0, r.getString(0) + " - " + r.getInteger(1) + " years old");
            }
            if (i==0) {
            	return false;
            }

        } catch ( Exception e ) {         
            e.printStackTrace();
		} finally {
			try {
				st.close();
			} catch (DatabaseException e) {
			}
        }
		return true;
    }
    
    private boolean deleteTableData() {
    	boolean result = false;
    	if (db==null) {
            Dialog.alert("no database opened");
    		return result;
    	}
    	
    	Statement st = null;
        try {
            st = db.createStatement("DELETE FROM People");
            st.prepare();
            st.execute();
            result = true;
        } catch ( Exception e ) {         
            e.printStackTrace();
        }
        return result;
    }
}
