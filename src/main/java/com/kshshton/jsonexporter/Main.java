package com.kshshton.jsonexporter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;
import java.util.List;
import java.util.Map;

public class Main {
    public static class Response {
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

    public static class Post {
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

    public static class Database {
        private final Connection conn;

        public Database() throws SQLException {
            this("jdbc:sqlite:app.db");
        }

        public Database(String url) throws  SQLException {
            this.conn = DriverManager.getConnection(url);

            createTable();
        }

        private void createTable() throws SQLException {
            String sql = """
                CREATE TABLE IF NOT EXISTS posts (
                    id INTEGER PRIMARY KEY,
                    title TEXT NOT NULL,
                    tags TEXT,
                    views INTEGER,
                    userId INTEGER
                );
                """;

            try (Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            }
        }

        public void savePost(Post post) throws SQLException {
            String sql = """
                INSERT INTO posts (id, title, tags, views, userId)
                VALUES (?, ?, ?, ?, ?);
                """;

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, post.id);
                stmt.setString(2, post.title);
                stmt.setString(3, post.tags.toString());
                stmt.setInt(4, post.views);
                stmt.setInt(5, post.userId);
                stmt.executeUpdate();
            }
        }
    }

    void main() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://dummyjson.com/posts"))
                .GET()
                .build();
        HttpResponse<String> response = null;
        Database db = null;

        try {
            db = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
            for (Post post : data.posts) {
                db.savePost(post);
            }
        } catch (JsonProcessingException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
