import java.util.Set;


public class WB extends MainClass
{
	private int line_number;
	public static boolean occupied;
	private boolean is_this_stage_required;
	private boolean is_this_stage_completed;
	private boolean is_in_scope;
	
	private boolean has_written;
	private boolean is_WAR_true;
	private boolean is_WAW_true;
	private int cc;
	
	WB(int from_constructor)
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
	WB(boolean from_constructor,int line_Number)
	{
		
		is_WAR_true=false;
		is_WAW_true=false;
		line_number=line_Number;
		is_in_scope=true;
		if(!get_instruction_word(line_number).equals("S.D") )
		{
			is_WAR_true=Check_WAR(line_number);
			is_WAW_true=Check_WAW(line_number);
		}
		if(!is_WAR_true && !is_WAW_true)
		{
			System.out.println("<><><><>"+F_Register.get(2));
			occupied=true;
			is_this_stage_completed=true;
			Instruction_Sequence.set(line_Number, Instruction_Sequence.get(line_Number)+"WB        ");
			Finished_Instructions.add(line_number);
			max_line_finished=find_max(Finished_Instructions);
			has_written=true;
			if(!get_instruction_word(line_number).equals("S.D")){
			F_Register.set(Integer.parseInt(get_ID_operand1(line_number)), get_ID_value1(line_number));
			}
		}
		else
		{
			if(is_WAR_true && is_WAW_true)
				System.out.println("yes WAW and WaR stalled");
			else if(is_WAR_true)
				System.out.println("yes WAR stalled");
			else
				System.out.println("yes WAW stalled");
			Instruction_Sequence.set(line_number, Instruction_Sequence.get(line_number)+"stall     ");	
		}
	}
	public static int find_max(Set<Integer> temp)
	{
		int max=max_line_finished;
		for(int w:temp)
			if(w>max)
			{
				boolean yes_update_it=true;
				for(int i=w-1;i>max;i--)
					if(!temp.contains(i))
						yes_update_it=false;
				if(yes_update_it)
					max=w;
			}
		return max;
	}
	
	boolean return_has_written()
	{
		return has_written; 
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
