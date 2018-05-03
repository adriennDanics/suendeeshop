package dbtest;

import com.codecool.shop.config.loadConfigJDBC;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCartDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
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

    private int countRecords(String tableName){
        int numberOfRecords = 0;

        try(Connection con = getConnection()) {
            String query = "SELECT * FROM " + tableName +";";
            Statement statement = con.createStatement();
            ResultSet results = statement.executeQuery(query);

            while(results.next()){
                ++numberOfRecords;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numberOfRecords;
    }

    private int getMaxId(String tablename) {
        int id = 0;

        try(Connection con = getConnection()) {
            String query = "SELECT * FROM " + tablename + " WHERE id = (SELECT MAX(id) FROM "+ tablename +");";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                id = resultSet.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
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

    @Test
    public void testProductDaoJDBCAdd() {
        ProductDao testProduct = new ProductDaoJDBC();
        Product test = new Product("testName", 2, "USD", "JDBC", ProductCategoryDaoJDBC.getInstance().find(1), SupplierDaoJDBC.getInstance().find(1));

        testProduct.add(test);

        String nameTest = null;
        int default_price = 0;
        String currency = null;
        String descr = null;


        try(Connection con = getConnection()) {
            String query = "SELECT * FROM products WHERE id = (SELECT MAX(id) FROM products);";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                nameTest = resultSet.getString("name");
                default_price = resultSet.getInt("default_price");
                currency = resultSet.getString("currency");
                descr = resultSet.getString("description");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("testName", nameTest);
        Assert.assertEquals("USD", currency);
        Assert.assertEquals("JDBC", descr);
        Assert.assertEquals(2, default_price);

    }

    @Test
    public void testSupplierDaoJDBCAdd() {
        SupplierDao testSupplier = new SupplierDaoJDBC();
        Supplier test = new Supplier("testName", "test");

        testSupplier.add(test);

        String nameTest = null;
        String descr = null;


        try(Connection con = getConnection()) {
            String query = "SELECT * FROM suppliers WHERE id = (SELECT MAX(id) FROM suppliers);";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                nameTest = resultSet.getString("name");
                descr = resultSet.getString("description");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Assert.assertEquals("testName", nameTest);
        Assert.assertEquals("test", descr);
    }

    @Test
    public void testShoppingCartDaoJDBCAdd(){
        int recordsBefore = countRecords("shoppingcart");
        ShoppingCartDao test = ShoppingCartDaoJDBC.getInstance();

        test.add(ProductDaoJDBC.getInstance().find(getMaxId("products")));
        int recordsAfter = countRecords("shoppingcart");
        Assert.assertEquals(1, recordsAfter-recordsBefore);
    }

    @Test
    public void testShoppingCartDaoJDBCRemove(){
        ShoppingCartDao test = ShoppingCartDaoJDBC.getInstance();
        test.add(ProductDaoJDBC.getInstance().find(getMaxId("products")));
        int recordsBefore = countRecords("shoppingcart");

        test.remove(getMaxId("products"));
        int recordsAfter = countRecords("shoppingcart");
        Assert.assertEquals(1, recordsBefore-recordsAfter);
    }

    @Test
    public void testProductCategoryDaoJDBCRemove(){
        int recordsBefore = countRecords("productcategories");
        ProductCategoryDao test = ProductCategoryDaoJDBC.getInstance();

        test.remove(getMaxId("productcategories"));
        int recordsAfter = countRecords("productcategories");
        Assert.assertEquals(1, recordsBefore-recordsAfter);
    }

    @Test
    public void testSupplierDaoJDBCRemove(){
        int recordsBefore = countRecords("suppliers");
        SupplierDao test = SupplierDaoJDBC.getInstance();

        test.remove(getMaxId("suppliers"));
        int recordsAfter = countRecords("suppliers");
        Assert.assertEquals(1, recordsBefore-recordsAfter);
    }

    @Test
    public void testProductsDaoJDBCRemove(){
        int recordsBefore = countRecords("products");
        ProductDao test = ProductDaoJDBC.getInstance();

        test.remove(getMaxId("products"));
        int recordsAfter = countRecords("products");
        Assert.assertEquals(1, recordsBefore-recordsAfter);
    }

}
