package com.service.auto.util;

public class DataBaseConstant {
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    public static final String DATE_PATTERN = "dd.MM.yyyy";


    // ==========================================================
    // ==================== FILE_STORAGE ========================
    // ==========================================================
    public static final String FILE_STORAGE_TABLE = "file_storage";
    public static final String FILE_STORAGE_ID = "id";
    public static final String FILE_STORAGE_FILE_MD5 = "file_md5";
    public static final String FILE_STORAGE_UPLOAD_DATE = "file_upload_date";
    public static final String FILE_STORAGE_SUBDIR_PATH = "file_subdir_path";
    public static final String FILE_STORAGE_ROOT_PATH = "file_root_path";
    public static final String FILE_STORAGE_ORIGINAL_NAME = "file_original_name";
    public static final String FILE_STORAGE_UTILIZATOR = "utilizator";
    public static final String FILE_STORAGE_IS_ENABLED = "is_enabled";
    public static final String FILE_STORAGE_VERSION = "version";

    // ==========================================================
    // ======================== MARCA ===========================
    // ==========================================================
    public static final String MARCA_TABLE = "marca";
    public static final String MARCA_ID = "id";
    public static final String MARCA_NUME = "nume";
    public static final String MARCA_TARA_ORIGINE = "tara_origine";
    public static final String MARCA_IS_ENABLED = "is_enabled";
    public static final String MARCA_VERSION = "version";

    // ==========================================================
    // ======================== MODEL ===========================
    // ==========================================================
    public static final String MODEL_TABLE = "model";
    public static final String MODEL_ID = "id";
    public static final String MODEL_FK_MARCA = "fk_marca";
    public static final String MODEL_NUME_MODEL = "nume_model";
    public static final String MODEL_AN_FABRICATIE = "an_fabricatie";
    public static final String MODEL_IS_ENABLED = "is_enabled";
    public static final String MODEL_VERSION = "version";

    // ==========================================================
    // ====================== PROGRAMARE ========================
    // ==========================================================
    public static final String PROGRAMARE_TABLE = "programare";
    public static final String PROGRAMARE_ID = "id";
    public static final String PROGRAMARE_FK_USER = "fk_user";
    public static final String PROGRAMARE_FK_FILE_STORAGE = "fk_file_storage";
    public static final String PROGRAMARE_FK_MARCA = "fk_marca";
    public static final String PROGRAMARE_FK_MODEL = "fk_model";
    public static final String PROGRAMARE_DATA = "data_programare";
    public static final String PROGRAMARE_ORA = "ora_programare";
    public static final String PROGRAMARE_MINUT = "minut_programare";
    public static final String PROGRAMARE_SERIE_SASIU = "serie_sasiu";
    public static final String PROGRAMARE_OBSERVATII = "observatii";
    public static final String PROGRAMARE_IS_CONFIRMED = "is_confirmed";
    public static final String PROGRAMARE_IS_CANCELED = "is_canceled";
    public static final String PROGRAMARE_CREATED_AT = "created_at";
    public static final String PROGRAMARE_VERSION = "version";
}
