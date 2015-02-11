import java.util.Scanner;


public class Memory extends MainClass
{
	private int line_number;
	public static boolean occupied;
	private boolean is_this_stage_required;
	private int cc;
	private boolean is_this_stage_completed;
	private boolean is_in_scope;
	private boolean has_read;
	public boolean has_finished_execution;
	private float reg_immediate_holds_value_of_contents_to_written_to_writeback_to_register_or_the_value_of_a_register;
	private int target_address;
	private boolean is_RAW3_true;
	private boolean is_WAR_true;
	
	Memory(int from_constructor)
	{
		cc=0;
		is_in_scope=false;
		is_this_stage_required=false;
		is_this_stage_completed=false;
		has_read=false;
		has_finished_execution=false;
	}
	public int getcc() {
		return cc;
	}
	public void setcc(int cc) {
		this.cc = cc;
	}
	Memory(boolean from_constructor,int line_Number,String instruction_word)
	{
		is_in_scope=true;
		line_number=line_Number;
		occupied=true;
		//one_mem_happened=false;
		is_RAW3_true=false;
		is_WAR_true=false;
		if(!get_instruction_word(line_number).equals("L.D"))
			check_for_RAW();
	else
		{
			is_WAR_true=Check_WAR(line_number);
			if(!is_WAR_true){
			has_read=true;
			has_finished_execution=true;
			occupied=true;
			is_this_stage_completed=true;
			target_address=compute_target_address(get_ID_operand3(line_number),get_is_offset_positive(line_number),get_ID_operand2(line_number));
			reg_immediate_holds_value_of_contents_to_written_to_writeback_to_register_or_the_value_of_a_register=Memory_Content.get(target_address);
			set_ID_values_of_operand1(line_number, reg_immediate_holds_value_of_contents_to_written_to_writeback_to_register_or_the_value_of_a_register);
			F_Register.set(Integer.parseInt(get_ID_operand1(line_number)),get_ID_value1(line_number));
			}
		}	
	if(!is_RAW3_true && !is_WAR_true)
	{
		occupied=true;
		is_this_stage_completed=true;
		Instruction_Sequence.set(line_Number, Instruction_Sequence.get(line_number)+"MEM       ");
		has_read=true;
		has_finished_execution=true;
	}
	else
	{
	//	System.out.println("yes stalled");
		Instruction_Sequence.set(line_number, Instruction_Sequence.get(line_number)+"stall     ");	
	}
	if(has_finished_execution)
	{
		
		if(get_instruction_word(line_number).equals("S.D")){
			set_ID_values_of_operand1(line_number, reg_immediate_holds_value_of_contents_to_written_to_writeback_to_register_or_the_value_of_a_register);
		Memory_Content.set((int)(target_address),get_ID_value1(line_number));
		Memory_Content_Modified.add((int)(target_address));
		}
	}		
	}
	
	boolean return_has_finished_execution()
	{
		return has_finished_execution;
	}
	
	boolean return_has_read()
	{
		return has_read;
	}
	
	void set_scope()
	{
		this.is_in_scope=true;
	}
	
	private void check_for_RAW()
	{
		//System.out.print("l"+line_number+" ");
		{
			Scanner scan=new Scanner(Find_RAW(return_operand(line_number,false),line_number));	
			if(!scan.nextBoolean())
				{
					if(scan.nextBoolean())
					{
						reg_immediate_holds_value_of_contents_to_written_to_writeback_to_register_or_the_value_of_a_register=scan.nextFloat();	
						target_address=compute_target_address(get_ID_operand1(line_number),get_is_offset_positive(line_number),get_ID_operand2(line_number));	
					}
					else
					{
						target_address=compute_target_address(get_ID_operand1(line_number),get_is_offset_positive(line_number),get_ID_operand2(line_number));
						reg_immediate_holds_value_of_contents_to_written_to_writeback_to_register_or_the_value_of_a_register=target_address;
					}
				}
			else
				{
					is_RAW3_true=true;
				}
			//scan.close();
		}
		//System.out.print(" "+is_RAW3_true );
	}
	
	int compute_target_address(String operand_intValues,boolean is_offset_negative,String offset)
	{
		if(!is_offset_negative)
			return intValues.get(Integer.parseInt(operand_intValues))+Integer.parseInt(offset);
		else
			return intValues.get(Integer.parseInt(operand_intValues))-Integer.parseInt(offset);
	}
	
	int return_target_address()
	{
		return target_address;
	}
	
	void set_reg_immediate_holds_value_of_contents_to_written_to_writeback_to_register_or_the_value_of_a_register(float value)
	{
		this.reg_immediate_holds_value_of_contents_to_written_to_writeback_to_register_or_the_value_of_a_register=value;
	}
	
	float get_reg_immediate_holds_value_of_contents_to_written_to_writeback_to_register_or_the_value_of_a_register()
	{
		return reg_immediate_holds_value_of_contents_to_written_to_writeback_to_register_or_the_value_of_a_register;
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
