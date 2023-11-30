package de.swt23.chat.message;


import de.swt23.chat.receiver.Receiver;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Image extends Message {

    //Attributes
    private String path;


    //Constructor
    public Image(Receiver receiver, MessageDirection direction, String timeStamp, String path) {
        super(receiver, direction, timeStamp);
        this.path = path.toLowerCase();
    }

    public Image(Receiver receiver, MessageDirection direction, String path) {
        super(receiver, direction);
        this.path = path.toLowerCase();
    }

    //Methods
    public String getMimeType() {
        if (super.getDirection() == MessageDirection.OUT) {
            String mimeType = "image/";
            if (path.contains(".png")) {
                mimeType += "png";
            }
            if (path.contains(".jpg") || path.contains(".jpeg")) {
                mimeType += "jpg";
            }

            return mimeType;

        }
        return null;
    }

    public InputStream getImageData() {
        if (super.getDirection() == MessageDirection.IN) {
            try {
                FileInputStream is = new FileInputStream(path);

                return is;
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + path;
    }
}
