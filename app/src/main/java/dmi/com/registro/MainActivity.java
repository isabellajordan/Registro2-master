package dmi.com.registro;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    //Con base de datos

    EditText nombre;
    EditText correo; 
    EditText contrasena;
    Button enviar;
    TextView conCuenta;

    FirebaseDatabase db;
    //Para la auth
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instanciar base de datos y auth
        db= FirebaseDatabase.getInstance();
        auth= FirebaseAuth.getInstance();

        nombre= findViewById(R.id.edt_nombre);
        correo= findViewById(R.id.edt_correo);
        contrasena= findViewById(R.id.edt_contrasena);
        enviar=findViewById(R.id.btn_enviar);
        conCuenta=findViewById(R.id.tv_tiene_cuenta);


        ///////////// sacar key
        printKEY();

        //Acceder a la base de datos

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //add on complete listener me avisa si la operación resultó exitosa o no
        auth.createUserWithEmailAndPassword(correo.getText().toString(),contrasena.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            //Saber si operación es exitosa o no
            public void onComplete(@NonNull Task<AuthResult> task) {

                //Task es objeto que me responde firebase ESTA ES LA RESPUESTA DE FIREBASE
        if(task.isSuccessful()){

            Usuario nuevo= new Usuario(nombre.getText().toString(),correo.getText().toString(),contrasena.getText().toString());

            //Si la autentificación es exitosa se mete en la base de datos
            //Uid es el id unico del usuario
            //se crea el objeto para mandarlo a la base
            nuevo.setUid(auth.getCurrentUser().getUid());

        //se manda a base de datos
            //Push es hacer una lista de varios usuarios como un arraylist
            //nuevo.getUid sirve para que la rama de la carpeta me aparezca con el mismo nombre del Uid del usuario
            db.getReference().child("usuarios").child(nuevo.getUid()).setValue(nuevo);


        }else{
            Toast.makeText(MainActivity.this, ""+task.getException(),Toast.LENGTH_SHORT).show();

                }
            }
            });
            }
        });





        conCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MainActivity.this,InicioSesion.class);
                startActivity(i);
                //cerrar la actividad
                finish();
            }
        });




    }

    private void printKEY() {
        try{
            PackageInfo info = getPackageManager().getPackageInfo("dmi.com.registro", PackageManager.GET_SIGNATURES);

            for (Signature signature:info.signatures){
                MessageDigest messageDigest =  MessageDigest.getInstance("SHA");
                messageDigest.update(signature.toByteArray());

                Log.e("KEYHASH", Base64.encodeToString(messageDigest.digest(), Base64.DEFAULT));
            }


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
