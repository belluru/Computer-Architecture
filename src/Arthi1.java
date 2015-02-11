import java.util.Scanner;


public class Arthi1 extends MainClass
{
	private int line_number;
	public static boolean occupied;
    private boolean is_this_stage_required;
	private boolean is_this_stage_completed;
	private boolean is_in_scope;
	
	private boolean has_read;
	private boolean is_RAW3_true;
	private boolean is_RAW2_true;
	private int cc;
	
	Arthi1(int from_constructor)
	{
		cc=0;
		is_in_scope=false;
		is_this_stage_required=false;
		is_this_stage_completed=false;
	}
	public int getcc() {
		return cc;
	}
	public void setcc(int cc) {
		this.cc = cc;
	}
	Arthi1(boolean from_constructor,int line_Number)
	{
		
		line_number=line_Number;
		if(line_Number==8)
		{
			System.out.print("\n"+return_operand(line_number,true)+" "+return_operand(line_number,false));
			System.out.println("<>"+F_Register.get(2));
		}
		is_in_scope=true;
		is_RAW2_true=false;
		is_RAW3_true=false;
		check_for_RAW();
		if(is_RAW2_true || is_RAW3_true)
			check_for_RAW();
		if(!is_RAW2_true && !is_RAW3_true)
		{
			if(line_Number==8)
				System.out.println("<>"+F_Register.get(2));
			has_read=true;
			is_this_stage_required=true;
			is_this_stage_completed=true;
			occupied=true;
			Instruction_Sequence.set(line_Number, Instruction_Sequence.get(line_Number)+"A1        ");
		}
		else
		{
				//System.out.println("yes stalled");
				Instruction_Sequence.set(line_number, Instruction_Sequence.get(line_number)+"stall     ");	
		}
	}
	
	private void check_for_RAW()
	{
		{
			if(line_number==8)
			{
				System.out.println("Enter 1");
			}
		
			Scanner scan=new Scanner(Find_RAW(return_operand(line_number,true),line_number));	
			if(!scan.nextBoolean())
				{
					if(scan.nextBoolean())
					{
						set_ID_values_of_operand2(line_number,scan.nextFloat());
						
					}
					else
					{	
						set_ID_values_of_operand2(line_number,get_value_of_FP_Register(Integer.parseInt(get_ID_operand2(line_number))));	
					}
				}
			else
				{
					is_RAW2_true=true;
				}
			//scan.close();
		}
		{
			if(line_number==8)
			{
				System.out.println("Enter 2");
			}
		
			Scanner scan=new Scanner(Find_RAW(return_operand(line_number,false),line_number));	
			if(!scan.nextBoolean())
				{
				if(scan.nextBoolean())
					{
						set_ID_values_of_operand3(line_number,scan.nextFloat());
					}
				else
					{	
						set_ID_values_of_operand3(line_number,get_value_of_FP_Register(Integer.parseInt(get_ID_operand3(line_number))));
					}	
				}
			else
				{
						is_RAW3_true=true;
				}
			//scan.close();
		}
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
