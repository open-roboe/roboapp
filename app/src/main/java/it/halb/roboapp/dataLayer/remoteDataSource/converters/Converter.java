package it.halb.roboapp.dataLayer.remoteDataSource.converters;


import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Generic converter that allows conversions between Data Transfers Objects, which are the classes
 * defined in the api.model package, and the actual classes used in the application
 *
 * <a href="https://java-design-patterns.com/patterns/converter/#explanation">java pattern source<a>
 *
 * @param <T> The DTO class, defined in the api models
 * @param <U> The local data source class
 */
public class Converter<T, U> {

    private final Function<T, U> fromDto;
    private final Function<U, T> fromEntity;

    public Converter(final Function<T, U> fromDto, final Function<U, T> fromEntity) {
        this.fromDto = fromDto;
        this.fromEntity = fromEntity;
    }

    public final U convertFromDto(final T dto) {
        return fromDto.apply(dto);
    }

    public final T convertFromEntity(final U entity) {
        return fromEntity.apply(entity);
    }

    public final List<U> createFromDtos(final Collection<T> dtos) {
        return dtos.stream().map(this::convertFromDto).collect(Collectors.toList());
    }

    public final List<T> createFromEntities(final Collection<U> entities) {
        return entities.stream().map(this::convertFromEntity).collect(Collectors.toList());
    }
}
