package de.swt23.chat.message;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;

public class Image extends Message{

    //Attributes
    private String mimeType;
    private String imageData;
    private String url;

    //Constructor
    public Image(Receiver receiver, String url){
        super(receiver);
        this.url = url.toLowerCase();
    }

    //Methods
    public String getMimeType(){
        if(url.contains(".png")) {
            mimeType = "png";

        }
        if(url.contains(".jpg")){
           mimeType = "jpg";
        }
        return mimeType;
    }

    public InputStream getImageData(){

        byte[] array = new byte[100];

        try {
            InputStream is = new FileInputStream(url);

            System.out.println("Available bytes in the file: " + is.available());

            // Read byte from the input stream
            is.read(array);
            System.out.println("Data read from the file: ");

            // Convert Byte Data to String
            String data = new String(array);
            System.out.println(data);

            // Close the input stream
            is.close();
            return is;

        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }
}
