package com.example.milkyway.listener;

import com.example.milkyway.model.MilkModel;

import java.util.List;

public interface IMilkLoadListener {
    void onMilkLoadSuccess(List<MilkModel> milkModelList);
    void onMilkLoadFailed(String message);

}
