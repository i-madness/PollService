package net.imadness.util;

import net.imadness.entities.Option;
import net.imadness.entities.Poll;
import net.imadness.entities.Question;

/**
 * Объект-обёртка для опроса. Требуется для формирования html-кода опроса, необходимого для вставки на сторонний ресурс
 */
public class EmbedPollWrapper {
    private Poll poll;
    private String hostName;
    private StringBuffer stringBuffer;

    /**
     * Создаёт экземпляр обёртки; принимает на вход необходимые данные и конструирует нужный html-код
     * @param poll опрос, для которого формируется код
     * @param hostName имя ресурса предоставляющего сервис опросов
     */
    public EmbedPollWrapper(Poll poll, String hostName) {
        this.poll = poll;
        this.hostName = hostName;
        stringBuffer = new StringBuffer();
        stringBuffer
             .append("<form method=\"post\" action=\"").append(hostName).append("/poll/").append(poll.getId()).append("/receiveResultsFromEmbed\">");
        for (Question question : poll.getQuestions()) {
            stringBuffer.append("<div style=\"left: 10px;\"><div>").append(question.getName()).append("</div>");
            for (Option option : question.getOptions()) {
                stringBuffer.append("<div style=\"left: 10px;\"><input type=\"radio\" name=\"")
                        .append(option.getContent()).append("\">")
                        .append(option.getContent()).append("</div>");
            }
            stringBuffer.append("</div>");
        }
        stringBuffer.append("</form>");
    }

    /**
     * Возвращает html код обёртки опроса
     * @return html код для вставки в виде строки
     */
    @Override
    public String toString() {
        return stringBuffer.toString();
    }
}
