package info.mike.task.service;

import info.mike.task.domain.CurrencyJson;
import info.mike.task.domain.Input;
import info.mike.task.domain.Rate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestServiceImpl implements RestService {

    private String url;

    private double average_buying_rate;

    private double standard_deviation_of_sales_rates;

    private RestTemplate restTemplate;

    @Override
    public double getStandardDeviationOfSalesRates(CurrencyJson currencyJson) {
        List<Rate> dataList = currencyJson.getRates();

        return standardDeviation(dataList
            .stream()
            .map(v -> v.getAsk())
            .collect(Collectors
                .toList()));
    }

    @Override
    public double getAverageBuyingRate(CurrencyJson currencyJson) {
        List<Rate> dataList = currencyJson.getRates();

        return dataList
            .stream()
            .collect(Collectors
                .averagingDouble(m -> m.getBid()));
    }

    @Override
    public CurrencyJson getObject(Input input) {
        restTemplate = new RestTemplate();
        url = "http://api.nbp.pl/api/exchangerates/rates/c/"
            + input.getCurrency_name() + "/" + input.getFrom().toString() + "/" + input.getTo().toString() + "/?format=json";

        ResponseEntity<CurrencyJson> response = restTemplate.getForEntity(url, CurrencyJson.class);
        return response.getBody();
    }

    public double standardDeviation(List<Double> ask_list){
        double average = ask_list.stream().collect(Collectors.averagingDouble(a -> a));
        double temp = 0;

        for(double ask : ask_list) {
            temp += Math.pow((ask - average), 2);
        }

        return Math.sqrt((temp/ask_list.size()));
    }

}
