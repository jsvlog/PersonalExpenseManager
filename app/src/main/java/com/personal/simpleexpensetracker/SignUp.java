package com.personal.simpleexpensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    ImageView logo;
    Animation animation, animation2;
    EditText emailSignup, passwordSignup, usernameSignup;
    CheckBox checkBox;
    TextView signupLogin;
    Button loginButton;
    FirebaseAuth mAuth;
    DatabaseReference usernameref;
    ProgressDialog pd;
    Boolean emailExist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        logo = findViewById(R.id.signupLogo);
        animation = AnimationUtils.loadAnimation(this,R.anim.anim);
        animation2 = AnimationUtils.loadAnimation(this,R.anim.anim2);
        emailSignup = findViewById(R.id.emailSignup);
        passwordSignup = findViewById(R.id.passwordSignup);
        usernameSignup = findViewById(R.id.usernameSignup);
        checkBox = findViewById(R.id.checkboxSignup);
        signupLogin = findViewById(R.id.signupLogin);
        loginButton = findViewById(R.id.loginButton);


        mAuth = FirebaseAuth.getInstance();

        usernameref = FirebaseDatabase.getInstance().getReference().child("usernames");





        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email = emailSignup.getText().toString();
                String password = passwordSignup.getText().toString();
                String username = usernameSignup.getText().toString();

                ProgressDialog pd = new ProgressDialog(SignUp.this);
                pd.setMessage("loaaaaaading");
                pd.show();
                //check if email used is existing
                mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                        if(task.getResult().getSignInMethods().size() == 0){
                            emailExist = false;
                        }else{
                            emailExist = true;
                        }
                        try {
                            task.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });





                if(username.isEmpty()){
                    usernameSignup.setError("please input username");
                }else if (email.isEmpty()){
                    emailSignup.setError("please input email");
                }else if(!isValidEmailId(email)){
                    emailSignup.setError("please input valid email address");
                }else if(password.isEmpty()){
                    passwordSignup.setError("please input password");
                }else if (password.length() < 6){
                    passwordSignup.setError("password must be at least 6 characters");
                }else if(emailExist){
                    Toast.makeText(SignUp.this, "email already registered", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                String id = usernameref.child(mAuth.getCurrentUser().getUid()).push().getKey();
                                usernameref.child(mAuth.getCurrentUser().getUid()).child(id).child("username").setValue(username);
                            }
                            Toast.makeText(SignUp.this, "Signup successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUp.this,DashboardTest.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            }
        });




        signupLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this,MainActivity.class);
                startActivity(intent);
            }
        });



        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    passwordSignup.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    passwordSignup.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        //this is for the animation of logo in log in activity
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                logo.setAnimation(animation2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        logo.setAnimation(animation);
    }


    // to check if email is valid
    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
}