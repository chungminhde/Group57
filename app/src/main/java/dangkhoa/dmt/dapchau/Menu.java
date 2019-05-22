package dangkhoa.dmt.dapchau;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        anhXa();
        nhanNut();

    }
    private Button btnResume;

    private Button btnNewGame;

    private Button btnHighScore;

    private Button btnAbout;

    private ArrayList<Integer> arrayList;
    private void anhXa()
    {
        btnResume = (Button)findViewById(R.id.btnResume_Menu);
        btnNewGame = (Button)findViewById(R.id.btnNewGame_Menu);
        btnHighScore = (Button)findViewById(R.id.btnHighScore_Menu);
        btnAbout = (Button)findViewById(R.id.btnAbout_Menu);
    }

    private void nhanNut()
    {
        btnResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                overridePendingTransition(R.anim.anim_enter_reverse,R.anim.anim_exit_reverse);
            }
        });

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Menu.this, NewGame.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_enter,R.anim.anim_exit);
            }
        });

        btnHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, HighScoreOption.class));
                overridePendingTransition(R.anim.anim_enter,R.anim.anim_exit);
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, About.class));
                overridePendingTransition(R.anim.anim_enter,R.anim.anim_exit);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_enter_reverse,R.anim.anim_exit_reverse);
    }
}
