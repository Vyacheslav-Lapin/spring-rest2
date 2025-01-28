package ru.ibs.trainings.spring.stub.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.intellij.lang.annotations.Language;

import java.util.List;

@UtilityClass
public class CsvUtils {

  private final CsvMapper CSV_MAPPER = new CsvMapper();

  @SneakyThrows
  public <T> MappingIterator<T> readFile(@Language("file-reference") String fileName, TypeReference<T> typeReference) {
//
    val schema = CSV_MAPPER
        .disable(CsvParser.Feature.WRAP_AS_ARRAY)
        .typedSchemaFor(typeReference)
        .withColumnSeparator(';')
        .withHeader();

    return CSV_MAPPER.readerFor(typeReference)
                     .with(schema)
                     .readValues(CsvUtils.class.getResource(fileName));
  }

  @SneakyThrows
  public MappingIterator<List<String>> readFile(@Language("file-reference") String fileName) {

    val schema = CSV_MAPPER.enable(CsvParser.Feature.WRAP_AS_ARRAY)
                           .schema()
                           .withColumnSeparator(';');

    return CSV_MAPPER.readerFor(List.class)
                     .with(schema)
                     .readValues(CsvUtils.class.getResource(fileName));
  }
}
