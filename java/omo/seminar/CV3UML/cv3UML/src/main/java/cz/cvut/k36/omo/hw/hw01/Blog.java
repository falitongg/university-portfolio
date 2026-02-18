package cz.cvut.k36.omo.hw.hw01;

import java.util.ArrayList;
import java.util.List;

public class Blog {
    private List<Topic> topics = new ArrayList<>();
    private List<Article> articles = new ArrayList<>();

    public void addArticle(Article article, Topic topic) {
        articles.add(article);
        topic.addArticle(article);
    }
    public void addTopic(Topic topic) {
        topics.add(topic);
    }
    public List<Topic> getTopics() {
        return topics;
    }
    public List<Article> getArticles() {
        return articles;
    }
}
