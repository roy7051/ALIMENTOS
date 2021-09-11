package com.example.alimentos;

public class orders {

    String itemname;
    String email;

    public orders(){

    }

    public orders(String itemname,String email){
        this.itemname = itemname;
        this.email = email;
    }

    public String getItemname(){
        return itemname;
    }

    public String getEmail(){return email;}

    public void setItemname(String itemname){this.itemname = itemname;}

    public void setEmail(String email){this.email = email;}


}
