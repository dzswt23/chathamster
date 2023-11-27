package de.swt23.chat.message;


import java.io.InputStream;
import java.io.FileInputStream;

public class Image extends Message{

    //Attributes
    private String mimeType;

    private String path;

    //Constructor
    public Image(Receiver receiver, String path){
        super(receiver);
        this.path = path.toLowerCase();
    }

    //Methods
    public String getMimeType(){
        String mimeType = "image/";
        if(path.contains(".png")) {
            mimeType += "png";

        }
        if(path.contains(".jpg") || path.contains(".jpeg")){
           mimeType += "jpg";
        }
        return mimeType;
    }

    public InputStream getImageData(){

        byte[] array = new byte[100];

        try {
            InputStream is = new FileInputStream(path);

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
