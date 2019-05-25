// RAISAAT ATIFA RASHID

import BSTree.*;
import java.util.*;
import java.io.*;

public class Main 
{
    public static void main (String[] args) throws IOException
    {
        // Create a new RedboxBST type object
        RedboxBST bst = new RedboxBST ();
        
        // Create a Scanner to read data from the inventory file
        Scanner inFile = new Scanner(new File("inventory.dat"));
        
        // While the inventory file has a next line
        while (inFile.hasNext())
        {
            // Read the line from the inventory file and tokenize it around ", and "
            String[] tokens = inFile.nextLine().split("\",|\"");
            
            // Tokenize the stirng containing the no. available and the no. rented around ,
            String[]tokens1 = tokens[2].split(",");
            
            // Create a new node with the tokens and insert it into the bst
            bst.insertNode(tokens[1], Integer.parseInt(tokens1[0]), Integer.parseInt(tokens1[1]));
        }
        // Close the inventory file
        inFile.close();
        
        // Create a StringBuilder type object for invalid strings
        StringBuilder errorStr = new StringBuilder();
        
        // Open the transaction file for reading
        inFile = new Scanner(new File("transaction.log"));
       
        // While the transaction file has a next line
        while (inFile.hasNext())
        {
            // Read the string from the transaction file into a String object
            String str = inFile.nextLine();
            
            // If the string read in is of the correct format
            if (validateFormat(str))
            {
                // Tokenize it
                String[] tokens = str.split(" \"|\",|\"");
                
                // If the first token is "add", add the movie to the inventory
                if (tokens[0].equals("add"))
                    bst.add(tokens[1],Integer.parseInt(tokens[2]));
                
                // Else if the first token is "remove", remove it from the inventory
                else if (tokens[0].equals("remove"))
                    bst.remove(tokens[1],Integer.parseInt(tokens[2]));
                
                // Else if the first token is "rent", call the bst's rent function
                else if (tokens[0].equals("rent"))
                    bst.rent(tokens[1]);
                
                // Else, call the bst's Return function
                else
                    bst.Return(tokens[1]);
            }
            // If the string read in is not of the correct format
            else
            {
               // If it is not the first incorrect string, append a newline character to errorStr
                if (!(errorStr.toString().equals("")))
                   errorStr.append('\n');
               
               // Append the incorrect string to errorStr 
               errorStr.append(str);
            } 
        }
        // Close the transaction file
        inFile.close();
        
        // If errorStr does not conatin an empty string
        if (!(errorStr.toString().equals("")))
        {
            // Create a PrintWriter object for the error file
            PrintWriter outFile = new PrintWriter("error.log");
            
            // Write the contents of errorStr to the file
            outFile.println(errorStr.toString());
            
            // Close the error file
            outFile.close();
        }
        
        // Create a PrintWriter object for redbox_kiosk.txt
        PrintWriter outFile = new PrintWriter("redbox_kiosk.txt");
        
        // Write the report's column headings to redbox_kiosk.txt
        outFile.printf("%-40s%-13s%s\n", "Title", "No. Available", "No. Rented");
        
        // Call function writeReport to write the report
        writeReport(bst.getRoot(), outFile);
        
        // Close redbox_kiosk.txt
        outFile.close();
    }
    
    // function validateFormat checks whether a line of the inventory file is of the correct format
    public static boolean validateFormat (String str)
    {
        // If the last two characters of the string are a space and a " return false
        if (str.charAt(str.length() - 2) == ' ' && str.charAt(str.length() - 1) == '"')
           return false;
        
        // tokenize the string around " \""
        String[] tokens = str.split(" \"");
       
        // If the no. of tokens is not equal to 2, return false
        if (tokens.length != 2)
            return false;
        
        // If the first token is "add" or "remove"
        if (tokens[0].equals("add") || tokens[0].equals("remove"))
        {
            // If the second to last character is " or the last character is , return false
            if (str.charAt(str.length() - 2) == '"' || str.charAt(str.length() - 1) == ',')
                return false;
            
            // Tokenize the second token around ",
            tokens = tokens[1].split("\",");
            
            // If the no. of tokens produced as a result of the previous statement is not equal to 2, return false
            if (tokens.length != 2)
                return false;
            
            // Call function isInteger to determine whether the second token represents an integer,
            // and return the boolean value that function isInteger returns
            return isInteger(tokens[1]);
        }
        // If the first token is "rent" or "return", determine whether the last character is "
        // If it is, return true, else return false.
        if (tokens[0].equals("rent") || tokens[0].equals("return"))
            return (str.charAt(str.length() - 1) == '"');
        
        // Return false
        return false;
    }
    
    // function isInteger determines whether a string represents an integer
    public static boolean isInteger (String s)
    {
        for (int i = 0; i < s.length(); i++)
        {
            // if the character in the string is not a digit, return false
            if (s.charAt(i) < '0' || s.charAt(i) > '9')
                return false;
        }
        // return true
        return true;
    }
    
    // function writeReport recursively writes a report on the status of the inventory to a file
    public static void writeReport (Node curr, PrintWriter outfile)
    {
        // Return if curr is equal to null
        if (curr == null)
            return;
        
        // Call the function itself with curr's left node and the PrintWriter parameter as arguments
        writeReport(curr.getLeft(), outfile);
        
        // Write the title, the no. available and the no. rented of the current node
        outfile.printf("%-40s%-19d%d\n", curr.getTitle(), curr.getAvailable(), curr.getRented());
        
        // Call the function itself with curr's right node and the PrintWriter parameter as arguments
        writeReport(curr.getRight(), outfile);
    }
}
