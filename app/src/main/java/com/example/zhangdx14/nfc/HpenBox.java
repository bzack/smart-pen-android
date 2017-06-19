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
        Hpen hpen = new Hpen("abbv1234");
        hpen.setExpDate(new Date(1507766400));      // 10/12/2017 @ 12:00am (UTC)
        hpen.setInjDate(new Date(1497514320));      // 06/15/2017 @ 8:12am (UTC)
        hpen.setStatus("Red");
        hpen.setNote("Already injected");
        hpens.add(hpen);
        hpen = new Hpen("abbv3456");
        hpen.setExpDate(new Date(1507766400));      // 10/12/2017 @ 12:00am (UTC)
        hpens.add(hpen);
        hpen = new Hpen("abbv5678");
        hpen.setExpDate(new Date(1507766400));      // 10/12/2017 @ 12:00am (UTC)
        hpen.setDaysSinceRoomTemp(5);
        hpen.setStatus("Yellow");
        hpen.setNote("5 days since room temperature");
        hpens.add(hpen);
        hpen = new Hpen("abbvie7890");
        hpen.setExpDate(new Date(1507766400));      // 10/12/2017 @ 12:00am (UTC)
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
