package com.example.milkyway.listener;

import com.example.milkyway.model.CartModel;
import com.example.milkyway.model.MilkModel;

import java.util.List;

public interface ICartLoadListener {
    void onCartLoadSuccess(List<CartModel> cartModelList);
    void onCartLoadFailed(String message);
}