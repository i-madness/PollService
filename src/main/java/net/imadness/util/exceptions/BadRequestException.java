package net.imadness.util.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Запрещённый тип HTTP-запроса")
public class BadRequestException extends RuntimeException {
}
