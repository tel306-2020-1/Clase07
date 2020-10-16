package com.example.clase07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class EmpleadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleado);

        Intent intent = getIntent();
        int employeeId = intent.getIntExtra("id",0);
        Toast.makeText(this,"employeeId:" + employeeId,Toast.LENGTH_SHORT).show();
    }
}