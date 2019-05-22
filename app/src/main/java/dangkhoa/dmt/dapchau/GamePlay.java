package dangkhoa.dmt.dapchau;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GamePlay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        myGamePlay = this;

        anhXa();
        DataGame.getDatagame().setDiemBest(luuDiemSo.getInt("DiemBest", 0));
        myDiemBest.setText(""+DataGame.getDatagame().getDiemBest());

        if(tinhHinh == 1)
        {
            tinhHinh = 0;
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }

        khoiTao();
        setData();

    }

    private static int tinhHinh = 1;
    private SharedPreferences luuDiemSo;


    private static GamePlay myGamePlay;

    public static GamePlay getMyGamePlay()
    {
        return myGamePlay;
    }


    private GridView gdvGamePlay;

    private O_So_Adapter adapter;

    private Button btnMenu;

    private Button btnUndo;

    private TextView myDiem;

    private TextView myDiemBest;

    private SoundPlayer sound;

    private double x0, y0, x, y;

    private void anhXa(){
        luuDiemSo = getSharedPreferences("DiemSoGame", MODE_PRIVATE);
        gdvGamePlay = (GridView)findViewById(R.id.gdvGamePlay);
        myDiem = (TextView)findViewById(R.id.myDiem);
        myDiemBest = (TextView)findViewById(R.id.myBestDiem);
        btnMenu = (Button)findViewById(R.id.btnMenu);
        btnUndo = (Button)findViewById(R.id.btnUnDo);
        sound = new SoundPlayer(this);
    }

    private void khoiTao(){
        DataGame.getDatagame().intt(GamePlay.this);
        adapter = new O_So_Adapter(GamePlay.this, 0, DataGame.getDatagame().getArrSo());
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
                                    DataGame.getDatagame().vuotPhai();
                                    adapter.notifyDataSetChangedGame(DataGame.getDatagame().getArrSo(), DataGame.getDatagame().getLoai());
                                } else if (x < x0) {                                //Vuốt trái
                                    DataGame.getDatagame().vuotTrai();
                                    adapter.notifyDataSetChangedGame(DataGame.getDatagame().getArrSo(), DataGame.getDatagame().getLoai());
                                }
                            } else if (Math.abs(x - x0) < Math.abs(y - y0)) {
                                if (y > y0) {                            // Vuốt xuống
                                    DataGame.getDatagame().vuotXuong();
                                    adapter.notifyDataSetChangedGame(DataGame.getDatagame().getArrSo(), DataGame.getDatagame().getLoai());
                                } else if (y < y0) {                                 // Vuốt lên
                                    DataGame.getDatagame().vuotLen();
                                    adapter.notifyDataSetChangedGame(DataGame.getDatagame().getArrSo(), DataGame.getDatagame().getLoai());
                                }
                            }
                            myDiem.setText("" + DataGame.getDatagame().getDiem());
                            myDiemBest.setText("" + DataGame.getDatagame().getDiemBest());
                            if (DataGame.getDatagame().kiemTra() == 0) {
                                tinhHinh = 2;
                                sound.playThuaSound();
                                DataGame.getDatagame().dongBo();
                                HighScore.getHighScore().dongBo();
                                Toast.makeText(GamePlay.this, "GAME OVER", Toast.LENGTH_SHORT).show();
                            }
                            if (DataGame.getDatagame().phaDao() == 1) {
                                tinhHinh = 2;
                                sound.playThangSound();
                                DataGame.getDatagame().dongBo();
                                HighScore.getHighScore().dongBo();
                                Toast.makeText(GamePlay.this, "YOU WIN", Toast.LENGTH_SHORT).show();
                            }
                            break;
                    }
                }
                else if(tinhHinh == 2)
                {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            startActivity(new Intent(GamePlay.this, NhapTen.class));
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
                startActivity(new Intent(GamePlay.this, Menu.class));
                overridePendingTransition(R.anim.anim_enter,R.anim.anim_exit);
            }
        });

        btnUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DataGame.getDatagame().getDiem() != 0) {
                    HighScore.getHighScore().quayLaiDiem();
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
        DataGame.getDatagame().getBack();
        adapter.notifyDataSetChangedGame(DataGame.getDatagame().getArrSo(), DataGame.getDatagame().getLoai());
        myDiem.setText(""+DataGame.getDatagame().getDiem());
        myDiemBest.setText(""+DataGame.getDatagame().getDiemBest());
        setData();
    }

    public void Luu()
    {
        SharedPreferences.Editor editor = luuDiemSo.edit();
        editor.putInt("DiemBest", DataGame.getDatagame().getDiemBest());
        editor.commit();
    }

    public void xoaLuu()
    {
        SharedPreferences.Editor editor = luuDiemSo.edit();
        editor.putInt("DiemBest", 0);
        editor.commit();
    }

    public void resetTinhHinh()
    {
        tinhHinh = 0;
    }

    public void amThanh()
    {
        sound.playHitSound();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_enter_reverse,R.anim.anim_exit_reverse);
    }
}
