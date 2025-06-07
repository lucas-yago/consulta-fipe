package br.com.onealura.TabelaFipe.main;

import java.util.Scanner;

public class Main {
    private final Scanner scanner = new Scanner(System.in);

    public void displayMenu() {
        System.out.println("""
                ***** OPÇÕES *****
                Carro
                Moto
                Caminhão
                
                Digite uma das opções para consultar valores:""");
        String vehicleType = scanner.nextLine();

    }
}
