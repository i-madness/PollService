package net.imadness.util.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Нельзя принимать участие в голосовании более одного раза!")
public class ParticipationException extends RuntimeException {
}
