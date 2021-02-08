package com.genedev.mathsolver.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by raajesharunachalam on 3/25/17.
 */

public class QueryResult {
    @SerializedName("success")
    private boolean success;

    @SerializedName("numpods")
    private int numPods;

    @SerializedName("pods")
    private Pod[] pods;

    public boolean getSuccess(){
        return success;
    }

    public int getNumPods(){
        return numPods;
    }

    public Pod[] getPods(){
        return pods;
    }
}
