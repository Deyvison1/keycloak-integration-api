package br.com.generic.constants;

public final class ApiConstants {

    private ApiConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String SUCCESS = "Operação realizada com sucesso.";
    public static final String ERR = "Operação falhou.";

    public static final String API = "/api";
    public static final String VERSION = "/v1";
    public static final String API_V1 = API + VERSION;

}
