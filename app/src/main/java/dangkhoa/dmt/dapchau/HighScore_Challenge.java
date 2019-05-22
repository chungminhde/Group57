package dangkhoa.dmt.dapchau;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HighScore_Challenge extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score__challenge);
        highScoreChallenge = this;

        if(luuDiemHigh == null)
        {
            luuDiemHigh = getSharedPreferences("DiemSoHighChallenge", MODE_PRIVATE);
        }

        if(tinhHinh == 1)
        {
            tinhHinh = 0;
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }


        diemTop[0] = luuDiemHigh.getInt("DiemTop1Challenge", 0);
        diemTop[1] = luuDiemHigh.getInt("DiemTop2Challenge", 0);
        diemTop[2] = luuDiemHigh.getInt("DiemTop3Challenge", 0);
        diemTop[3] = luuDiemHigh.getInt("DiemTop4Challenge", 0);
        diemTop[4] = luuDiemHigh.getInt("DiemTop5Challenge", 0);
        diemTop[5] = luuDiemHigh.getInt("DiemTop6Challenge", 0);
        diemTop[6] = luuDiemHigh.getInt("DiemTop7Challenge", 0);
        diemTop[7] = luuDiemHigh.getInt("DiemTop8Challenge", 0);
        diemTop[8] = luuDiemHigh.getInt("DiemTop9Challenge", 0);
        diemTop[9] = luuDiemHigh.getInt("DiemTop10Challenge", 0);

        hoTen[0] = luuDiemHigh.getString("hoTen1Challenge", "");
        hoTen[1] = luuDiemHigh.getString("hoTen2Challenge", "");
        hoTen[2] = luuDiemHigh.getString("hoTen3Challenge", "");
        hoTen[3] = luuDiemHigh.getString("hoTen4Challenge", "");
        hoTen[4] = luuDiemHigh.getString("hoTen5Challenge", "");
        hoTen[5] = luuDiemHigh.getString("hoTen6Challenge", "");
        hoTen[6] = luuDiemHigh.getString("hoTen7Challenge", "");
        hoTen[7] = luuDiemHigh.getString("hoTen8Challenge", "");
        hoTen[8] = luuDiemHigh.getString("hoTen9Challenge", "");
        hoTen[9] = luuDiemHigh.getString("hoTen10Challenge", "");

        Top1 = (TextView)findViewById(R.id.highScore1Challenge);
        Top2 = (TextView)findViewById(R.id.highScore2Challenge);
        Top3 = (TextView)findViewById(R.id.highScore3Challenge);
        Top4 = (TextView)findViewById(R.id.highScore4Challenge);
        Top5 = (TextView)findViewById(R.id.highScore5Challenge);
        Top6 = (TextView)findViewById(R.id.highScore6Challenge);
        Top7 = (TextView)findViewById(R.id.highScore7Challenge);
        Top8 = (TextView)findViewById(R.id.highScore8Challenge);
        Top9 = (TextView)findViewById(R.id.highScore9Challenge);
        Top10 = (TextView)findViewById(R.id.highScore10Challenge);

        Ten1 = (TextView)findViewById(R.id.highScoreName1Challenge);
        Ten2 = (TextView)findViewById(R.id.highScoreName2Challenge);
        Ten3 = (TextView)findViewById(R.id.highScoreName3Challenge);
        Ten4 = (TextView)findViewById(R.id.highScoreName4Challenge);
        Ten5 = (TextView)findViewById(R.id.highScoreName5Challenge);
        Ten6 = (TextView)findViewById(R.id.highScoreName6Challenge);
        Ten7 = (TextView)findViewById(R.id.highScoreName7Challenge);
        Ten8 = (TextView)findViewById(R.id.highScoreName8Challenge);
        Ten9 = (TextView)findViewById(R.id.highScoreName9Challenge);
        Ten10 = (TextView)findViewById(R.id.highScoreName10Challenge);


        Top1.setText("" + diemTop[0]);
        Top2.setText("" + diemTop[1]);
        Top3.setText("" + diemTop[2]);
        Top4.setText("" + diemTop[3]);
        Top5.setText("" + diemTop[4]);
        Top6.setText("" + diemTop[5]);
        Top7.setText("" + diemTop[6]);
        Top8.setText("" + diemTop[7]);
        Top9.setText("" + diemTop[8]);
        Top10.setText("" + diemTop[9]);

        Ten1.setText("1. " + hoTen[0]);
        Ten2.setText("2. " + hoTen[1]);
        Ten3.setText("3. " + hoTen[2]);
        Ten4.setText("4. " + hoTen[3]);
        Ten5.setText("5. " + hoTen[4]);
        Ten6.setText("6. " + hoTen[5]);
        Ten7.setText("7. " + hoTen[6]);
        Ten8.setText("8. " + hoTen[7]);
        Ten9.setText("9. " + hoTen[8]);
        Ten10.setText("10. " + hoTen[9]);

        reset = (Button)findViewById(R.id.btnResetChallenge);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLuuXoaDiem();
            }
        });
    }


    private static HighScore_Challenge highScoreChallenge;
    static {
        highScoreChallenge = new HighScore_Challenge();
    }
    public static HighScore_Challenge getHighScoreChallenge(){
        return highScoreChallenge;
    }

    private Button reset;

    private TextView Top1;
    private TextView Top2;
    private TextView Top3;
    private TextView Top4;
    private TextView Top5;
    private TextView Top6;
    private TextView Top7;
    private TextView Top8;
    private TextView Top9;
    private TextView Top10;

    private TextView Ten1;
    private TextView Ten2;
    private TextView Ten3;
    private TextView Ten4;
    private TextView Ten5;
    private TextView Ten6;
    private TextView Ten7;
    private TextView Ten8;
    private TextView Ten9;
    private TextView Ten10;


    static private int diem = 0;
    static private int diem_2 = 0;
    static private int[] diemTop = {0,0,0,0,0,0,0,0,0,0};

    static private String[] hoTen = {"","","","","","","","","",""};
    static private int tinhHinh = 1;

    private SharedPreferences luuDiemHigh;

    public void resetDiem() {
        diem = -10;
        diem_2 = 0;
    }

    public void congDiem()
    {
        diem_2 = diem;
        diem = DataGameChallenge.getDatagameChallenge().getDiem();
    }

    public void dongBo()
    {
        diem_2 = diem;
    }
    public void quayLaiDiem()
    {
        diem = diem_2;
    }

    public void ketThuc()
    {

        luuDiemHigh = getSharedPreferences("DiemSoHighChallenge", MODE_PRIVATE);


        diemTop[0] = luuDiemHigh.getInt("DiemTop1Challenge", 0);
        diemTop[1] = luuDiemHigh.getInt("DiemTop2Challenge", 0);
        diemTop[2] = luuDiemHigh.getInt("DiemTop3Challenge", 0);
        diemTop[3] = luuDiemHigh.getInt("DiemTop4Challenge", 0);
        diemTop[4] = luuDiemHigh.getInt("DiemTop5Challenge", 0);
        diemTop[5] = luuDiemHigh.getInt("DiemTop6Challenge", 0);
        diemTop[6] = luuDiemHigh.getInt("DiemTop7Challenge", 0);
        diemTop[7] = luuDiemHigh.getInt("DiemTop8Challenge", 0);
        diemTop[8] = luuDiemHigh.getInt("DiemTop9Challenge", 0);
        diemTop[9] = luuDiemHigh.getInt("DiemTop10Challenge", 0);

        hoTen[0] = luuDiemHigh.getString("hoTen1Challenge", "");
        hoTen[1] = luuDiemHigh.getString("hoTen2Challenge", "");
        hoTen[2] = luuDiemHigh.getString("hoTen3Challenge", "");
        hoTen[3] = luuDiemHigh.getString("hoTen4Challenge", "");
        hoTen[4] = luuDiemHigh.getString("hoTen5Challenge", "");
        hoTen[5] = luuDiemHigh.getString("hoTen6Challenge", "");
        hoTen[6] = luuDiemHigh.getString("hoTen7Challenge", "");
        hoTen[7] = luuDiemHigh.getString("hoTen8Challenge", "");
        hoTen[8] = luuDiemHigh.getString("hoTen9Challenge", "");
        hoTen[9] = luuDiemHigh.getString("hoTen10Challenge", "");
        if(diem > diemTop[9]) {
            int i;
            for (i = 9; i >= 0; i--) {
                if (diem < diemTop[i]) {
                    break;
                }
            }
            for (int j = 9; j > i + 1; j--) {
                diemTop[j] = diemTop[j - 1];
                hoTen[j] = hoTen[j - 1];
            }
            diemTop[i + 1] = diem;
            hoTen[i + 1] = NhapTen_Challenge.getMyNhapTenChallenge().getHoTen();
        }

        setLuuDiemHighTop();
    }
    public void setLuuDiemHighTop()
    {
        SharedPreferences.Editor editor = luuDiemHigh.edit();
        editor.putInt("DiemTop1Challenge", diemTop[0]);
        editor.putInt("DiemTop2Challenge", diemTop[1]);
        editor.putInt("DiemTop3Challenge", diemTop[2]);
        editor.putInt("DiemTop4Challenge", diemTop[3]);
        editor.putInt("DiemTop5Challenge", diemTop[4]);
        editor.putInt("DiemTop6Challenge", diemTop[5]);
        editor.putInt("DiemTop7Challenge", diemTop[6]);
        editor.putInt("DiemTop8Challenge", diemTop[7]);
        editor.putInt("DiemTop9Challenge", diemTop[8]);
        editor.putInt("DiemTop10Challenge", diemTop[9]);

        editor.putString("hoTen1Challenge",hoTen[0]);
        editor.putString("hoTen2Challenge",hoTen[1]);
        editor.putString("hoTen3Challenge",hoTen[2]);
        editor.putString("hoTen4Challenge",hoTen[3]);
        editor.putString("hoTen5Challenge",hoTen[4]);
        editor.putString("hoTen6Challenge",hoTen[5]);
        editor.putString("hoTen7Challenge",hoTen[6]);
        editor.putString("hoTen8Challenge",hoTen[7]);
        editor.putString("hoTen9Challenge",hoTen[8]);
        editor.putString("hoTen10Challenge",hoTen[9]);
        editor.commit();
    }


    public void setLuuXoaDiem()
    {
        SharedPreferences.Editor editor = luuDiemHigh.edit();
        editor.putInt("DiemTop1Challenge", 0);
        editor.putInt("DiemTop2Challenge", 0);
        editor.putInt("DiemTop3Challenge", 0);
        editor.putInt("DiemTop4Challenge", 0);
        editor.putInt("DiemTop5Challenge", 0);
        editor.putInt("DiemTop6Challenge", 0);
        editor.putInt("DiemTop7Challenge", 0);
        editor.putInt("DiemTop8Challenge", 0);
        editor.putInt("DiemTop9Challenge", 0);
        editor.putInt("DiemTop10Challenge", 0);

        editor.putString("hoTen1Challenge","");
        editor.putString("hoTen2Challenge","");
        editor.putString("hoTen3Challenge","");
        editor.putString("hoTen4Challenge","");
        editor.putString("hoTen5Challenge","");
        editor.putString("hoTen6Challenge","");
        editor.putString("hoTen7Challenge","");
        editor.putString("hoTen8Challenge","");
        editor.putString("hoTen9Challenge","");
        editor.putString("hoTen10Challenge","");
        editor.commit();
        this.recreate();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_enter_reverse,R.anim.anim_exit_reverse);
    }
}
