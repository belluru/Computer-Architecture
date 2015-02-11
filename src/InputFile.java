import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputFile extends MainClass
{
	
	private Scanner scan;
	public InputFile(String s)
	{
		fileopen_for_reading(s);
		readdata();
	}
	
	public void fileopen_for_reading(String s)
	{
		
		try
		{
			scan= new Scanner((new File(s)));
			//System.out.println("File opened sucessfully");
		}catch(Exception e)
		{
			System.out.println("no");
		}

	}	

	public void readdata()
	{
		find_I_register();
		Read_all_I_Registers();
		Read_all_FP_Registers();
		Read_all_Memory_values();	
		Read_all_Code();
	}
	
	public boolean find_I_register()
	{
	boolean pattern_matched=false;
	String string = scan.next(); 
	if(string.equals("I-REGISTERS"))
		{
			pattern_matched=true;
			System.out.println("Matched I-REGISTERS");
		}	
	return pattern_matched;
	}
	
	public boolean Read_all_I_Registers() 
	{	
		Pattern p = Pattern.compile("((R)((1(\\d)|2(\\d)|3[0-1]|((\\d)(\\s)))))");	
		boolean pattern_matched=false;
		boolean identify_register = true;
		int temp_reg_reference=0;
		int value_reg_reference=0;
		while(scan.hasNext() && !pattern_matched)
		{
			String strin = scan.next(); 
			String string = strin+" "; 
			if(string.equals("FP-REGISTERS "))
			{
				pattern_matched=true;
				System.out.println("FP-REGISTERS --> Matched");
			}
				else 
				{
					if(identify_register)
					{
						Matcher match=p.matcher(string);
						if(match.find())
						{
						//the string is FP Register value...extract the value of the register
						//System.out.print(string);
						Pattern register_pattern = Pattern.compile("(1(\\d)|2(\\d)|3[0-1]|(\\d))");	
						Matcher match_it_to_register=register_pattern.matcher(string);
						if(match_it_to_register.find())
							temp_reg_reference=Integer.parseInt(match_it_to_register.group());
						identify_register=false;
						}
					}	
					else if(!identify_register)
						{
							identify_register=true;
							value_reg_reference=Integer.parseInt(strin);
							//System.out.print("temp_reg_reference "+ temp_reg_reference+" ");
							//System.out.println("value_reg_reference "+value_reg_reference);
							intValues.add(temp_reg_reference,value_reg_reference);
						}
				}			
				
		}
		return pattern_matched;
		}
	
	public boolean Read_all_FP_Registers()
	{
		Pattern p = Pattern.compile("((F)((1(\\d)|2(\\d)|3[0-1]|((\\d)(\\s)))))");		
		boolean pattern_matched=false;
		boolean identify_register = true;
		int temp_reg_reference=0;
		float value_reg_reference=0;
		while(scan.hasNext() && !pattern_matched)
		{			
			String strin = scan.next(); 
			String string = strin+" "; 		
			if(string.equals("MEMORY "))
			{
				pattern_matched=true;
				System.out.println("MEMORY --> Matched");
			}
			else
			{
				if(identify_register)
				{
					Matcher match=p.matcher(string);
					if(match.find())
					{
						//the string is FP Register value...extract the value of the register
						//System.out.print(string);
						Pattern register_pattern = Pattern.compile("(1(\\d)|2(\\d)|3[0-1]|(\\d))");	
						Matcher match_it_to_register=register_pattern.matcher(string);
						if(match_it_to_register.find())
							temp_reg_reference=Integer.parseInt(match_it_to_register.group());
						identify_register=false;
						
					}
				}
				else if(!identify_register)
				{
					identify_register=true;
					value_reg_reference=Float.parseFloat(strin);
					F_Register.add(temp_reg_reference,value_reg_reference);
				}
			}
			
		}
	return pattern_matched;	
	}
		
	public boolean Read_all_Memory_values()
	{
		Pattern p = Pattern.compile("(\\d)+");				
		boolean pattern_matched=false;
		boolean identify_Memory_Location = true;
		Integer temp_Memory_reference =0;
		Float value_to_Memory_Location=0f;
		while(scan.hasNext() && !pattern_matched)
			{
				String string = scan.next();
				//System.out.println(string+" "+identify_Memory_Location);
				if(string.equals("CODE"))
				{
					pattern_matched=true;
					System.out.println("CODE --> Matched");
				}
				else
				{
					if(identify_Memory_Location)
					{
						Matcher match=p.matcher(string);
						if(match.find() && Integer.parseInt(string)%8==0 && Integer.parseInt(string)<1000)
						{
							//the string is  MemoryLocation...
							temp_Memory_reference = Integer.parseInt(string); 
							identify_Memory_Location=false;	
						}
					}
					else if(!identify_Memory_Location)
					{
						identify_Memory_Location=true;
						value_to_Memory_Location=Float.parseFloat(string);				
						Memory_Content_Modified.add(temp_Memory_reference);
						Memory_Content.set(temp_Memory_reference,value_to_Memory_Location);	
						//System.out.println(Memory_Contented.get(temp_Memory_reference));
					}
				}			
			}		
	return pattern_matched;	
	}
	
	public void Read_all_Code()
	{
		while(scan.hasNext())
		{
			line_input.add(scan.nextLine());
		}
		
	}
	
	public void closed()
	{
		//scan.close();
	}

}

