package main;

import converter.CsvToJson;
import converter.JsonToCsv;

public class Main {
	
	public static void main(String[] args) {
		
		new JsonToCsv("resources/json/teste.json", "resources/csv/teste2.csv");
		
		new CsvToJson("resources/csv/horario-exemplo.csv", "resources/json/teste2.json");
	}

}
