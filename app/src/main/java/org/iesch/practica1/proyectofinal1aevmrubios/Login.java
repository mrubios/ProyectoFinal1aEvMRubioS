package org.iesch.practica1.proyectofinal1aevmrubios;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Login extends AppCompatActivity {
    private final String URL = "https://cdn-icons-png.flaticon.com/512/914/914726.png";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImageView imagen = findViewById(R.id.imageView);
        Picasso.get().load(URL).error(R.drawable.starter_guide).into(imagen);
    }
}