package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.ShoppingCart;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.ShoppingCartMem;
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
        Supplier nature = new Supplier("Nature", "Digital content and services");
        supplierDataStore.add(nature);
        Supplier higherPower = new Supplier("Higher Power", "Computers");
        supplierDataStore.add(higherPower);
        Supplier innerVoice = new Supplier("Inner Voice", "Computers");
        supplierDataStore.add(innerVoice);
        Supplier fate = new Supplier("Fate", "Phones");
        supplierDataStore.add(fate);

        //setting up a new product category
        ProductCategory necessities = new ProductCategory("Necessities", "Abstract", "A necessities computer, commonly shortened to necessities, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(necessities);
        ProductCategory powerups = new ProductCategory("Power-ups", "Abstract", "A portable computer with a built in display");
        productCategoryDataStore.add(powerups);
        ProductCategory materialGoods = new ProductCategory("Material Goods", "Material Goods", "A mobile materialGoods that performs many of the functions of a computer, typically having a touchscreen interface, Internet access");
        productCategoryDataStore.add(materialGoods);
        ProductCategory priceless = new ProductCategory("Priceless", "Abstract", "An aircraft without a human pilot aboard");
        productCategoryDataStore.add(priceless);

        //setting up products and printing it
        productDataStore.add(new Product("Eternal Life", Integer.MAX_VALUE, "USD", "Fantastic price. You will be able to watch all your enemies die (your friends too)! Lifetime guarantee!", priceless, higherPower));
        productDataStore.add(new Product("Will To Live", 10000, "USD", "Tired of living? Buy today and you will learn to love your life immediately. Terms and conditions apply.", powerups, innerVoice));
        productDataStore.add(new Product("Sense Of Humour", 20003.9f, "USD", "If you think you already have it, buy NOW. Political correctness sold separately.", necessities, nature));
        productDataStore.add(new Product("Motivation", 100000, "USD", "Couldn't be bothered to come up with a proper description so here you go.", powerups, innerVoice));
        productDataStore.add(new Product("True Love", 69, "USD", "We think it's a myth but we also need to pay bills. It comes cheap, apparently.", priceless, fate));
        productDataStore.add(new Product("Two-tailed Dog", 0.5f, "USD", "Sweet, cute, genetically modified best friend for sale. NOT PC!!!", materialGoods, nature));
        productDataStore.add(new Product("Free Beer", 0, "USD", "Order a lifetime supply of (NOT craft) beer of suitably low quality.", materialGoods, nature));
        productDataStore.add(new Product("Beauty", 210000, "USD", "Solve all of your problems at once. Personality not included but true love is free.", powerups, nature));
        productDataStore.add(new Product("Basic Intelligence", 0.99f, "USD", "Tired of your IQ being under room temperature? Upgrade it now to triple digits!", necessities, nature));
        productDataStore.add(new Product("Intelligence Raise", 1000, "USD", "Tired of travelling through space-time linearly? Invent something to change that, using this power-up!", powerups, higherPower));
        productDataStore.add(new Product("Schizophrenia", 666, "USD", "Buy as a gift to anyone you hate. Optionally, get it for yourself, in case you're feeling lonely.", powerups, innerVoice));
        productDataStore.add(new Product("Good Luck", 777, "USD", "Buy a patch of good luck you can use five times to make everything go your way.", powerups, fate));
        productDataStore.add(new Product("Bad Luck", 4444, "USD", "Buy a patch of bad luck you can use five times to make everything go wrong for somebody deserving.", priceless, fate));
        productDataStore.add(new Product("Conscience", 0, "USD", "It can get in the way of copious amounts of debauchery and general fun. Get it for your enemies!", necessities, nature));
        productDataStore.add(new Product("72 Virgins", 72000, "USD", "Afraid of death? Don't know how to make a bomb? Let us make it easy for you at a bargain price.", powerups, higherPower));

    }
}
