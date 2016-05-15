package net.imadness.services.management;

import org.springframework.stereotype.Service;

/**
 * Сервис проверки участника голосования / теста на повторное участие.
 * Призван отсеивать голоса от одних и тех же пользователей.
 */
@Service
public class ParticipationService {

    /**
     * Комплексная проверка участника опроса
     * @return результат проверки (true | false)
     */
    public Boolean checkParticipant() {
        // check cookie
        // check ip
        // check captcha
        return true;
    }

    public Boolean checkCookie(String pollId, String cookie) {
        return true;
    }
}
