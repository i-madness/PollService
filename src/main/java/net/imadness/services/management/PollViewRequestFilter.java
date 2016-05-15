package net.imadness.services.management;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Отвечает за проверку корректности HTTP-запросов к странице опроса.
 * Используется с целью отсеять те запросы, которые могут отправляться не из браузеров, а из сторонних
 * клиентских приложений с целью накрутки в опросах.
 */
@Service
public class PollViewRequestFilter {

    /**
     * Проверяет, соответствуют ли запрос и его заголовки требованиям приложения
     * @param request HTTP-запрос
     * @return истинное значение, если запрос корректен с точки зрения приложения
     */
    public Boolean checkRequest(HttpServletRequest request) {
        return checkReferrer(request.getHeader("Referer")) &&
               checkUserAgent(request.getHeader("User-Agent"));
    }

    /**
     * Проверка того, что реферером для страницы опроса была специальная подставная страницв
     * @see @link{net.imadness.controllers.PollController}
     * @param url строка URL реферера
     * @return истинное значение при наличии строки пути специальной странице в URL реферера
     */
    private Boolean checkReferrer(String url) {
        return url != null && url.contains("poll/r/");
    }

    /**
     * Проверка факта отправления запроса из какого-либо браузера
     * @param userAgent строка заголовка User-Agent
     * @return истинное значение, если запрос отправлен именно из известного приложению браузера
     */
    private Boolean checkUserAgent(String userAgent) {
        return userAgent.contains("Mozilla") ||
               userAgent.contains("Gecko") ||
               userAgent.contains("Windows NT") ||
               userAgent.contains("Chrome") ||
               userAgent.contains("Safari") ||
               userAgent.contains("WebKit");
    }

}
