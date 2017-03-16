package com.github.itswisdomagain.developers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String location = getIntent().getExtras().getString("location");
        final String language = getIntent().getExtras().getString("language");

        if (language != null) {
            ((TextView)findViewById(R.id.tvMainTitle)).setText(language.concat(" developers").toUpperCase(Locale.ENGLISH));
        }
        if (location != null) {
            ((TextView)findViewById(R.id.tvSubTitle)).setText(location.toUpperCase(Locale.ENGLISH));
        }

        final RecyclerView recyclerView = (RecyclerView)findViewById(R.id.usersRecyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        final RecyclerViewItemClickListener userCardClicked = new RecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(int itemIndex, Object item) {
                GithubUser user = ((GithubUser)item);

                Intent developerInfo = new Intent(MainActivity.this, UserProfileActivity.class);
                developerInfo.putExtra("username", user.getUsername());
                developerInfo.putExtra("image", user.getImageUrl());
                developerInfo.putExtra("url", user.getProfileUrl());
                developerInfo.putExtra("location", location);
                developerInfo.putExtra("language", language);
                startActivity(developerInfo);
            }
        };

        //when the data is returned, the following method is called. that's where you display the results in the list
        GitubCaller.CallCompleted afterBackgroundAPICallIsCompleted = new GitubCaller.CallCompleted() {
            @Override
            public void DataReceived(List<GithubUser> githubUsers) {
                findViewById(R.id.loader).setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(new UsersRecyclerViewAdapter(MainActivity.this, githubUsers, userCardClicked));
            }

            @Override
            public void ErrorOccurred(String errorMessage) {
                //the API call was not successful
                //tell the user what the problem was and what the likely solution is
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();

                //you should at this point hide the progress indicator
                findViewById(R.id.loader).setVisibility(View.GONE);
            }
        };

        //now call the GithubCaller to fetch data using the above method as a continuation after the call
        GitubCaller.Start(afterBackgroundAPICallIsCompleted);
    }
}
