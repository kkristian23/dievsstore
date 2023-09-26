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
    static String folderName = "poze";

    public static void main(String[] args) throws IOException, InterruptedException {
        download_img();
    }

    static int x = 1;

    static void download_img() throws InterruptedException, IOException {
        String file_descriere = "D:\\dievs_code\\dievs\\" + "link" + ".txt";
        File create_folder_for_new_photos = new File("D:\\dievs_code\\new_photos\\");
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
                    Thread.sleep(500);
                    WebElement nr_total_de_poze_gasite = driver.findElement(By.xpath("//div/span[@id='lg-counter-all']"));
//                              =======================================
//                              Create new photo Folder
                    if (!create_folder_for_new_photos.exists()) {
                        create_folder_for_new_photos.mkdir();
                        System.out.println("S-a creat Folder nou cu New_photos");
                    } else {
                        System.out.println("Folderul new_photos era deja creat.");
                    }
//                              END
//                              =====================================
                    int numarTotalPoze = Integer.parseInt(nr_total_de_poze_gasite.getText());

                    for (int i = 1; i <= numarTotalPoze; i++) {
                        Thread.sleep(500);
                        File create_folder_for_price_and_description = new File("D:\\dievs_code\\new_photos\\" + folderName + x);
                        if (!create_folder_for_price_and_description.exists()) {
                            create_folder_for_price_and_description.mkdir();
                            System.out.println("S-a creat Folder nou cu [ " + folderName + x + " ]");
                        } else {
                            System.out.println("Folderul " + folderName + x + " era deja creat.");
                        }
                        WebElement imageElement = driver.findElement(By.xpath("//*[@id='bodyGlobal']/div[4]/div/div[1]/div[" + i + "]/div/img"));
                        String imageUrl = imageElement.getAttribute("src");
                        URL url2 = new URL(imageUrl);
                        BufferedInputStream in = new BufferedInputStream(url2.openStream());

                        String imagePath = "D:\\dievs_code\\new_photos\\" + folderName + x + "\\" + i + ".jpg";
                        Thread.sleep(500);
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
                    }
                    x++;
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                driver.quit(); // Închide driver-ul la sfârșitul buclei.
                System.out.println("\nTotul s-a salvat cu succes!!");
            }
        } else {
            System.out.println("Fișierul " + file_descriere + " nu există. Trecând la următorul fișier.");
        }
    }
}