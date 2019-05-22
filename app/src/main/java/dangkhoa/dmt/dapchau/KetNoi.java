<<<<<<< HEAD
package dangkhoa.dmt.dapchau;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class KetNoi extends AppCompatActivity {

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
        setContentView(R.layout.activity_ket_noi);
        mSocket.on("ketquaMoiBan", onMessage_ketquaMoiBan);
        mSocket.on("vaotran", onMessage_ketquaVaoTran);
        mSocket.on("guiTuChoi", onMessage_guiTuChoi);
        mSocket.on("traloi", onMessage_traloi);
        mSocket.on("danhsachcho", onMessage_danhsachcho);
        friend = (EditText)findViewById(R.id.friendDangNhap);
        moi = (Button)findViewById(R.id.btnMoi);
        dangXuat = (Button)findViewById(R.id.btnDangXuat);
        myList = (ListView)findViewById(R.id.listPhongCho);

        Intent intent1 = getIntent();
        tenDangNhap = intent1.getIntExtra("tenDangNhap", 0);

        mSocket.emit("danhsachCho", 0);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                friend.setText("" + danhsach.get(position));
            }
        });

        moi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dangMoi == false) {
                    if (friend.getText().toString().length() == 0) {
                        Toast.makeText(KetNoi.this, "Bạn chưa nhập tên đối thủ", Toast.LENGTH_SHORT).show();
                    } else {
                        mSocket.emit("moiBan", friend.getText().toString());
                    }
                }
                else
                {
                    Toast.makeText(KetNoi.this, "Có người đang mời, vui lòng chờ một lát", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSocket.emit("thoatHangCho", tenDangNhap);
                mSocket.emit("danhsachCho", 0);
                tenDangNhap = 0;
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                overridePendingTransition(R.anim.anim_enter_reverse,R.anim.anim_exit_reverse);
            }
        });
    }

    private EditText friend;
    private Button moi;
    private Button dangXuat;
    private ListView myList;
    private int tenDangNhap;
    private boolean dangMoi = false;
    private ArrayList<Integer> danhsach = new ArrayList<Integer>();
    private Emitter.Listener onMessage_ketquaMoiBan = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    int ketqua;
                    int minh;
                    int ban;
                    int nhan;
                    try {
                        nhan = data.getInt("tenNguoiNhan");
                        minh = data.getInt("tenminh");
                        ban = data.getInt("tenban");
                        ketqua = data.getInt("kqmoi");
                        if(ketqua == 2)
                        {
                            dangMoi = true;
                            if(tenDangNhap == ban)
                            {
                                minh = data.getInt("tenminh");
                                ban = data.getInt("tenban");
                                moi.setText("Đồng ý");
                                dangXuat.setText("Từ chối");
                                Toast.makeText(KetNoi.this, "Bạn được '" + minh +"' mời vào trận" , Toast.LENGTH_SHORT).show();
                                moi.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mSocket.emit("vaoTran", 0);
                                    }
                                });
                                dangXuat.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mSocket.emit("tuChoi", 0);
                                        recreate();
                                    }
                                });
                            }
                        }
                        else
                        {
                            if(tenDangNhap == nhan) {
                                mSocket.emit("traLoi", ketqua);
                            }
                        }


                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };


    private Emitter.Listener onMessage_ketquaVaoTran = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    int ban;
                    int minh;
                    try {
                        ban = data.getInt("tenban");
                        minh = data.getInt("tenminh");
                        dangMoi = false;
                        if(tenDangNhap == minh)
                        {
                            tenDangNhap = 0;
                            DataGameMulti.getDataGameMulti().restart();
                            ban = data.getInt("tenban");
                            minh = data.getInt("tenminh");
                            mSocket.emit("thoatHangCho", minh);
                            mSocket.emit("danhsachCho", 0);
                            Intent intent = new Intent(KetNoi.this, Multi.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("tenMinh", minh);
                            bundle.putInt("tenBan", ban);
                            intent.putExtra("guiten", bundle);
                            startActivity(intent);
                            overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                        }
                        else if(tenDangNhap == ban)
                        {
                            tenDangNhap = 0;
                            DataGameMulti.getDataGameMulti().restart();
                            ban = data.getInt("tenban");
                            minh = data.getInt("tenminh");
                            mSocket.emit("thoatHangCho", ban);
                            mSocket.emit("danhsachCho", 0);
                            Intent intent = new Intent(KetNoi.this, Multi.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("tenMinh", ban);
                            bundle.putInt("tenBan", minh);
                            intent.putExtra("guiten", bundle);
                            startActivity(intent);
                            overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                        }
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };

    private Emitter.Listener onMessage_guiTuChoi = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    int ban;
                    int minh;
                    try {
                        ban = data.getInt("tenban");
                        minh = data.getInt("tenminh");
                        dangMoi = false;
                        if(tenDangNhap == minh)
                        {
                            Toast.makeText(KetNoi.this, "'" + ban + "' đã từ chối lời mời của bạn", Toast.LENGTH_SHORT).show();
                            mSocket.emit("xoaBanMinh", 0);
                        }
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };
    private Emitter.Listener onMessage_traloi = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    int ketqua;
                    int ten;
                    try {
                        ketqua = data.getInt("kq");
                        ten = data.getInt("tenTraLoi");
                        if(tenDangNhap == ten && tenDangNhap !=0) {
                            if (ketqua == 0) {
                                Toast.makeText(KetNoi.this, "Tài khoản đó đang offline hoặc không tồn tại", Toast.LENGTH_SHORT).show();
                            } else if (ketqua == 1) {
                                Toast.makeText(KetNoi.this, "Tài khoản đó là chính bạn đó", Toast.LENGTH_SHORT).show();
                            } else if (ketqua == 3) {
                                Toast.makeText(KetNoi.this, "Tài khoản đó đang chơi", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };

    private Emitter.Listener onMessage_danhsachcho = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    JSONArray a;
                    try {
                        a = data.getJSONArray("danhsach");
                        danhsach.clear();
                        for(int i = 0;i < a.length();i++)
                        {
                            danhsach.add(a.getInt(i));
                        }
                        ArrayAdapter adapter = new ArrayAdapter(KetNoi.this, android.R.layout.simple_list_item_1, danhsach);
                        myList.setAdapter(adapter);
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_enter_reverse,R.anim.anim_exit_reverse);
    }

    @Override
    protected void onStop() {
        mSocket.emit("thoatHangCho", tenDangNhap);
        mSocket.emit("danhsachCho", 0);
        tenDangNhap = 0;
        super.onStop();
    }
}
=======
package dangkhoa.dmt.dapchau;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class KetNoi extends AppCompatActivity {

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
        setContentView(R.layout.activity_ket_noi);
        mSocket.on("ketquaMoiBan", onMessage_ketquaMoiBan);
        mSocket.on("vaotran", onMessage_ketquaVaoTran);
        mSocket.on("guiTuChoi", onMessage_guiTuChoi);
        mSocket.on("traloi", onMessage_traloi);
        mSocket.on("danhsachcho", onMessage_danhsachcho);
        friend = (EditText)findViewById(R.id.friendDangNhap);
        moi = (Button)findViewById(R.id.btnMoi);
        dangXuat = (Button)findViewById(R.id.btnDangXuat);
        myList = (ListView)findViewById(R.id.listPhongCho);

        Intent intent1 = getIntent();
        tenDangNhap = intent1.getIntExtra("tenDangNhap", 0);

        mSocket.emit("danhsachCho", 0);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                friend.setText("" + danhsach.get(position));
            }
        });

        moi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dangMoi == false) {
                    if (friend.getText().toString().length() == 0) {
                        Toast.makeText(KetNoi.this, "Bạn chưa nhập tên đối thủ", Toast.LENGTH_SHORT).show();
                    } else {
                        mSocket.emit("moiBan", friend.getText().toString());
                    }
                }
                else
                {
                    Toast.makeText(KetNoi.this, "Có người đang mời, vui lòng chờ một lát", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSocket.emit("thoatHangCho", tenDangNhap);
                mSocket.emit("danhsachCho", 0);
                tenDangNhap = 0;
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                overridePendingTransition(R.anim.anim_enter_reverse,R.anim.anim_exit_reverse);
            }
        });
    }

    private EditText friend;
    private Button moi;
    private Button dangXuat;
    private ListView myList;
    private int tenDangNhap;
    private boolean dangMoi = false;
    private ArrayList<Integer> danhsach = new ArrayList<Integer>();
    private Emitter.Listener onMessage_ketquaMoiBan = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    int ketqua;
                    int minh;
                    int ban;
                    int nhan;
                    try {
                        nhan = data.getInt("tenNguoiNhan");
                        minh = data.getInt("tenminh");
                        ban = data.getInt("tenban");
                        ketqua = data.getInt("kqmoi");
                        if(ketqua == 2)
                        {
                            dangMoi = true;
                            if(tenDangNhap == ban)
                            {
                                minh = data.getInt("tenminh");
                                ban = data.getInt("tenban");
                                moi.setText("Đồng ý");
                                dangXuat.setText("Từ chối");
                                Toast.makeText(KetNoi.this, "Bạn được '" + minh +"' mời vào trận" , Toast.LENGTH_SHORT).show();
                                moi.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mSocket.emit("vaoTran", 0);
                                    }
                                });
                                dangXuat.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mSocket.emit("tuChoi", 0);
                                        recreate();
                                    }
                                });
                            }
                        }
                        else
                        {
                            if(tenDangNhap == nhan) {
                                mSocket.emit("traLoi", ketqua);
                            }
                        }


                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };


    private Emitter.Listener onMessage_ketquaVaoTran = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    int ban;
                    int minh;
                    try {
                        ban = data.getInt("tenban");
                        minh = data.getInt("tenminh");
                        dangMoi = false;
                        if(tenDangNhap == minh)
                        {
                            tenDangNhap = 0;
                            DataGameMulti.getDataGameMulti().restart();
                            ban = data.getInt("tenban");
                            minh = data.getInt("tenminh");
                            mSocket.emit("thoatHangCho", minh);
                            mSocket.emit("danhsachCho", 0);
                            Intent intent = new Intent(KetNoi.this, Multi.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("tenMinh", minh);
                            bundle.putInt("tenBan", ban);
                            intent.putExtra("guiten", bundle);
                            startActivity(intent);
                            overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                        }
                        else if(tenDangNhap == ban)
                        {
                            tenDangNhap = 0;
                            DataGameMulti.getDataGameMulti().restart();
                            ban = data.getInt("tenban");
                            minh = data.getInt("tenminh");
                            mSocket.emit("thoatHangCho", ban);
                            mSocket.emit("danhsachCho", 0);
                            Intent intent = new Intent(KetNoi.this, Multi.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("tenMinh", ban);
                            bundle.putInt("tenBan", minh);
                            intent.putExtra("guiten", bundle);
                            startActivity(intent);
                            overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                        }
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };

    private Emitter.Listener onMessage_guiTuChoi = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    int ban;
                    int minh;
                    try {
                        ban = data.getInt("tenban");
                        minh = data.getInt("tenminh");
                        dangMoi = false;
                        if(tenDangNhap == minh)
                        {
                            Toast.makeText(KetNoi.this, "'" + ban + "' đã từ chối lời mời của bạn", Toast.LENGTH_SHORT).show();
                            mSocket.emit("xoaBanMinh", 0);
                        }
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };
    private Emitter.Listener onMessage_traloi = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    int ketqua;
                    int ten;
                    try {
                        ketqua = data.getInt("kq");
                        ten = data.getInt("tenTraLoi");
                        if(tenDangNhap == ten && tenDangNhap !=0) {
                            if (ketqua == 0) {
                                Toast.makeText(KetNoi.this, "Tài khoản đó đang offline hoặc không tồn tại", Toast.LENGTH_SHORT).show();
                            } else if (ketqua == 1) {
                                Toast.makeText(KetNoi.this, "Tài khoản đó là chính bạn đó", Toast.LENGTH_SHORT).show();
                            } else if (ketqua == 3) {
                                Toast.makeText(KetNoi.this, "Tài khoản đó đang chơi", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };

    private Emitter.Listener onMessage_danhsachcho = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    JSONArray a;
                    try {
                        a = data.getJSONArray("danhsach");
                        danhsach.clear();
                        for(int i = 0;i < a.length();i++)
                        {
                            danhsach.add(a.getInt(i));
                        }
                        ArrayAdapter adapter = new ArrayAdapter(KetNoi.this, android.R.layout.simple_list_item_1, danhsach);
                        myList.setAdapter(adapter);
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_enter_reverse,R.anim.anim_exit_reverse);
    }

    @Override
    protected void onStop() {
        mSocket.emit("thoatHangCho", tenDangNhap);
        mSocket.emit("danhsachCho", 0);
        tenDangNhap = 0;
        super.onStop();
    }
}
>>>>>>> 439ab5c2a805414b13d03806129e493fe28b65eb
