
public class Multiply6 extends MainClass
{
	private int line_number;
	public static boolean occupied;
	private boolean is_this_stage_required;
	private boolean is_this_stage_completed;
	private boolean is_in_scope;
	private int cc;
	
	Multiply6(int from_constructor)
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
	Multiply6(boolean from_constructor,int line_Number)
	{
		is_in_scope=true;
		line_number=line_Number;
		is_this_stage_required=true;
		occupied=true;
		is_this_stage_completed=true;
		Instruction_Sequence.set(line_Number, Instruction_Sequence.get(line_Number)+"M6        ");
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
