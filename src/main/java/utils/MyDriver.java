package utils;

import io.appium.java_client.android.AndroidDriver;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import net.thucydides.core.webdriver.DriverSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class MyDriver implements DriverSource {

  private static final Properties CONFIG = loadConfiguration();
  private static AndroidDriver driver;

  public static AndroidDriver getDriver() {
    return driver;
  }

  @Override
  public WebDriver newDriver() {
    try {
      DesiredCapabilities caps = new DesiredCapabilities();

      setCapability(caps, "automationName", "appium.automationName", "UiAutomator2");
      setCapability(caps, "platformName", "appium.platformName", "Android");
      setCapability(caps, "appPackage", "appium.appPackage", "com.whatsapp");
      setCapability(caps, "appActivity", "appium.appActivity", ".Main");
      setBooleanCapability(caps, "noReset", "appium.noReset", true);
      setBooleanCapability(caps, "fullReset", "appium.fullReset", false);
      setBooleanCapability(caps, "autoGrantPermissions", "appium.autoGrantPermissions", true);
      setBooleanCapability(caps, "autoDismissAlerts", "appium.autoDismissAlerts", true);
      setIntegerCapability(caps, "newCommandTimeout", "appium.newCommandTimeout", 3000);

      String app = property("appium.app", "").trim();
      if (!app.isEmpty()) {
        caps.setCapability("app", app);
      }

      String udid = property("appium.udid", "").trim();
      if (!udid.isEmpty()) {
        caps.setCapability("udid", udid);
      }

      URL hub = new URL(property("appium.hub", "http://127.0.0.1:4723/wd/hub"));
      driver = new AndroidDriver(hub, caps);
      long implicitWaitMillis = longProperty("webdriver.timeouts.implicitlywait", 3000L);
      driver.manage().timeouts().implicitlyWait(implicitWaitMillis, TimeUnit.MILLISECONDS);
      return driver;

    } catch (Exception e) {
      throw new RuntimeException("Error iniciando el driver de Appium", e);
    }
  }

  @Override
  public boolean takesScreenshots() {
    return true;
  }

  private static void setCapability(
      DesiredCapabilities caps, String capability, String key, String defaultValue) {
    caps.setCapability(capability, property(key, defaultValue));
  }

  private static void setBooleanCapability(
      DesiredCapabilities caps, String capability, String key, boolean defaultValue) {
    caps.setCapability(capability, Boolean.parseBoolean(property(key, String.valueOf(defaultValue))));
  }

  private static void setIntegerCapability(
      DesiredCapabilities caps, String capability, String key, int defaultValue) {
    caps.setCapability(capability, Integer.parseInt(property(key, String.valueOf(defaultValue))));
  }

  private static long longProperty(String key, long defaultValue) {
    return Long.parseLong(property(key, String.valueOf(defaultValue)));
  }

  private static String property(String key, String defaultValue) {
    return System.getProperty(key, CONFIG.getProperty(key, defaultValue)).trim();
  }

  private static Properties loadConfiguration() {
    Properties properties = new Properties();
    Path configuration = Paths.get(System.getProperty("user.dir"), "serenity.properties");
    try (InputStream input = Files.newInputStream(configuration)) {
      properties.load(input);
      return properties;
    } catch (IOException e) {
      throw new IllegalStateException("No fue posible leer serenity.properties", e);
    }
  }
}
