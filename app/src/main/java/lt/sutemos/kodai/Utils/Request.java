package lt.sutemos.kodai.Utils;

public enum Request {
    IMPORT_CSV(0), EXPORT_CSV(1);

    private final int code;
    Request(int i) {
        this.code = i;
    }

    public int getCode(){return code;}
}
