package com.beginningblackberry.fileconnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.ObjectListField;
import net.rim.device.api.ui.container.GridFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.util.StringProvider;

public class FileConnectionScreen extends MainScreen {
    private ObjectListField fileList;
    private String currentPath = "file:///";
    private GridFieldManager grid = new GridFieldManager(2,3,0); ;
    public FileConnectionScreen() {
        setTitle("FileConnection");

        fileList = new ObjectListField();

        fileList.set(new String[] {"store/", "SDCard/"});

        add(fileList);
    }

    protected void makeMenu(Menu menu, int instance) {
        super.makeMenu(menu, instance);
        
        menu.add(new MenuItem(new StringProvider("Select"), 10, 10) {
            public void run() {
                loadFile();
            }
        });
        
        String selectedItem = (String)fileList.get(fileList, fileList.getSelectedIndex());
        if (!selectedItem.endsWith("/")) {
            menu.add(new MenuItem(new StringProvider("Copy"), 10, 10) {
                public void run() {
                    copyFile();
                }
            });
        }
        
    }

    private void loadFile() {
        currentPath += fileList.get(fileList, fileList.getSelectedIndex());
        try {
            FileConnection fileConnection = (FileConnection)Connector.open(currentPath);
            if (fileConnection.isDirectory()) {
                Enumeration directoryEnumerator = fileConnection.list();
                Vector contentVector = new Vector();
                while(directoryEnumerator.hasMoreElements()) {

                    contentVector.addElement(directoryEnumerator.nextElement());
                }
                String[] directoryContents = new String[contentVector.size()];
                contentVector.copyInto(directoryContents);
                fileList.set(directoryContents);
            }
            else if (currentPath.endsWith(".jpg") || currentPath.endsWith(".png")) {
                InputStream inputStream = fileConnection.openInputStream();
                byte[] imageBytes = new byte[(int)fileConnection.fileSize()];
                inputStream.read(imageBytes);
                inputStream.close();
                EncodedImage eimg = EncodedImage.createEncodedImage(imageBytes, 0, imageBytes.length);

                UiApplication.getUiApplication().pushScreen(new ImageDisplayScreen(eimg));



            }


        } catch (IOException ex) {

        }
    }

    private void copyFile() {
        // Prompt for the new filename
        FileNameScreen screen = new FileNameScreen();
        UiApplication.getUiApplication().pushModalScreen(screen);
        String newFilename = screen.getFilename();

        try {
            FileConnection newFileConnection = (FileConnection)Connector.open(currentPath + newFilename);
            if (newFileConnection.exists()) {
                Dialog.alert("The file '" + newFilename + "' already exists!");
                newFileConnection.close();
                return;
            }

            // The file doesn't exist, so we'll create it
            newFileConnection.create();
            OutputStream newFileOutputStream = newFileConnection.openOutputStream();

            // Open the old file
            currentPath += fileList.get(fileList, fileList.getSelectedIndex());
            FileConnection fileConnection = (FileConnection)Connector.open(currentPath);
            InputStream inputStream = fileConnection.openInputStream();


            // Copy the contents of the old file into the new one
            byte[] fileContents = new byte[(int)fileConnection.fileSize()];
            inputStream.read(fileContents);
            newFileOutputStream.write(fileContents, 0, fileContents.length);
            inputStream.close();
            newFileOutputStream.close();
            Dialog.inform("Successfully copied the file!");
        } catch (IOException ex) {

        }
    }

}
