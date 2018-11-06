package dmi.com.registro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InicioSesion extends AppCompatActivity {

    EditText usuario, password;
    TextView registro;
    Button inicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        usuario= findViewById(R.id.edt_usuario);
        password= findViewById(R.id.edt_contrasena);
        registro= findViewById(R.id.tv_registro);
        inicio= findViewById(R.id.btn_iniciar);


        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(InicioSesion.this,MainActivity.class);
                startActivity(i);
                //hay que cerrar la actividad para pasar al otro lado
                finish();
            }
        });

    }
}
