package data.enums;

public enum Product {
    INTELLIJ_IDEA("II", "IntelliJ IDEA Ultimate"),
    PY_CHARM("PC", "PyCharm"),
    CLION("CL","CLion"),
    WEB_STORM("WS","WebStorm"),
    TOOLBOOX_ENTRPRS("TBEB", "Toolbox Enterprise"),
    NOT_EXISTED("NULL", "INVALID PRODUCT"),
    ;

    private final String name;
    private final String code;

    Product(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode(){
        return this.code;
    }
}
