package model.services;

import java.time.Duration;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {
  
    private Double pricePerHour;
    private Double pricePerDay;

    private TaxService brazilTax;
    
    public RentalService() {
    }

    public RentalService(Double pricePerHour, Double pricePerDay, TaxService braziltax) {
        this.pricePerHour = pricePerHour;
        this.pricePerDay = pricePerDay;
        this.brazilTax = braziltax;

    }

    public Double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(Double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public void processInvoice(CarRental carRental){

        // minutes recebe a duração entre o inicio e fim do carRental passado como parâmetro, utilizando toMinutes()
        double minutes = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes(); 
        double hours = minutes / 60;

        double basicPayment;
        if (hours <= 12.0){
            basicPayment = pricePerHour * Math.ceil(hours); //Math.ceil converte um númeroe decimal no maior inteiro imediato (arredonda o número para cima)

        }else{
            basicPayment = pricePerDay * Math.ceil(hours / 24.0);

        }

        double tax = brazilTax.tax(basicPayment);

        carRental.setInvoice(new Invoice(basicPayment, tax));
    }
}
