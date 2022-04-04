import com.thoughtworks.gauge.Logger;
import com.thoughtworks.gauge.Step;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class BasePage extends BaseTest {

    MobileElement element;

    @Step("<saniye> saniye kadar bekle")
    public void wait(int second) throws InterruptedException {
        Thread.sleep(1000 * second);
    }

    @Step("<id> id'li elementin sayfada gorunur oldugunu kontrol et ve tıkla")
    public void findByIdAndClick(String id) {
        element = appiumDriver.findElement(By.id(id));
        if (element.isDisplayed()) {
            element.click();
            Logger.info("Ilgili elemente tiklandi");
        } else {
            Logger.info("Kontrol edilen element bulunamadi  <--");
        }
    }

    @Step("<xpath> XPath'li elementi bul ve tıkla")
    public void findByXpathAndClick(String xpath) {
        element = appiumDriver.findElement(By.xpath(xpath));
        if(element.isDisplayed()){
            element.click();
            Logger.info("Elemente tiklandi");
        }else
        {
            Logger.info("Kontrol edilen element bulunamadi -->");
        }
    }

    @Step("<id> id'li elementi bul ve <text> yazan alanı kontrol et")
    public void textControlById(String id, String text) {
        Assert.assertTrue("Ilgili icerik bulunamadi", appiumDriver.findElement(By.id(id)).getText().contains(text));
        Logger.info("Ilgili icerik bulundu -> text: " + text);
    }

    @Step("<xpath> xpath'li elementi bul ve <text> yazan alanı kontrol et")
    public void textControlByXpath(String xpath, String text) {
        Assert.assertTrue("Ilgili icerik bulunamadi", appiumDriver.findElement(By.xpath(xpath)).getText().contains(text));
        Logger.info("Ilgili icerik bulundu -> text: " + text);
    }

    @Step("Sayfayı aşağı doğru kaydır")
    public void swipeUp() {
        //final int ANIMATION_TIME = 200; // ms
        final int PRESS_TIME = 200; // ms
        int edgeBorder = 10; // better avoid edges
        PointOption pointOptionStart, pointOptionEnd;
        // init screen variables
        Dimension dims = appiumDriver.manage().window().getSize();
        // init start point = center of screen
        pointOptionStart = PointOption.point(dims.width / 2, dims.height / 2);
        pointOptionEnd = PointOption.point(dims.width / 2, dims.height / 6);
        try {
            new TouchAction(appiumDriver)
                    .press(pointOptionStart)
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
                    .moveTo(pointOptionEnd)
                    .release().perform();
            Logger.info("Sayfa asagi dogru kaydirildi");
        } catch (Exception e) {
            System.err.println("swipeScreen(): TouchAction FAILED\n" + e.getMessage());
            return;
        }

        /*// always allow swipe action to complete
        try {
            Thread.sleep(ANIMATION_TIME);
        } catch (InterruptedException e) {
            // ignore
        }*/
    }

    @Step("<xpath> xpathi verilen elementlerden birine rastgele tikla")
    public void clickRandomElementByXpath(String xpath) {
        Random random = new Random();
        List<MobileElement> elements = appiumDriver.findElements(By.xpath(xpath));
        int index = random.nextInt(elements.size());
        elements.get(index).click();
        Logger.info("Elementlerden biri rastgele secildi");
    }

    @Step("<id> id'si verilen elementi bul ve <text> değerini yaz")
    public void sendKeysById(String id, String text) {
        appiumDriver.findElement(By.id(id)).sendKeys(text);
        Logger.info("Element bulundu ve " + text + " degeri yazildi");
    }

}
