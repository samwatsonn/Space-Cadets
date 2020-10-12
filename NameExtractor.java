import java.util.Scanner;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class NameExtractor {

    public static void main(String args[]){
        // Create scanner to get input from cmd line
        Scanner cmdInput = new Scanner(System.in);
        System.out.print(">>>");
        String email = cmdInput.nextLine();

        // Get just the id from the email
        String email_id = getStringBeforeChar(email, "@");

        try {
            Boolean name_found = false;

            // Create URL
            URL url = new URL("https://www.ecs.soton.ac.uk/people/" + email_id);

            // Create a BufferedReader object to read from page
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(url.openStream()));

            // Read through each line
            String line;
            while ((line = br.readLine()) != null)

                // Find the line that contains the name
                if(line.contains("property=\"name\">")) {

                    //Remove everything before name
                    line = line.substring(line.indexOf("property=\"name\">") + 16);

                    // Remove everything after name
                    line = getStringBeforeChar(line, "<");

                    // Print result
                    System.out.println(line);

                    name_found = true;
                }
            // Close the buffered reader
            br.close();

            if(!name_found){
                System.out.println("Name not found");
            }

        // Exception Handling
        }catch(MalformedURLException e){
            e.printStackTrace();
            System.out.println("Malformed URL Exception");
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("IO Exception");
        }
    }

    // Method to get part of string before a certain character/string occurs
    private static String getStringBeforeChar(String str, String character){

        if(str.contains(character)) {
            // Find the index of the character
            int index_end = str.indexOf(character);

            // Take everything before the character
            return str.substring(0, index_end);
        }else{
            return str;
        }

    }
}
