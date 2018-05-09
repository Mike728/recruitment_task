package info.mike.task.service;

import info.mike.task.domain.CurrencyJson;
import info.mike.task.domain.Input;
import info.mike.task.domain.Rate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestServiceImplTest {

    @Autowired
    private RestService restService;

    private CurrencyJson exampleObject;

    private Input input;

    @Before
    public void setUp() throws Exception {
        exampleObject = new CurrencyJson();
        Rate rate = new Rate();
        rate.setAsk(4.8602);
        rate.setBid(4.764);
        rate.setEffectiveDate("2018-05-02");
        rate.setNo("085/C/NBP/2018");

        Rate rate2 = new Rate();
        rate2.setAsk(4.9142);
        rate2.setBid(4.8168);
        rate2.setEffectiveDate("2018-05-04");
        rate2.setNo("086/C/NBP/2018");

        ArrayList<Rate> rates = new ArrayList<>();
        rates.add(rate);
        rates.add(rate2);

        exampleObject.setCode("123");
        exampleObject.setCurrency("gbp");
        exampleObject.setTable("c");
        exampleObject.setRates(rates);

        input = new Input();
        input.setCurrency_name("gbp");
        input.setFrom(LocalDate.parse("2018-05-02"));
        input.setTo(LocalDate.parse("2018-05-04"));
    }

    @Test
    public void assertThatJsonIsProcessed() throws Exception {
        assertEquals(restService.getAverageBuyingRate(exampleObject), 4.7904, 0.0001);
        assertEquals(restService.getStandardDeviationOfSalesRates(exampleObject), 0.027, 0.001);
    }

    @Test
    public void assertThatJsonMayBeReceived() throws Exception {
        CurrencyJson receivedJson = restService.getObject(input);
        assertNotNull(receivedJson);
    }

}
