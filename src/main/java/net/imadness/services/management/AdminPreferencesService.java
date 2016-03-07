package net.imadness.services.management;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Класс, необходимый для хранения данных администратора для входа в систему.
 * Авторизационные данные хранятся в .xml-файле на сервере и могут быть изменены администратором по желанию
 */
@Service
@Scope("singleton")
public class AdminPreferencesService {
    private class Preferences {
        private String name;
        private String password;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
        public Preferences(String name, String password) {
            this.name = name;
            this.password = password;
        }
    }
    private static Preferences preferencesInstance;

    /**
     * Получает авторизационные данные из xml-файла
     * @return true, если загрузка данных прошла успешна, в противном случае - false
     */
    @PostConstruct
    private Boolean loadPreferences() {
        try {
            FileInputStream fileInputStream = new FileInputStream("admin-preferences.xml");
            XMLDecoder xmlDecoder = new XMLDecoder(fileInputStream);
            preferencesInstance = (Preferences) xmlDecoder.readObject();
            xmlDecoder.close();
            return true;
        } catch (FileNotFoundException e) { return false; }
    }

    /**
     * Записывает авторизационные данные в xml-файл
     */
    private void savePreferences() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("admin-preferences.xml");
            XMLEncoder xmlEncoder = new XMLEncoder(fileOutputStream);
            xmlEncoder.writeObject(preferencesInstance);
            xmlEncoder.flush();
            xmlEncoder.close();
        } catch (FileNotFoundException e) { e.printStackTrace(); }
    }

    /**
     * Проверка введённых пользователем данных при авторизации
     * @param name введённое пользователем имя администратора
     * @param password введённый пользователем пароль
     * @return true, если текущие авторизационные данные совпадают с введёнными, в противном случае - false
     */
    public Boolean preferencesEqual(String name, String password) {
        if(preferencesInstance == null) {
            if(!loadPreferences())
                preferencesInstance = new Preferences("admin","admin");
        }
        return preferencesInstance.getName().equals(name) && preferencesInstance.getPassword().equals(password);
    }

    /**
     * Назначает новые настройки для входа в систему
     * @param name новое имя администратора
     * @param password новый пароль
     */
    public void setPreferences(String name, String password) {
        if(name!=null)
            preferencesInstance.setName(name);
        if(password!=null)
            preferencesInstance.setPassword(password);
        savePreferences();
    }
}
