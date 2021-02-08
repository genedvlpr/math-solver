package com.genedev.mathsolver.data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by raajesharunachalam on 3/25/17.
 */

public class WolframAlphaAsyncTask extends AsyncTask<String, Void, WolframResult> {
    WolframAdapter adapter;
    Context context;

    public WolframAlphaAsyncTask(WolframAdapter adapter, Context context) {
        this.adapter = adapter;
        this.context = context;
    }

    @Override
    protected WolframResult doInBackground(String... params) {
        String urlString = params[0];
        Log.e("URL?", urlString);
        try {
            URL url = new URL(urlString);
            InputStream inStream = url.openStream();
            InputStreamReader reader = new InputStreamReader(inStream, Charset.forName("UTF-8"));
            JsonReader jsonReader = new JsonReader(reader);
            Gson gson = new Gson();
            WolframResult solutionsList = gson.fromJson(jsonReader, WolframResult.class);
            return solutionsList;
        } catch (MalformedURLException badURL) {
            badURL.printStackTrace();
            throw new RuntimeException();
        } catch (IOException networkError) {
            networkError.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(WolframResult wolframResult) {
        super.onPostExecute(wolframResult);

        try{
            QueryResult queryResult = wolframResult.getResult();
            Pod[] pods = queryResult.getPods();
            adapter.setPods(pods);

            ArrayList<Subpod> newResults = new ArrayList<>();
            for (Pod pod : pods) {
                for (Subpod subpod : pod.getSubpods()) {
                    newResults.add(subpod);
                }
            }
            adapter.setResults(newResults);
            adapter.notifyDataSetChanged();

        } catch (Exception e) {
            Log.d("QueryResult Error", Objects.requireNonNull(e.getMessage()));
        }



    }
}
