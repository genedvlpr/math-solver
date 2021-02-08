package com.genedev.mathsolver.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by raajesharunachalam on 3/25/17.
 */

public class Pod {
    @SerializedName("title")
    private String title;

    @SerializedName("numsubpods")
    private int numSubpods;

    @SerializedName("subpods")
    private Subpod[] subpods;

    public String getTitle(){
        return title;
    }

    public int getNumSubpods(){
        return numSubpods;
    }

    public Subpod[] getSubpods(){
        return subpods;
    }
}
