package com.example.myshoppinglist.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myshoppinglist.Database.DataBase;
import com.example.myshoppinglist.Model.DataPojo;
import java.util.List;

public class HomeViewModel extends ViewModel {
    DataBase dataBase = new DataBase();

    public  MutableLiveData<List<DataPojo>> renderHomeView() {
    return dataBase.renderHomeView();
    }
    public MutableLiveData<String> getTotalAmount(){
        return dataBase.getTotalAmount();
    }
    public  void addToDataBase(DataPojo dataPojo){
        dataBase.addToDataBase(dataPojo);
    }
}
