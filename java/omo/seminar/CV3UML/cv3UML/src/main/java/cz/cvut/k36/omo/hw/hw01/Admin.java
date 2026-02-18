package cz.cvut.k36.omo.hw.hw01;

public class Admin extends Account{
    public Admin(String username, String password) {
        super(username, password);
    }

    public Topic createTopic(String name, String description) {
        return new Topic(name, description);
    }

    public Article createArticle(String name, String description) {
        return new Article(name, description, this);
    }
}
