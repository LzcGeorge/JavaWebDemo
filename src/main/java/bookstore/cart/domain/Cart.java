package bookstore.cart.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private Map<String,CartItem> map = new LinkedHashMap<String,CartItem>();

    public double getCartTotal() {
        BigDecimal sum = new BigDecimal("0");
        for(CartItem cartItem : map.values()) {
            BigDecimal subTotal = new BigDecimal("" + cartItem.getSubtotal());
            sum = sum.add(subTotal);
        }
        return sum.doubleValue();
    }

    public void addCartItem(CartItem cartItem) {
        String bid = cartItem.getBook().getBid();
        if(map.containsKey(bid)) {
            CartItem newItem = map.get(bid);
            newItem.setCount(newItem.getCount() + cartItem.getCount());
            map.put(bid,newItem);
        } else {
            map.put(bid,cartItem);
        }
    }

    public void clearCartItem() {
        map.clear();
    }

    public void deleteCartItem(String bid) {
        map.remove(bid);
    }

    public Collection<CartItem> getCartItems() {
        return map.values();
    }
}
