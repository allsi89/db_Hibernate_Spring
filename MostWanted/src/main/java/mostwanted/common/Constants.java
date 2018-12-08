package mostwanted.common;

public class Constants {

    private static final String DIR_PATH = System.getProperty("user.dir");

    public final static String DUPLICATE_DATA_MESSAGE = "Error: Duplicate Data!";

    public final static String INCORRECT_DATA_MESSAGE = "Error: Incorrect Data!";

    public final static String SUCCESSFUL_IMPORT_MESSAGE = "Successfully imported %s – %s.";

    public static final String CARS_JSON_FILE_PATH = DIR_PATH + "/src/main/resources/files/cars.json";
    public static final String DISTRICTS_JSON_FILE_PATH = DIR_PATH + "/src/main/resources/files/districts.json";
    public static final String RACERS_JSON_FILE_PATH = DIR_PATH + "/src/main/resources/files/racers.json";
    public static final String TOWNS_JSON_FILE_PATH = DIR_PATH + "/src/main/resources/files/towns.json";
    public static final String RACE_ENTRIES_XML_FILE_PATH = DIR_PATH + "/src/main/resources/files/race-entries.xml";
    public static final String RACES_XML_FILE_PATH = DIR_PATH + "/src/main/resources/files/races.xml";
}
