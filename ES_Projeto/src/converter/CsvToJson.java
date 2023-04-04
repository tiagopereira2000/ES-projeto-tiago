package converter;


import java.io.*;
import java.util.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.csv.*;

public class CsvToJson {
	
	File input;
    File output;
	
	public CsvToJson(String inputPath, String outputPath) {
		
		input = new File(inputPath);
		output = new File(outputPath);
		
		try {
			List<Map<?, ?>> data = readObjectsFromCsv(input);
			writeAsJson(data, output);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static List<Map<?, ?>> readObjectsFromCsv(File file) throws IOException {
       CsvSchema bootstrap = CsvSchema.emptySchema().withHeader();
       CsvMapper csvMapper = new CsvMapper();
       try (MappingIterator<Map<?, ?>> mappingIterator = csvMapper.readerFor(Map.class).with(bootstrap).readValues(file)) {
           return mappingIterator.readAll();
       }
   }
   
   public static void writeAsJson(List<Map<?, ?>> data, File file) throws IOException {
       ObjectMapper mapper = new ObjectMapper();
       mapper.writeValue(file, data);
   }
   
}
