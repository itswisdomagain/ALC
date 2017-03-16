package com.github.itswisdomagain.developers;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class GitubCaller {
    public interface CallCompleted
    {
        void DataReceived(List<GithubUser> githubUsers);
        void ErrorOccurred(String errorMessage);
    }

    public static void Start(CallCompleted callCompleted)
    {
        Start("lagos", "java", callCompleted);
    }

    public static void Start(String location, String language, CallCompleted callCompleted)
    {
        new BackgroundLoader(callCompleted).execute(location, language);
    }

    private static class BackgroundLoader extends AsyncTask<String, Void, ArrayList<GithubUser>>
    {
        private CallCompleted callCompleted;

        private String errorMessage;

        BackgroundLoader(CallCompleted callCompleted) {
            this.callCompleted = callCompleted;
        }

        @Override
        protected ArrayList<GithubUser> doInBackground(String... inParams) {
            String location = inParams[0];
            String language = inParams[1];

            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet("https://api.github.com/search/users?q=location:" + location +
                "&language=" + language);

            try {
                HttpResponse response = httpclient.execute(httpget);
                HttpEntity entity = response.getEntity();

                if (entity != null)
                {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
                    StringBuilder sb = new StringBuilder();
                    String line;

                    try {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line).append("\n");
                        }
                    } catch (IOException e) {
                        errorMessage = e.getLocalizedMessage();
                    }

                    JSONObject jsonResponse = new JSONObject(sb.toString());
                    JSONArray users = jsonResponse.getJSONArray("items");

                    ArrayList<GithubUser> githubUsers = new ArrayList<>();

                    for (int i = 0; i < users.length(); i++) {
                        JSONObject jsonobject = users.getJSONObject(i);

                        String username = jsonobject.getString("login");
                        String imageUrl = jsonobject.getString("avatar_url");
                        String profileUrl = jsonobject.getString("html_url");

                        GithubUser user = new GithubUser(username, imageUrl, profileUrl);
                        githubUsers.add(user);
                    }

                    return githubUsers;
                }

            } catch (Exception e)
            {
                errorMessage = e.getLocalizedMessage();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<GithubUser> githubUsers) {
            if (errorMessage != null && !errorMessage.equals("")) {
                callCompleted.ErrorOccurred(errorMessage);
            }
            else {
                callCompleted.DataReceived(githubUsers);
            }
        }
    }

}
