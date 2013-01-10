package com.example.help;

import java.util.ArrayList;
import java.util.List;
 

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.example.help.UserPage;
 
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
 
public class Login extends Activity {
    Button b1,b2,tempButton1,tempButton2,tempButton3,tempButton4;
    EditText et,pass;
    TextView tv;
    HttpPost httppost;
    StringBuffer buffer;
    String string1="found";
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    ProgressDialog dialog = null;
     
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        b1= (Button)findViewById(R.id.Button01);
        et = (EditText)findViewById(R.id.username);
        pass= (EditText)findViewById(R.id.password);
        b2=(Button)findViewById(R.id.Register);
        tempButton1=(Button)findViewById(R.id.tempbutton1);
        tempButton2=(Button)findViewById(R.id.tempbutton2);
        tempButton3=(Button)findViewById(R.id.tempbutton3);
        tempButton4=(Button)findViewById(R.id.tempbutton4);
        b1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dialog = ProgressDialog.show(Login.this, "",
                        "Validating user...", true);
                 new Thread(new Runnable() {
                        public void run() {
                            login();                         
                        }
                      }).start();              
            }
        }); 
    }
     
 
    
    void login(){
        try{           
              
            httpclient=new DefaultHttpClient();
            httppost= new HttpPost("http://helpandroid.net78.net/check.php"); // make sure the url is correct.
            //add your data
            nameValuePairs = new ArrayList<NameValuePair>(2);
            // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar,
            nameValuePairs.add(new BasicNameValuePair("username",et.getText().toString().trim()));  // $Edittext_value = $_POST['Edittext_value'];
            nameValuePairs.add(new BasicNameValuePair("password",pass.getText().toString().trim()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            //Execute HTTP Post Request
            response=httpclient.execute(httppost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response1 = httpclient.execute(httppost, responseHandler);
            System.out.println("Response : " + response1);
            runOnUiThread(new Runnable() {
                public void run() {
                

//                    tv.setText("Response from PHP : '" + response1+"'");
                    dialog.dismiss();
                }
            });
             
            if(response1.equalsIgnoreCase(string1))
            {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(Login.this,"Login Success", Toast.LENGTH_SHORT).show();
    					Intent openMain = new Intent("com.example.help.Menu");
				
				startActivity(openMain);
                    }
                });
                 
                startActivity(new Intent(Login.this, UserPage.class));
            }else{
                showAlert1();               
            }
             
        }catch(Exception e){
            dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
        }
    }
    public void showAlert1(){
        Login.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setTitle("Login Error.");
                builder.setMessage("User not found in Help database") 
                       .setCancelable(false)
                       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                           }
                       });                    
                AlertDialog alert = builder.create();
                alert.show();              
            }
        });
    }
}