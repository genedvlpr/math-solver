package com.genedev.mathsolver.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.genedev.mathsolver.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by raajesharunachalam on 3/25/17.
 */

public class WolframAdapter extends RecyclerView.Adapter<WolframAdapter.SolutionViewHolder>{
    Context context;
    Pod[] pods;
    ArrayList<Subpod> results;
    int[][] targets;


    public static final double NEXUS_DP_WIDTH = 360.0;
    public static final int NEXUS_IMAGE_WIDTH = 320;

    public static final double KINDLE_DP_WIDTH = 600.0;
    public static final int KINDLE_IMAGE_WIDTH = 400;


    public WolframAdapter(Context context, ArrayList<Subpod> subpods){
        this.context = context;
        this.results = subpods;
        targets = new int[0][0];
        pods = new Pod[0];
    }

    public ArrayList<Subpod> getResults(){
        return results;
    }

    public void setPods(Pod[] newPods){
        pods = newPods;
    }

    public void setResults(ArrayList<Subpod> newResults){
        this.results = newResults;
        PicassoAsyncTask imageTask = new PicassoAsyncTask(context, this);
        imageTask.execute(results);
    }

    public void setTargets(int[][] newTargets){
        targets = newTargets;
    }

    @Override
    public SolutionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        int movieItemLayout = R.layout.solution_list_item;
        boolean shouldAttachToParent = false;
        View view = inflater.inflate(movieItemLayout, parent, shouldAttachToParent);
        return new SolutionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SolutionViewHolder holder, int position) {
//        Subpod currentSubpod = results.get(position);
//        Image currentImage = currentSubpod.getImage();
//        String currentURL = currentImage.getSourceLink();
//
//
//        int targetWidth = 0;
//        int targetHeight = 0;
//        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
//        double dpWidth = displayMetrics.widthPixels / displayMetrics.density;
//        if(dpWidth == NEXUS_DP_WIDTH){
//            targetWidth = NEXUS_IMAGE_WIDTH;
//        } else if(dpWidth == KINDLE_DP_WIDTH){
//            targetWidth = KINDLE_IMAGE_WIDTH;
//        }
////        Picasso.with(context).load(currentURL).resize(200, 100).into(holder.solutionImage);
//
//        try {
//            Bitmap image = Picasso.with(context).load(currentURL).get();
//            int width = image.getWidth();
//            int height = image.getHeight();
//
//            int targetWidth2 = width;
//            if(width < 200){
//                targetWidth2 = 200;
//            }
//            int targetHeight2 = height;
//            if(height < 100){
//                targetHeight2 = 100;
//            }
//            Bitmap newBitMap = getResizedBitmap(image, targetWidth2, targetHeight2);
//        } catch(IOException networkError){
//            networkError.printStackTrace();
//            return;
//        }
        Subpod currentSubpod = results.get(position);
        if(pods.length > 0){
            for(Pod pod : pods){
                for(Subpod subpod : pod.getSubpods()){
                    if(subpod.equals(currentSubpod)){
                        holder.itemLabel.setText(pod.getTitle());
                        holder.plainText.setText(results.get(position).getPlainText());
                    }

                }
            }
        }

        if(targets.length > 0){

            String itemLabel = holder.itemLabel.getText().toString();
            if(itemLabel.equals("Result")
                    || itemLabel.equals("Input")
                    || itemLabel.equals("Number name")
                    || itemLabel.equals("Typical human computation times")
                    || itemLabel.equals("Geometric figure")
                    || itemLabel.equals("Properties as a function")
                    || itemLabel.equals("Properties as a real function")
                    || itemLabel.equals("Equation")
                    || itemLabel.equals("Input interpretation")
                    || itemLabel.equals("Alternate forms")
                    || itemLabel.equals("Alternate form")
                    || itemLabel.equals("Solution")
                    || itemLabel.equals("Continued fraction")
                    || itemLabel.equals("Total")
                    || itemLabel.equals("Midpoint")
                    || itemLabel.contains("Line through")
                    || itemLabel.contains("Exact result")
                    || itemLabel.contains("Decimal")
                    || itemLabel.contains("Unit conversions")
                    || itemLabel.contains("Interpretations")
                    || itemLabel.contains("Basic unit dimensions")){
                        holder.solutionImage.setVisibility(View.GONE);
                        holder.plainText.setVisibility(View.VISIBLE);
            } else {
                holder.solutionImage.setVisibility(View.VISIBLE);
                holder.plainText.setVisibility(View.GONE);
                String currentURL = results.get(position).getImage().getSourceLink();
                //Toast.makeText(context, results.get(0).getImage().getSourceLink(), Toast.LENGTH_LONG).show();
                Picasso.with(context).load(currentURL).into(holder.solutionImage);
            }
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class SolutionViewHolder extends RecyclerView.ViewHolder{
        public ImageView solutionImage;
        public TextView itemLabel, plainText;

        public SolutionViewHolder(View itemView) {
            super(itemView);
            solutionImage = (ImageView) itemView.findViewById(R.id.solution_image);
            itemLabel = (TextView) itemView.findViewById(R.id.item_label);
            plainText = (TextView) itemView.findViewById(R.id.item_plain_text);
            solutionImage.setVisibility(View.GONE);
        }
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
