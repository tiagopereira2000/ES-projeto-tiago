package converter;

import java.io.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.csv.*;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.Builder;

public class JsonToCsv {
	
	private File input;
	private File output;

	public JsonToCsv(String inputPath, String outputPath) {
		input = new File(inputPath);
		output = new File(outputPath);
		
		try {
			readJson();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readJson() throws IOException {
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
	
}
