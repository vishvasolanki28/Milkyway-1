package com.example.milkyway.listener;

import com.example.milkyway.model.ButterModel;
import com.example.milkyway.model.MilkModel;

import java.util.List;

public interface IButterLoadListener {
    void onButterLoadSuccess(List<ButterModel> butterModelList);
    void onButterLoadFailed(String message);
}
