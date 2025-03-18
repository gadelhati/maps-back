package com.maps.persistence;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

public interface MapperInterface<T, I, O> {
    O toDTO(T entity);
    T toObject(I dto);
}