package dbtest;

import com.codecool.shop.config.loadConfigJDBC;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoJDBC;
import com.codecool.shop.model.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import com.codecool.shop.*;

import java.sql.*;
import java.util.Properties;

public class DBTest {

    private static Properties properties = loadConfigJDBC.getProperties();
    private static final String DATABASE = "jdbc:postgresql://" + properties.getProperty("url") + "/" + properties.getProperty("database");
    private static final String USER = properties.getProperty("user");
    private static final String PASSWORD = properties.getProperty("password");

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                USER,
                PASSWORD
        );
    }

    @Test
    public void testProductCategoryDaoJDBCAdd() {
        ProductCategoryDao testProductCategory = new ProductCategoryDaoJDBC();
        ProductCategory test = new ProductCategory("testName", "testDPT", "testDescr");

        testProductCategory.add(test);
        String nameTest = null;
        String dept = null;
        String descr = null;

        try(Connection con = getConnection()) {
            String query = "SELECT * FROM productcategories WHERE id = (SELECT MAX(id) FROM productcategories);";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                nameTest = resultSet.getString("name");
                dept = resultSet.getString("department");
                descr = resultSet.getString("description");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("testName", nameTest);
        Assert.assertEquals("testDPT", dept);
        Assert.assertEquals("testDescr", descr);



    }


}
