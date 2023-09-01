package auto_save_img;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class havos extends links {
    static WebDriver driver;
    static WebDriverWait wait;
    static String folderName = "poze";

    public static void main(String[] args) throws IOException, InterruptedException {
        int x = 1;
        String file_descriere = "D:\\" + "link" + ".txt";
        File descriere = new File(file_descriere);
        driver = new ChromeDriver();
        Actions actions = new Actions(driver);
        try {
            Scanner myReader = new Scanner(descriere);
            while (myReader.hasNextLine()) {
                if (descriere.exists()) {
                    File create_folder_for_price_and_description = new File("D:\\new\\" + folderName + x);
                    if (!create_folder_for_price_and_description.exists()) {
                        create_folder_for_price_and_description.mkdir();
                        System.out.println("S-a creat Folder nou cu [ " + folderName + " ]");
                    }
                } else {
                    System.out.println("Fișierul " + file_descriere + " nu există. Trecând la următorul fișier.");
                }
                String data = myReader.nextLine();
                driver.get(data);
                driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());
                Thread.sleep(1000);
                List<WebElement> listAutomobile = driver.findElements(By.xpath("//div/div/img[@id='imgurunresmi']"));
                int count_total = listAutomobile.size();
                int max_taburi = count_total;
                List<WebElement> firstFiveAutomobiles = listAutomobile.subList(0, max_taburi);
                int anunturiDeschise = 0;
                int count = 0;
                for (WebElement renewButton : firstFiveAutomobiles) {
                    actions.keyDown(Keys.CONTROL).click(renewButton).keyUp(Keys.CONTROL).build().perform();
                    count++;
                    anunturiDeschise++;
                    System.out.println(count);
                    Thread.sleep(1000);
                    WebElement curentElement = driver.findElement(By.xpath("/html[@id='htmlGlobal']/body[@id='bodyGlobal']/form[@id='formGlobal']/div[@id='divIcerik']/div[@id='mainHolder_divBlocks']/div[@id='divCenterBlock']/div[2]/div/div[@id='contentProductDetail']/div[@class='ProductDetail']/div[@id='ProductDetailMain']/div[@class='TopDet']/div[@class='leftImage']/div[@id='divThumbList']/div[@class='AltImgCapSmallImg'][" + count + "]/img[@id='imgurunresmi']"));
                    String imageUrl = curentElement.getAttribute("src");
                    URL url2 = new URL(imageUrl);
                    BufferedInputStream in = new BufferedInputStream(url2.openStream());
                    String imagePath = "D:\\new\\" + folderName + x + "\\" + count + ".jpg";
                    FileOutputStream fileOutputStream = new FileOutputStream(imagePath);
                    Thread.sleep(500);
                    byte[] dataBuffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                        fileOutputStream.write(dataBuffer, 0, bytesRead);
                    }
                    fileOutputStream.close();
                    in.close();
                    System.out.println("Download 999.md -> Photo # " + count);
                }
                x++;
                System.out.println("x =" + x);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            driver.quit(); // Închide driver-ul la sfârșitul buclei.
        }
    }
}
