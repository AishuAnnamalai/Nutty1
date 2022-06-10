import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
public class multiclient 
{
  public static void main(String[] args) 
  {
	 
	String answer=" ";
	String result="";
	
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	try
	{
	    Socket socket=new Socket("127.0.0.1",8888);
	    DataInputStream inStream=new DataInputStream(socket.getInputStream());
	    
	    DataOutputStream outStream=new DataOutputStream(socket.getOutputStream());
	
	    
	    String line="";
	    
	    while(!line.equals("6"))
	    {
			try
			{
				DataInputStream din=new DataInputStream(socket.getInputStream());
				
				
				 System.out.println("1.CREATE");
				 System.out.println("2.READ");
				 System.out.println("3.UPDATE");
				 System.out.println("4.DELETE");
				 
				 System.out.println("5.QUIT");
				 
				line = reader.readLine();
				switch(line)
				{
				case "1":{
					answer=create();
					break;}
				case "2":{
					answer=search();
					break;}
				case "3":{
					answer=update();
					break;}
				case "4":{
					answer=delete();
					break;}
				default:{
					System.out.println("Wrong Option");
					
					break;}
					
				}
				
				outStream.writeUTF(answer);
				result=din.readUTF();
				System.out.println(result);
				
			}
			catch(IOException i)
			{
				System.out.println(i);
			}
	}
		try
		{
			inStream.close();
			outStream.close();
			socket.close();
		}
		catch(IOException i)
		{
			System.out.println(i);
		}
    }
	catch(Exception e)
	{
	    System.out.println(e);
	}
  }
 

public static String create()
{
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	String value=" ";
	System.out.println("Enter The details");
	String Id,newName,Email,Status;
	String Crperiod,Balance;
	System.out.println("\n\n\t\t\t\t\t\tADD RECORDS");
	String addchoice;
	try
	{
	do
	{
	    
	    int no;
		int x = 0;
		
		do
	    {
	    	System.out.println("Customer ID:");
			 Id =  reader.readLine();
	        
	        
			 File file = new File("customer.csv");

				if (!file.exists()) 
					{
						file.createNewFile();
					}


				RandomAccessFile raf = new RandomAccessFile(file, "rw");
				boolean found = false;

				
				String nameNumberString;
	        while (raf.getFilePointer() < raf.length()) 
			{

				nameNumberString = raf.readLine();

				String[] lineSplit = nameNumberString.split(",");


	             String id = lineSplit[0];
	             

				if (id == Id) 
				{
					found = true;
					break;
				}
			}
	        Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
	        Matcher match = pattern.matcher(Id);
	        boolean hassplchars = match.find();
	        if (found == true)
	        {
	        	System.out.println("\n\t\t\t\t\tThe CustomerId Already exist");
	            x++;
	            no = 1;
	        }
	      
	        else if (hassplchars == true)
	        {

	        	System.out.println("\n\t\t\t\t\tThe CustomerId consist spl characters");
	            x++;
	            no = 1;

	        }
	        else if (isNullOrSpaces(Id)==true)
	        {
	        	System.out.println("\n\t\t\t\t\tThe CustomerId is a required field cannot be blank");
	            x++;
	            no = 1;
	        }
	        else
	        {
	            no = 0;
	        }
	        
	        if (x >= 3) no = 0;
	    } while (no == 1);
	    if (x >= 3) break;
	    
	    //customer name
	    int xn = x;
	    do
	    {
	    	
	    	System.out.println("Customer Name:");
			 newName =  reader.readLine();
	    
	        int count = 0;
	        int i = newName.length() - 1;
	        
	       
	        if (isNullOrSpaces(newName)==true)
	        {
	        	System.out.println("\n\t\t\t\t\tThe CustomerName is a required field cannot be blank");
	            xn++;
	            no = 1;
	        }
	        Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
	        Matcher match = pattern.matcher(newName);
	        boolean haschars = match.find();
	        if (haschars == false)
	        {
	        	System.out.println("\n\t\t\t\t\tThe CustomerName contains other than alphabet");
	            xn++;
	            no = 1;
	        }
	        if ((newName.startsWith(" ")) || (count != 0))
	        {
	        	System.out.println("\n\t\t\t\t\tThe CustomerName cannot begin have leading or trailing space");
	            xn++;
	            no = 1;
	        }

	        if (xn >= 3) no = 0;
	    } while (no == 1);
	    if (xn >= 3) break;
	    //Customer Email ID
	    int xe = xn;
	    do
	    {
	    	System.out.println("Customer Email:");
			 Email =  reader.readLine();
			 
	        
	        if (isValid(Email))
	        { 
	            no = 0;
	        }
	        if (isValid(Email))
	        {
	        	System.out.println("\n\t\t\t\t\t\tInvalid email ID");
	            xe++;
	            no = 1;
	        }
	        if (isNullOrSpaces(Email)==true)
	        {
	        	System.out.println("\n\t\t\t\t\t\tThe CustomerEmail is a required field, cannot be blank");
	            xe++;
	            no = 1;
	        }
	       
	        if (xe >= 3) no = 0;
	    } while (no == 1);
	    if (xe >= 3) break;
	    System.out.println("Credit Period");
		Crperiod =reader.readLine();
	   
		Status="Wholesale";
		Balance ="0";
		value="create"+","+Id+","+newName+","+Email+","+Crperiod+","+Status+","+Balance;

	   
	    System.out.println("\n\t\t\t\tDo you Want to add more Records(Y/N/y/n)");
	    addchoice = reader.readLine();
	} while ((addchoice.equals("y")) || (addchoice.equals("Y")));
	
} 
	catch (IOException e) 
	{
		
		e.printStackTrace();
	}
	
	
	return value;
}
public static boolean isNullOrSpaces(String str){
    if(str == null || str.isBlank())
      return true;
    return false;
  }
public static boolean isValid(String email)
{
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                        "[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                        "A-Z]{2,7}$";
                          
    Pattern pat = Pattern.compile(emailRegex);
    if (email == null)
        return false;
    return pat.matcher(email).matches();
}
public static String search()
{
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	String value=" ",ID;
	
	 try
	 {
		System.out.println("enter the ID you wish to search");
		ID=reader.readLine();
		value="read"+","+ID;
	}
	 catch (IOException e) 
	 {
	
		e.printStackTrace();
		
	}
	return value;
}
public static String update()
{
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	String value=" ",ID,newname;
	
	 try
	 {
		System.out.println("enter the ID you wish to update");
		ID=reader.readLine();
		System.out.println("enter the new name for the ID"+ID+": ");
		newname=reader.readLine();
		value="update"+","+ID+","+newname;
	}
	 catch (IOException e) 
	{
		e.printStackTrace();	
	}
	return value;
}
public static String delete()
{
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	String value=" ",ID;
	
	 try
	 {
		System.out.println("enter the ID you wish to Delete");
		ID=reader.readLine();
		value="delete"+","+ID;
	}
	 catch (IOException e)
	 {
		 e.printStackTrace();
	 }
	return value;
}

  }





