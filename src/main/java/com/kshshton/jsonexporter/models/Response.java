package com.kshshton.jsonexporter.models;

import java.util.List;

public class Response {
    public List<Post> posts;
    public int total;
    public int skip;
    public int limit;

    @Override
    public String toString() {
        return "Response{" +
                "posts=" + posts +
                ", total=" + total +
                ", skip=" + skip +
                ", limit=" + limit +
                "}";
    }
}
