package model;

import java.time.LocalDate;

class Reserva {
    String numeroQuarto;
    String nomeCliente;
    LocalDate dataCheckIn;
    LocalDate dataCheckOut;
    String categoriaQuarto;

    public Reserva(String numeroQuarto, String nomeCliente, LocalDate dataCheckIn, LocalDate dataCheckOut, String categoriaQuarto) {
        this.numeroQuarto = numeroQuarto;
        this.nomeCliente = nomeCliente;
        this.dataCheckIn = dataCheckIn;
        this.dataCheckOut = dataCheckOut;
        this.categoriaQuarto = categoriaQuarto;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "numeroQuarto='" + numeroQuarto + '\'' +
                ", nomeCliente='" + nomeCliente + '\'' +
                ", dataCheckIn=" + dataCheckIn +
                ", dataCheckOut=" + dataCheckOut +
                ", categoriaQuarto='" + categoriaQuarto + '\'' +
                '}';
    }
}
