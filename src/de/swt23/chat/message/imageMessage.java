package de.swt23.chat.message;

public class imageMessage extends Message{

    //Attributes
    private String mimeType;
    private String imageData;

    //Constructor
    public imageMessage(String mimeType, String imageData){
        super(receiver);
        this.mimeType = mimeType;
        this.imageData = imageData;
    }

    //Methods
    public String getMimeType(){
        return mimeType;
    }

    public String getImageData(){
        return imageData;
    }
}
