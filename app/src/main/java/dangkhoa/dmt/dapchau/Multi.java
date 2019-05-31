package dangkhoa.dmt.dapchau;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.Locale;

public class Multi extends AppCompatActivity {


    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.56.1:3000");
        } catch (URISyntaxException e) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi);

        mSocket.connect();

        mSocket.on("guiMang", onMessage_guiMang);
        mSocket.on("guiDiem", onMessage_guiDiem);
        mSocket.on("guiThua", onMessage_guiThua);
        mSocket.on("guiThang", onMessage_guiThang);
        mSocket.on("batDauTran", onMessage_batDauTran);
        mSocket.on("tenThoiGian", onMessage_tenThoiGian);
        mSocket.on("thoatgame", onMessage_thoatgame);
        myMulti = this;

        Intent intent1 = getIntent();
        Bundle bundle = intent1.getBundleExtra("guiten");
        tenDangNhap = bundle.getString("tenMinh");
        tenBan = bundle.getString("tenBan");

        tongThoiGian = 10000;
        timerLeft = tongThoiGian;
        timerLeft_2 = tongThoiGian;
        anhXa();
        colorTimer = myTimer.getTextColors();
        khoiTao();
        setData();
        setDataMulti();
        updateTimerText();
        updateTimerText_2();
    }


    private int tinhHinh = 0;

    private static Multi myMulti;

    public static Multi getMyMulti()
    {
        return myMulti;
    }

    private String tenDangNhap = "";
    private String tenBan = "";
    private GridView gdvGamePlay, gdvMulti;

    private O_So_Adapter adapter;

    private O_So_Adapter_Multi adapter_multi;

    private Button btnUndo;

    private TextView myDiem;

    private TextView friendDiem;

    private ArrayList<Integer> mangHaiChieu;

    private ArrayList<Integer> loai;

    private double x0, y0, x, y;

    private TextView myTimer, friendTimer;

    private long timerLeft = 10000, timerLeft_2 = 10000;

    private int tongThoiGian;

    private ColorStateList colorTimer;

    private CountDownTimer timer, timer_2;

    private boolean timerRunning, timerRunning_2;

    private SoundPlayer sound;

    private void anhXa(){
        gdvGamePlay = (GridView)findViewById(R.id.gdvGamePlayMulti);
        gdvMulti = (GridView)findViewById(R.id.gdvGamePlayMulti2);
        myDiem = (TextView)findViewById(R.id.myDiemMulti);
        friendDiem = (TextView)findViewById(R.id.myFriendScore);
        btnUndo = (Button)findViewById(R.id.btnUnDoMulti);

        myTimer = (TextView)findViewById(R.id.thoiGian1);
        friendTimer = (TextView)findViewById(R.id.thoiGian2);
        sound = new SoundPlayer(this);
    }

    private void khoiTao(){
        DataGameMulti.getDataGameMulti().intt(Multi.this);
        adapter = new O_So_Adapter(Multi.this, 0, DataGameMulti.getDataGameMulti().getArrSo());
        adapter_multi = new O_So_Adapter_Multi(Multi.this, 0, DataGameMulti.getDataGameMulti().getArrSo());
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
                                    DataGameMulti.getDataGameMulti().vuotPhai();
                                    adapter.notifyDataSetChangedGame(DataGameMulti.getDataGameMulti().getArrSo(), DataGameMulti.getDataGameMulti().getLoai());
                                    mSocket.emit("xoaMang", 0);
                                    mangHaiChieu = DataGameMulti.getDataGameMulti().getArrSo();
                                    loai = DataGameMulti.getDataGameMulti().getLoai();
                                    for (int i = 0; i < 16; i++) {
                                        mSocket.emit("nhapMang", mangHaiChieu.get(i));
                                        mSocket.emit("nhapLoai", loai.get(i));
                                    }
                                    mSocket.emit("xuatMang", 0);
                                    if (timerRunning == false) {
                                        startTimer();
                                        startTimer_2();
                                        mSocket.emit("batDau", 0);
                                    } else {
                                        if (DataGameMulti.getDataGameMulti().isTaoSo() > 0) {
                                            pauseTimer();
                                            themTimer();
                                            startTimer();
                                            mSocket.emit("themThoiGian", timerLeft);
                                        }
                                    }
                                } else if (x < x0) {                                //Vuốt trái

                                    DataGameMulti.getDataGameMulti().vuotTrai();
                                    adapter.notifyDataSetChangedGame(DataGameMulti.getDataGameMulti().getArrSo(), DataGameMulti.getDataGameMulti().getLoai());
                                    mSocket.emit("xoaMang", 0);
                                    mangHaiChieu = DataGameMulti.getDataGameMulti().getArrSo();
                                    loai = DataGameMulti.getDataGameMulti().getLoai();
                                    for (int i = 0; i < 16; i++) {
                                        mSocket.emit("nhapMang", mangHaiChieu.get(i));
                                        mSocket.emit("nhapLoai", loai.get(i));
                                    }
                                    mSocket.emit("xuatMang", 0);
                                    if (timerRunning == false) {
                                        startTimer();
                                        startTimer_2();
                                        mSocket.emit("batDau", 0);
                                    } else {
                                        if (DataGameMulti.getDataGameMulti().isTaoSo() > 0) {
                                            pauseTimer();
                                            themTimer();
                                            startTimer();
                                            mSocket.emit("themThoiGian", timerLeft);
                                        }
                                    }
                                }
                            } else if (Math.abs(x - x0) < Math.abs(y - y0)) {
                                if (y > y0) {                            // Vuốt xuống

                                    DataGameMulti.getDataGameMulti().vuotXuong();
                                    adapter.notifyDataSetChangedGame(DataGameMulti.getDataGameMulti().getArrSo(), DataGameMulti.getDataGameMulti().getLoai());
                                    mSocket.emit("xoaMang", 0);
                                    mangHaiChieu = DataGameMulti.getDataGameMulti().getArrSo();
                                    loai = DataGameMulti.getDataGameMulti().getLoai();
                                    for (int i = 0; i < 16; i++) {
                                        mSocket.emit("nhapMang", mangHaiChieu.get(i));
                                        mSocket.emit("nhapLoai", loai.get(i));
                                    }
                                    mSocket.emit("xuatMang", 0);
                                    if (timerRunning == false) {
                                        startTimer();
                                        startTimer_2();
                                        mSocket.emit("batDau", 0);
                                    } else {
                                        if (DataGameMulti.getDataGameMulti().isTaoSo() > 0) {
                                            pauseTimer();
                                            themTimer();
                                            startTimer();
                                            mSocket.emit("themThoiGian", timerLeft);
                                        }
                                    }

                                } else if (y < y0) {                                 // Vuốt lên

                                    DataGameMulti.getDataGameMulti().vuotLen();
                                    adapter.notifyDataSetChangedGame(DataGameMulti.getDataGameMulti().getArrSo(), DataGameMulti.getDataGameMulti().getLoai());
                                    mSocket.emit("xoaMang", 0);
                                    mangHaiChieu = DataGameMulti.getDataGameMulti().getArrSo();
                                    loai = DataGameMulti.getDataGameMulti().getLoai();
                                    for (int i = 0; i < 16; i++) {
                                        mSocket.emit("nhapMang", mangHaiChieu.get(i));
                                        mSocket.emit("nhapLoai", loai.get(i));
                                    }
                                    mSocket.emit("xuatMang", 0);
                                    if (timerRunning == false) {
                                        startTimer();
                                        startTimer_2();
                                        mSocket.emit("batDau", 0);
                                    } else {
                                        if (DataGameMulti.getDataGameMulti().isTaoSo() > 0) {
                                            pauseTimer();
                                            themTimer();
                                            startTimer();
                                            mSocket.emit("themThoiGian", timerLeft);
                                        }
                                    }
                                }
                            }
                            myDiem.setText("" + DataGameMulti.getDataGameMulti().getDiem());
                            mSocket.emit("nhapDiem", DataGameMulti.getDataGameMulti().getDiem());
                            mSocket.emit("xuatDiem", 0);

                            if (DataGameMulti.getDataGameMulti().kiemTra() == 0) {
                                tinhHinh = 2;
                                sound.playThuaSound();
                                pauseTimer();
                                pauseTimer_2();
                                DataGameMulti.getDataGameMulti().dongBo();
                                Toast.makeText(Multi.this, "YOU LOSE", Toast.LENGTH_SHORT).show();
                                mSocket.emit("xuatThua", 0);
                            }
                            if (DataGameMulti.getDataGameMulti().phaDao() == 1) {
                                tinhHinh = 2;
                                sound.playThangSound();
                                pauseTimer();
                                pauseTimer_2();
                                DataGameMulti.getDataGameMulti().dongBo();
                                Toast.makeText(Multi.this, "YOU WIN", Toast.LENGTH_SHORT).show();
                                mSocket.emit("xuatThang", 0);
                            }
                            break;
                    }

                }
                else if(tinhHinh == 2)
                {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            mSocket.emit("thoatDangChoi", tenDangNhap);
                            tenDangNhap = "";
                            tenBan = "";
                            resetTimer();
                            mSocket.disconnect();
                            MainActivity.getMyMain().khoaThoatRa();
                            startActivity(new Intent(Multi.this, MainActivity.class));
                            overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                            break;
                    }
                }
                return true;
            }
        });


        btnUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DataGameMulti.getDataGameMulti().getDiem() != 0) {
                    setMyUnDo();
                }
            }
        });
    }

    public void setData(){
        gdvGamePlay.setAdapter(adapter);
    }

    public void setDataMulti()
    {
        gdvMulti.setAdapter(adapter_multi);
    }

    public void setMyUnDo()
    {
        DataGameMulti.getDataGameMulti().getBack();
        adapter.notifyDataSetChangedGame(DataGameMulti.getDataGameMulti().getArrSo(), DataGameMulti.getDataGameMulti().getLoai());
        mSocket.emit("xoaMang", 0);
        mangHaiChieu = DataGameMulti.getDataGameMulti().getArrSo();
        loai = DataGameMulti.getDataGameMulti().getLoai();
        for(int i = 0;i < 16;i++)
        {
            mSocket.emit("nhapMang", mangHaiChieu.get(i));
            mSocket.emit("nhapLoai", loai.get(i));
        }
        mSocket.emit("xuatMang", 0);
        mSocket.emit("nhapDiem", DataGameMulti.getDataGameMulti().getDiem());
        mSocket.emit("xuatDiem", 0);
        myDiem.setText(""+DataGameMulti.getDataGameMulti().getDiem());
        setData();
    }

    public void resetTinhHinh()
    {
        tinhHinh = 0;
    }

    private Emitter.Listener onMessage_guiMang = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    JSONArray mang;
                    JSONArray loaiMang;
                    String ten = "";
                    ArrayList<Integer> mangMulti = new ArrayList<Integer>();
                    ArrayList<Integer> mangLoai = new ArrayList<Integer>();
                    try {
                        mang = data.getJSONArray("danhsachMang");
                        loaiMang = data.getJSONArray("danhsachLoai");
                        ten = data.getString("tenDangNhap");
                        if(ten.equals(tenBan) == true && tenBan.equals("") != true)
                        {
                            if(mang.length() > 0)
                            {
                                for(int i = 0;i < 16;i++)
                                {
                                    mangMulti.add(mang.getInt(i));
                                    mangLoai.add(loaiMang.getInt(i));
                                }
                                adapter_multi.notifyDataSetChangedGame(mangMulti,mangLoai);
                                setDataMulti();
                            }
                            else
                            {
                                for(int i = 0;i < 16;i++)
                                {
                                    mangLoai.add(0);
                                    mangMulti.add(0);
                                }
                                adapter_multi.notifyDataSetChangedGame(mangMulti,mangLoai);
                                setDataMulti();
                            }
                        }

                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };

    private Emitter.Listener onMessage_guiDiem = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String ten = "";
                    int diem = 0;
                    try {
                        ten = data.getString("tenDangNhapDiem");
                        diem = data.getInt("diemDat");
                        if(ten.equals(tenBan) == true && tenBan.equals("") != true) {
                            friendDiem.setText("" + diem);
                        }

                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };
    private Emitter.Listener onMessage_guiThua = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String ten = "";
                    try {
                        ten = data.getString("tenDangNhapThang");
                        if(ten.equals(tenBan) == true && tenBan.equals("") != true) {
                            pauseTimer();
                            if(timerRunning_2 == true)
                            {
                                pauseTimer_2();
                            }
                            tinhHinh = 2;
                            sound.playThangSound();
                            DataGameMulti.getDataGameMulti().dongBo();
                            Toast.makeText(Multi.this, "YOU WIN", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };

    private Emitter.Listener onMessage_guiThang = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String ten = "";
                    try {
                        ten = data.getString("tenDangNhapThua");
                        if(ten.equals(tenBan) == true && tenBan.equals("") != true) {
                            pauseTimer();
                            if(timerRunning_2 == true)
                            {
                                pauseTimer_2();
                            }
                            tinhHinh = 2;
                            sound.playThuaSound();
                            DataGameMulti.getDataGameMulti().dongBo();
                            Toast.makeText(Multi.this, "YOU LOSE", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };

    private Emitter.Listener onMessage_batDauTran = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String ten;
                    try {
                        ten = data.getString("tenTran");
                        if(ten.equals(tenBan) == true && tenBan.equals("") != true) {
                            startTimer();
                            startTimer_2();
                        }
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };

    private Emitter.Listener onMessage_tenThoiGian = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String ten;
                    long thoigian;
                    try {
                        ten = data.getString("tenthoigian");
                        thoigian = data.getLong("thoigian");
                        if(ten.equals(tenBan) == true && tenBan.equals("") != true) {
                            pauseTimer_2();
                            timerLeft_2 = thoigian;
                            startTimer_2();
                        }
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };

    private Emitter.Listener onMessage_thoatgame = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String ten;
                    try {
                        ten = data.getString("nguoiThoat");
                        if(ten.equals(tenBan) == true && tenBan.equals("") != true) {
                            if(timerRunning == true)
                            {
                                pauseTimer();
                                pauseTimer_2();
                                timerRunning = false;
                                timerRunning_2 = false;
                            }
                            resetTimer();
                            mSocket.emit("thoatDangChoi", tenDangNhap);
                            Toast.makeText(Multi.this, "'"+ tenBan + "' đã đăng xuất khỏi trò chơi", Toast.LENGTH_SHORT).show();
                            tenDangNhap = "";
                            tenBan = "";
                            Intent intent2 = new Intent();
                            setResult(RESULT_OK, intent2);
                            finish();
                            overridePendingTransition(R.anim.anim_enter_reverse,R.anim.anim_exit_reverse);
                        }
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };


    public void startTimer()
    {
        timer = new CountDownTimer(timerLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerLeft = millisUntilFinished;
                updateTimerText();
                if(timerLeft <= 6000)
                {
                    Animation animation = AnimationUtils.loadAnimation(Multi.this, R.anim.anim_scale);
                    myTimer.startAnimation(animation);
                }
            }

            @Override
            public void onFinish() {
                timerLeft = 0;
                updateTimerText();
                pauseTimer_2();
                Animation animation = AnimationUtils.loadAnimation(Multi.this, R.anim.anim_scale);
                myTimer.startAnimation(animation);
                timerRunning = false;
                tinhHinh = 2;
                sound.playThuaSound();
                DataGameMulti.getDataGameMulti().dongBo();
                Toast.makeText(Multi.this, "YOU LOSE", Toast.LENGTH_SHORT).show();
                mSocket.emit("xuatThua", 0);
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
        timerLeft_2 = Long.MAX_VALUE;
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

    public void startTimer_2()
    {
        timer_2 = new CountDownTimer(timerLeft_2, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerLeft_2 = millisUntilFinished;
                updateTimerText_2();
                if(timerLeft_2 <= 6000)
                {
                    Animation animation = AnimationUtils.loadAnimation(Multi.this, R.anim.anim_scale);
                    friendTimer.startAnimation(animation);
                }
            }

            @Override
            public void onFinish() {
                timerLeft_2 = 0;
                updateTimerText_2();
                Animation animation = AnimationUtils.loadAnimation(Multi.this, R.anim.anim_scale);
                friendTimer.startAnimation(animation);
                timerRunning_2 = false;
            }
        }.start();
        timerRunning_2 = true;
    }

    public void pauseTimer_2()
    {
        timer_2.cancel();
        timerRunning_2 = false;
        updateTimerText_2();
    }

    public void themTimer_2()
    {
        timerLeft_2+=1000;
    }


    public void updateTimerText_2()
    {
        int phut = (int) (timerLeft_2 / 1000) / 60;
        int giay = (int) (timerLeft_2 / 1000) % 60;

        if(timerLeft_2 <= 11000)
        {
            friendTimer.setTextColor(Color.RED);
        }
        else
        {
            friendTimer.setTextColor(colorTimer);
        }

        String thoiGian = String.format(Locale.getDefault(),"%02d:%02d", phut, giay);
        friendTimer.setText(thoiGian);
    }


    public void amThanh()
    {
        sound.playHitSound();
    }

    @Override
    protected void onStop() {
        if(timerRunning == true)
        {
            pauseTimer();
            pauseTimer_2();
            timerRunning = false;
            timerRunning_2 = false;
        }
        mSocket.emit("thoatDangChoi", tenDangNhap);
        tenDangNhap = "";
        tenBan = "";
        resetTimer();
        mSocket.emit("thoatGame", 0);
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_enter_reverse,R.anim.anim_exit_reverse);
    }
}