package com.beginningblackberry.unifiedsearch;

public class Customer 
{
    private String _name;
    private String _data;        
    
    public Customer(String name, String data) {
        _name = name;
        _data = data;        
    }

    public String toString()  {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getName()).append(", ").append(getData());
        return buffer.toString();
    }
    
    public String getName() {
        return _name;
    }  

    public String getData() {
        return _data;
    }      

}
