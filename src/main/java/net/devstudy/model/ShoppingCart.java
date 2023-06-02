package net.devstudy.model;


import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

public class ShoppingCart {

    private static final Logger LOG = Logger.getLogger(ShoppingCart.class.getName());

    private List<ShoppingCartItem> listItem = new ArrayList<>();
    // количество одного вида товара
    private int totalCount;
    //вместимость корзины
    private int capacity;

    final int LIMIT_CAPACITY = 20;
    final int LIMIT_TOTAL_COUNT = 200;

    public ShoppingCart() {
    }

    void addProduct(int idProduct, int count) throws ValidationException {
        if (count > 20) {
            throw new ValidationException("Количество единиц для одного видав товара не может быть больше 20");
        }
        if ((totalCount + count) <= LIMIT_TOTAL_COUNT && (capacity + 1) <= LIMIT_CAPACITY) {
            for (ShoppingCartItem item : listItem) {
                if (item.getId() == idProduct) {
                    if (item.getQuantity() < 10) {
                        if ((item.getQuantity() + count) <= 10) {
                            item.setQuantity(item.getQuantity() + count);
                            totalCount = totalCount + count;
                            capacity = capacity + 1;
                            System.out.println("capacity" + capacity);
                            System.out.println("totalCount" + totalCount);
                        }
                    } else {
                        throw new ValidationException("Количество единиц для данного типа товара болье 10");
                    }
                } else {
                    ShoppingCartItem newItem = new ShoppingCartItem(idProduct, count);
                    listItem.add(newItem);
                }
            }
        } else {
            throw new ValidationException("Общее количество или количество вида товара больше установленного лимита");
        }
    }

    void removeProduct(Integer idProduct, int count) {
        if(count > 20){
            LOG.info("Количество удаляемого вида товара не может быть более 20");
        }

        if(listItem.get(idProduct).getId() == idProduct){
                if((totalCount - count) < 0){
                LOG.info("Укажите меньшее количество удаляемого вида товара");
            }
            ShoppingCartItem item = listItem.get(idProduct);
            item.setQuantity(item.getQuantity() - count);
            totalCount = totalCount - count;
            if(item.getQuantity()<=0){
                listItem.remove(listItem.get(idProduct));
            }

        }



    }

    Collection<ShoppingCartItem> getItems() {
        return listItem;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getCapacity() {
        return capacity;
    }

}
