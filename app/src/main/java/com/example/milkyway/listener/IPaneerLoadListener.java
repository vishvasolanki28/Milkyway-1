package com.example.milkyway.listener;

import com.example.milkyway.model.MilkModel;
import com.example.milkyway.model.PaneerModel;

import java.util.List;

public interface IPaneerLoadListener {
    void onPaneerLoadSuccess(List<PaneerModel> paneerModelList);
    void onPaneerLoadFailed(String message);
}
