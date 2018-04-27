package scanner;
/**
 * @author Gabriel Bergstrom
 */
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class MyScannerMain
{

    public static void main( String[] args)
    {
        String filename = args[0];
        System.out.println(filename);

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert fis != null;
        InputStreamReader isr = new InputStreamReader(fis);

        MyScanner myScanner = new MyScanner(isr);
        Token aToken = null;
        do
        {
            try {
                aToken = myScanner.nextToken();
            }
            catch( Exception e) { e.printStackTrace();}
        } while( aToken != null);
        try {
            myScanner.yyclose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

