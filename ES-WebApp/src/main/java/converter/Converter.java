package converter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.Builder;

import horario.Aula;
import horario.Horario;

public class Converter {

	private static ObjectMapper mapper = new ObjectMapper();

//	--------------------------------- CSV TO JSON ---------------------------------
	
	public static void csvToJson(String inputPath, String outputPath) {

		File input = new File(inputPath);
		File output = new File(outputPath);

		try {
			List<Map<String, Object>> objects = readObjectsFromCsv(input);
			writeAsJson(objects, output);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static List<Map<String, Object>> readObjectsFromCsv(File file) throws IOException {
		CsvSchema bootstrap = CsvSchema.emptySchema().withHeader();
		mapper = new CsvMapper();
		try (MappingIterator<Map<String, Object>> iterator = mapper.readerFor(Map.class).with(bootstrap)
				.readValues(file)) {
			return iterator.readAll();
		}
	}

	private static void writeAsJson(List<Map<String, Object>> objects, File file) throws IOException {
		renameKeys(objects);
		mapper = new ObjectMapper();
		mapper.writeValue(file, objects);
	}
	
	private static void renameKeys(List<Map<String, Object>> objects) {
		for(Map<String,Object> map : objects) {
			
				if(map.containsKey("Curso"))
					map.put("curso", map.remove("Curso"));
				
				if(map.containsKey("Unidade Curricular"))
					map.put("unidadeCurricular", map.remove("Unidade Curricular"));
				
				if(map.containsKey("Turno"))
					map.put("turno", map.remove("Turno"));
				
				if(map.containsKey("Turma"))
					map.put("turma", map.remove("Turma"));
				
				if(map.containsKey("Inscritos no turno"))
					map.put("inscritos", map.remove("Inscritos no turno"));
				
				if(map.containsKey("Dia da semana"))
					map.put("diaDaSemana", map.remove("Dia da semana"));
				
				if(map.containsKey("Hora inÃ­cio da aula"))
					map.put("horaInicio", map.remove("Hora inÃ­cio da aula"));
				
				if(map.containsKey("Hora fim da aula"))
					map.put("horaFim", map.remove("Hora fim da aula"));
				
				if(map.containsKey("Data da aula"))
					map.put("data", map.remove("Data da aula"));
			
				if(map.containsKey("Sala atribuÃ­da Ã  aula"))
					map.put("sala", map.remove("Sala atribuÃ­da Ã  aula"));
				
				if(map.containsKey("LotaÃ§Ã£o da sala"))
					map.put("lotacao", map.remove("LotaÃ§Ã£o da sala"));
		}
	}
	
//	-------------------------------------------------------------------------------
//	--------------------------------- JSON TO CSV ---------------------------------
	
	public static void jsonToCsv(String inputPath, String outputPath) {
		File input = new File(inputPath);
		File output = new File(outputPath);
		
			try {
				readJson(input, output);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	private static void readJson(File input, File output) throws IOException {
		JsonNode jsonTree = new ObjectMapper().readTree(input);
		Builder csvSchemaBuilder = CsvSchema.builder();
		JsonNode firstObject = jsonTree.elements().next();
		firstObject.fieldNames().forEachRemaining(csvSchemaBuilder::addColumn );
		CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
      
		CsvMapper csvMapper = new CsvMapper();
		csvMapper.writerFor(JsonNode.class)
			.with(csvSchema)
			.writeValue(output, jsonTree);
	}
	
//	-----------------------------------------------------------------------------
//	--------------------------------- JAVA/JSON ---------------------------------

	//incomplete: falta passar um horario com varias aulas para json
	public static String javaToJson(Aula aula) {
		try {
			return mapper.writeValueAsString(aula);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "javaToJson: writeError";
		}
	}

	
	public static Horario jsonToJava(String json) {
		
		Horario horario = new Horario();

		if (json.startsWith("[")) { // is jsonArray
			List<Map<String, Object>> listaDeAulas = new ArrayList<>();
			try {
				listaDeAulas = mapper.readValue(json, new TypeReference<List<Map<String, Object>>>(){});
				for(Map<String,Object> map: listaDeAulas) {
					Aula aula = mapper.convertValue (map, Aula.class);
					horario.addAula(aula);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return horario;
		} else { // is jsonObject
			try {
				Aula aula = mapper.readValue(json, Aula.class);
				horario.addAula(aula);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return horario;
		}
	}
	
	
	//mudar para os testes
	public static void main(String[] args) {
		
		String json = "{\"curso\":\"ME\",\"unidadeCurricular\":\"Teoria dos Jogos e dos Contratos\","
				+ "\"turno\":\"01789TP01\",\"turma\":\"MEA1\",\"inscritos\":\"30\",\"diaDaSemana\":\"Sex\","
				+ "\"horaInicio\":\"13:00:00\",\"horaFim\":\"14:30:00\",\"data\":\"02/12/2022\","
				+ "\"sala\":\"AA2.25\",\"lotacao\":\"34\"}";
		
		String json2 = "[{\"curso\":\"ME\",\"unidadeCurricular\":\"Teoria dos Jogos e dos Contratos\","
				+ "\"turno\":\"01789TP01\",\"turma\":\"MEA1\",\"inscritos\":\"30\",\"diaDaSemana\":\"Sex\","
				+ "\"horaInicio\":\"13:00:00\",\"horaFim\":\"14:30:00\",\"data\":\"02/12/2022\","
				+ "\"sala\":\"AA2.25\",\"lotacao\":\"34\"},"
					+ "{\"curso\":\"DF\",\"unidadeCurricular\":\"Investimentos II\",\"turno\":\"01074TP01\","
				+ "\"turma\":\"DFB1\",\"inscritos\":\"3\",\"diaDaSemana\":\"Seg\","
				+ "\"horaInicio\":\"17:30:00\",\"horaFim\":\"19:00:00\",\"data\":\"21/11/2022\","
				+ "\"sala\":\"D1.07\",\"lotacao\":\"27\"},"
					+ "{\"curso\":\"LETI, LEI, LEI-PL, LIGE, LIGE-PL\",\"unidadeCurricular\":\"Arquitetura de Redes\","
				+ "\"turno\":\"03708T02\",\"turma\":\"EI-C6, EI-C5, EI-C4, EI-C3, EI-C2, EI-C1\",\"inscritos\":\"99\","
				+ "\"diaDaSemana\":\"Seg\",\"horaInicio\":\"14:30:00\",\"horaFim\":\"16:00:00\","
				+ "\"data\":\"28/11/2022\",\"sala\":\"\",\"lotacao\":\"\"}]";
		
		System.out.println(Converter.jsonToJava(json2).toString());
		
		Converter.csvToJson("src/main/resources/csv/teste2.csv",	"src/main/resources/json/teste4.json");
	
//		Converter.csvToJson("src/main/resources/csv/horario-exemplo.csv", "src/main/resources/json/abrirNoBrowser.json");
	}

}
