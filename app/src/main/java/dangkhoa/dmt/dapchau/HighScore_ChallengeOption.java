package dangkhoa.dmt.dapchau;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HighScore_ChallengeOption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score__challenge_option);
        khoiTao();
        nhanNut();
    }

    private Button giay10;
    private Button giay20;
    private Button giay30;

    public void khoiTao()
    {
        giay10 = (Button)findViewById(R.id.btn10GiayHigh);
        giay20 = (Button)findViewById(R.id.btn20GiayHigh);
        giay30 = (Button)findViewById(R.id.btn30GiayHigh);
    }

    public void nhanNut()
    {
        giay10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HighScore_ChallengeOption.this, HighScore_Challenge10.class);
                startActivity(intent);
            }
        });
        giay20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HighScore_ChallengeOption.this, HighScore_Challenge20.class);
                startActivity(intent);
            }
        });
        giay30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HighScore_ChallengeOption.this, HighScore_Challenge.class);
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