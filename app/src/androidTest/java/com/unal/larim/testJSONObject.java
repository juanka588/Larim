package com.unal.larim;

import android.test.AndroidTestCase;

import com.unal.larim.Services.JSONParser;

/**
 * Created by JuanCamilo on 08/10/2015.
 */
public class TestJSONObject extends AndroidTestCase {
    public void testJSONObject() {
        JSONParser parser = new JSONParser(getContext());
        parser.parseJSON();
        assertNotNull("JSONParser instanced", parser);
    }
}
