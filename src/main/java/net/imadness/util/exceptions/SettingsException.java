package net.imadness.util.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Ошибка при попытке сохранить новые настройки")
public class SettingsException extends RuntimeException {
}
