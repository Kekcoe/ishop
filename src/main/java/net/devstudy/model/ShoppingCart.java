package net.devstudy.model;


import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShoppingCart {

    private List<ShoppingCartItem> listItem = new ArrayList<>();
    //количество единиц опеределенного вида товара
    private int count;

    public ShoppingCart() {
        int capacity = 20;
        int totalCount = 200;
    }

    void addProduct(int idProduct, int count) throws ValidationException {

    }

    void removeProduct(Integer idProduct, int count){

    }

    Collection<ShoppingCartItem> getItems(){
        return listItem;
    }

    int getTotalCount(){
        return 0;
    }
}
