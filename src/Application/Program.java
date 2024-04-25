package Application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

import model.entities.CarRental;
import model.entities.Vehicle;
import model.services.BrazilTaxService;
import model.services.RentalService;

public class Program {
    public static void main(String[] args) {
        
        Locale.setDefault(Locale.US);
        Scanner tec = new Scanner(System.in);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");    //Definindo um padrão de data para formatação

        System.out.println("Digite os dados do aluguel");
        System.out.print("Modelo do carro: ");
        String modelo = tec.nextLine();

        System.out.print("Retirada: (dd/MM/yyyy HH:mm)");
        LocalDateTime inicio = LocalDateTime.parse(tec.nextLine(), fmt); // Convertendo o texto do usuário para o tipo LocalDateTime com a formatação feita acima
        System.out.print("Devolução: (dd/MM/yyyy HH/mm)");
        LocalDateTime fim = LocalDateTime.parse(tec.nextLine(), fmt);

        CarRental cr = new CarRental(inicio, fim, new Vehicle(modelo));

        System.out.print("Digite o preço por hora: ");
        double preçoHora = Double.parseDouble(tec.nextLine());
        System.out.print("Digite o preço por dia: ");
        double preçoDia = Double.parseDouble(tec.nextLine());

        RentalService rs = new RentalService(preçoHora, preçoDia, new BrazilTaxService());

        rs.processInvoice(cr);

        System.out.println("FATURA");
        System.out.println("Pagamento basico: " + String.format("%.2f", cr.getInvoice().getBasicPayment()));
        System.out.println("Imposto: " + String.format("%.2f", cr.getInvoice().getTax()));
        System.out.println("Pagamento total: " + String.format("%.2f", cr.getInvoice().getTotalPayment()));
        tec.close();
    }
}
