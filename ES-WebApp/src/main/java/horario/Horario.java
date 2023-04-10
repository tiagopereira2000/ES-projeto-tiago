package horario;

import java.util.LinkedList;

public class Horario {
	

	private LinkedList<Aula> aulas;
	
	public Horario() {
		aulas = new LinkedList<>();
	}
	
	public LinkedList<Aula> getAulas(){
		return aulas;
	}
	
	public void addAula(Aula aula) {
		aulas.add(aula);
	}
	
	public Horario getAulasFromUC(String unidadeCurricular) {
		Horario novoHorario = new Horario();
		for (Aula aula: aulas) {
			if(aula.getUnidadeCurricular().equals(unidadeCurricular)) {
				novoHorario.addAula(aula);
			}
		}
		return novoHorario;
	}
	
	@Override
	public String toString() {
		return "Horario [aulas=" + aulas.toString() + "]";
	}
	
}
