package com.maps.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Getter
@Setter
@RequiredArgsConstructor
public class ValidationError {
    private final String field;
    private final Object rejectedValue;
    private final String message;
}