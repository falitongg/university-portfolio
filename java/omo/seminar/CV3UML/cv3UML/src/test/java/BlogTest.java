import cz.cvut.k36.omo.hw.hw01.*;
import cz.cvut.k36.omo.hw.hw01.Enum;
import org.junit.Test;
import static org.junit.Assert.*;

public class BlogTest {
    @Test
    public void testAccountCreationAndLogin() {
        User user = new User("john", "pass");
        assertTrue(user.login("pass"));
        assertFalse(user.login("wrong"));
        Admin admin = new Admin("admin", "root");
        assertTrue(admin.login("root"));
    }

    @Test
    public void testAdminCreatesTopicAndArticle() {
        Admin admin = new Admin("admin", "root");
        Topic t = admin.createTopic("IT", "Informační tech");
        Article a = admin.createArticle("Java", "OOP v Javě");
        assertEquals("IT", t.getName());
        t.addArticle(a);
        assertEquals(1, t.getArticles().size());
    }

    @Test
    public void testBlogAndDashboard() {
        Blog blog = new Blog();
        Admin admin = new Admin("admin", "root");
        Topic topic = admin.createTopic("JAVA", "programování");
        Article a1 = admin.createArticle("OOP", "text");
        Article a2 = admin.createArticle("Enum", "text2");
        a2.setState(Enum.NEAKTUALNI); // článek nebude vidět
        blog.addTopic(topic);
        blog.addArticle(a1, topic);
        blog.addArticle(a2, topic);

        Dashboard dash = new Dashboard();
        dash.showArticles(blog); // vidět bude jen "OOP"
        dash.showArticlesByTopic(topic); // také jen "OOP"
    }
}