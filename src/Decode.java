import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Decode extends MainClass
{
	private int line_number;
	public static boolean occupied;
	private boolean is_this_stage_completed;
	private boolean is_in_scope;
	private int cc;
	private int noOfStalls;
	
    private String instruction_word;
	private String operand1;
	private String operand2;
	private String operand3;
	private float value1;
	private float value2;
	private float value3;
	private boolean is_instruction_Store;
	private boolean is_instruction_Load;
	private boolean is_instruction_Add;
	private boolean is_instruction_Multiply;
	private boolean is_instruction_Subtraction;
	private boolean has_a_negative_offset;
	Decode(int from_constructor)
	{
		noOfStalls=0;
		cc=0;
		is_this_stage_completed=false;
		is_in_scope=false;
	}
	public int getNoOfStalls() {
		return noOfStalls;
	}
	public void setNoOfStalls(int noOfStalls) {
		this.noOfStalls = noOfStalls;
	}
	public int getcc() {
		return cc;
	}
	public void setcc(int cc) {
		this.cc = cc;
	}
	Decode(boolean from_constructor,String string_from_constructor,int line_Number)
	{
		line_number=line_Number;
		occupied=true;
		is_in_scope=true;
		is_this_stage_completed=true;
		Scanner scan;
		scan=new Scanner(string_from_constructor);
		Instruction_Sequence.set(line_Number, Instruction_Sequence.get(line_Number)+"ID        ");
		instruction_word=scan.next();
		switch(instruction_word)
		{
		 
		case "S.D":
			{
			operand1=find_register_with_offest_address(scan.next());  //operand2 offset,operand 1 registerR
			operand3=find_operand_without_comma(scan.next());   
			is_instruction_Store=true;
			is_instruction_Load=false;
			is_instruction_Add=false;
			is_instruction_Multiply=false;
			is_instruction_Subtraction=false;
			}
			break;
			
		case "L.D":
			{
			operand1=find_operand_with_comma(scan.next());
			operand3=find_register_with_offest_address(scan.next());  //operand2 offset,operand 3 registerR
			is_instruction_Store=false;
			is_instruction_Load=true;
			is_instruction_Add=false;
			is_instruction_Multiply=false;	
			is_instruction_Subtraction=false;
			}	
			break;
		
		case "ADD.D":
			operand1=find_operand_with_comma(scan.next());
			operand2=find_operand_with_comma(scan.next());
			operand3=find_operand_without_comma(scan.next());
			is_instruction_Store=false;
			is_instruction_Load=false;
			is_instruction_Add=true;
			is_instruction_Multiply=false;
			is_instruction_Subtraction=false;
			break;
		case "SUB.D":
			operand1=find_operand_with_comma(scan.next());
			operand2=find_operand_with_comma(scan.next());
			operand3=find_operand_without_comma(scan.next());
			is_instruction_Store=false;
			is_instruction_Load=false;
			is_instruction_Add=false;
			is_instruction_Multiply=false;
			is_instruction_Subtraction=true;
			break;
		case "MUL.D":
			operand1=find_operand_with_comma(scan.next());
			operand2=find_operand_with_comma(scan.next());
			operand3=find_operand_without_comma(scan.next());
			is_instruction_Store=false;
			is_instruction_Load=false;
			is_instruction_Add=false;
			is_instruction_Multiply=true;
			is_instruction_Subtraction=false;
			break;
		
		default:
			break;
		}
		close(scan);
	}
	private void close(Scanner s)
	{
		//s.close();
	}
	
	private String find_operand_with_comma(String s)
	{
			String temp=s;
			String return_string=null;
			Pattern p=Pattern.compile("F(1(\\d)|2(\\d)|3[0-1]|((\\d)(,)))");
			Matcher match=p.matcher(temp);
			if(match.find())
				{
				Pattern p1=Pattern.compile("(1(\\d)|2(\\d)|3[0-1]|((\\d)))");
				Matcher matched=p1.matcher(temp);
					if(matched.find())
						return_string=matched.group();	
				}
			return return_string;
	}
	private String find_operand_without_comma(String s)
	{
			String temp=s;
			String return_string=null;
			Pattern p=Pattern.compile("F(1(\\d)|2(\\d)|3[0-1]|(\\d))");
			Matcher match=p.matcher(temp);
			if(match.find())
				{
				Pattern p1=Pattern.compile("1(\\d)|2(\\d)|3[0-1]|(\\d)");
				Matcher matched=p1.matcher(temp);
					if(matched.find())
						return_string=matched.group();	
				}
			return return_string;
	}
	
	private String find_register_with_offest_address(String s)
	{
		String temp=null;
		String temp1=null;
		Pattern p=Pattern.compile("-{0,1}(\\d+)\\({1}R(1(\\d)|2(\\d)|3[0-1]|(\\d))\\){1}");
		
		Matcher match =p.matcher(s);
			if(match.find())
			{
				String temperory=match.group();
				Pattern p1=Pattern.compile("(\\d+)");
				Matcher matched =p1.matcher(temperory);
				if(matched.find())
					temp1=matched.group();   //match first digit --->offset
				if(matched.find())
					temp=matched.group();	 //match second digit--->Register Coeffecient		
				//find + or - sign
				Pattern p2=Pattern.compile("-");  //match the - sign
				Matcher matched_again =p2.matcher(temperory);
				if(matched_again.find())
					{
					operand2=temp1;
					has_a_negative_offset=true;
					}
				else
					{	
					operand2=temp1;
					has_a_negative_offset=false;
					}
			}
	return temp;
	}
	
	boolean return_is_instruction_Store()
	{
	return is_instruction_Store;	
	}
	boolean return_is_instruction_Load()
	{
	return is_instruction_Load;	
	}
	boolean return_is_instruction_Add()
	{
	return is_instruction_Add;	
	}
	boolean return_is_instruction_Subtraction()
	{
	return is_instruction_Subtraction;	
	}
	boolean return_is_instruction_Multiply()
	{
	return is_instruction_Multiply;	
	}	
	void set_value1(float value_of_operand1)
	{
		this.value1=value_of_operand1; 
	}
	
	void set_value2(float value_of_operand2)
	{
		this.value2=value_of_operand2; 
	}
	void set_value3(float value_of_operand3)
	{
		this.value3=value_of_operand3; 
	}
	
	float return_value1()
	{
		return this.value1;
	}
	float return_value2()
	{
		return this.value2;
	}
	float return_value3()
	{
		return this.value3;
	}
	
	String return_operand1()
	{
		return operand1;
	}
	String return_operand2()
	{
		return operand2;
	}
	String return_operand3()
	{
		return operand3;
	}
	String return_instruction_word()
	{
		return instruction_word;
	}
	boolean return_has_a_negative_offset()
	{
		return has_a_negative_offset;
	}

	boolean return_is_this_stage_completed()
	{
		return is_this_stage_completed;
	}
	boolean return_is_in_scope()

	{
		return is_in_scope;
	}
	

}
