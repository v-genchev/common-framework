package helpers;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.*;

public class PropertiesCache {

    private final PropertiesConfiguration properties = new PropertiesConfiguration();

    private PropertiesCache() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("config.properties");
        InputStreamReader reader = new InputStreamReader(in);
        try {
            properties.read(reader);
        } catch (ConfigurationException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class LazyHolder {
        private static final PropertiesCache INSTANCE = new PropertiesCache();
    }

    public static PropertiesCache getInstance() {
        return LazyHolder.INSTANCE;
    }

    public String getProperty(String key) {
        return properties.getString(key);
    }
}
