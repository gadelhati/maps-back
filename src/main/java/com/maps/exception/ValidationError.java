package com.maps.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Data
@RequiredArgsConstructor
public class ValidationError {
    private final String field;
    private final Object rejectedValue;
    private final String message;
}