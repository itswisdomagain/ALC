package com.github.itswisdomagain.developers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {
    String url;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        String location = getIntent().getExtras().getString("location");
        String language = getIntent().getExtras().getString("language");
        String image = getIntent().getExtras().getString("image");
        username = getIntent().getExtras().getString("username");
        url = getIntent().getExtras().getString("url");

        SpannableString content = new SpannableString(url);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        if (language != null && location != null) {
            ((TextView)findViewById(R.id.tvSummary)).
                    setText(language.concat(" developer, ").concat(location).toUpperCase(Locale.ENGLISH));
        }

        ((TextView)findViewById(R.id.tvUsername)).setText(username);
        ((TextView)findViewById(R.id.tvUrl)).setText(content);
        Picasso.with(UserProfileActivity.this).load(image).noFade().into((CircleImageView)findViewById(R.id.profile_image));
    }

    public void shareButtonClicked(View v)
    {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, String.format(Locale.ENGLISH,
                "Check out this awesome developer %s, %s.", username, url));
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public void profileUrlClicked(View v)
    {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
