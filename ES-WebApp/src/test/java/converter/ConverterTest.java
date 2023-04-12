package converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import horario.Aula;

class ConverterTest {
	
	private Aula aula = new Aula("ME","Teoria dos Jogos e dos Contratos", "01789TP01","MEA1","30",
			"Sex","13:00:00","14:30:00","02/12/2022","AA2.25","34");
	
	private String jsonObject = "{\"curso\":\"ME\",\"unidadeCurricular\":\"Teoria dos Jogos e dos Contratos\","
				+ "\"turno\":\"01789TP01\",\"turma\":\"MEA1\",\"inscritos\":\"30\",\"diaDaSemana\":\"Sex\","
				+ "\"horaInicio\":\"13:00:00\",\"horaFim\":\"14:30:00\",\"data\":\"02/12/2022\","
				+ "\"sala\":\"AA2.25\",\"lotacao\":\"34\"}";
	
	private String jsonArray = "[{\"curso\":\"ME\",\"unidadeCurricular\":\"Teoria dos Jogos e dos Contratos\","
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
	
	private String jsonObjectWithWeirdCharacters = "{\"Curso\":\"PIUDHIST\",\"Unidade Curricular\":\"SeminÃ¡rio de Projecto I (Piudhist)\","
			+ "\"Turno\":\"SP-I_(Piudhist)S01\",\"Turma\":\"DHMCMG1\",\"Inscritos no turno\":\"0\","
			+ "\"Dia da semana\":\"Seg\",\"Hora inÃ­cio da aula\":\"18:00:00\",\"Hora fim da aula\":\"20:00:00\","
			+ "\"Data da aula\":\"12/12/2022\",\"Sala atribuÃ­da Ã  aula\":\"AA3.24\",\"LotaÃ§Ã£o da sala\":\"30\"}";

	
//	@Test
//	void givenCsvReturnsJson() {
//		File json = new File();
//		assertEquals(json, Converter.csvToJson(jsonObject).getAulas().getFirst().getCurso());
//	}
	
//	@Test
//	void givenCsvReturnsJavaObject() {
//		System.out.println(Converter.csvToJava("src/main/resources/csv/teste2.csv").toString());
//	}
	
	@Test
	void givenJsonObjectReturnsJavaObject() {
		assertEquals("ME", Converter.jsonToJava(jsonObject).getAulas().getFirst().getCurso());
	}
	
	@Test
	void givenJsonArrayReturnsJavaObject() {
		assertEquals("ME", Converter.jsonToJava(jsonArray).getAulas().getFirst().getCurso());
		assertEquals("LETI, LEI, LEI-PL, LIGE, LIGE-PL", Converter.jsonToJava(jsonArray).getAulas().getLast().getCurso());
	}
	
	@Test
	void givenJavaObjectReturnsJson() {
		assertEquals(jsonObject, Converter.javaToJson(aula));
	}
	
	@Test
	void givenUTF8StringReturnsISOString() throws UnsupportedEncodingException {
		String UTF8string = "SeminÃ¡rio inÃ­cio LotaÃ§Ã£o";

		String UTF8string2 = "SeminÃ¡rio inÃ­cio LotaÃ§Ã£o atribuÃ­da Ã  aula";
		assertEquals("Seminário início Lotação atribuída à aula", new String(UTF8string2.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
	}
	
//	@Test//TODO
//	void givenJsonArrayReturnsCsv() {
//		Converter.jsonToCsv("src/test/resources/json/teste2.json", "src/test/resources/csv/teste.csv");
//		File expected = new File("src/test/resources/csv/teste2.csv");
//		File actual = new File("src/test/resources/csv/teste.csv");
//		assertEquals(expected, actual);
//	}
//	
//	@Test//TODO
//	void givenCsvReturnsJson() {
//		Converter.csvToJson("src/test/resources/csv/teste2.csv", "src/test/resources/json/teste2.json");
//		File jsonFile = new File("src/test/resources/json/teste2.json");
//		assertEquals(jsonObject, "" );
//	}

	@Test
    void deserializeJson() throws IOException {
        String json = "{\"curso\":\"lei\",\"inscritos\":30}";

        JsonNode jsonTree = new ObjectMapper().readTree(json);

        assertEquals("lei", jsonTree.get("curso").asText());
        assertEquals(30, jsonTree.get("inscritos").asInt());
    }
	
}
