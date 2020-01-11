package com.example.pee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pee.myrequest.MyRequest;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    Button btn1 , btn2;
    EditText username0 , email0 , password0 , rpassword0 ;
    private RequestQueue queue;
    private MyRequest request ;
    String HttpUrl = "https://ekri.000webhostapp.com/user/UserRegistration.php" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btn1= (Button) findViewById(R.id.button4);
        username0 = (EditText) findViewById(R.id.editText3);
        email0 = (EditText) findViewById(R.id.editText);
        password0= (EditText) findViewById(R.id.editText2);
        rpassword0= (EditText) findViewById(R.id.editText4);
        btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(Register.this, Login.class);
                startActivity(inten);
            }
        });
        queue=VolleySingleton.getInstance(this).getRequestQueue();
        request =new MyRequest(this,queue);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creating string request with post method.
                String username1= username0.getText().toString().trim();
                String email1= email0.getText().toString().trim();
                String password1= password0.getText().toString().trim();
                String repassord= rpassword0.getText().toString().trim();
                if (username1.isEmpty() || email1.isEmpty() ||password1.isEmpty() || repassord.isEmpty() ){
                 Toast.makeText(Register.this,"please enter the missing filed !",Toast.LENGTH_SHORT).show();
                }else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String ServerResponse) {
                                    Toast.makeText(Register.this, ServerResponse, Toast.LENGTH_LONG).show();
                                    Intent inte = new Intent(Register.this, Login.class);
                                    startActivity(inte);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {

                                    // Hiding the progress dialog after all task complete.

                                    // Showing error message if something goes wrong.
                                    Toast.makeText(Register.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            // Creating Map String Params.
                            Map<String, String> params = new HashMap<String, String>();
                            String username1 = username0.getText().toString().trim();
                            String email1 = email0.getText().toString().trim();
                            String password1 = password0.getText().toString().trim();
                            // Adding All values to Params.
                            params.put("username", username1);
                            params.put("email", email1);
                            params.put("password", password1);

                            return params;
                        }

                    };
                    // Creating RequestQueue.
                    RequestQueue requestQueue = Volley.newRequestQueue(Register.this);

                    // Adding the StringRequest object into requestQueue.
                    requestQueue.add(stringRequest);
                }
            }
        });

    }
}
