package com.example.cardstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {


    ImageView atras;
    EditText nombre;
    EditText correoRegister;
    EditText contraseñaRegister;
    EditText confirmarContraseña;
    EditText phoneRegister;
    Button btnRegister;
    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;
    String clienteID = "";
    //coneccion al servidor

    static String MQTTHOST= "tcp://franciscorojas.cloud.shiftr.io:1883";
    static String USER= "franciscorojas";
    static String PASS= "LGHXko6JNibOTg1T";
    static String TOPIC = "REGISTRO";
    static String MSG = "NUEVO REGISTRO";

    MqttAndroidClient cliente;
    MqttConnectOptions opciones;
    Boolean permisoPublicar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        atras = findViewById(R.id.atras);
        nombre = findViewById(R.id.nombre);
        correoRegister = findViewById(R.id.correoRegister);
        contraseñaRegister = findViewById(R.id.contraseñaRegister);
        confirmarContraseña = findViewById(R.id.confirmarContraseña);
        phoneRegister = findViewById(R.id.phoneRegister);
        btnRegister = findViewById(R.id.btnRegister);
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        getNombreCliente();
        connectBroker();

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
                enviarMensaje(TOPIC, MSG);
            }

        });
    }
    private void  checkConnection(){
        if(this.cliente.isConnected()){
            this.permisoPublicar = true;
        }else{
            this.permisoPublicar = false;
            connectBroker();
        }
    };
    private void enviarMensaje(String topic, String msg) {
        checkConnection();
        if(this.permisoPublicar){
            try {
                int qos= 0;
                this.cliente.publish(topic, msg.getBytes(),qos,false);
                Toast.makeText(getBaseContext(), topic + ":" + msg, Toast.LENGTH_SHORT);
            }catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }
    private void suscribirseTopic(){
        try {
            this.cliente.subscribe(TOPIC, 0);
        } catch (MqttSecurityException e){
            e.printStackTrace();
        } catch (MqttException e){
            e.printStackTrace();
        }
        this.cliente.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                Toast.makeText(getBaseContext(), "Se Desconecto", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                if(topic.matches(TOPIC)){
                    String msg = new String(message.getPayload());
                    Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }
    private void getNombreCliente() {
        String manufacturer = Build.MANUFACTURER;
        String modelName = Build.MODEL;
        this.clienteID = manufacturer + " " + modelName;
    }

    private void connectBroker(){
        this.cliente = new MqttAndroidClient(this.getApplicationContext(),MQTTHOST, this.clienteID);
        this.opciones = new MqttConnectOptions();
        this.opciones.setUserName(USER);
        this.opciones.setPassword(PASS.toCharArray());
        try {
            IMqttToken token = this.cliente.connect(opciones);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(getBaseContext(), "Conectado", Toast.LENGTH_SHORT).show();
                    suscribirseTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(getBaseContext(), "Fallo", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (MqttException e){
             e.printStackTrace();
        }
}
    private void register() {
        String name = nombre.getText().toString();
        String email = correoRegister.getText().toString();
        String password = contraseñaRegister.getText().toString();
        String confirmPassword = confirmarContraseña.getText().toString();


        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()){
            if (isEmailValid(email)){
                if (password.equals(confirmPassword)){
                    if (password.length() >= 6){
                        createUser(name, email, password);
                    }
                    else{
                        Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "El correo no es valido", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Por favor rellena todo los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void createUser(String name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String id = mAuth.getCurrentUser().getUid();
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("email",email);
                    map.put("password", password);
                    mFirestore.collection("Users").document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(i);
                                Toast.makeText(RegisterActivity.this, "El usuario se almaceno correctamente", Toast.LENGTH_SHORT).show();
                               // sendMessage("Nuevo registrado");
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(RegisterActivity.this, "No se pudo registrar el usuario, Intente de nuevo", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    public boolean isEmailValid(String email){
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}