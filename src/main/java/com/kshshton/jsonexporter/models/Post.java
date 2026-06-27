package com.kshshton.jsonexporter.models;

import java.util.List;
import java.util.Map;

public class Post {
    public int id;
    public String title;
    public String body;
    public List<String> tags;
    public Map<String, Integer> reactions;
    public int views;
    public int userId;

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", tags=" + tags +
                ", reactions=" + reactions +
                ", views=" + views +
                ", userId=" + userId +
                '}';
    }
}
