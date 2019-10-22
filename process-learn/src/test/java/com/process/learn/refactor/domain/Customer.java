package com.process.learn.refactor.domain;

import java.util.Enumeration;
import java.util.Vector;
import lombok.Data;

/**
 * @author Lynn
 * @since 2019-05-23
 */
@Data
public class Customer {

    private String name;
    private Vector _rentals = new Vector();

    public Customer(String _name) {
        this.name = _name;
    }

    public String statement() {
        double totalAmount = 0;
        int frequentRenterPointers = 0;
        Enumeration rentals = _rentals.elements();
        StringBuilder result = new StringBuilder("Rental Record for " + getName() + "\n");
        while (rentals.hasMoreElements()) {
            Rental rental = (Rental) rentals.nextElement();
            // determine amounts for each lines
            // add frequent renter points
//            frequentRenterPointers++;
            // add bonus for two day new release rental
//            if ((rental.getMovie().getPriceCode() == Movie.NEW_RELEASE) && rental.getDaysRented() > 1) {
//                frequentRenterPointers++;
//            }
            frequentRenterPointers += rental.getFrequentRenterPointers();

            result.append("\t").append(rental.getMovie().getTitle()).append("\t").append(rental.getCharge())
                    .append("\n");
            totalAmount += rental.getCharge();
        }
        // add footer lines
        result.append("Amount owed is").append(totalAmount).append("\n");
        result.append("You earned ").append(frequentRenterPointers).append(" frequent renter points");
        return result.toString();
    }

}
