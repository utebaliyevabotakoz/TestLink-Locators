import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;


public class TestLinkSuites {

    @Test
    public void testCasesLink() {

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("http://localhost/testlink/login.php");

        //LoginPage
        driver.findElement(By.xpath("//input[@name='tl_login']")).sendKeys("admin");
        driver.findElement(By.xpath("//input[@name='tl_password']")).sendKeys("admin");
        driver.findElement(By.xpath("//input[@type ='submit']")).click();

/*
driver.switchTo().parentFrame();    // to move back to parent frame
driver.switchTo().defaultContent(); // to move back to most parent or main frame
 */
        driver.switchTo().frame(1);
        driver.findElement(By.xpath("//a[@href='lib/project/projectView.php']")).click();

        driver.findElement(By.xpath("//input[@name='create']")).click();
        driver.findElement(By.xpath("//input[@name='tprojectName']")).sendKeys("AutoTestJava");
        driver.findElement(By.xpath("//input[@name='tcasePrefix']")).sendKeys("qa");

        driver.switchTo().frame(0);
        driver.findElement(By.xpath("//body[@class='cke_editable cke_editable_themed cke_contents_ltr cke_show_borders']")).sendKeys("AutoProject was creatd by BUtebaliyeva");
        driver.switchTo().parentFrame();
        driver.findElement(By.xpath("//input[@name='doActionButton']")).click();

        WebElement searchContainer = driver.findElement(By.cssSelector("#item_view"));
        List<WebElement> rows = searchContainer.findElements(By.cssSelector("tbody > tr"));
        for (WebElement row : rows) {
            List<WebElement> columns = row.findElements(By.cssSelector("td"));
            if (columns.get(0).getText().equals("AutoTestJava")) {
                if (columns.get(1).getText().equals("AutoProject was creatd by BUtebaliyeva")) {
                    if (columns.get(2).getText().equals("qa"))
                        System.out.println("Yeah!!!!! Good!!! Fine!!! Finally!! ");
                    WebElement activ = driver.findElement(By.xpath("//input[@src='http://localhost/testlink/gui/themes/default/images/lightbulb.png']"));
                    String activTitle = activ.getAttribute("title");
                    assertThat(activTitle, startsWith("Active (click to set inactive)"));

                    WebElement publicyes = driver.findElement(By.xpath("//img[@src='http://localhost/testlink/gui/themes/default/images/accept.png']"));
                    String publicyesTitle = publicyes.getAttribute("title");
                    assertThat(publicyesTitle, startsWith("Public"));
                }
            }
        }
        if (driver != null) {
            driver.quit();
        }
    }
}