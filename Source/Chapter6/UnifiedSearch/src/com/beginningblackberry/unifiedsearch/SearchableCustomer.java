package com.beginningblackberry.unifiedsearch;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.component.Status;
import net.rim.device.api.ui.image.Image;
import net.rim.device.api.ui.image.ImageFactory;
import net.rim.device.api.unifiedsearch.SearchField;
import net.rim.device.api.unifiedsearch.SearchFieldCriteria;
import net.rim.device.api.unifiedsearch.SearchFieldCriteriaList;
import net.rim.device.api.unifiedsearch.action.UiAction;
import net.rim.device.api.unifiedsearch.searchables.Searchable;
import net.rim.device.api.unifiedsearch.searchables.adapters.EntityBasedSearchableProvider;
import net.rim.device.api.unifiedsearch.searchables.adapters.SearchableDataObject;

public class SearchableCustomer extends SearchableDataObject
{   
    public static final String FIELD_NAME = "Name";
    public static final String FIELD_DATA = "Data";    
	
    private Customer _dataObject;
    private EntityBasedSearchableProvider _searchableProvider;
    private SearchFieldCriteriaList _searchFieldCriteriaList;
    private Image _icon;
    private UiAction _action;

    public SearchableCustomer(Customer dataObject, EntityBasedSearchableProvider searchableProvider)
    {
        _dataObject = dataObject;
        _searchableProvider = searchableProvider;
        _searchFieldCriteriaList = new SearchFieldCriteriaList();

        Bitmap img = Bitmap.getBitmapResource("location.png");

        if(img != null) {
            _icon = ImageFactory.createImage(img);
        } else {
            _icon = searchableProvider.getIcon();
        }
        initializeCriteriaFields();
        _action = new LocationAction();
        

    }
    
    private void initializeCriteriaFields() {

        SearchField[] fields = _searchableProvider.getSupportedSearchFields();

        for(int i = 0; i < fields.length; i++)
        {
            String delimiter = " ";
            String[] searchPhrase = null;
                        
            if(fields[i].getName().equals(FIELD_NAME)) {               
                searchPhrase = new String[] {_dataObject.getName()};                
            } else if(fields[i].getName().equals(FIELD_DATA)) {                
                searchPhrase = new String[] {_dataObject.getData()};                
            }
            
            // Add criteria to list
            SearchFieldCriteria searchFieldCriteria = new SearchFieldCriteria(fields[i], searchPhrase, true, delimiter);
            _searchFieldCriteriaList.addCriteria(searchFieldCriteria);              
        }
    }

    public Object getData()
    {
        return _dataObject;
    }

    public SearchFieldCriteriaList getSearchCriteria()
    {
        return _searchFieldCriteriaList;
    }

    public Searchable getSearchable()
    {
        return _searchableProvider;
    }

    public String getSummary()
    {
        return _dataObject.getData();
    }

    public String getTitle()
    {
        return _dataObject.getName();
    }

    public Image getIcon()
    {
        return _icon;
    }    

    public UiAction getUiActions(Object contextObject, UiAction[] uiActions)
    {
        return _action;
    }

    private class LocationAction extends UiAction
    {
        protected void runAction() {
        	Status.show("Do stuff here");
        }
    }
}
