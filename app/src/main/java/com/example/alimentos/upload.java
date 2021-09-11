package com.example.alimentos;

import org.jetbrains.annotations.NotNull;

public class upload {

     private String name;
     private String imageUrl;
     private String cityname;
     private String email;

    public upload(){

    }

    public upload( String name, String imageUrl , String cityname ,String email){
       /* if (name.trim().equals("")){
            name = "No Name";

        }  */
       this.name = name;
        this.imageUrl = imageUrl;
        this.cityname  = cityname;
        this.email= email;

    }

    public String getName(){
        return name;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public String getCityname(){return cityname;}

    public String getEmail(){return email;}

    public void setName(String name){
        this.name = name;
    }

    public void setCityname(String cityname){this.cityname = cityname;}

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

    private void setEmail(String email){this.email = email;}
}
