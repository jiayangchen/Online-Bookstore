package com.heitian.ssm.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by ChenJiayang on 2017/3/17.
 */
public class CartQuantityList implements Serializable {
    private Map<Long, Integer> cartItem;

    public Map<Long, Integer> getCartItem() {
        return cartItem;
    }

    public void setCartItem(Map<Long, Integer> cartItem) {
        this.cartItem = cartItem;
    }

    public Integer fetchWithDefault(Long bId, Integer defaultVal) {
        if (cartItem == null || !cartItem.containsKey(bId)) {
            return defaultVal;
        }

        return cartItem.get(bId);
    }
}
