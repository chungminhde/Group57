package dangkhoa.dmt.dapchau;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HighScore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        highScore = this;

        if(luuDiemHigh == null)
        {
            luuDiemHigh = getSharedPreferences("DiemSoHigh", MODE_PRIVATE);
        }

        if(tinhHinh == 1)
        {
            tinhHinh = 0;
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }


        diemTop[0] = luuDiemHigh.getInt("DiemTop1", 0);
        diemTop[1] = luuDiemHigh.getInt("DiemTop2", 0);
        diemTop[2] = luuDiemHigh.getInt("DiemTop3", 0);
        diemTop[3] = luuDiemHigh.getInt("DiemTop4", 0);
        diemTop[4] = luuDiemHigh.getInt("DiemTop5", 0);
        diemTop[5] = luuDiemHigh.getInt("DiemTop6", 0);
        diemTop[6] = luuDiemHigh.getInt("DiemTop7", 0);
        diemTop[7] = luuDiemHigh.getInt("DiemTop8", 0);
        diemTop[8] = luuDiemHigh.getInt("DiemTop9", 0);
        diemTop[9] = luuDiemHigh.getInt("DiemTop10", 0);

        hoTen[0] = luuDiemHigh.getString("hoTen1", "");
        hoTen[1] = luuDiemHigh.getString("hoTen2", "");
        hoTen[2] = luuDiemHigh.getString("hoTen3", "");
        hoTen[3] = luuDiemHigh.getString("hoTen4", "");
        hoTen[4] = luuDiemHigh.getString("hoTen5", "");
        hoTen[5] = luuDiemHigh.getString("hoTen6", "");
        hoTen[6] = luuDiemHigh.getString("hoTen7", "");
        hoTen[7] = luuDiemHigh.getString("hoTen8", "");
        hoTen[8] = luuDiemHigh.getString("hoTen9", "");
        hoTen[9] = luuDiemHigh.getString("hoTen10", "");

        Top1 = (TextView)findViewById(R.id.highScore1);
        Top2 = (TextView)findViewById(R.id.highScore2);
        Top3 = (TextView)findViewById(R.id.highScore3);
        Top4 = (TextView)findViewById(R.id.highScore4);
        Top5 = (TextView)findViewById(R.id.highScore5);
        Top6 = (TextView)findViewById(R.id.highScore6);
        Top7 = (TextView)findViewById(R.id.highScore7);
        Top8 = (TextView)findViewById(R.id.highScore8);
        Top9 = (TextView)findViewById(R.id.highScore9);
        Top10 = (TextView)findViewById(R.id.highScore10);

        Ten1 = (TextView)findViewById(R.id.highScoreName1);
        Ten2 = (TextView)findViewById(R.id.highScoreName2);
        Ten3 = (TextView)findViewById(R.id.highScoreName3);
        Ten4 = (TextView)findViewById(R.id.highScoreName4);
        Ten5 = (TextView)findViewById(R.id.highScoreName5);
        Ten6 = (TextView)findViewById(R.id.highScoreName6);
        Ten7 = (TextView)findViewById(R.id.highScoreName7);
        Ten8 = (TextView)findViewById(R.id.highScoreName8);
        Ten9 = (TextView)findViewById(R.id.highScoreName9);
        Ten10 = (TextView)findViewById(R.id.highScoreName10);


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

        reset = (Button)findViewById(R.id.btnReset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLuuXoaDiem();
            }
        });
    }


    private static HighScore highScore;
    static {
        highScore = new HighScore();
    }
    public static HighScore getHighScore(){
        return highScore;
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
        diem = DataGame.getDatagame().getDiem();
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

        luuDiemHigh = getSharedPreferences("DiemSoHigh", MODE_PRIVATE);


        diemTop[0] = luuDiemHigh.getInt("DiemTop1", 0);
        diemTop[1] = luuDiemHigh.getInt("DiemTop2", 0);
        diemTop[2] = luuDiemHigh.getInt("DiemTop3", 0);
        diemTop[3] = luuDiemHigh.getInt("DiemTop4", 0);
        diemTop[4] = luuDiemHigh.getInt("DiemTop5", 0);
        diemTop[5] = luuDiemHigh.getInt("DiemTop6", 0);
        diemTop[6] = luuDiemHigh.getInt("DiemTop7", 0);
        diemTop[7] = luuDiemHigh.getInt("DiemTop8", 0);
        diemTop[8] = luuDiemHigh.getInt("DiemTop9", 0);
        diemTop[9] = luuDiemHigh.getInt("DiemTop10", 0);

        hoTen[0] = luuDiemHigh.getString("hoTen1", "");
        hoTen[1] = luuDiemHigh.getString("hoTen2", "");
        hoTen[2] = luuDiemHigh.getString("hoTen3", "");
        hoTen[3] = luuDiemHigh.getString("hoTen4", "");
        hoTen[4] = luuDiemHigh.getString("hoTen5", "");
        hoTen[5] = luuDiemHigh.getString("hoTen6", "");
        hoTen[6] = luuDiemHigh.getString("hoTen7", "");
        hoTen[7] = luuDiemHigh.getString("hoTen8", "");
        hoTen[8] = luuDiemHigh.getString("hoTen9", "");
        hoTen[9] = luuDiemHigh.getString("hoTen10", "");
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
            hoTen[i + 1] = NhapTen.getMyNhapTen().getHoTen();
        }

        setLuuDiemHighTop();
    }
    public void setLuuDiemHighTop()
    {
        SharedPreferences.Editor editor = luuDiemHigh.edit();
        editor.putInt("DiemTop1", diemTop[0]);
        editor.putInt("DiemTop2", diemTop[1]);
        editor.putInt("DiemTop3", diemTop[2]);
        editor.putInt("DiemTop4", diemTop[3]);
        editor.putInt("DiemTop5", diemTop[4]);
        editor.putInt("DiemTop6", diemTop[5]);
        editor.putInt("DiemTop7", diemTop[6]);
        editor.putInt("DiemTop8", diemTop[7]);
        editor.putInt("DiemTop9", diemTop[8]);
        editor.putInt("DiemTop10", diemTop[9]);

        editor.putString("hoTen1",hoTen[0]);
        editor.putString("hoTen2",hoTen[1]);
        editor.putString("hoTen3",hoTen[2]);
        editor.putString("hoTen4",hoTen[3]);
        editor.putString("hoTen5",hoTen[4]);
        editor.putString("hoTen6",hoTen[5]);
        editor.putString("hoTen7",hoTen[6]);
        editor.putString("hoTen8",hoTen[7]);
        editor.putString("hoTen9",hoTen[8]);
        editor.putString("hoTen10",hoTen[9]);
        editor.commit();
    }


    public void setLuuXoaDiem()
    {
        GamePlay.getMyGamePlay().xoaLuu();
        SharedPreferences.Editor editor = luuDiemHigh.edit();
        editor.putInt("DiemTop1", 0);
        editor.putInt("DiemTop2", 0);
        editor.putInt("DiemTop3", 0);
        editor.putInt("DiemTop4", 0);
        editor.putInt("DiemTop5", 0);
        editor.putInt("DiemTop6", 0);
        editor.putInt("DiemTop7", 0);
        editor.putInt("DiemTop8", 0);
        editor.putInt("DiemTop9", 0);
        editor.putInt("DiemTop10", 0);

        editor.putString("hoTen1","");
        editor.putString("hoTen2","");
        editor.putString("hoTen3","");
        editor.putString("hoTen4","");
        editor.putString("hoTen5","");
        editor.putString("hoTen6","");
        editor.putString("hoTen7","");
        editor.putString("hoTen8","");
        editor.putString("hoTen9","");
        editor.putString("hoTen10","");
        editor.commit();
        this.recreate();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_enter_reverse,R.anim.anim_exit_reverse);
    }
}
