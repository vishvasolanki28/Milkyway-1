package com.example.milkyway.listener;

import com.example.milkyway.model.CheeseModel;
import com.example.milkyway.model.MilkModel;

import java.util.List;

public interface ICheeseLoadListener {
    void onCheeseLoadSuccess(List<CheeseModel> cheeseModelList);
    void onCheeseLoadFailed(String message);
}
