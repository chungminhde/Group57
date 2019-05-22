package dangkhoa.dmt.dapchau;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NhapTen_Challenge extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_ten__challenge);
        myNhapTenChallenge = this;
        Intent intent = getIntent();
        tong = intent.getIntExtra("thoigian2", 0);
        khaiBao();
        thucHien();

    }

    private static NhapTen_Challenge myNhapTenChallenge;
    public static NhapTen_Challenge getMyNhapTenChallenge()
    {
        return myNhapTenChallenge;
    }

    private int tong;
    private static int cheDo = 0;

    private TextView diem;

    private TextView thongBao;

    private EditText ten;

    private Button luu;

    public void khaiBao()
    {
        diem = (TextView)findViewById(R.id.yourScoreChallenge);
        thongBao = (TextView)findViewById(R.id.txvThongBaoChallenge);
        ten = (EditText)findViewById(R.id.yourHoTenChallenge);
        luu = (Button)findViewById(R.id.yourLuuChallenge);
    }

    public void thucHien() {
        diem.setText("Your Score: " + DataGameChallenge.getDatagameChallenge().getDiem());

        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ten.getText().toString().isEmpty()) {
                    if(ten.getText().length() > 16)
                    {
                        thongBao.setText("Không được nhập quá 16 ký tự");
                    }
                    else
                    {
                        for(int i = 0;i < ten.getText().length();i++)
                        {
                            if((ten.getText().charAt(i) >= 0 && ten.getText().charAt(i) < 32) || (ten.getText().charAt(i) > 32 && ten.getText().charAt(i) < 48) || (ten.getText().charAt(i) > 57 && ten.getText().charAt(i) < 65) || (ten.getText().charAt(i) > 90 && ten.getText().charAt(i) < 97) || (ten.getText().charAt(i) > 122 && ten.getText().charAt(i) <= 126))
                            {
                                cheDo = 1;
                                break;
                            }
                        }
                        if(cheDo == 1)
                        {
                            cheDo = 0;
                            thongBao.setText("Không được chứa ký tự đặc biệt");
                        }
                        else
                        {
                            thongBao.setText("");
                            MainActivity.getMyMain().khoaThoatRa();
                            if(tong == 10000)
                            {
                                HighScore_Challenge10.getHighScoreChallenge10().ketThuc();
                            }
                            else if(tong == 20000)
                            {
                                HighScore_Challenge20.getHighScoreChallenge20().ketThuc();
                            }
                            else if(tong == 30000)
                            {
                                HighScore_Challenge.getHighScoreChallenge().ketThuc();
                            }
                            startActivity(new Intent(NhapTen_Challenge.this, MainActivity.class));
                            overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
                        }
                    }

                }
                else {
                    thongBao.setText("Bạn bắt buộc phải nhập tên");
                }

            }
        });

    }

    public String getHoTen()
    {
        return ten.getText().toString();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_enter_reverse,R.anim.anim_exit_reverse);
    }
}
