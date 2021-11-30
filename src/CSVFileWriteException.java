public class CSVFileWriteException extends RuntimeException{

    public CSVFileWriteException(String msg, Exception error) {
        super(msg, error);
    }
}