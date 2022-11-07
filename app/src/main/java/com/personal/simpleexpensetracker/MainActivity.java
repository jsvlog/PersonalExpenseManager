package com.personal.simpleexpensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;



public class MainActivity extends AppCompatActivity {
ImageView logo;
Animation animation, animation2;
EditText emailLogin, passwordLogin;
CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo = findViewById(R.id.logLogo);
        animation = AnimationUtils.loadAnimation(this,R.anim.anim);
        animation2 = AnimationUtils.loadAnimation(this,R.anim.anim2);
        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        checkBox = findViewById(R.id.checkboxLogin);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    passwordLogin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    passwordLogin.setTransformationMethod(PasswordTransformationMethod.getInstance());
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