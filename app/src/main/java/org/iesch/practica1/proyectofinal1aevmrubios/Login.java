package org.iesch.practica1.proyectofinal1aevmrubios;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.squareup.picasso.Picasso;

enum ProviderType {
    BASIC,
    GOOGLE
}

public class Login extends AppCompatActivity {
    public static final int REQUEST_CODE = 100;
    private final String URL = "https://cdn-icons-png.flaticon.com/512/914/914726.png";
    private EditText correo, contrasena;
    private Button ingresar, registrar, google;
    private LinearLayout linear;
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onStart() {
        super.onStart();
        linear.setVisibility(View.VISIBLE);
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        ImageView imagen = findViewById(R.id.imageView);
        Picasso.get().load(URL).error(R.drawable.starter_guide).into(imagen);

        linear = findViewById(R.id.linear);
        correo = findViewById(R.id.correo);
        contrasena = findViewById(R.id.contrasena);
        ingresar = findViewById(R.id.ingresar);
        registrar = findViewById(R.id.registrar);
        google = findViewById(R.id.google);

        iniciarAnalytics();
        iniciarAutenticacion();
        comprobarLogueo();
    }

    private void comprobarLogueo() {
        SharedPreferences sesion = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        String _email = sesion.getString("email", null);
        String _metodo = sesion.getString("metodo", null);
        if (_email != null && _metodo != null) {
            irAHomeActivity(_email, ProviderType.valueOf(_metodo));
            linear.setVisibility(View.INVISIBLE);
        }
    }

    private void iniciarAutenticacion() {
        mAuth = FirebaseAuth.getInstance();
        registrar.setOnClickListener(v -> {
            registrarConEmailPassword();
        });
        ingresar.setOnClickListener(v -> {
            loguearConEmailyPassword();
        });
        google.setOnClickListener(v -> {
            loguearConGoogle();
        });
    }

    private void loguearConGoogle() {
        // Al hacer click en el boton de login con Google:
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Nos creamos el GoogleSignIn Client
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, REQUEST_CODE);
    }

    private void irAHomeActivity(String email, ProviderType tipoLogueo) {
        guardarPreferencias(email, tipoLogueo);
        Intent i = new Intent(Login.this, HomeActivity.class);
        i.putExtra("email", email);
        i.putExtra("metodo", tipoLogueo.toString());
        startActivity(i);
    }

    private void loguearConEmailyPassword() {
        String _email = correo.getText().toString();
        String _password = contrasena.getText().toString();
        if (!_email.isEmpty() || !_password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(_email, _password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("FIREBASE", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                // En caso de usuario y password correctos pasaremos a HomeActivity
                                irAHomeActivity(_email, ProviderType.BASIC);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("FIREBASE", "signInWithEmail:failure", task.getException());
                                Toast.makeText(Login.this, "Error on Login",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "Rellena los campos", Toast.LENGTH_SHORT).show();
        }
    }
    private void guardarPreferencias(String email, ProviderType p){
        SharedPreferences preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        SharedPreferences.Editor Obj_editor = preferences.edit();

        Obj_editor.putString("email", email);
        Obj_editor.putString("metodo", p.toString());
        //Commit confirma que lo que acabamoos de recuperar arriba lo queremos GUARDAR.
        // Sin commit no guarda nada en SharedPreferences
        Obj_editor.commit();
    }

    private void registrarConEmailPassword() {
        String _email = correo.getText().toString();
        String _password = contrasena.getText().toString();
        if (!_email.isEmpty() || !_password.isEmpty()) {

            mAuth.createUserWithEmailAndPassword(_email, _password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("FIREBASE", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(Login.this, "El Usuario se ha registrado correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("FIREBASE", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Login.this, "Error al Registrar el Usuario",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "Rellena los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void iniciarAnalytics() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString("Info", "Inicio de la app");
        mFirebaseAnalytics.logEvent("Info", bundle);
        comprobarLogueo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE){
            // Esto signifoca que venimos de loguearnos con Google
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                // La autenticacion con Google ha sido exitosa
                Log.w("FIREBASE", "firebasAuthConGoogle: "+ account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e){
                // La autenticacion con Google ha fallado
                Log.w("FIREBASE","Google SignIn ha fallado",e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            // Voy a HomeActivity
                            irAHomeActivity(user.getEmail(),ProviderType.GOOGLE);
                        } else {
                            Log.w("FIREBASE", "Logueo con Google: Fallo");
                        }
                    }
                });
    }
}