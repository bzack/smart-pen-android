package com.example.zhangdx14.nfc;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class NfcActivity extends AppCompatActivity {
    private static final String TAG = "NfcActivity";

    private NfcAdapter nfcAdapter;
    private TextView textViewInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        textViewInfo = (TextView) findViewById(R.id.info);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this,
                    "NFC NOT supported on this devices!",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (!nfcAdapter.isEnabled()) {
            textViewInfo.setText("NFC is disabled.");
        } else {
            textViewInfo.setText(R.string.nfc_info);
        }

        handleIntent(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(
                this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        // Notice that this is the same filter as in our manifest.
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndef.addDataType("text/plain");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            Log.d(TAG, "wrong MIME type");
            throw new RuntimeException("Check your mime type.");
        }
        IntentFilter[] intentFiltersArray = new IntentFilter[]{ndef,};
        String[][] techListsArray = new String[][]{new String[]{NfcF.class.getName()}};
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray);
    }

    @Override
    protected void onPause() {
        super.onPause();
        nfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        /**
         * This method gets called, when a new Intent gets associated with the current activity instance.
         * Instead of creating a new activity, onNewIntent will be called. For more information have a look
         * at the documentation.
         *
         * In our case this method gets called, when the user attaches a Tag to the device.
         */

        // onResume gets called after this to handle the intent
        super.onNewIntent(intent);

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages = intent
                    .getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMessages != null) {
                // Process the first message, first recode only.
                NdefRecord record = ((NdefMessage) rawMessages[0]).getRecords()[0];
                // expect plain text
                if (record.getTnf() == NdefRecord.TNF_WELL_KNOWN &&
                        Arrays.equals(record.getType(), NdefRecord.RTD_TEXT)) {
                    byte[] payload = record.getPayload();
                    textViewInfo.setText(getText(payload));
                } else {
                    Log.d(TAG, "NFC recode is not plain text");
                }
            }
        }
    }

    private String getText(byte[] payload) {
        String nfcText = "";

        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
        int langCodeLen = payload[0] & 63;

        try {
            nfcText = new String(payload, langCodeLen + 1, payload.length - langCodeLen - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.d(TAG, "text encoding error");
        }

        return nfcText;
    }
}
