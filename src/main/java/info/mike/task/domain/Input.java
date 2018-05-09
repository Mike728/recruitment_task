package info.mike.task.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Input {

    private String currency_name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate from;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate to;

    public String getCurrency_name() {
        return currency_name;
    }

    public void setCurrency_name(String currency_name) {
        this.currency_name = currency_name;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "Input{" +
            "currency_name='" + currency_name + '\'' +
            ", from=" + from +
            ", to=" + to +
            '}';
    }
}
