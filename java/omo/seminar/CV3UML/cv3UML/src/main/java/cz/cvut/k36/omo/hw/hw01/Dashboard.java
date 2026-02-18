package cz.cvut.k36.omo.hw.hw01;

public class Dashboard {
    public void showArticles(Blog blog) {
        for (Article a : blog.getArticles()) {
            if (a.getState() == Enum.PUBLIKOVANY)
                System.out.println(a.getTitle());
        }
    }
    public void showArticlesByTopic(Topic topic) {
        for (Article a : topic.getArticles()) {
            if (a.getState() == Enum.PUBLIKOVANY)
                System.out.println(a.getTitle());
        }
    }
}
