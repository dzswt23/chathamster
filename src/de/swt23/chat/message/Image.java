package de.swt23.chat.message;

import de.swt23.chat.receiver.Entity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * the image class inherits from message and provides information and functionality for an image message
 */
public class Image extends Message {
    private final String path;

    //Constructor
    public Image(Entity entity, MessageDirection direction, String timeStamp, String path) {
        super(entity, direction, timeStamp);
        this.path = path.toLowerCase();
    }

    public Image(Entity entity, MessageDirection direction, String path) {
        super(entity, direction);
        this.path = path.toLowerCase();
    }

    /**
     * get the type of the file that was provided
     *
     * @return a string of the type of the image
     */
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

    /**
     * create an input stream to read the data of the image
     *
     * @return open FileInputStream
     */
    public InputStream getImageData() {
        if (super.getDirection() == MessageDirection.OUT) {
            try {
                return new FileInputStream(path);
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred whilst processing the image: " + e.getMessage());
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
