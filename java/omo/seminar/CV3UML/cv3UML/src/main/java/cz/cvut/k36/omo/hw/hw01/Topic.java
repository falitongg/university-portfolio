package cz.cvut.k36.omo.hw.hw01;

import java.util.ArrayList;
import java.util.List;

public class Topic {

    private String name;
    private String description;
    private List<Article> articles = new ArrayList<>();

    public Topic(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addArticle(Article a) {
        articles.add(a);
    }

    public List<Article> getArticles() {
        return articles;
    }
    public String getName() {
        return name;
    }
}
