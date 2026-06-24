package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

class Main {
    static class Response {
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

    static class Post {
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
                    ", views=" + views +
                    '}';
        }
    }


    void main() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://dummyjson.com/posts"))
                .GET()
                .build();
        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(response.statusCode());

        String content = response.body();
        ObjectMapper mapper = new ObjectMapper();

        try {
            Response data = mapper.readValue(content, Response.class);
            Post firstPost = data.posts.get(0);
            System.out.println(firstPost);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
