package com.example.callreciever;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.callreciever.receiver.MyBroadcastReceiver;

public class MainActivity extends AppCompatActivity {
    String numero = "";
    private myPhoneStateChangeListener phoneStateListener = new myPhoneStateChangeListener();
    MyBroadcastReceiver receiver;
    Button buttonLlamar;
    EditText editTextNumero, editTextMensaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        iFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        iFilter.addAction("android.intent.action.NEW_OUTGOING_CALL");

        receiver = new MyBroadcastReceiver();
        registerReceiver(receiver, iFilter);

        buttonLlamar = findViewById(R.id.btnLlamar);
        editTextMensaje = findViewById(R.id.txtMen);
        editTextNumero = findViewById(R.id.txtNumero);



        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

        buttonLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llamar();
            }
        });

    }

    private void llamar(){
        SmsManager smsManager =
                SmsManager.getDefault();

        smsManager.sendTextMessage(
                editTextNumero.getText().toString(),
                null, editTextMensaje.getText().toString(),
                null,null);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.NEW_OUTGOING_CALL");
        intent.putExtra("numberPhone",numero);
        sendBroadcast(intent);
    }

    public class myPhoneStateChangeListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            if (state == TelephonyManager.CALL_STATE_RINGING) {
                String phoneNumber =   incomingNumber;
                Toast.makeText(MainActivity.this, phoneNumber, Toast.LENGTH_SHORT).show();
                numero = phoneNumber;
            }
        }
    }

}