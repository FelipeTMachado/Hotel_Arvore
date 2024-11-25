package model;

import java.time.LocalDate;

public class Main {
	public static void main(String[] args) {
		SistemaReservas sistema = new SistemaReservas();

        Reserva reserva1 = new Reserva("102", "João", LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 5), "Luxo");
        sistema.cadastrarReserva("12345678900", reserva1);

        Reserva reserva2 = new Reserva("101", "Maria", LocalDate.of(2024, 11, 3), LocalDate.of(2024, 11, 7), "Econômico");
        sistema.cadastrarReserva("98765432100", reserva2);

        sistema.cancelarReserva("12345678900");
        
        System.out.println("");
        
        sistema.gerarRelatoriosGerenciais(LocalDate.of(2024, 10, 1), LocalDate.of(2024, 12, 3));
	}
}
