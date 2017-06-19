package com.example.zhangdx14.nfc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

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
        String hpenId = getArguments().getString(ARG_HPEN_ID);    // get fragment argument
        if (hpenId != null) {
            hPen = HpenBox.get(getActivity()).getHpen(hpenId);      // get model object from singleton
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nfc, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView textViewId = (TextView) view.findViewById(R.id.id);
        TextView textViewExpDate = (TextView) view.findViewById(R.id.exp_date);
        TextView textViewInjDate = (TextView) view.findViewById(R.id.inj_date);
        TextView textViewNote = (TextView) view.findViewById(R.id.note);

        if (hPen != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh.mm");
            textViewId.setText(hPen.getId());
            if (hPen.getExpDate() != null) {
                textViewExpDate.setText(sdf.format(hPen.getExpDate()));
            }
            if (hPen.getInjDate() != null) {
                textViewInjDate.setText(sdf.format(hPen.getInjDate()));
            }
            textViewNote.setText(hPen.getNote());
        }
    }
}
