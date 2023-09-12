package ucv.android.pulsos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {
    EditText txt_email, txt_clave;
    Button btn_ingresar;
    TextView tvt_registrar;

    //Componentes de Firebase
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Instanciamos el componente de Firebase Autentification
        mAuth = FirebaseAuth.getInstance();

        txt_email = findViewById(R.id.txt_email);
        txt_clave = findViewById(R.id.txt_clave);
        tvt_registrar = findViewById(R.id.tvt_registrar);
        btn_ingresar = findViewById(R.id.btn_ingresar);

        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IniciarSesion();
            }
        });

        tvt_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });

        Observador();
    }

    private void IniciarSesion() {

        String email = txt_email.getText().toString().trim();
        String clave = txt_clave.getText().toString().trim();

        //Validamos que el campo email no este vacío
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Ingrese su correo electronico", Toast.LENGTH_SHORT).show();
            return;
        }

        //Validamos que el campo password no este vacío
        if(TextUtils.isEmpty(clave)){
            Toast.makeText(this, "Ingrese su contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        //Mostramos un ProgressDialog para esperar la confirmacion de Firebase
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Autentificando");
        dialog.setMessage("Espere porfavor...");
        dialog.setCancelable(false);
        dialog.show();

        //Autentificar al Usuario si se encuentra registrado en Firebase (Autentification)
        mAuth.signInWithEmailAndPassword(email, clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    dialog.dismiss();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Usuario y/o Contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void Observador(){

        mAuthStateListener = firebaseAuth -> {
            FirebaseUser usuario = firebaseAuth.getCurrentUser();
            if(usuario == null){
                //NO ESTA LOGUEADO
                // Toast.makeText(this, "No hay sesión activa!", Toast.LENGTH_SHORT).show();
            }else{
                //ESTA LOGUEADO
                Toast.makeText(this, "La sesion esta activa!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
    }

    //Ciclo del Activity

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthStateListener != null){
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}