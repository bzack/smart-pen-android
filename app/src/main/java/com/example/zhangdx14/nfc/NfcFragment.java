package com.example.zhangdx14.nfc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        TextView textViewInfo = (TextView) view.findViewById(R.id.info);
        if (hPen == null) {
            textViewInfo.setText("");
        } else {
            textViewInfo.setText(hPen.getId());
            // todo: set other view objects
        }
    }
}
