package ucv.android.pulsos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class SplashScreenActivity extends AppCompatActivity {

    LinearLayout ContenedorLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ContenedorLogo = findViewById(R.id.ContenedorLogo);

        Animation mianimacion = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        ContenedorLogo.startAnimation(mianimacion);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }
}