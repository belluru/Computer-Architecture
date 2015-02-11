
public class Arthi3 extends MainClass
{
	private int line_number;
	public static boolean occupied;
	private boolean is_this_stage_required;
	private boolean is_this_stage_completed;
	private boolean is_in_scope;
	private int cc;

	boolean has_finished_execution;
	
	Arthi3(int from_constructor)
	{
		cc=0;
		is_in_scope=false;
		is_this_stage_required=false;
		is_this_stage_completed=false;
		has_finished_execution=false;
	}
	public int getcc() {
		return cc;
	}
	public void setcc(int cc) {
		this.cc = cc;
	}
	Arthi3(boolean from_constructor,int line_Number)
	{
		is_in_scope=true;
		line_number=line_Number;
		is_this_stage_required=true;
		occupied=true;
		is_this_stage_completed=true;
		Instruction_Sequence.set(line_Number, Instruction_Sequence.get(line_Number)+"A3        ");
	}
	
	boolean return_has_finished_execution()
	{
		return has_finished_execution;
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
