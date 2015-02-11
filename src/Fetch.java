public class Fetch extends MainClass
{
	private int line_number;
	public static boolean occupied;
	private boolean is_this_stage_completed;
	private boolean is_in_scope;
	private String fetch_line;
	private int cc;
	
	Fetch(int from_constructor)
	{
		cc=0;
		is_in_scope=false;
		is_this_stage_completed=false;
	}
	public int getcc() {
		return cc;
	}
	public void setcc(int cc) {
		this.cc = cc;
	}
	Fetch(boolean from_constructor,int int_from_constructor)
	{
		line_number=int_from_constructor;
		fetch_line=line_input.get(int_from_constructor);
		occupied=true;	
		is_in_scope=true;
		is_this_stage_completed=true;
		Instruction_Sequence.set(line_number, "IF        ");
	}
	
	
	boolean return_is_this_stage_completed()
	{
		return is_this_stage_completed;
	}
	
	String return_line_fetched()
	{
		return fetch_line;
	}
	boolean return_is_in_scope()
	{
		return is_in_scope;
	}
}
