package ai.deepdetect.exceptions;

public class RecordNotFoundException extends Exception {

    public RecordNotFoundException() {
        super("Requested Record Not Found.");
    }
    
    public RecordNotFoundException(String errorMsg) {
        super(errorMsg);
    }

}
