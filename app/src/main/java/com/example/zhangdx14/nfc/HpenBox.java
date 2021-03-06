package com.example.zhangdx14.nfc;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * singleton
 * hold list of Hpen
 */

public class HpenBox {
    private static HpenBox hpenBox;
    private List<Hpen> hpens;

    public static HpenBox get(Context context) {
        if (hpenBox == null){
            hpenBox = new HpenBox(context);
        }
        return hpenBox;
    }

    private HpenBox(Context context) {
        hpens = new ArrayList<>();
        // hardcode
        Hpen hpen = new Hpen("abbv1356");
        hpen.setExpDate(new Date(1507766400L * 1000));      // 10/12/2017 @ 12:00am (UTC)
        hpens.add(hpen);        // green
        hpen = new Hpen("abbv1104");
        hpen.setExpDate(new Date(1507766400L * 1000));      // 10/12/2017 @ 12:00am (UTC)
        hpen.setDaysSinceRoomTemp(5);
        hpen.setStatus("Yellow");
        hpen.setNote("5 days since room temperature");
        hpens.add(hpen);
        hpen = new Hpen("abbv0928");
        hpen.setExpDate(new Date(1507766400L * 1000));      // 10/12/2017 @ 12:00am (UTC)
        hpen.setDaysSinceRoomTemp(15);
        hpen.setStatus("Red");
        hpen.setNote("15 days since room temperature");
        hpens.add(hpen);
    }

    public List<Hpen> getHpens() {
        return hpens;
    }

    public Hpen getHpen(String id) {
        for (Hpen hpen : hpens) {
            if (hpen.getId().equals(id)) {
                return hpen;
            }
        }

        return null;
    }
}
