package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class SistemaReservas {
    private RedBlackTree<String, Reserva> reservas = new RedBlackTree<>();
    private RedBlackTree<String, Reserva> historico = new RedBlackTree<>();
    private Map<String, Integer> ocupacaoPorQuarto = new HashMap<>();
    private int totalQuartos = 50;

    
    public void cadastrarReserva(String cpf, Reserva reserva) {
        if (!verificarDisponibilidade(reserva.numeroQuarto, reserva.dataCheckIn, reserva.dataCheckOut)) {
            System.out.println("Erro: Conflito de reserva.");
            return;
        }
        reservas.put(cpf, reserva);
        ocupacaoPorQuarto.put(reserva.numeroQuarto, ocupacaoPorQuarto.getOrDefault(reserva.numeroQuarto, 0) + 1);
        verificarCapacidade();
        System.out.println("Reserva cadastrada com sucesso!");
    }
    
    public List<String> consultarDisponibilidadeQuartos(LocalDate dataInicio, LocalDate dataFim, String categoria) {
        Set<String> ocupados = getAllReservas().stream()
                .filter(r -> (dataInicio.isBefore(r.dataCheckOut) && dataFim.isAfter(r.dataCheckIn)) &&
                             r.categoriaQuarto.equalsIgnoreCase(categoria))
                .map(r -> r.numeroQuarto)
                .collect(Collectors.toSet());

        List<String> disponiveis = new ArrayList<>();
        for (int i = 1; i <= totalQuartos; i++) {
            String numeroQuarto = String.valueOf(i);
            if (!ocupados.contains(numeroQuarto)) {
                disponiveis.add(numeroQuarto);
            }
        }
        return disponiveis;
    }

    
    public void gerarRelatoriosGerenciais(LocalDate inicio, LocalDate fim) {
        // Taxa de ocupação
        long totalReservas = getAllReservas().stream()
                .filter(r -> (r.dataCheckIn.isBefore(fim) && r.dataCheckOut.isAfter(inicio)))
                .count();

        double taxaOcupacao = ((double) totalReservas / totalQuartos) * 100;
        System.out.printf("Taxa de ocupação no período: %.2f%%\n", taxaOcupacao);

        // Quartos mais e menos reservados
        String quartoMaisReservado = ocupacaoPorQuarto.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse("Nenhum");

        String quartoMenosReservado = ocupacaoPorQuarto.entrySet().stream()
                .min(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse("Nenhum");

        System.out.println("Quarto mais reservado: " + quartoMaisReservado);
        System.out.println("Quarto menos reservado: " + quartoMenosReservado);

        // Cancelamentos no período
        long totalCancelamentos = getAllReservas(historico).stream()
                .filter(r -> (r.dataCheckIn.isBefore(fim) && r.dataCheckOut.isAfter(inicio)))
                .count();
        System.out.println("Total de cancelamentos no período: " + totalCancelamentos);
    }
    
    private void verificarCapacidade() {
        int totalReservas = getAllReservas().size();
        double ocupacao = ((double) totalReservas / totalQuartos) * 100;

        if (ocupacao >= 90) {
            System.out.printf("ALERTA: Taxa de ocupação atingiu %.2f%%. Planeje a chegada dos hóspedes!\n", ocupacao);
        }
    }
    
    public Reserva consultarReservaPorCliente(String cpf) {
        return reservas.get(cpf);
    }
    
    public void cancelarReserva(String cpf) {
        Reserva reserva = reservas.get(cpf);
        if (reserva != null) {
            reservas.delete(cpf);
            historico.put(cpf, reserva);
            System.out.println("Reserva cancelada e movida para o histórico.");
        } else {
            System.out.println("Erro: Reserva não encontrada.");
        }
    }

    public boolean verificarDisponibilidade(String numeroQuarto, LocalDate dataInicio, LocalDate dataFim) {
        for (Reserva reserva : getAllReservas()) {
            if (reserva.numeroQuarto.equals(numeroQuarto) &&
                (dataInicio.isBefore(reserva.dataCheckOut) && dataFim.isAfter(reserva.dataCheckIn))) {
                return false;
            }
        }
        return true;
    }

    private List<Reserva> getAllReservas() {
        List<Reserva> lista = new ArrayList<>();
        getAllReservas(reservas.getRoot(), lista);
        return lista;
    }

    private List<Reserva> getAllReservas(RedBlackTree<String, Reserva> tree) {
        List<Reserva> lista = new ArrayList<>();
        getAllReservas(tree.getRoot(), lista);
        return lista;
    }

    private void getAllReservas(RedBlackTree<String, Reserva>.Node node, List<Reserva> lista) {
        if (node != null) {
            getAllReservas(node.left, lista);
            lista.add(node.value);
            getAllReservas(node.right, lista);
        }
    }
    
    
    // GETTERS E SETTERS
	public RedBlackTree<String, Reserva> getReservas() {
		return reservas;
	}
	public void setReservas(RedBlackTree<String, Reserva> reservas) {
		this.reservas = reservas;
	}
	public RedBlackTree<String, Reserva> getHistorico() {
		return historico;
	}
	public void setHistorico(RedBlackTree<String, Reserva> historico) {
		this.historico = historico;
	}

	public Map<String, Integer> getOcupacaoPorQuarto() {
		return ocupacaoPorQuarto;
	}
	public void setOcupacaoPorQuarto(Map<String, Integer> ocupacaoPorQuarto) {
		this.ocupacaoPorQuarto = ocupacaoPorQuarto;
	}
	public int getTotalQuartos() {
		return totalQuartos;
	}
	public void setTotalQuartos(int totalQuartos) {
		this.totalQuartos = totalQuartos;
	}
}