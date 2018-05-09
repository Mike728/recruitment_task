package info.mike.task.service;

import info.mike.task.domain.CurrencyJson;
import info.mike.task.domain.Input;

public interface RestService {

    public CurrencyJson getObject(Input input);

    public double getStandardDeviationOfSalesRates(CurrencyJson currencyJson);

    public double getAverageBuyingRate(CurrencyJson currencyJson);


}
