package daotest;

import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.dao.ProductCategoryDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductCategoryMemTestSet extends ProductCategoryDaoTest {

    @BeforeAll
    public static void setup() {
        dao = ProductCategoryDaoMem.getInstance();
        ProductCategory tablet = new ProductCategory("Necessities", "Abstract", "Everything you need to live your life but nothing material.");
        dao.add(tablet);
        ProductCategory laptop = new ProductCategory("Power-ups", "Abstract", "Everything you might need to live your life happily, mainly attitude.");
        dao.add(laptop);
    }

    @Override
    @Test
    public void testIsProductCategoryDaoSingleton() {
        List<ProductCategory> daoList = new ArrayList<>(dao.getAll());
        ProductCategoryDao fakeDao = ProductCategoryDaoMem.getInstance();
        List<ProductCategory> fakeList = new ArrayList<>(fakeDao.getAll());
        assertEquals(daoList, fakeList);
    }

    @Override
    @Test
    public void testIsGetAllWorking() {
        assertEquals(3, dao.getAll().size());
    }
}
