package com.genedev.mathsolver.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.AsyncTask;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by raajesharunachalam on 3/26/17.
 */

public class PicassoAsyncTask extends AsyncTask<ArrayList<Subpod>, Void, int[][]>{

    public Context context;
    public WolframAdapter adapter;

    public PicassoAsyncTask(Context context, WolframAdapter adapter){
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    protected int[][] doInBackground(ArrayList<Subpod>... params) {
        ArrayList<Subpod> subpods = params[0];
        int[][] targets = new int[subpods.size()][2];
        for(int i = 0; i < subpods.size(); i++){
            try {
                String currentURL = subpods.get(i).getImage().getSourceLink();
                Bitmap image = Picasso.with(context).load(currentURL).get();
                int width = image.getWidth();
                int height = image.getHeight();

                int targetWidth2 = width;

                if(width < 50) {
                    targetWidth2 = 75;
                } else if(width < 200){
                    targetWidth2 = 200;
                }

                int targetHeight2 = height;
                if(height < 100){
                    targetHeight2 = 100;
                }
                targets[i][0] = image.getWidth();
                targets[i][1] = image.getHeight();
            } catch(IOException networkError){
                networkError.printStackTrace();
                return null;
            }
        }
        return targets;
    }

    @Override
    protected void onPostExecute(int[][] targets) {
        adapter.setTargets(targets);
        adapter.notifyDataSetChanged();
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
}
