package org.example.javapracticaltestassignment.converter;

public interface ConverterEx<S, T> extends Converter<S, T> {
    T convert(S source, long id);
}
