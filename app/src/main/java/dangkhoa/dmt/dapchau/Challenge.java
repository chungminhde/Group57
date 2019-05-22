package dangkhoa.dmt.dapchau;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Challenge extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        myChallenge = this;

        Intent intent1 = getIntent();
        tongThoiGian = intent1.getIntExtra("thoigian", 0);
        timerLeft = tongThoiGian;
        anhXa();
        if(tongThoiGian == 10000) {
            luuDiemSo = getSharedPreferences("DiemSoHighChallenge10", MODE_PRIVATE);
            DataGameChallenge.getDatagameChallenge().setDiemBest(luuDiemSo.getInt("DiemTop1Challenge10", 0));
        }
        else if(tongThoiGian == 20000)
        {
            luuDiemSo = getSharedPreferences("DiemSoHighChallenge20", MODE_PRIVATE);
            DataGameChallenge.getDatagameChallenge().setDiemBest(luuDiemSo.getInt("DiemTop1Challenge20", 0));
        }
        else if(tongThoiGian == 30000)
        {
            luuDiemSo = getSharedPreferences("DiemSoHighChallenge", MODE_PRIVATE);
            DataGameChallenge.getDatagameChallenge().setDiemBest(luuDiemSo.getInt("DiemTop1Challenge", 0));
        }

        myDiemBest.setText("" + DataGameChallenge.getDatagameChallenge().getDiemBest());

        colorTimer = myTimer.getTextColors();
        if (tinhHinh == 1) {
            tinhHinh = 0;
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }

        khoiTao();
        setData();
        updateTimerText();

    }

    private static int tinhHinh = 1;
    private SharedPreferences luuDiemSo;


    private static Challenge myChallenge;

    public static Challenge getMyChallenge()
    {
        return myChallenge;
    }


    private GridView gdvGamePlay;

    private O_So_Adapter_Challenge adapter;

    private Button btnMenu;

    private Button btnUndo;

    private TextView myDiem;

    private TextView myDiemBest;

    private TextView myTimer;

    private long timerLeft;

    private double x0, y0, x, y;

    private ColorStateList colorTimer;

    private CountDownTimer timer;

    private boolean timerRunning;

    private int tongThoiGian;

    private SoundPlayer sound;

    private void anhXa(){
        if(tongThoiGian == 10000) {
            luuDiemSo = getSharedPreferences("DiemSoGameChallenge10", MODE_PRIVATE);
        }
        else if(tongThoiGian == 20000)
        {
            luuDiemSo = getSharedPreferences("DiemSoGameChallenge20", MODE_PRIVATE);
        }
        else if(tongThoiGian == 30000)
        {
            luuDiemSo = getSharedPreferences("DiemSoGameChallenge", MODE_PRIVATE);
        }
        gdvGamePlay = (GridView)findViewById(R.id.gdvGamePlayChallenge);
        myDiem = (TextView)findViewById(R.id.myDiemChallenge);
        myDiemBest = (TextView)findViewById(R.id.myBestDiemChallenge);
        myTimer = (TextView)findViewById(R.id.myTimer);
        btnMenu = (Button)findViewById(R.id.btnMenuChallenge);
        btnUndo = (Button)findViewById(R.id.btnUnDoChallenge);
        sound = new SoundPlayer(this);
    }

    private void khoiTao(){
        DataGameChallenge.getDatagameChallenge().intt(Challenge.this);
        adapter = new O_So_Adapter_Challenge(Challenge.this, 0, DataGameChallenge.getDatagameChallenge().getArrSo());
        gdvGamePlay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (tinhHinh == 0) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            x0 = event.getX();
                            y0 = event.getY();
                            break;
                        case MotionEvent.ACTION_UP:
                            x = event.getX();
                            y = event.getY();
                            if (Math.abs(x - x0) > Math.abs(y - y0)) {
                                if (x > x0) {                                     // Vuốt phải
                                    DataGameChallenge.getDatagameChallenge().vuotPhai();
                                    adapter.notifyDataSetChanged();
                                    if(timerRunning == false)
                                    {
                                        startTimer();
                                    }
                                    else
                                    {
                                        if(DataGameChallenge.getDatagameChallenge().isTaoSo() > 0)
                                        {
                                            pauseTimer();
                                            themTimer();
                                            startTimer();
                                        }
                                    }
                                } else if (x < x0) {                                //Vuốt trái
                                    DataGameChallenge.getDatagameChallenge().vuotTrai();
                                    adapter.notifyDataSetChanged();
                                    if(timerRunning == false)
                                    {
                                        startTimer();
                                    }
                                    else
                                    {
                                        if(DataGameChallenge.getDatagameChallenge().isTaoSo() > 0)
                                        {
                                            pauseTimer();
                                            themTimer();
                                            startTimer();
                                        }

                                    }
                                }
                            } else if (Math.abs(x - x0) < Math.abs(y - y0)) {
                                if (y > y0) {                            // Vuốt xuống
                                    DataGameChallenge.getDatagameChallenge().vuotXuong();
                                    adapter.notifyDataSetChanged();
                                    if(timerRunning == false)
                                    {
                                        startTimer();
                                    }
                                    else
                                    {
                                        if(DataGameChallenge.getDatagameChallenge().isTaoSo() > 0)
                                        {
                                            pauseTimer();
                                            themTimer();
                                            startTimer();
                                        }

                                    }
                                } else if (y < y0) {                                 // Vuốt lên
                                    DataGameChallenge.getDatagameChallenge().vuotLen();
                                    adapter.notifyDataSetChanged();
                                    if(timerRunning == false)
                                    {
                                        startTimer();
                                    }
                                    else
                                    {
                                        if(DataGameChallenge.getDatagameChallenge().isTaoSo() > 0)
                                        {
                                            pauseTimer();
                                            themTimer();
                                            startTimer();
                                        }
                                    }
                                }
                            }
                            myDiem.setText("" + DataGameChallenge.getDatagameChallenge().getDiem());
                            myDiemBest.setText("" + DataGameChallenge.getDatagameChallenge().getDiemBest());
                            if (DataGameChallenge.getDatagameChallenge().kiemTra() == 0) {
                                pauseTimer();
                                tinhHinh = 2;
                                sound.playThuaSound();
                                DataGameChallenge.getDatagameChallenge().dongBo();
                                if(tongThoiGian == 10000) {
                                    HighScore_Challenge10.getHighScoreChallenge10().dongBo();
                                }
                                else if(tongThoiGian == 20000)
                                {
                                    HighScore_Challenge20.getHighScoreChallenge20().dongBo();
                                }
                                else if(tongThoiGian == 30000)
                                {
                                    HighScore_Challenge.getHighScoreChallenge().dongBo();
                                }
                                Toast.makeText(Challenge.this, "GAME OVER", Toast.LENGTH_SHORT).show();
                            }
                            if (DataGameChallenge.getDatagameChallenge().phaDao() == 1) {
                                pauseTimer();
                                tinhHinh = 2;
                                sound.playThangSound();
                                DataGameChallenge.getDatagameChallenge().dongBo();
                                if(tongThoiGian == 10000) {
                                    HighScore_Challenge10.getHighScoreChallenge10().dongBo();
                                }
                                else if(tongThoiGian == 20000)
                                {
                                    HighScore_Challenge20.getHighScoreChallenge20().dongBo();
                                }
                                else if(tongThoiGian == 30000)
                                {
                                    HighScore_Challenge.getHighScoreChallenge().dongBo();
                                }
                                Toast.makeText(Challenge.this, "YOU WIN", Toast.LENGTH_SHORT).show();
                            }
                            break;
                    }
                }
                else if(tinhHinh == 2)
                {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            resetTimer();
                            Intent intent2 = new Intent(Challenge.this, NhapTen_Challenge.class);
                            if(tongThoiGian == 10000)
                            {
                                intent2.putExtra("thoigian2", 10000);
                            }
                            else if(tongThoiGian == 20000)
                            {
                                intent2.putExtra("thoigian2", 20000);
                            }
                            else if(tongThoiGian == 30000)
                            {
                                intent2.putExtra("thoigian2", 30000);
                            }
                            startActivity(intent2);
                            overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                            break;
                    }
                }
                return true;
            }
        });


        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerRunning == true)
                {
                    pauseTimer();
                    timerRunning = false;
                }
                startActivity(new Intent(Challenge.this, Menu.class));
                overridePendingTransition(R.anim.anim_enter,R.anim.anim_exit);
            }
        });

        btnUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DataGameChallenge.getDatagameChallenge().getDiem() != 0) {
                    if(tongThoiGian == 10000) {
                        HighScore_Challenge10.getHighScoreChallenge10().quayLaiDiem();
                    }
                    else if(tongThoiGian == 20000)
                    {
                        HighScore_Challenge20.getHighScoreChallenge20().quayLaiDiem();
                    }
                    else if(tongThoiGian == 30000)
                    {
                        HighScore_Challenge.getHighScoreChallenge().quayLaiDiem();
                    }
                    setMyUnDo();
                }
            }
        });
    }

    public void setData(){
        gdvGamePlay.setAdapter(adapter);
    }

    public void setMyUnDo()
    {
        DataGameChallenge.getDatagameChallenge().getBack();
        adapter.notifyDataSetChanged();
        myDiem.setText(""+DataGameChallenge.getDatagameChallenge().getDiem());
        myDiemBest.setText(""+DataGameChallenge.getDatagameChallenge().getDiemBest());
        setData();
    }


    public void resetTinhHinh()
    {
        tinhHinh = 0;
    }

    public void startTimer()
    {
        timer = new CountDownTimer(timerLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerLeft = millisUntilFinished;
                updateTimerText();
                if(timerLeft <= 6000)
                {
                    Animation animation = AnimationUtils.loadAnimation(Challenge.this, R.anim.anim_scale);
                    myTimer.startAnimation(animation);
                }
            }

            @Override
            public void onFinish() {

                timerLeft = 0;
                updateTimerText();
                Animation animation = AnimationUtils.loadAnimation(Challenge.this, R.anim.anim_scale);
                myTimer.startAnimation(animation);
                timerRunning = false;
                tinhHinh = 2;
                sound.playThuaSound();
                DataGameChallenge.getDatagameChallenge().dongBo();
                if(tongThoiGian == 10000) {
                    HighScore_Challenge10.getHighScoreChallenge10().dongBo();
                }
                else if(tongThoiGian == 20000)
                {
                    HighScore_Challenge20.getHighScoreChallenge20().dongBo();
                }
                else if(tongThoiGian == 30000)
                {
                    HighScore_Challenge.getHighScoreChallenge().dongBo();
                }
                Toast.makeText(Challenge.this, "GAME OVER", Toast.LENGTH_LONG).show();
            }
        }.start();
        timerRunning = true;
    }

    public void pauseTimer()
    {
        timer.cancel();
        timerRunning = false;
        updateTimerText();
    }

    public void themTimer()
    {
        timerLeft+=1000;
    }
    public void resetTimer()
    {
        timerLeft = Long.MAX_VALUE;
    }


    public void updateTimerText()
    {
        int phut = (int) (timerLeft / 1000) / 60;
        int giay = (int) (timerLeft / 1000) % 60;

        if(timerLeft <= 11000)
        {
            myTimer.setTextColor(Color.RED);
        }
        else
        {
            myTimer.setTextColor(colorTimer);
        }

        String thoiGian = String.format(Locale.getDefault(),"%02d:%02d", phut, giay);
        myTimer.setText(thoiGian);

    }

    @Override
    public void onBackPressed() {
        if(timerRunning == true)
        {
            pauseTimer();
            timerRunning = false;
        }
        resetTimer();
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_enter_reverse,R.anim.anim_exit_reverse);
    }

    @Override
    protected void onStop() {
        if(timerRunning == true)
        {
            pauseTimer();
            timerRunning = false;
        }
        super.onStop();
    }

    public void amThanh()
    {
        sound.playHitSound();
    }
}
