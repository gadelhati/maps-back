package com.maps.persistence;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

public interface MapperInterface<T, I, O> {
    O toDTO(T entity);
    T toObject(I dto);
}
