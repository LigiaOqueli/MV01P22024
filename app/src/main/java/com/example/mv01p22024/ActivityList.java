package com.example.mv01p22024;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import Configuracion.Personas;
import Configuracion.SQLLiteConexion;
import Configuracion.Trans;

public class ActivityList extends AppCompatActivity {

    SQLLiteConexion conexion;
    ListView listperson;
    ArrayList<Personas> lista;
    //crear un arreglo de string para poder declarar
    ArrayList<String> Arreglo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        conexion = new SQLLiteConexion(this, Trans.DBname, null, Trans.Version);
        listperson = (ListView) findViewById(R.id.listperson);

        ObtenerInfo();

        //llenar objeto de lista
        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Arreglo);
        listperson.setAdapter(adp);

        //para declarar una nueva lista adapter
        listperson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ElementoSeleccionado = (String) parent.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(), ElementoSeleccionado, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ObtenerInfo() {
        //conexion a la bd
        SQLiteDatabase db = conexion.getReadableDatabase();
        //declarar objeto
        Personas person = null;
        //nueva instancia
        lista = new ArrayList<Personas>();

        //Cursor para recorrer los datos de la tabla selectAllPerson es un escript creado, null no requiere argumentos que mandarle
        Cursor cursor = db.rawQuery(Trans.SelectAllPerson, null);

        //hay que moverse dentro del cursor
        while (cursor.moveToNext())
        {
            //aqui vamos a utilizar el constructor vacio que creamos
            person = new Personas();
            //0 es la primera posicion o sea el id inicia en posicion 0, nombres posicion 1, etc...
            person.setId(cursor.getInt(0));
            person.setNombres(cursor.getString(1));
            person.setApellidos(cursor.getString(2));
            person.setEdad(cursor.getInt(3));
            person.setCorreo(cursor.getString(4));

            //listar
            lista.add(person);

        }

        //cerrar curso
        cursor.close();

        FillData();
    }

    private void FillData()
    {
        Arreglo = new ArrayList<String>();
        //recorrer el arreglo
        for(int i=0; i < lista.size(); i++)
        {
            //llenar informacion
            Arreglo.add(lista.get(i).getId() + " "+
                    lista.get(i).getNombres() + " "+
                    lista.get(i).getApellidos());
        }

    }


}