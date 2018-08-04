package com.example.lrmah.edxfortelusko;


public class data {
    String name,company,description,duration,link,imageUrl;

    data()
    {

    }

    data(String name,String company,String description,String duration,String link)
    {
        this.name=name;
        this.company=company;
        this.description=description;
        this.duration=duration;
        this.link=link;
    }

    data(String name,String company,String description,String duration,String link,String imageUrl)
    {
        this.name=name;
      this.company=company;
      this.description=description;
      this.duration=duration;
      this.link=link;
      this.imageUrl=imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public String getDescription() {
        return description;
    }

    public String getDuration() {
        return duration;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}