package ru.ibs.trainings.spring.advanced.impl.mappers;

import lombok.experimental.ExtensionMethod;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.StreamSupport;

@UtilityClass
@ExtensionMethod(value = StreamSupport.class, suppressBaseMethods = false)
public class CollectionMapper {

  public <T, U> Iterable<U> transformIterable(Iterable<? extends T> self, Function<? super T, U> mapper) {
    return self.spliterator()
               .stream(false)
               .map(mapper)
               .toList();
  }

  public <T, U> Collection<U> transformCollection(Collection<? extends T> self, Function<? super T, U> mapper) {
    return transformList(self, mapper);
  }

  public <T, U> List<U> transformList(Collection<? extends T> self, Function<? super T, U> mapper) {
    return self.stream()
               .map(mapper)
               .toList();
  }
}
