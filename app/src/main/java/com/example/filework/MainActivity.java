package com.example.filework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    }

    /*public void OnOpenFileClick(View view) {
        MainActivity2 fileDialog = new MainActivity2(this);
        fileDialog.show();
    }*/

    //получение и использование пути к внешнему хранилищу
    private File getExternalPath() {
        return new File(getExternalFilesDir(null), FILE_NAME);
    }

    // сохранение файла
    public void saveText(View view){

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


    // открытие файла
    public void openText(View view){

        TextView textView = findViewById(R.id.text);
        File file = getExternalPath();
        // если файл не существует, выход из метода
        if(!file.exists()) return;
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