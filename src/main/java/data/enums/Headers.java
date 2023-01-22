package data.enums;

public enum Headers {
    COMPANY("X-Customer-Code"),
    TOKEN("X-Api-Key");

    private final String httpHeader;

    Headers(String httpHeader) {
       this.httpHeader = httpHeader;
    }

    public String getHttpHeader(){
        return this.httpHeader;
    }
}
