package com.kshshton.jsonexporter;

import java.sql.*;

public class Database {
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

    public void savePost(Main.Post post) throws SQLException {
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

//    void saveSamplePost() throws SQLException, IOException, InterruptedException {
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://dummyjson.com/posts"))
//                .GET()
//                .build();
//        Database db = new Database();
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        System.out.println(response.statusCode());
//
//        String content = response.body();
//        ObjectMapper mapper = new ObjectMapper();
//        Response data = mapper.readValue(content, Response.class);
//
//        for (Post post : data.posts) {
//            db.savePost(post);
//        }
//    }
}
