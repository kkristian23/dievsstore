package auto_save_img;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.net.URL;
import java.util.Scanner;

public class butikmerveaksoy extends links {
//    public static WebDriver driver = new ChromeDriver();
static WebDriver driver;
     static WebDriverWait wait;
    static   String  folderName ="poze";
    public static void main(String[] args) throws IOException, InterruptedException {

     download_img();
//        link("https://www.butikmerveaksoy.com/orgu-detayli-askili-keten-elbise-4680");
//        link("https://www.butikmerveaksoy.com/askili-firca-desenli-elbise-4669");
//        link("https://www.butikmerveaksoy.com/dantelli-oversize-gomlek");
//        link("https://www.butikmerveaksoy.com/adel-keten-dokulu-pantolon-bluz-takim-ekru");
//
//        for (int i = 0; i < link.size(); i++) {
//            download_img(link.get(i));
//        }
    }
    static int x=1;
      static void download_img() throws InterruptedException, IOException {

                  String file_descriere = "D:\\" + "link" + ".txt";
                  File descriere = new File(file_descriere);

                  if (descriere.exists()) {
                      WebDriver driver = new ChromeDriver();

                      try {
                          Scanner myReader = new Scanner(descriere);
                          while (myReader.hasNextLine()) {
                              String data = myReader.nextLine();
                              driver.get(data);
                              // Poți adăuga aici orice alte acțiuni dorești să faci cu pagina deschisă.
                              driver.findElement(By.xpath("//div/a/img[@class='wm-zoom-default-img lazyImage entered loaded']")).click();
                              Thread.sleep(1000);
                              WebElement nr_total_de_poze_gasite = driver.findElement(By.xpath("//div/span[@id='lg-counter-all']"));
                              int numarTotalPoze = Integer.parseInt(nr_total_de_poze_gasite.getText());
                              File create_folder_for_price_and_description = new File("D:\\new\\" + folderName + x);
                              if (!create_folder_for_price_and_description.exists()) {
                                  create_folder_for_price_and_description.mkdir();
                                  System.out.println("S-a creat Folder nou cu [ " + folderName + " ]");
                              }
                              for (int i = 1; i <= numarTotalPoze; i++) {
                                  Thread.sleep(500);
                                  WebElement imageElement = driver.findElement(By.xpath("//*[@id='bodyGlobal']/div[4]/div/div[1]/div[" + i + "]/div/img"));
                                  String imageUrl = imageElement.getAttribute("src");
                                  URL url2 = new URL(imageUrl);
                                  BufferedInputStream in = new BufferedInputStream(url2.openStream());
                                  String imagePath = "D:\\new\\" + folderName + x + "\\" + i + ".jpg";
                                  FileOutputStream fileOutputStream = new FileOutputStream(imagePath);
                                  Thread.sleep(500);
                                  byte[] dataBuffer = new byte[1024];
                                  int bytesRead;
                                  while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                                      fileOutputStream.write(dataBuffer, 0, bytesRead);
                                  }
                                  fileOutputStream.close();
                                  in.close();
                                  System.out.println("Download 999.md -> Photo # " + i);
                                  driver.findElement(By.xpath("//div/button[@class='lg-next lg-icon']")).click();
//                            driver.quit();
                              }
                              x++;
                          }
                          myReader.close();
                      } catch (FileNotFoundException e) {
                          e.printStackTrace();
                      } finally {
                          driver.quit(); // Închide driver-ul la sfârșitul buclei.
                      }
                  } else {
                      System.out.println("Fișierul " + file_descriere + " nu există. Trecând la următorul fișier.");
                  }


//        for (int x=1; x<=5;x++){



        }
}