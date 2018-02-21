package com.hfad.starbuzz;

/**
 * Created by spacerc on 04.01.2018.
 */

public class Food {
    private String name;
    private String description;
    private int imageResourceId;

    public static final Food[] foods = {
            new Food("Burger", "tasty burger", R.drawable.burger),
            new Food("Fri", "crisby popato fri in fryer", R.drawable.fri),
            new Food("Shaverma", "cool shaverma grill in our souse", R.drawable.shava)

    };
    private Food(String name, String description, int imageResourceId){
        this.name = name;
        this.description = description;
        this.imageResourceId =imageResourceId;
    }
    public String getDescription(){
        return description;
    }
    public String getName(){
        return name;
    }
    public int getImageResourceId(){
        return imageResourceId;
    }

    public String toString(){
        return this.name;
    }
}
