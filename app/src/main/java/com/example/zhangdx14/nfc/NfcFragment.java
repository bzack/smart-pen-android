package com.example.zhangdx14.nfc;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by zhangdx14 on 6/18/2017.
 */

public class NfcFragment extends Fragment {
    private static final String ARG_HPEN_ID = "hpen_id";

    private Hpen hPen;

    public static NfcFragment newInstance(String hpenId) {
        Bundle args = new Bundle();
        args.putString(ARG_HPEN_ID, hpenId);      // use fragment argument

        NfcFragment fragment = new NfcFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        String hpenId = getArguments().getString(ARG_HPEN_ID);    // get fragment argument
        if (hpenId != null) {
            hPen = HpenBox.get(getActivity()).getHpen(hpenId);      // get model object from singleton
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nfc, container, false);

        Button uploadButton = (Button) view.findViewById(R.id.upload);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UploadTask().execute();     // background job to upload data
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView textViewId = (TextView) view.findViewById(R.id.id);
        ImageView statusImgView = (ImageView) view.findViewById(R.id.status_img);
        TextView textViewNote = (TextView) view.findViewById(R.id.note);
        Button statusButton = (Button) view.findViewById(R.id.status);
        Button daysButton = (Button) view.findViewById(R.id.days);

        if (hPen != null) {
            textViewId.setText(hPen.getId().toUpperCase());
            textViewNote.setText(hPen.getNote());
            switch (hPen.getStatus().toUpperCase()){
                case "GREEN":
                    statusImgView.setImageResource(R.drawable.green);
                    statusButton.setText("READY FOR USE");
                    statusButton.setBackgroundResource(R.drawable.button_green);
                    daysButton.setVisibility(View.GONE);
                    break;
                case "YELLOW":
                    statusImgView.setImageResource(R.drawable.yellow);
                    statusButton.setText("READY FOR USE");
                    statusButton.setBackgroundResource(R.drawable.button_yellow);
                    int daysRemain = 14 - hPen.getDaysSinceRoomTemp();
                    daysButton.setText(daysRemain + " DAYS REMAINING");
                    daysButton.setBackgroundResource(R.drawable.button_blue);
                    break;
                case "RED":
                    statusImgView.setImageResource(R.drawable.red);
                    statusButton.setText("DO NOT USE");
                    statusButton.setBackgroundResource(R.drawable.button_red);
                    daysButton.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_nfc, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_home:
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * asynctask to upload data through rest api
     */
    private class UploadTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Loading...");
            progressDialog.setMessage("Please wait.");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            new Uploader().upload(hPen);
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            if (progressDialog != null) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                progressDialog = null;
            }
        }
    }
}
