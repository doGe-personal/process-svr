package com.process.mobike.refactor.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Lynn
 * @since 2019-05-23
 */
@AllArgsConstructor
@Data
public class Rental {

    private Movie movie;
    private int daysRented;

    public double getCharge() {
        double result = 0;
        switch (this.getMovie().getPriceCode()) {
            case Movie.REGAULAR:
                result += 2;
                if (this.getDaysRented() > 2) {
                    result += (this.getDaysRented() - 2) * 1.5;
                }
                break;
            case Movie.NEW_RELEASE:
                result += this.getDaysRented() * 3;
                break;
            case Movie.CHILDRENS:
                result += 1.5;
                if (this.getDaysRented() > 3) {
                    result += (this.getDaysRented() - 3) * 1.5;
                }
                break;
        }
        return result;
    }

    public int getFrequentRenterPointers() {
        if ((this.getMovie().getPriceCode() == Movie.NEW_RELEASE) && this.getDaysRented() > 1) {
            return 2;
        }
        return 1;
    }



}
