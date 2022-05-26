package com.example.filework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final static String FILE_NAME = "document.txt";
    private final static String FILE_NAME1 = "content.txt";
    //private static final int PICKFILE_RESULT_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button menegButton = (Button)findViewById(R.id.open_text3);
        /*menegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnOpenFileClick(v);
                *//*Intent intent = new Intent();
                intent.setClass(RufActivity.this, MainActivity.class);
                startActivity(intent);*//*
            }
        });*/
        /*menegButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent,PICKFILE_RESULT_CODE);
            }});*/

        Button imageButton = (Button)findViewById(R.id.open_text4);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ImageActivity.class);
                startActivity(intent);
            }
        });

        Button fileworkButton = (Button)findViewById(R.id.open_text2);
        fileworkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, FileworkActivity.class);
                startActivity(intent);
            }
        });
    }

    /*public void OnOpenFileClick(View view) {
        MainActivity2 fileDialog = new MainActivity2(this);
        fileDialog.show();
    }*/

    //получение и использование пути к внешнему хранилищу
    private File getExternalPath() {
        return new File(getExternalFilesDir(null), FILE_NAME);
    }

    // сохранение файла на SD
    public void saveText(View view){
        if(!isExternalStorageWritable()) return;
        if (!checkSpace()){
            Toast.makeText(this, "Осталось менее 600 МБ", Toast.LENGTH_SHORT).show();
        } else{
            try(FileOutputStream fos = new FileOutputStream(getExternalPath())) {
                EditText textBox = findViewById(R.id.editor);
                String text = textBox.getText().toString();
                fos.write(text.getBytes());
                Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
            }
            catch(IOException ex) {

                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }


    // открытие файла SD
    public void openText(View view){

        TextView textView = findViewById(R.id.text);
        File file = getExternalPath();
        // если файл не существует, выход из метода
        if(!file.exists() || !isExternalStorageReadable()) return;
        try(FileInputStream fin =  new FileInputStream(file)) {
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
            textView.setText(text);
        }
        catch(IOException ex) {

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    //РАБОТА С SD
    //проверка на возможность чтения и записи с/на SD
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public void deleteFileSD(View view){
        File file = getExternalPath();
        file.delete();
    }

    public void deleteFile(View view){
        File dir = getFilesDir();
        File file = new File(dir,FILE_NAME1);
        file.delete();
    }

    public boolean checkSpace(){
        File dir = new File(String.valueOf(getExternalFilesDir(null)));
        System.out.println("МЕСТО total "+ dir.getTotalSpace()/(1024*1024));
        System.out.println("МЕСТО usable "+ dir.getUsableSpace()/(1024*1024));
        System.out.println(dir.getTotalSpace()/(1024*1024)-dir.getUsableSpace()/(1024*1024));
        if ((dir.getTotalSpace()/(1024*1024)-dir.getUsableSpace()/(1024*1024))>600)
            return true;
        else
            return false;
    }


    // сохранение файла
    public void saveText1(View view){

        FileOutputStream fos = null;
        try {
            EditText textBox = findViewById(R.id.editor);
            String text = textBox.getText().toString();

            fos = openFileOutput(FILE_NAME1, MODE_PRIVATE);
            fos.write(text.getBytes());
            Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
        }
        catch(IOException ex) {

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally{
            try{
                if(fos!=null)
                    fos.close();
            }
            catch(IOException ex){

                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    // открытие файла
    public void openText1(View view){

        FileInputStream fin = null;
        TextView textView = findViewById(R.id.text);
        try {
            fin = openFileInput(FILE_NAME1);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
            textView.setText(text);
        }
        catch(IOException ex) {

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally{

            try{
                if(fin!=null)
                    fin.close();
            }
            catch(IOException ex){

                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICKFILE_RESULT_CODE) {
            if (resultCode == RESULT_OK) {
                String file = data.getData().getPath();
                TextView textView = findViewById(R.id.text);
                try(FileInputStream fin =  new FileInputStream(file)) {
                    byte[] bytes = new byte[fin.available()];
                    fin.read(bytes);
                    String text = new String (bytes);
                    textView.setText(text);
                }
                catch(IOException ex) {

                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }*/
}