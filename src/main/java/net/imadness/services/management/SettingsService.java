package net.imadness.services.management;

import net.imadness.entities.extended.Settings;
import org.springframework.stereotype.Component;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

/**
 * Содержит логику работы с настройками доступа администратора
 */
@Component
public class SettingsService {

    private Settings instance;
    private final Settings defaults = new Settings("admin","admin");
    private final String PATH_TO_SETTINGS = new File(System.getProperty("java.io.tmpdir")).getParent() + "\\spoll.Settings.xml";

    /**
     * Получает объект текущих настроек (ленивая инициализация). Если объект невозможно прочитать из файла настроек,
     * сервис вернёт настройки в состояние по умолчанию {@link net.imadness.services.management.SettingsService#defaults}
     * @return текущий объект Settings
     */
    public Settings getSettings() {
        if (instance == null) {
            try {
                instance = parseSettings();
            } catch (IOException e) {
                instance = defaults;
                applySettings();
            }
        }
        return instance;
    }

    /**
     * Применяет новые настройки
     * @param newSettings объект настроек, получаемый из метода контроллера настроек
     * @see net.imadness.controllers.SettingsController
     */
    public void setSettings(Settings newSettings) {
        instance = newSettings;
        applySettings();
    }

    /**
     * Читает объект настроек из xml-файла
     * @return объект Settings
     * @throws IOException исключение, выбрасываемое, если файл не найден
     */
    private Settings parseSettings() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(PATH_TO_SETTINGS);
        XMLDecoder xmlDecoder = new XMLDecoder(fileInputStream);
        Settings settings = (Settings) xmlDecoder.readObject();
        xmlDecoder.close();
        return settings;
    }

    /**
     * Сохраняет текущие настройки в xml-файл
     */
    public void applySettings() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(PATH_TO_SETTINGS);
            XMLEncoder xmlEncoder = new XMLEncoder(new BufferedOutputStream(fileOutputStream));
            xmlEncoder.writeObject(instance);
            //xmlEncoder.flush();
            xmlEncoder.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPath() {
        return PATH_TO_SETTINGS;
    }
}
