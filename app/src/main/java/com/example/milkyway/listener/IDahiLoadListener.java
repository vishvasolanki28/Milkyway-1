package com.example.milkyway.listener;

import com.example.milkyway.model.DahiModel;
import com.example.milkyway.model.MilkModel;

import java.util.List;

public interface IDahiLoadListener {
    void onDahiLoadSuccess(List<DahiModel> dahiModelList);
    void onDahiLoadFailed(String message);
}
