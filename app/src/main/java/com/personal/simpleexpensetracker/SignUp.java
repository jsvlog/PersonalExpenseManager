package com.personal.simpleexpensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {
    ImageView logo;
    Animation animation, animation2;
    EditText emailSignup, passwordSignup, usernameSignup;
    CheckBox checkBox;
    TextView signupLogin;

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
}