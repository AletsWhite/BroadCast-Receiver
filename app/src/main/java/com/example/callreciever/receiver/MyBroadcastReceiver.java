package com.example.callreciever.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

public class MyBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        String actionStr = intent.getAction();
        String numero = intent.getStringExtra("numberPhone");
        Log.d("MIBROADCASTB", actionStr);

        if (intent.getAction().equals(
                Telephony.Sms.Intents.SMS_RECEIVED_ACTION) && numero.equals("4171238872")){

            Bundle sms = intent.getExtras();

            Object[] pdus  = (Object[])sms.get("pdus");

            String mensaje = "Numero: ";
            for (Object message : pdus){
                SmsMessage men = SmsMessage.createFromPdu((byte[]) message);
                mensaje += men.getOriginatingAddress();
                mensaje += "\n"+ men.getMessageBody();
            }

            Log.d("MIBROADCASTB", mensaje);
            Toast.makeText(context, mensaje , Toast.LENGTH_SHORT).show();

        }
    }

}
