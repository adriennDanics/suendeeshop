package daotest;

import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.dao.ProductCategoryDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

abstract class ProductCategoryDaoTest {

    static ProductCategoryDao dao = null;

    @BeforeAll
    public static void setup() {
    }

    @Test
    abstract public void testIsProductCategoryDaoSingleton();

    @Test
    abstract public void testIsGetAllWorking();

    @Test
    public void testIsAddAddingElements() {
        int daoSize = dao.getAll().size();
        dao.add(new ProductCategory("Material Goods", "Material Goods", "What you might want and cannot get anywhere else."));
        int newDaoSize = dao.getAll().size();
        assertEquals(daoSize + 1, newDaoSize);
    }

    @Test
    public void testIsFindWorking() {
        ProductCategory found = dao.find(1);
        assertEquals("Necessities", found.getName());
    }
}
