package Models;

public class News {
    public News(String heading, String body) {
        this.heading = heading;
        this.body = body;
    }

    public News(int newsId, String heading, String body) {
        this.newsId = newsId;
        this.heading = heading;
        this.body = body;
    }

    public int newsId;
    public String heading;
    public String body;
}
