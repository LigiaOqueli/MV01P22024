package com.example.mv01p22024;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import Configuracion.Personas;
import Configuracion.SQLLiteConexion;
import Configuracion.Trans;

public class ActivityCombo extends AppCompatActivity {

    SQLLiteConexion conexion;
    Spinner combopersonas;
    EditText nombres, apellidos, correo;
    ArrayList<Personas> lista;
    ArrayList<String> Arreglo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_combo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        conexion = new SQLLiteConexion(this, Trans.DBname, null, Trans.Version);
        combopersonas = (Spinner) findViewById(R.id.spinner);
        nombres = (EditText) findViewById(R.id.cbnombre);
        apellidos = (EditText) findViewById(R.id.cbapellido);
        correo = (EditText) findViewById(R.id.cbcorreo);

        //declarar funcion
        ObtenerInfo();

    }

    private void ObtenerInfo()
    {
        //TRAER LOS DATOS DE LA BSE DE DATOS PARA QUE SEAN LEIDOS
        SQLiteDatabase db = conexion.getReadableDatabase();

        //Crear una variable person del tipo Personas que no haga referencia a ningun objeto null
        Personas persona = null;

        //Crear un array que contenga los objetos del tipo Personas
        lista = new ArrayList<>();

        //Creamos un cursor que permita ejecutar la consulta del SQL
        Cursor cursor = db.rawQuery(Trans.SelectAllPerson, null);

        //El bucle while nos dice que mientras hayan filas en el cursos esta devolver true
        //permitiendo que vaya a la siguiente fila con el metodo moveToNext

        while (cursor.moveToNext()) {
          //Creamos una nueva instancia de Personas vacio
          persona = new Personas();
          //para las demas lineas seteamos el valor dependiendo de la posicion
          persona.setId(cursor.getInt(0));
          persona.setNombres(cursor.getString(1));
          persona.setApellidos(cursor.getString(2));
          persona.setCorreo(cursor.getString(4));

          //en cada vuelta del bucle agregamos los datos al ArrayList lista
          lista.add(persona);
        }
        //cerramos el cursor
        cursor.close();

        //funcion
        FillData();
        

    }

    private void FillData()
    {
        //se crea una variable vacia
        Arreglo = new ArrayList<>();
        //recorre la lista de los objetos obteniendo la informacion de cada objeto almacenando en Arreglo
        for (int i = 0; i < lista.size(); i++) {
            Arreglo.add(lista.get(i).getId() + " " +
                    lista.get(i).getNombres() + " " +
                    lista.get(i).getApellidos() + " " +
                    lista.get(i).getCorreo());
    }
    //se crea variable de tipo adaptador
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Arreglo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //para seleccionar una de los objeto individual
        combopersonas.setAdapter(adapter);

        combopersonas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Personas selectedPerson = lista.get(position);
                nombres.setText(selectedPerson.getNombres());
                apellidos.setText(selectedPerson.getApellidos());
                correo.setText(selectedPerson.getCorreo());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

    }
}
