package info.mike.task.controller;

import info.mike.task.service.RestService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestClientException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MainControllerTest {

    @Mock
    RestService restService;

    MainController mainController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mainController = new MainController(restService);

        mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
    }

    @Test
    public void mainView() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("mainView"))
            .andExpect(model().attributeExists("input"));
    }

    @Test
    public void process() throws Exception {
        mockMvc.perform(post("/process"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/info"));
    }

    @Test
    public void infoView() throws Exception {
        when(restService.getAverageBuyingRate(any())).thenReturn(4.7432);
        when(restService.getStandardDeviationOfSalesRates(any())).thenReturn(0.0017);

        mockMvc.perform(get("/info"))
            .andExpect(status().isOk())
            .andExpect(view().name("infoView"))
            .andExpect(model().attributeExists("average"))
            .andExpect(model().attributeExists("std_dev"));
    }

    @Test
    public void handleNotFoundException() throws Exception {
        when(restService.getObject(any())).thenThrow(RestClientException.class);

        mockMvc.perform(get("/info"))
            .andExpect(status().isNotFound())
            .andExpect(view().name("error"));

    }

}
