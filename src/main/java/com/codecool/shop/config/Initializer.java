package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);
        Supplier asus = new Supplier("Asus", "Computers");
        supplierDataStore.add(asus);
        Supplier apple = new Supplier("Apple", "Phones");
        supplierDataStore.add(apple);
        Supplier samsung = new Supplier("Samsung", "Phones");
        supplierDataStore.add(samsung);
        Supplier dji = new Supplier("Dji", "Drones");
        supplierDataStore.add(dji);

        //setting up a new product category
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(tablet);
        ProductCategory laptop = new ProductCategory("Laptop", "Hardware", "A portable computer with a built in display");
        productCategoryDataStore.add(laptop);
        ProductCategory phone = new ProductCategory("Phone", "Hardware", "A mobile phone that performs many of the functions of a computer, typically having a touchscreen interface, Internet access");
        productCategoryDataStore.add(phone);
        ProductCategory drone = new ProductCategory("Drone", "Hardware", "An aircraft without a human pilot aboard");
        productCategoryDataStore.add(drone);

        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
        productDataStore.add(new Product("Asus gl553ve", 890, "USD", "Simple gamer laptop.", laptop, asus));
        productDataStore.add(new Product("Asus GX700", 3300, "USD", "Optionally watercooled Asus gamer laptop", laptop, asus));
        productDataStore.add(new Product("Apple iPhone X", 990, "USD", "Apples latest iPhone with a notch", phone, apple));
        productDataStore.add(new Product("iPhone 8", 790, "USD", "The all-new design of iPhone 8 features durable glass, front and back. More advanced cameras", phone, apple));
        productDataStore.add(new Product("Samsung Galaxy S9", 890, "USD", "Simple gamer laptop.", phone, samsung));
        productDataStore.add(new Product("Samsung Galaxy Note 8", 940, "USD", "Latest model of the Note Series from Samsung", phone, samsung));
        productDataStore.add(new Product("Dji Mavic", 450, "USD", "Small formfactor drone", drone, dji));
        productDataStore.add(new Product("Dji Spark", 390, "USD", "Smallest Dji drone", drone, dji));

    }
}
