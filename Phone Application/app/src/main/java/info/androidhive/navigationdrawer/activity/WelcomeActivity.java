package info.androidhive.navigationdrawer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import info.androidhive.navigationdrawer.R;

public class WelcomeActivity extends AppCompatActivity {

    private TextView tvname,tvhobby;
    private Button btnlogout,crdBtn;
    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        preferenceHelper = new PreferenceHelper(this);

        tvhobby = (TextView) findViewById(R.id.tvhobby);
        tvname = (TextView) findViewById(R.id.tvname);
        btnlogout = (Button) findViewById(R.id.btn);
        crdBtn = (Button) findViewById(R.id.crbtn);

        tvname.setText("Welcome "+preferenceHelper.getName()+"\n"+"Email:"+preferenceHelper.getEmail()+"\n"+preferenceHelper.getAddress()+preferenceHelper.getPhone()+"cnic:"+preferenceHelper.getCnic());
        tvhobby.setText("Your UID is "+preferenceHelper.getCnic());

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceHelper.putIsLogin(false);
                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        });

        crdBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(WelcomeActivity.this,CreditsActivity.class);
                startActivity(intent);
            }
        });

    }
}