package dangkhoa.dmt.dapchau;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChallengeOption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_option);
        khoiTao();
        nhanNut();
    }

    private Button giay10;
    private Button giay20;
    private Button giay30;

    public void khoiTao()
    {
        giay10 = (Button)findViewById(R.id.btn10Giay);
        giay20 = (Button)findViewById(R.id.btn20Giay);
        giay30 = (Button)findViewById(R.id.btn30Giay);
    }

    public void nhanNut()
    {
        giay10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HighScore_Challenge10.getHighScoreChallenge10().resetDiem();
                DataGameChallenge.getDatagameChallenge().restart();
                Challenge.getMyChallenge().resetTinhHinh();
                Intent intent = new Intent(ChallengeOption.this, Challenge.class);
                intent.putExtra("thoigian", 10000);
                startActivity(intent);
            }
        });
        giay20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HighScore_Challenge20.getHighScoreChallenge20().resetDiem();
                DataGameChallenge.getDatagameChallenge().restart();
                Challenge.getMyChallenge().resetTinhHinh();
                Intent intent = new Intent(ChallengeOption.this, Challenge.class);
                intent.putExtra("thoigian", 20000);
                startActivity(intent);
            }
        });
        giay30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HighScore_Challenge.getHighScoreChallenge().resetDiem();
                DataGameChallenge.getDatagameChallenge().restart();
                Challenge.getMyChallenge().resetTinhHinh();
                Intent intent = new Intent(ChallengeOption.this, Challenge.class);
                intent.putExtra("thoigian", 30000);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_enter_reverse,R.anim.anim_exit_reverse);
    }
}
