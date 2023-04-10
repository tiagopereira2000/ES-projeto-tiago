package converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import horario.Aula;

class ConverterTest {
	
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

	@Test
	void givenJsonReturnsObject() throws JsonProcessingException {
		
		assertEquals(Converter.jsonToJava(jsonObject).getAulas().getFirst().getCurso(), "ME");
	}
	
	@Test
	void givenObjectReturnsJson() throws JsonProcessingException {
		Aula aula = new Aula();
		String json = Converter.javaToJson(aula);
		assertEquals(json,Converter.jsonToJava(json));
	}

	
	
	@Test
	void shouldWriteJsonFileFromObject() {
		
		Map<Object, Object> unidadeCurricular1 = new HashMap<Object, Object>();
		unidadeCurricular1.put("curso", "lei");
		unidadeCurricular1.put("inscritos", 30);
		
		Map<Object, Object> unidadeCurricular2 = new HashMap<Object, Object>();
		unidadeCurricular2.put("curso", "lige");
		unidadeCurricular2.put("inscritos", 10);
		
		Map<Object, Object> unidadeCurricular3 = new HashMap<Object, Object>();
		unidadeCurricular3.put("curso", "lcd");
		unidadeCurricular3.put("inscritos", 2);
		
//		List<Object> listaDeUCs = Arrays.asList(unidadeCurricular1, unidadeCurricular2, unidadeCurricular3);
//		List<Object> listaDeUCs = Arrays.asList(unidadeCurricular1);
		
		

//		String json = "{\"curso\":\"lei\",\"inscritos\":30}";
		
		assertEquals("lei", unidadeCurricular1.get("curso"));
//		JsonNode expectedJsonTree = new ObjectMapper().readTree(json);
       
//		JsonNode actualJsonTree = new ObjectMapper().readTree(json);
//		Map<Object,Object> mMap = new HashMap<>();
//		List<Object> list = new List<>();
        
//		ObjectMapper mapper = new ObjectMapper();
//	    mapper.writeValue(new File(), listaDeUCs);
	}
	
	@Test
	public void shoudProperlySerializeLocalDateToJson() throws IOException {

		fail("Not yet implemented");
//	  String actual = new ObjectMapper().writeValueAsString(new Sample());
//
//	  assertEquals("{\"orderDate\":\"2015-10-07\"}", actual);
	}
	
	@Test
    void deserializeJson() throws IOException {
        String json = "{\"curso\":\"lei\",\"inscritos\":30}";

        JsonNode jsonTree = new ObjectMapper().readTree(json);

        assertEquals("lei", jsonTree.get("curso").asText());
        assertEquals(30, jsonTree.get("inscritos").asInt());
    }
	
}
