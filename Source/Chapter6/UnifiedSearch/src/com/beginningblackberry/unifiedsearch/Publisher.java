package com.beginningblackberry.unifiedsearch;

import java.util.Vector;
import net.rim.device.api.system.*;
import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.image.*;
import net.rim.device.api.unifiedsearch.*;
import net.rim.device.api.unifiedsearch.content.*;
import net.rim.device.api.unifiedsearch.searchables.adapters.*;

public class Publisher implements AppContentListener
{
    private EntityBasedSearchableProvider _searchableProvider;
    private UiApplication _uiApplication;

    public Publisher() throws Exception
    {
        _searchableProvider = new EntityBasedSearchableProvider();        
        
        SearchField[] searchFields = new SearchField[] {new SearchField(SearchableCustomer.FIELD_NAME), new SearchField(SearchableCustomer.FIELD_DATA)};
        
        _searchableProvider.setSupportedSearchFields(searchFields);

        Bitmap bitmap = Bitmap.getBitmapResource("app_logo.png");

        if(bitmap != null) {
            Image img = ImageFactory.createImage(bitmap);
            _searchableProvider.setIcon(img);
        }
            
        _searchableProvider.setName("Unified Search");    
        
        if(!_searchableProvider.register().isValid()) {
            throw new Exception("Failed to register with the Unified Search Framework");
        }

        _uiApplication = UiApplication.getUiApplication();
    }

    public void insertData(Vector dataVector)
    {
        SearchableCustomer[] dataEntities;
        
        synchronized(dataVector)
        {
            // Build a UnifiedSearchDemoEntity array out of the given data objects
            int size = dataVector.size();
            dataEntities = new SearchableCustomer[size];

            for(int i = size - 1; i >= 0; --i)
            {
                Customer object = (Customer)dataVector.elementAt(i);
                dataEntities[i] = new SearchableCustomer(object, _searchableProvider);
            }
        }
        
        _searchableProvider.addSearchableData(dataEntities, this);
    }

    // AppContentListener implementation -----------------------------------------
    public void onInsertComplete(final int insertCount)
    { 
        _uiApplication.invokeLater(new Runnable() {
            public void run() {
                // Inform user of successful insert
                StringBuffer buffer = new StringBuffer("Inserted ");
                buffer.append(insertCount);
                buffer.append(" data objects. Try the search word \"acme\"!");
                Status.show(buffer.toString(), 2000);
            }
        });
    }

    public void onUpdateComplete(int updCount) {}

	public void onDeleteComplete(int arg0) {}

}
