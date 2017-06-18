in manifest

<intent-filter>
    <action android:name="android.nfc.action.NDEF_DISCOVERED"/>
    <category android:name="android.intent.category.DEFAULT"/>
    <data android:mimeType="text/plain" />
</intent-filter>

the intent only triggers if in NFC tag, first message, first recode is plain text
INF = TNF_WELL_KNOWN
Type = RTD_TEXT

NFC tools can write multiple records to a NFC message

NFC NDEF can have multiple message, each message can have multiple records
// Process the messages array.
for (int i = 0; i < rawMessages.length; i++) {
    NdefRecord[] recs = ((NdefMessage) rawMessages[i]).getRecords();
    for (int j = 0; j < recs.length; j++) {
        if (recs[j].getTnf() == NdefRecord.TNF_WELL_KNOWN &&
                Arrays.equals(recs[j].getType(), NdefRecord.RTD_TEXT)) {
            byte[] payload = recs[j].getPayload();
            nfcText = ("\n\nNdefMessage[" + i + "], NdefRecord[" + j + "]:\n\"" +
                    getText(payload) + "\"");
        }
    }
}