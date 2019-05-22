package dangkhoa.dmt.dapchau;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HighScoreOption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score_option);
        khaiBao();
        nhanNut();

    }

    private Button standard;

    private Button challenge;

    public void khaiBao()
    {
        standard = (Button)findViewById(R.id.btnStandardScore);
        challenge = (Button)findViewById(R.id.btnChallengeScore);
    }

    public void nhanNut()
    {
        standard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HighScoreOption.this, HighScore.class));
                overridePendingTransition(R.anim.anim_enter,R.anim.anim_exit);
            }
        });

        challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HighScoreOption.this, HighScore_ChallengeOption.class));
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

