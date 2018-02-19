/*
package info.androidhive.navigationdrawer.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import info.androidhive.navigationdrawer.R;

public class MenuActivity extends AppCompatActivity {

    Button history, nfc, credits, recharge;
    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        preferenceHelper = new PreferenceHelper(this);
        history = (Button) findViewById(R.id.history);
        nfc = (Button) findViewById(R.id.nfc);
        credits = (Button) findViewById(R.id.credits);
        recharge = (Button) findViewById(R.id.recharge);

        credits.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent intent = new Intent(MenuActivity.this,CreditsActivity.class);
                startActivity(intent);
            }
        });

        nfc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MenuActivity.this,NFCActivity.class);
                startActivity(intent);
            }
        });
    }
}
*/
