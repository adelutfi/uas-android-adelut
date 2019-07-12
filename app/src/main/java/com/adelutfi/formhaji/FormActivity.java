package com.adelutfi.formhaji;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.adelutfi.formhaji.models.Peserta;
import com.adelutfi.formhaji.networks.BaseListResponse;
import com.adelutfi.formhaji.networks.BaseResponse;
import com.adelutfi.formhaji.others.LoginActivity;
import com.adelutfi.formhaji.services.ApiServices;
import com.adelutfi.formhaji.services.ApiUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormActivity extends AppCompatActivity {
    private EditText et_name, et_address, et_hp;
    private Spinner jk;
    private Button btn_simpan, btn_tampil;
    private ApiServices apiService = ApiUtils.Companion.getApiService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        isLoggedIn();
        inisialisasi();
        btn_simpan.setOnClickListener(v -> {
            String name = et_name.getText().toString().trim();
            String address = et_address.getText().toString().trim();
            String phone = et_hp.getText().toString().trim();
            String gender = jk.getSelectedItem().toString().trim();
            if(!name.isEmpty() && !address.isEmpty() && !phone.isEmpty()){
                Call req = apiService.insert("Bearer "+getToken(), name, phone, address, gender);
                req.enqueue(new Callback< BaseResponse< Peserta>>(){

                    @Override
                    public void onResponse(Call<BaseResponse<Peserta>> call, Response<BaseResponse<Peserta>> response) {
                        if(response.isSuccessful()){
                            BaseResponse<Peserta> body =  response.body();
                            if(body != null && body.getStatus()){
                                Toast.makeText(FormActivity.this, "Berhasil disimpan", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(FormActivity.this, "Gagal menyimpan", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(FormActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Peserta>> call, Throwable t) {
                        Toast.makeText(FormActivity.this, "Tidak dapat terhubung ke server", Toast.LENGTH_SHORT).show();

                    }
                });
            }else{
                Toast.makeText(FormActivity.this, "Isi semua form", Toast.LENGTH_SHORT).show();
            }
        });
        btn_tampil.setOnClickListener(v -> {
            startActivity(new Intent(FormActivity.this, ShowActivity.class));
        });
    }

    private void inisialisasi(){
        et_name = findViewById(R.id.et_name);
        et_address = findViewById(R.id.et_address);
        et_hp = findViewById(R.id.et_phone);
        jk = findViewById(R.id.sp_jk);
        btn_simpan = findViewById(R.id.btn_save);
        btn_tampil = findViewById(R.id.btn_show);
    }

    private String getToken(){
        SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        String token = sharedPreferences.getString("API_TOKEN", "UNDEFINED");
        return token;
    }

    private void isLoggedIn() {
        SharedPreferences settings = getSharedPreferences("USER", MODE_PRIVATE);
        String token = settings.getString("API_TOKEN", "UNDEFINED");
        if (token == null || token == "UNDEFINED") {
            startActivity(new Intent(FormActivity.this, LoginActivity.class));
            finish();
        }
    }
}
