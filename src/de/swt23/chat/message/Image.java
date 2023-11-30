package de.swt23.chat.message;


import de.swt23.chat.receiver.Receiver;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Image extends Message {

    //Attributes
    private String path;

    //Constructor
    public Image(Receiver receiver, String path) {
        super(receiver);
        this.path = path.toLowerCase();
    }

    //Methods
    public String getMimeType() {
        String mimeType = "image/";
        if (path.contains(".png")) {
            mimeType += "png";
        }
        if (path.contains(".jpg") || path.contains(".jpeg")) {
            mimeType += "jpg";
        }

        return mimeType;
    }

    public InputStream getImageData() {
        try {
            FileInputStream is = new FileInputStream(path);


            return is;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }
}
