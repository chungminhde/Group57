package dangkhoa.dmt.dapchau;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class DangNhap extends AppCompatActivity {

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.56.1:3000");
        } catch (URISyntaxException e) {
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        mSocket.connect();
        mSocket.on("ketquaDangKy", onMessage_ketquaDangKy);
        mSocket.on("ketquaDangNhap", onMessage_ketquaDangNhap);
        mSocket.on("guitenDangNhap", onMessage_guitenDangNhap);

        btnDangNhap = (Button)findViewById(R.id.btnDangNhap);
        btnDangKy = (Button)findViewById(R.id.btnDangKy);
        myDangNhap = (EditText)findViewById(R.id.myDangNhap);


        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSocket.connected() == true) {
                    if (myDangNhap.getText().toString().length() != 0) {
                        mSocket.emit("dangNhap", myDangNhap.getText().toString());
                    } else {
                        Toast.makeText(DangNhap.this, "Bạn chưa nhập tên đăng nhập", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(DangNhap.this, "Bạn chưa kết nối server", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSocket.connected() == true)
                {
                    if(myDangNhap.getText().toString().length() == 0)
                    {
                        Toast.makeText(DangNhap.this, "Bạn chưa nhập tên đăng ký", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        mSocket.emit("dangKy",myDangNhap.getText().toString());
                    }

                }
                else
                {
                    Toast.makeText(DangNhap.this, "Bạn chưa kết nối server", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private Button btnDangNhap, btnDangKy;

    private EditText myDangNhap;

    private int tenMinh = 0;


    private Emitter.Listener onMessage_ketquaDangKy = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    int ketQua;
                    try {
                        ketQua = data.getInt("noidung");

                        if (ketQua == 0) {
                            Toast.makeText(DangNhap.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        } else if(ketQua == 1) {
                            Toast.makeText(DangNhap.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                        }
                        else if(ketQua == 2)
                        {
                            Toast.makeText(DangNhap.this, "Tên tài khoản không hợp lệ (khác 0)", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };
    private Emitter.Listener onMessage_guitenDangNhap = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        tenMinh = data.getInt("tenMinh");
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };


    private Emitter.Listener onMessage_ketquaDangNhap = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    int ketQua;
                    try {
                        ketQua = data.getInt("kq");
                        if (ketQua == 0) {
                            Intent intent = new Intent(DangNhap.this, KetNoi.class);
                            intent.putExtra("tenDangNhap", tenMinh);
                            startActivity(intent);
                            overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                        } else if (ketQua == 1) {
                            Toast.makeText(DangNhap.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                        } else if (ketQua == 2) {
                            Toast.makeText(DangNhap.this, "Tài khoản đang được sử dụng", Toast.LENGTH_SHORT).show();
                        }else if(ketQua == 3)
                        {
                            Toast.makeText(DangNhap.this, "Tài khoản đang được sử dụng", Toast.LENGTH_SHORT).show();
                        }
                        else if (ketQua == 4) {
                            Toast.makeText(DangNhap.this, "Tên tài khoản không hợp lệ (khác 0)", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };

    @Override
    public void onBackPressed() {
        mSocket.disconnect();
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_enter_reverse,R.anim.anim_exit_reverse);
    }
}
