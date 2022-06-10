
import java.net.*;
import java.io.*;
public class multi
{
  public static void main(String[] args)
  {
    try
    {
      ServerSocket server=new ServerSocket(8888);
      int counter=0;
      System.out.println("Server Started ....");
      while(true)
      {
        counter++;
        Socket serverClient=server.accept();  //server accept the client connection request
        System.out.println(" >> " + "Client No:" + counter + " started!");
        ServerClientThread sct = new ServerClientThread(serverClient,counter); //send  the request to a separate thread
        sct.start();
      }
    }
    catch(Exception e)
    {
      System.out.println(e);
    }
  }
}
class ServerClientThread extends Thread 
{
	  Socket serverClient;
	  int clientNo;
	  int squre;
	  public static String result="";
	  ServerClientThread(Socket inSocket,int counter)
	  {
	    serverClient = inSocket;
	    clientNo=counter;
	  }
	  public void run()
	  {
	    try
	    {
	      DataInputStream inStream = new DataInputStream(serverClient.getInputStream());
	      DataOutputStream outStream = new DataOutputStream(serverClient.getOutputStream());
	      String line="";
	      while(!line.equals("6"))
	      {
	    	  try
				{
					line = inStream.readUTF();
					String[] lineSplits = line.split(",");
					String First=lineSplits[0];
					System.out.println(First);
					if(First.equals("create")) 
					{
						Screate(lineSplits);
						outStream .writeUTF(result);
						outStream .flush();
						break;
					}
					else if(First.equals("read"))
					{
						Sread(lineSplits);
						outStream .writeUTF(result);
						outStream .flush();
					}
					else if(First.equals("update"))
					{
						Supdate(lineSplits);
						outStream .writeUTF(result);
						outStream .flush();
					}
					else if(First.equals("delete"))
					{
						Sdelete(lineSplits);
						outStream .writeUTF(result);
						outStream .flush();
					}
					else
					{
						result="Error";
						outStream .writeUTF(result);
						outStream .flush();
					}
				
					
				}
				catch(IOException i)
				{
					System.out.println(i);
				}
			 
	      }
	      inStream.close();
	      outStream.close();
	      serverClient.close();
	    }
	    catch(Exception ex)
	    {
	      System.out.println(ex);
	    }
	    finally
	    {
	      System.out.println("Client -" + clientNo + " exit!! ");
	    }
	  }
	  	public static void Screate(String[] lineSplits)
		{
			
			String id;
			String Id=lineSplits[1];
			String newName=lineSplits[2];
			String Email=lineSplits[3];
			String Crperiod=lineSplits[4];
			String Status=lineSplits[5];
			String Balance=lineSplits[6];
			try
			{
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

			        id= lineSplit[0];

					if (id == Id) 
						{
							found = true;
							break;
						}
				 }

			 if (found == false) 
				{

					nameNumberString = Id+","+newName + ","+Email+"," + Crperiod+","+Status+","+Balance;

					raf.writeBytes(nameNumberString);

					raf.writeBytes(System.lineSeparator());
					result="Customer added";
					raf.close();
						
				}

			else 
				{
					raf.close();
					System.out.println(" Input ID" + " already exists. ");
					result="error";
		
				}
			 
		
			} 

		catch (IOException ioe) 
		{

			System.out.println(ioe);
			result="error";
		
		}
		catch (NumberFormatException nef) 
		{

			System.out.println(nef);
			result="error";
			
		}
			
	}
	  	public static void Sread(String[] lineSplits)
		{
			
			String Id=lineSplits[1];
			try {
				 
		         String nameNumberString;
		         String id="",name="",email="",crperiod="",status="",balance="";
		         long number;
		         String namer;
		         int index;

		         File file = new File("customer.csv");
		         if (!file.exists()) 
		         {

		             file.createNewFile();
		         }

		         RandomAccessFile raf = new RandomAccessFile(file, "rw");
		         boolean found = false;

		         while (raf.getFilePointer() < raf.length()) 
		         {

		             nameNumberString = raf.readLine();

		             String[] lineSplit= nameNumberString.split(",");

		             id= lineSplit[0];
		             name=lineSplit[1];
		             email=lineSplit[2];
		             crperiod=lineSplit[3];
		             status=lineSplit[4];
		             balance=lineSplit[5];
		            
		             if(id.equals(Id))
		             {
		            	 result=nameNumberString;
		             }
		             else
		             {
		            	 result="notfound"; 
		             }
		            
		          }
				}
		             catch (IOException ioe)
		             {
		  
		                 System.out.println(ioe);
		                 result="ioe error";
		             }
		             catch (NumberFormatException nef)
		             {
		  
		                 System.out.println(nef);
		                 result="nef error";
		             }
		
		}
		public static void Supdate(String[] lineSplits)
		{
			try 
			 {
				String Id=lineSplits[1];
				String newName=lineSplits[2];

		         String nameNumberString;
		         String idupdate="",name="",email="",crperiod="",status="",balance="",namer;
		         long number;
		         int index;
		         
		         File file = new File("customer.csv");
		         
		         if (!file.exists()) 
		         {

		             file.createNewFile();
		         }

		         RandomAccessFile raf = new RandomAccessFile(file, "rw");
		         boolean found = false;

		         while (raf.getFilePointer() < raf.length()) 
		         {
		         	 
		             nameNumberString = raf.readLine();

		             String[] lineSplit = nameNumberString.split(",");

		             idupdate= lineSplit[0];
		             name=lineSplit[1];
		             email=lineSplit[2];
		             crperiod=lineSplit[3];
		             status=lineSplit[4];
		             balance=lineSplit[5];
		             
		             //number = Long.parseLong(lineSplit[1]);

		             if (idupdate == Id)
		             {
		             	
		             	 found = true;
		                  break;
		              }
		          }

		          if (found == false) 
		          {

		              File tmpFile = new File("temp.csv");

		              RandomAccessFile tmpraf = new RandomAccessFile(tmpFile, "rw");

		              raf.seek(0);

		              while (raf.getFilePointer()< raf.length()) 
		              {

		                  nameNumberString = raf.readLine();

		                  index = nameNumberString.indexOf(',');
		                  namer = nameNumberString.substring(0, index);

		                  if (namer.equals(Id))
		                  {

		                      nameNumberString = idupdate + ","+ newName+","+email+","+crperiod+","+status+","+balance;
		                  }

		                  tmpraf.writeBytes(nameNumberString);

		                  tmpraf.writeBytes( System.lineSeparator());
		              }

		              raf.seek(0);
		              tmpraf.seek(0);

		              while (tmpraf.getFilePointer() < tmpraf.length()) 
		              {
		                  raf.writeBytes(tmpraf.readLine());
		                  raf.writeBytes(System.lineSeparator());
		              }

		              raf.setLength(tmpraf.length());

		              tmpraf.close();
		              raf.close();

		              tmpFile.delete();
		              result="Customer detail updated";

		              
		          }

		          else 
		          {

		              raf.close();
		              result=" Input name"+ " does not exists. ";
		          }
		      }

		      catch (IOException ioe) {
		    	  result="ioe error";
		          System.out.println(ioe);
		      }

		      catch (NumberFormatException nef) {
		    	  result="nef error";
		          System.out.println(nef);
		      }
		}
		public static void Sdelete(String[] lineSplits)
		{
			
			String ID=lineSplits[1];
			try {
				 
		        
				 
		           //long newNumber = Long.parseLong(reader.readLine());
		           String nameNumberString;
		           
		           long number;
		           int index;
		           String id="",name="",email="",crperiod="",status="",balance="",namer;
		           File file = new File("customer.csv");

		           if (!file.exists())
		           {
		               file.createNewFile();
		           }

		           RandomAccessFile raf= new RandomAccessFile(file, "rw");
		           boolean found = false;

		           while (raf.getFilePointer() < raf.length()) 
		           {

		               nameNumberString = raf.readLine();

		               String[] lineSplit= nameNumberString.split(",");

		               id= lineSplit[0];
		               name=lineSplit[1];
		               email=lineSplit[2];
		               crperiod=lineSplit[3];
		               status=lineSplit[4];
		               balance=lineSplit[5];

		               if (id == ID) 
		               {
		                   found = true;
		                   break;
		               }
		           }


		           if (found == false) 
		           {

		               File tmpFile = new File("temp.csv");

		               RandomAccessFile tmpraf = new RandomAccessFile(tmpFile, "rw");

		               raf.seek(0);

		               while (raf.getFilePointer() < raf.length())
		               {

		                   nameNumberString = raf.readLine();

		                   index = nameNumberString.indexOf(',');
		                   namer = nameNumberString.substring(0, index);

		                   if (namer.equals(ID)) 
		                   {

		                       continue;
		                   }

		                   tmpraf.writeBytes(nameNumberString);

		                   tmpraf.writeBytes(System.lineSeparator());
		               }

		               raf.seek(0);
		               tmpraf.seek(0);

		               while (tmpraf.getFilePointer()< tmpraf.length()) 
		               {
		                   raf.writeBytes(tmpraf.readLine());
		                   raf.writeBytes(System.lineSeparator());
		               }

		               raf.setLength(tmpraf.length());

		               tmpraf.close();
		               raf.close();

		               tmpFile.delete();

		               result=" Customer record deleted. ";
		           }

		           else 
		           {

		               raf.close();
		               result=" Input name does not exists. ";
		           }
		       }

		       catch (IOException ioe) 
			 	{
		           
		           result="ioe error";
		        }
			}
		
	}




