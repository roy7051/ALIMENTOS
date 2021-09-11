package com.example.alimentos;

public class datamodel {

  /*  int image;
    String header, desc;  */

    private String name;
    private String imageUrl;
    private String cityname;

    public datamodel(){

    }

    public datamodel(String cityname, String imageUrl ,String name) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.cityname = cityname;

    }

   /* public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
*/

    public String getName(){
        return name;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public String getCityname(){return cityname;}

    public void setCityname(){this.cityname = cityname;}

    public void setName(String name){
        this.name = name;
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }


}