package com.pmdm.university.implementacion;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

import com.pmdm.university.entidad.UniversityDetail;
import com.pmdm.university.interfaz.Repositorio;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class UniversityDetailSQLiteHelper extends SQLiteOpenHelper implements Repositorio<UniversityDetail> {
    public UniversityDetailSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "University.db";

    public UniversityDetailSQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static class ContratoUniversity{
        private ContratoUniversity(){}
        public static class EntradaUniversity implements BaseColumns{
            public static final String TABLE_NAME = "UNIVERSITY_DETAIL";
            public static final String NAME = "NAME";
            public static final String IMAGE_URL = "IMAGE_URL";
            public static final String DESCRIPTION = "DESCRIPTION";

        }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ContratoUniversity.EntradaUniversity.TABLE_NAME + " (" +
                ContratoUniversity.EntradaUniversity.NAME + " TEXT NOT NULL," +
                ContratoUniversity.EntradaUniversity.IMAGE_URL + " TEXT NOT NULL," +
                ContratoUniversity.EntradaUniversity.DESCRIPTION + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    @Override
    public Optional<UniversityDetail> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<UniversityDetail> getAll() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(
                ContratoUniversity.EntradaUniversity.TABLE_NAME, // Nombre de la tabla
                null, // Lista de Columnas a consultar
                null, // Columnas para la cláusula WHERE
                null, // Valores a comparar con las columnas del WHERE
                null, // Agrupar con GROUP BY
                null, // Condición HAVING para GROUP BY
                null // Cláusula ORDER BY
        );

        List<UniversityDetail> universities = new LinkedList<>();
        while(c.moveToNext()){
            @SuppressLint("Range")
            String name = c.getString(
                    c.getColumnIndex(ContratoUniversity.EntradaUniversity.NAME));
            @SuppressLint("Range")
            String foto = c.getString(
                    c.getColumnIndex(ContratoUniversity.EntradaUniversity.IMAGE_URL));
            @SuppressLint("Range")
            String descripcion = c.getString(
                    c.getColumnIndex(ContratoUniversity.EntradaUniversity.DESCRIPTION));
            universities.add(new UniversityDetail());
        }

        return universities;
    }

    @Override
    public void save(UniversityDetail university) {this.save(university, null);}

    private void save(UniversityDetail university, SQLiteDatabase db) {
        if(db == null)
            db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ContratoUniversity.EntradaUniversity.NAME, university.getName());
        values.put(ContratoUniversity.EntradaUniversity.IMAGE_URL, university.getImageUrl());
        values.put(ContratoUniversity.EntradaUniversity.DESCRIPTION, university.getDescription());
        db.insert(ContratoUniversity.EntradaUniversity.TABLE_NAME, null, values);
    }

    @Override
    public void update(UniversityDetail university) {
// Obtenemos la BBDD para escritura
        SQLiteDatabase db = getWritableDatabase();
        // Contenedor de valores
        ContentValues values = new ContentValues();
        // Pares clave-valor
        values.put(ContratoUniversity.EntradaUniversity.NAME, university.getName());
        values.put(ContratoUniversity.EntradaUniversity.IMAGE_URL, university.getImageUrl());
        values.put(ContratoUniversity.EntradaUniversity.DESCRIPTION, university.getDescription());
        // Actualizar...
        db.update(ContratoUniversity.EntradaUniversity.TABLE_NAME,
                values,
                "nombre=?",
                new String[] {university.getName()});
    }

    @Override
    public void delete(UniversityDetail university) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(ContratoUniversity.EntradaUniversity.TABLE_NAME,
                "nombre=?",
                new String[] {university.getName()});
    }

    public UniversityDetail getUniversityDetail(String name){
        SQLiteDatabase db = getWritableDatabase();

        return null;
    }
}

