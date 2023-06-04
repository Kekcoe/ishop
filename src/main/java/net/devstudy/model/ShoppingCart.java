package net.devstudy.model;


import net.devstudy.exceptions.ValidationException;
import net.devstudy.utils.Constants;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;

public class ShoppingCart implements Serializable {

    private static final Logger LOG = Logger.getLogger(ShoppingCart.class.getName());
    private static final long serialVersionUID = 1535770438453611801L;
    private Map<Integer, ShoppingCartItem> products = new HashMap<>();
    private int totalCount = 0;

    private List<ShoppingCartItem> listItem = new ArrayList<>();
    // количество одного вида товара
    //вместимость корзины
    private int capacity;

    final int LIMIT_CAPACITY = 20;
    final int LIMIT_TOTAL_COUNT = 200;

    public ShoppingCart() {
    }

    public void addProduct(int idProduct, int count) throws ValidationException {
        validateShoppingCartSize(idProduct);
        ShoppingCartItem shoppingCartItem = products.get(idProduct);

        if (shoppingCartItem == null) {
            validateProductCount(count);
            shoppingCartItem = new ShoppingCartItem(idProduct, count);
            products.put(idProduct, shoppingCartItem);
        } else {
            validateProductCount(count + shoppingCartItem.getCount());
            shoppingCartItem.setCount(shoppingCartItem.getCount() + count);
        }
        refreshStatistics();
    }

    public void removeProduct(Integer idProduct, int count) {
        ShoppingCartItem shoppingCartItem = products.get(idProduct);
        if (shoppingCartItem != null) {
            if (shoppingCartItem.getCount() > count) {
                shoppingCartItem.setCount(shoppingCartItem.getCount() - count);
            } else {
                products.remove(idProduct);
            }
            refreshStatistics();
        }
    }

    public Collection<ShoppingCartItem> getItems() {
        return products.values();
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getCapacity() {
        return capacity;
    }

    private void validateShoppingCartSize(int idProduct){
        if(products.size() > Constants.MAX_PRODUCTS_PER_SHOPPING_CART ||
                (products.size() == Constants.MAX_PRODUCTS_PER_SHOPPING_CART && !products.containsKey(idProduct))) {
            throw new ValidationException("Limit for ShoppingCart size reached: size="+products.size());
        }
    }

    private void validateProductCount(int count) {
        if(count > Constants.MAX_PRODUCT_COUNT_PER_SHOPPING_CART){
            throw new ValidationException("Limit for product count reached: count="+count);
        }
    }

    private void refreshStatistics() {
        totalCount = 0;
        for (ShoppingCartItem shoppingCartItem : getItems()) {
            totalCount += shoppingCartItem.getCount();
        }
    }

    @Override
    public String toString() {
        return String.format("ShoppingCart [products=%s, totalCount=%s]", products, totalCount);
    }

}
