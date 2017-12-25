package com.mycompany.restaurant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Gidro on 25.12.2017.
 */

public class NewsActivity extends Activity {

    public static void start(Context parentContext) {
        Intent intent = new Intent(parentContext, NewsActivity.class);
        parentContext.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);
    }
}