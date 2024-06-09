package Configuracion;

public class Trans {
    //Nombre de la base de datos
    public static final String DBname = "PM12P";

    //Tabla personas
    public static final String TablePersonas = "personas";

    //Propiedades
    public static final String id = "id";
    public static final String nombres = "nombres";
    public static final String apellidos = "apellidos";
    public static final String edad = "edad";
    public static final String correo = "correo";
    public static final String foto = "foto";

    // DDL Crear objetos de BD
    public static final String CreateTablePersonas = "CREATE TABLE " + TablePersonas + " ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, nombres TEXT, apellidos TEXT, edad INTEGER, correo TEXT, foto TEXT )";



}
