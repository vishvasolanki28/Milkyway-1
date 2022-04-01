package com.example.milkyway.listener;

import com.example.milkyway.model.ButtermilkModel;
import com.example.milkyway.model.MilkModel;

import java.util.List;

public interface IButtermilkLoadListener {
    void onButtermilkLoadSuccess(List<ButtermilkModel> buttermilkModelList);
    void onButtermilkLoadFailed(String message);
}
