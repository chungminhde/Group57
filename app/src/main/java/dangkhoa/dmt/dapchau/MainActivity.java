package dangkhoa.dmt.dapchau;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myMain = this;
        if(tinhHinh == 1)
        {
            tinhHinh = 0;
            startActivity(new Intent(MainActivity.this, HighScore.class));
        }

        if(tinhHinh_2 == 1)
        {
            tinhHinh_2 = 0;
            startActivity(new Intent(MainActivity.this, GamePlay.class));
        }

        if(tinhHinh_3 == 1)
        {
            tinhHinh_3 = 0;
            startActivity(new Intent(MainActivity.this, Challenge.class));
        }

        if(tinhHinh_4 == 1)
        {
            tinhHinh_4 = 0;
            startActivity(new Intent(MainActivity.this, HighScore_Challenge10.class));
        }

        if(tinhHinh_5 == 1)
        {
            tinhHinh_5 = 0;
            startActivity(new Intent(MainActivity.this, HighScore_Challenge20.class));
        }

        if(tinhHinh_6 == 1)
        {
            tinhHinh_6 = 0;
            startActivity(new Intent(MainActivity.this, HighScore_Challenge.class));
        }



        anhXa();
        nhapNut();

    }

    @Override
    public void onBackPressed() {
        if(thoatRa == 1)
        {
            super.onBackPressed();
        }
    }

    private static MainActivity myMain;
    public static MainActivity getMyMain()
    {
        return myMain;
    }

    private Button btnNewGame;

    private Button btnHighScore;

    private Button btnAbout;

    static private int tinhHinh = 1;

    static private int tinhHinh_2 = 1;

    static private int tinhHinh_3 = 1;

    static private int tinhHinh_4 = 1;

    static private int tinhHinh_5 = 1;

    static private int tinhHinh_6 = 1;

    static private int tinhHinh_7 = 1;

    static private int thoatRa = 1;

    public void khoaThoatRa()
    {
        thoatRa = 0;
    }
    private void anhXa()
    {
        btnNewGame = (Button)findViewById(R.id.btnNewGame);
        btnHighScore = (Button)findViewById(R.id.btnHighScore);
        btnAbout = (Button)findViewById(R.id.btnAbout);
    }

    private void nhapNut()
    {
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HighScore.getHighScore().resetDiem();
                startActivity(new Intent(MainActivity.this, NewGame.class));
                overridePendingTransition(R.anim.anim_enter,R.anim.anim_exit);
            }
        });

        btnHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HighScoreOption.class));
                overridePendingTransition(R.anim.anim_enter,R.anim.anim_exit);
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, About.class));
                overridePendingTransition(R.anim.anim_enter,R.anim.anim_exit);
            }
        });
    }
}
