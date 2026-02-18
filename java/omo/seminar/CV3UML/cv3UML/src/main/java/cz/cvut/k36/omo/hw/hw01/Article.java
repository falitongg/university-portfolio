package cz.cvut.k36.omo.hw.hw01;

import java.util.Date;

public class Article {

    private String title;
    private String content;
    private String author;
    private Enum state = Enum.PUBLIKOVANY;

    public Article(String title, String content, Admin admin) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public String getAuthor() {
        return author;
    }
    public Enum getState() {
        return state;
    }
    public void setState(Enum state) {
        this.state = state;
    }
}
