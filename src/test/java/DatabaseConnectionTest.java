
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DatabaseConnectionTest {

    private static SessionFactory sessionFactory;

    @BeforeAll
    public static void setup() {
        try {
            Configuration configuration = new Configuration();

            // Thêm cấu hình từ file properties
            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/bookworm_library");
            configuration.setProperty("hibernate.connection.username", "root");
            configuration.setProperty("hibernate.connection.password", "sonquenguyen");
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");

            // Tạo SessionFactory
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Test
    public void testDatabaseConnection() {
        assertNotNull(sessionFactory, "SessionFactory should not be null");

        try (Session session = sessionFactory.openSession()) {
            assertTrue(session.isConnected(), "Database connection should be established");

            // Thực hiện một truy vấn đơn giản để kiểm tra kết nối
            Object result = session.createNativeQuery("SELECT 1").uniqueResult();
            assertNotNull(result, "Query should return a result");
            assertEquals(1, ((Number) result).intValue(), "Query should return 1");
        } catch (Exception e) {
            fail("Exception occurred while testing database connection: " + e.getMessage());
        }
    }

    @AfterAll
    public static void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}