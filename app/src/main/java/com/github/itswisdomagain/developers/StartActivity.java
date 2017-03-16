package com.github.itswisdomagain.developers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void itemClicked(View item){
        switch (item.getId())
        {
            case R.id.btnJDL:
                Intent listDevelopers = new Intent(StartActivity.this, MainActivity.class);
                listDevelopers.putExtra("location", "lagos");
                listDevelopers.putExtra("language", "java");
                startActivity(listDevelopers);
                break;
        }
    }
}
