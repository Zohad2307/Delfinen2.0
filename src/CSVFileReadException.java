public class CSVFileReadException extends RuntimeException{

    public CSVFileReadException(String msg, Exception error) {
        super(msg, error);
    }
}
