import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class MainClass {
	public static ArrayList<Integer> intValues = new ArrayList<Integer>();
	public static ArrayList<Float> F_Register = new ArrayList<Float>();
	public static ArrayList<Float> Memory_Content = new ArrayList<Float>();
	public static Set<Integer> Memory_Content_Modified = new LinkedHashSet<Integer>();
	public static ArrayList<String> line_input = new ArrayList<String>();
	public static ArrayList<String> Instruction_Sequence = new ArrayList<String>();
	public static Set<Integer> Finished_Instructions = new LinkedHashSet<Integer>();
	public static int current_instruction_line = 1;
	public static int max_line_finished = 0;
	public static int startccforinstr=1;
	public static boolean checkVariable = false;
	public static int clock_cycle;
	public static int maxcc=0;
	// object ArrayList
	public static ArrayList<Fetch> IF_ = new ArrayList<Fetch>();
	public static ArrayList<Decode> ID_ = new ArrayList<Decode>();
	public static ArrayList<Execute> EX_ = new ArrayList<Execute>();
	public static ArrayList<Arthi1> A1_ = new ArrayList<Arthi1>();
	public static ArrayList<Arthi2> A2_ = new ArrayList<Arthi2>();
	public static ArrayList<Arthi3> A3_ = new ArrayList<Arthi3>();
	public static ArrayList<Arthi4> A4_ = new ArrayList<Arthi4>();
	public static ArrayList<Multiply> M1_ = new ArrayList<Multiply>();
	public static ArrayList<Multiply2> M2_ = new ArrayList<Multiply2>();
	public static ArrayList<Multiply3> M3_ = new ArrayList<Multiply3>();
	public static ArrayList<Multiply4> M4_ = new ArrayList<Multiply4>();
	public static ArrayList<Multiply5> M5_ = new ArrayList<Multiply5>();
	public static ArrayList<Multiply6> M6_ = new ArrayList<Multiply6>();
	public static ArrayList<Multiply7> M7_ = new ArrayList<Multiply7>();
	public static ArrayList<Memory> MEM_ = new ArrayList<Memory>();
	public static ArrayList<WB> WB_ = new ArrayList<WB>();

	static File File_clock_cycles;
	static File File_FP_registers;
	static BufferedWriter bw;
	static FileWriter fw;
	static BufferedWriter bw1;
	static FileWriter fw1;
	static PrintWriter fwx;
	static FileWriter writer;

	public static void main(String[] args) throws Exception {

		for (int i = 0; i < 32; i++) {
			intValues.add(i, 0);
			F_Register.add(i, 0f);
		}
		for (int i = 0; i < 1000; i++) {
			Memory_Content.add(i, 0f);
		}

		String s = null;
		boolean t = true;
		if (t) {
			Scanner scan_string = new Scanner(System.in);
			boolean file_exists = false;
			while (file_exists != true) {
				System.out.println("PLease Input data file ");
				s = scan_string.next();
				File f = new File(s);
				file_exists = f.exists();
				if (!file_exists)
					System.out
							.println("Such Input file doesnot exits!! Please enter the Input file::");
			}

			System.out.println("Calculating the results:::");
		}

		InputFile file_handler = new InputFile(s);
		file_handler.closed();

		for (int i = 0; i < line_input.size(); i++)
			Instruction_Sequence.add(i, "");

		for (int i = 0; i < line_input.size(); i++) {
			IF_.add(i, new Fetch(0));
			ID_.add(i, new Decode(0));
			EX_.add(i, new Execute(0));
			A1_.add(i, new Arthi1(0));
			A2_.add(i, new Arthi2(0));
			A3_.add(i, new Arthi3(0));
			A4_.add(i, new Arthi4(0));
			M1_.add(i, new Multiply(0));
			M2_.add(i, new Multiply2(0));
			M3_.add(i, new Multiply3(0));
			M4_.add(i, new Multiply4(0));
			M5_.add(i, new Multiply5(0));
			M6_.add(i, new Multiply6(0));
			M7_.add(i, new Multiply7(0));
			MEM_.add(i, new Memory(0));
			WB_.add(i, new WB(0));
			Instruction_Sequence.add(i, null);
		}

		for (int i = 0; i < 32; i++) {
			System.out.println("F" + i + " " + F_Register.get(i));
			System.out.println("M" + i + " " + Memory_Content.get(i));
		}

		start_the_execution();

		//print_Instruction_Sequence();

		System.out.println("This is printed the results to console!!!");

		/*for (int i = 0; i < 32; i++)
			System.out.println("R" + i + "\t" + intValues.get(i));*/

		// creating the new output file
		//Writing output to Timing file
		File_clock_cycles = new File((get_file_name("CCoutput")));
		if (!File_clock_cycles.exists())
			File_clock_cycles.createNewFile();
		else{
			File_clock_cycles.delete();
			File_clock_cycles.createNewFile();
		}
		writer = new FileWriter(File_clock_cycles, true);
		fwx = new PrintWriter(writer);
		print_Instruction_Sequence(fwx);
		fwx.close();
		writer.close();
		//Writing output to Register file
		File_clock_cycles = new File((get_fpregisters_file_name("Regoutput")));
		if (!File_clock_cycles.exists())
			File_clock_cycles.createNewFile();
		else{
			File_clock_cycles.delete();
			File_clock_cycles.createNewFile();
		}
		writer = new FileWriter(File_clock_cycles, true);
		fwx = new PrintWriter(writer);
		for (int i = 0; i < 32; i++) {
			fwx.printf("F" + i + "\t" + F_Register.get(i) + "\n");
		}
		fwx.close();
		writer.close();
		/*File_FP_registers = new File(get_file_name("Regoutput"));
		if (!File_FP_registers.exists())
			File_FP_registers.createNewFile();
		fw1 = new FileWriter(File_FP_registers.getAbsoluteFile());
		bw1 = new BufferedWriter(fw1);
		System.out.println("This is the register output Contents");
		for (int i = 0; i < 32; i++) {
			bw1.append("F" + i + "\t" + F_Register.get(i));
			bw1.newLine();
		}
		bw1.close();*/

	}

	public static void start_the_execution() throws Exception {


		for (clock_cycle = startccforinstr; !checkVariable; clock_cycle++) {

			Fetch.occupied = false;
			Decode.occupied = false;
			Execute.occupied = false;
			Arthi1.occupied = false;
			Arthi2.occupied = false;
			Arthi3.occupied = false;
			Arthi4.occupied = false;
			Multiply.occupied = false;
			Multiply2.occupied = false;
			Multiply3.occupied = false;
			Multiply4.occupied = false;
			Multiply5.occupied = false;
			Multiply6.occupied = false;
			Multiply7.occupied = false;
			Memory.occupied = false;
			WB.occupied = false;
			int memcc = 0;
			int wbdonecc=0;
			boolean wbwaitforonecc=false;
			boolean memstld = false;
			int start_at_line = max_line_finished + 1;
			System.out.print("\nPrinting the CC output to console ::"
					+ clock_cycle);
			int i=start_at_line;
			if(start_at_line<line_input.size()){
			//for (int i = start_at_line; i <= current_instruction_line
				//	&& i < line_input.size(); i++) {
				if (MEM_.get(i).return_is_this_stage_completed()
						&& !WB_.get(i).return_is_this_stage_completed()){
					/*if (ID_.get(i).return_is_instruction_Store()){
						wbwaitforonecc=false;
					}
					else{
						if(wbdonecc == clock_cycle)
							wbwaitforonecc=true;
					}*/
					//if (!WB.occupied || !wbwaitforonecc){
					if (!WB.occupied){
						WB_.get(i).setcc(clock_cycle);
						WB_.set(i, new WB(true, i));
						if (WB_.get(i).return_is_this_stage_completed()) {
							WB_.get(i).setcc(clock_cycle);
								//wbdonecc=clock_cycle;
								if(clock_cycle > maxcc)
									maxcc=clock_cycle;
								rearrange_Instructions(i);
								startccforinstr=ID_.get(i).getcc();
								clock_cycle=startccforinstr-1;
						}

					} else {
						Instruction_Sequence.set(i, Instruction_Sequence.get(i)
								+ "stall     ");

					}
				}
			//}

			//for (int i = start_at_line; i <= current_instruction_line
				//	&& i < line_input.size(); i++) {

				if (ID_.get(i).return_is_this_stage_completed()
						&& !MEM_.get(i).return_is_this_stage_completed()) {
					if (ID_.get(i).return_is_instruction_Load()
							|| ID_.get(i).return_is_instruction_Store()) {

						if (EX_.get(i).return_is_this_stage_completed()) {
							// if(ID_.get(i-1).return_is_instruction_Add() ||
							// ID_.get(i-1).return_is_instruction_Multiply() ||
							// !Memory.occupied)
							if (!Memory.occupied){
									//|| ((memcc == clock_cycle) && !memstld)) {
								MEM_.get(i).setcc(clock_cycle);
								MEM_.set(i, new Memory(true, i, ID_.get(i)
										.return_instruction_word()));
								if (MEM_.get(i).return_has_finished_execution()) {
									MEM_.get(i).setcc(clock_cycle);
									//memcc = clock_cycle;
									//memstld = true;
								}
							} else {
								// Add a new variable like waitingformem and set
								// that variable and use this variable in RAW
								MEM_.get(i).set_scope();
								// MEM_.get(i).setOne_mem_happened(true);
								// MEM_.set(i,new
								// Memory(true,i,ID_.get(i).return_instruction_word()));
								Instruction_Sequence.set(i,
										Instruction_Sequence.get(i)
												+ "stall     ");
							}
						}
					}
					if (ID_.get(i).return_is_instruction_Add() || ID_.get(i).return_is_instruction_Subtraction()) {

						if (A4_.get(i).return_is_this_stage_completed()) {
							if (!Memory.occupied){ 
									//|| (memcc == clock_cycle)) {
								MEM_.set(i, new Memory(true, i, ID_.get(i)
										.return_instruction_word()));
								if (MEM_.get(i).return_has_finished_execution()) {
									MEM_.get(i).setcc(clock_cycle);
									//memcc = clock_cycle;
								}
							} else {
								MEM_.get(i).set_scope();
								Instruction_Sequence.set(i,
										Instruction_Sequence.get(i)
												+ "stall     ");
							}
						}
					}
					if (ID_.get(i).return_is_instruction_Multiply()) {
						if (M7_.get(i).return_is_this_stage_completed()) {

							if (!Memory.occupied){ 
									//|| (memcc == clock_cycle)) {
								MEM_.set(i, new Memory(true, i, ID_.get(i)
										.return_instruction_word()));
								if (MEM_.get(i).return_has_finished_execution()) {
									MEM_.get(i).setcc(clock_cycle);
									//memcc = clock_cycle;
								}
							} else {
								MEM_.get(i).set_scope();
								Instruction_Sequence.set(i,
										Instruction_Sequence.get(i)
												+ "stall     ");
							}
						}
					}
				}
			//}

			//for (int i = start_at_line; i <= current_instruction_line
				//	&& i < line_input.size(); i++) {
				if (M6_.get(i).return_is_this_stage_completed()
						&& !M7_.get(i).return_is_this_stage_completed())
					if (!Multiply7.occupied) {
						M7_.set(i, new Multiply7(true, i));
						if (M7_.get(i).return_is_this_stage_completed()) {
							M7_.get(i).setcc(clock_cycle);
						}
					} else {
						Instruction_Sequence.set(i, Instruction_Sequence.get(i)
								+ "stall     ");
					}
			//}
				
				//for (int i = start_at_line; i <= current_instruction_line
				//	&& i < line_input.size(); i++) {
				if (M5_.get(i).return_is_this_stage_completed()
						&& !M6_.get(i).return_is_this_stage_completed())
					if (!Multiply3.occupied) {
						M6_.set(i, new Multiply6(true, i));
						if (M6_.get(i).return_is_this_stage_completed()) {
							M6_.get(i).setcc(clock_cycle);
						}
					} else {
						Instruction_Sequence.set(i, Instruction_Sequence.get(i)
								+ "stall     ");
					}
			//}
				
				//for (int i = start_at_line; i <= current_instruction_line
				//	&& i < line_input.size(); i++) {
				if (M4_.get(i).return_is_this_stage_completed()
						&& !M5_.get(i).return_is_this_stage_completed())
					if (!Multiply3.occupied) {
						M5_.set(i, new Multiply5(true, i));
						if (M5_.get(i).return_is_this_stage_completed()) {
							M5_.get(i).setcc(clock_cycle);
						}
					} else {
						Instruction_Sequence.set(i, Instruction_Sequence.get(i)
								+ "stall     ");
					}
			//}
				
				//for (int i = start_at_line; i <= current_instruction_line
				//	&& i < line_input.size(); i++) {
				if (M3_.get(i).return_is_this_stage_completed()
						&& !M4_.get(i).return_is_this_stage_completed())
					if (!Multiply4.occupied) {
						M4_.set(i, new Multiply4(true, i));
						if (M4_.get(i).return_is_this_stage_completed()) {
							M4_.get(i).setcc(clock_cycle);
						}
					} else {
						Instruction_Sequence.set(i, Instruction_Sequence.get(i)
								+ "stall     ");
					}
			//}

			//for (int i = start_at_line; i <= current_instruction_line
				//	&& i < line_input.size(); i++) {
				if (M2_.get(i).return_is_this_stage_completed()
						&& !M3_.get(i).return_is_this_stage_completed())
					if (!Multiply3.occupied) {
						M3_.set(i, new Multiply3(true, i));
						if (M3_.get(i).return_is_this_stage_completed()) {
							M3_.get(i).setcc(clock_cycle);
						}
					} else {
						Instruction_Sequence.set(i, Instruction_Sequence.get(i)
								+ "stall     ");
					}
			//}
			//for (int i = start_at_line; i <= current_instruction_line
				//	&& i < line_input.size(); i++) {
				if (M1_.get(i).return_is_this_stage_completed()
						&& !M2_.get(i).return_is_this_stage_completed())
					if (!Multiply2.occupied) {
						M2_.set(i, new Multiply2(true, i));
						if (M2_.get(i).return_is_this_stage_completed()) {
							M2_.get(i).setcc(clock_cycle);
						}
					} else {
						Instruction_Sequence.set(i, Instruction_Sequence.get(i)
								+ "stall     ");
					}
		//	}
			//for (int i = start_at_line; i <= current_instruction_line
				//	&& i < line_input.size(); i++) {
				if (ID_.get(i).return_is_this_stage_completed()
						&& !M1_.get(i).return_is_this_stage_completed())
					if (ID_.get(i).return_is_instruction_Multiply()) {
						if (!Multiply.occupied) {
							M1_.get(i).setcc(clock_cycle);
							M1_.set(i, new Multiply(true, i));
							if (M1_.get(i).return_is_this_stage_completed()) {
								M1_.get(i).setcc(clock_cycle);
							}
						} else {
							Instruction_Sequence.set(i,
									Instruction_Sequence.get(i) + "stall     ");
						}
					}
			//}
			//for (int i = start_at_line; i <= current_instruction_line
				//	&& i < line_input.size(); i++) {
				if (A3_.get(i).return_is_this_stage_completed()
						&& !A4_.get(i).return_is_this_stage_completed())
					if (!Arthi4.occupied) {
						A4_.set(i, new Arthi4(true, i));
						if (A4_.get(i).return_is_this_stage_completed()) {
							A4_.get(i).setcc(clock_cycle);
						}
					} else {
						Instruction_Sequence.set(i, Instruction_Sequence.get(i)
								+ "stall     ");
					}
			//}
				
				//for (int i = start_at_line; i <= current_instruction_line
				//	&& i < line_input.size(); i++) {
				if (A2_.get(i).return_is_this_stage_completed()
						&& !A3_.get(i).return_is_this_stage_completed())
						if (!Arthi3.occupied) {
							A3_.get(i).setcc(clock_cycle);
							A3_.set(i, new Arthi3(true, i));
							if (A3_.get(i).return_is_this_stage_completed()) {
								A3_.get(i).setcc(clock_cycle);
							}
						} else {
							Instruction_Sequence.set(i,
									Instruction_Sequence.get(i) + "stall     ");
						}
			//}
				
				//for (int i = start_at_line; i <= current_instruction_line
				//	&& i < line_input.size(); i++) {
				if (A1_.get(i).return_is_this_stage_completed()
						&& !A2_.get(i).return_is_this_stage_completed())
						if (!Arthi2.occupied) {
							A2_.get(i).setcc(clock_cycle);
							A2_.set(i, new Arthi2(true, i));
							if (A2_.get(i).return_is_this_stage_completed()) {
								A2_.get(i).setcc(clock_cycle);
							}
						} else {
							Instruction_Sequence.set(i,
									Instruction_Sequence.get(i) + "stall     ");
						}
			//}
			//for (int i = start_at_line; i <= current_instruction_line
				//	&& i < line_input.size(); i++) {
				if (ID_.get(i).return_is_this_stage_completed()
						&& !A1_.get(i).return_is_this_stage_completed())
					if (ID_.get(i).return_is_instruction_Add() || ID_.get(i).return_is_instruction_Subtraction()) {
						if (!Arthi1.occupied) {
							A1_.get(i).setcc(clock_cycle);
							A1_.set(i, new Arthi1(true, i));
							if (A1_.get(i).return_is_this_stage_completed()) {
								A1_.get(i).setcc(clock_cycle);
							}
						} else {
							Instruction_Sequence.set(i,
									Instruction_Sequence.get(i) + "stall     ");
						}
					}
			//}
			//for (int i = start_at_line; i <= current_instruction_line
				//	&& i < line_input.size(); i++) {
				if (ID_.get(i).return_is_this_stage_completed()
						&& !EX_.get(i).return_is_this_stage_completed())
					if (ID_.get(i).return_is_instruction_Load()
							|| ID_.get(i).return_is_instruction_Store()) {
						if (!Execute.occupied) {
							EX_.set(i, new Execute(true, i));
							if (EX_.get(i).return_is_this_stage_completed()) {
								EX_.get(i).setcc(clock_cycle);
							}
						} else {
							Instruction_Sequence.set(i,
									Instruction_Sequence.get(i) + "stall     ");
						}
					}
			//}
			//for (int i = start_at_line; i <= current_instruction_line
				//	&& i < line_input.size(); i++) {
				if (IF_.get(i).return_is_this_stage_completed()
						&& !ID_.get(i).return_is_this_stage_completed())
					if (!Decode.occupied) {
						ID_.set(i, new Decode(true, IF_.get(i)
								.return_line_fetched(), i));
						if (ID_.get(i).return_is_this_stage_completed()) {
							ID_.get(i).setcc(clock_cycle);
						}
					}
			//}
			//for (int i = start_at_line; i <= current_instruction_line
				//	&& i < line_input.size(); i++) {
				if (!Fetch.occupied) {
					if (!IF_.get(i).return_is_this_stage_completed()) {
						IF_.set(i, new Fetch(true, i));
						if(IF_.get(i).return_is_this_stage_completed()){
							IF_.get(i).setcc(clock_cycle);
						}
					}
				}
			//}
		//}
			}
			if (current_instruction_line < line_input.size())
				current_instruction_line++;
			else if (Finished_Instructions.size() == (line_input.size() - 1))
				checkVariable = true;
		}
	}

	static String get_file_name(String s) {
		System.out.println("please enter the name of Timing file");

		Scanner input_recieve = new Scanner(System.in);
		String g = input_recieve.next();

		return g;
	}
	
	static String get_fpregisters_file_name(String s) {
		System.out.println("please enter the name of Register file");

		Scanner input_recieve = new Scanner(System.in);
		String g = input_recieve.next();

		return g;
	}
	

	//rearranging instructions
	public static void rearrange_Instructions(int instruction){
		int noOfStalls=0;
		int noOfStallsbwwbandmem=0;
		int ifcc=0;
		int idcurrentinstrcc=0;
		int excurrentinstrcc=0;
		int memcurrentinstrcc=0;
		int m1currentinstrcc=0;
		int a1currentinstrcc=0;
		int instr = instruction;
					if(ID_.get(instr).return_is_instruction_Store()){
						ifcc=IF_.get(instr).getcc();
						noOfStalls= MEM_.get(instr).getcc() - EX_.get(instr).getcc();
						if(noOfStalls>1){
							ID_.get(instr).setcc(ifcc+noOfStalls);
							EX_.get(instr).setcc(ifcc+noOfStalls+1);
						}
					}
					else if(ID_.get(instr).return_is_instruction_Load()){
						ifcc=IF_.get(instr).getcc();
						noOfStalls= MEM_.get(instr).getcc() - EX_.get(instr).getcc();
						if(noOfStalls>1){
							ID_.get(instr).setcc(ifcc+noOfStalls);
							EX_.get(instr).setcc(ifcc+noOfStalls+1);
						}
						idcurrentinstrcc=ID_.get(instr).getcc();
						excurrentinstrcc=EX_.get(instr).getcc();
						memcurrentinstrcc=MEM_.get(instr).getcc();
						noOfStallsbwwbandmem=WB_.get(instr).getcc()-MEM_.get(instr).getcc()-1;
						if(noOfStallsbwwbandmem > 0){
							ID_.get(instr).setcc(idcurrentinstrcc+noOfStallsbwwbandmem);
							EX_.get(instr).setcc(excurrentinstrcc+noOfStallsbwwbandmem);
							MEM_.get(instr).setcc(memcurrentinstrcc+noOfStallsbwwbandmem);
						}
					}
					else if(ID_.get(instr).return_is_instruction_Multiply()){
						ifcc=IF_.get(instr).getcc();
						noOfStalls= M1_.get(instr).getcc() - ID_.get(instr).getcc();
						if(noOfStalls>1){
							ID_.get(instr).setcc(ifcc+noOfStalls);
						}
						idcurrentinstrcc=ID_.get(instr).getcc();
						m1currentinstrcc=M1_.get(instr).getcc();
						memcurrentinstrcc=MEM_.get(instr).getcc();
						noOfStallsbwwbandmem=WB_.get(instr).getcc()-MEM_.get(instr).getcc()-1;
						if(noOfStallsbwwbandmem > 0){
							ID_.get(instr).setcc(idcurrentinstrcc+noOfStallsbwwbandmem);
							M1_.get(instr).setcc(m1currentinstrcc+noOfStallsbwwbandmem);
							M2_.get(instr).setcc(m1currentinstrcc+noOfStallsbwwbandmem+1);
							M3_.get(instr).setcc(m1currentinstrcc+noOfStallsbwwbandmem+2);
							M4_.get(instr).setcc(m1currentinstrcc+noOfStallsbwwbandmem+3);
							M5_.get(instr).setcc(m1currentinstrcc+noOfStallsbwwbandmem+4);
							M6_.get(instr).setcc(m1currentinstrcc+noOfStallsbwwbandmem+5);
							M7_.get(instr).setcc(m1currentinstrcc+noOfStallsbwwbandmem+6);
							MEM_.get(instr).setcc(memcurrentinstrcc+noOfStallsbwwbandmem);
						}
					}
					else if(ID_.get(instr).return_is_instruction_Add() || ID_.get(instr).return_is_instruction_Subtraction()){
						ifcc=IF_.get(instr).getcc();
						noOfStalls= A1_.get(instr).getcc() - ID_.get(instr).getcc();
						if(noOfStalls>1){
							ID_.get(instr).setcc(ifcc+noOfStalls);
						}
						idcurrentinstrcc=ID_.get(instr).getcc();
						a1currentinstrcc=A1_.get(instr).getcc();
						memcurrentinstrcc=MEM_.get(instr).getcc();
						noOfStallsbwwbandmem=WB_.get(instr).getcc()-MEM_.get(instr).getcc()-1;
						if(noOfStallsbwwbandmem > 0){
							ID_.get(instr).setcc(idcurrentinstrcc+noOfStallsbwwbandmem);
							A1_.get(instr).setcc(a1currentinstrcc+noOfStallsbwwbandmem);
							A2_.get(instr).setcc(a1currentinstrcc+noOfStallsbwwbandmem+1);
							A3_.get(instr).setcc(a1currentinstrcc+noOfStallsbwwbandmem+2);
							A4_.get(instr).setcc(a1currentinstrcc+noOfStallsbwwbandmem+3);
							MEM_.get(instr).setcc(memcurrentinstrcc+noOfStallsbwwbandmem);
						}
					}
					/*maxcc=clock_cycle;
					if(IF_.get(instr).getcc() != ID_.get(instr-1).getcc()){
						idcc=ID_.get(instr-1).getcc();
						idcurrentinstrcc=ID_.get(instr).getcc();
						excurrentinstrcc=EX_.get(instr).getcc();
						memcurrentinstrcc=MEM_.get(instr).getcc();
						wbcurrentinstrcc=WB_.get(instr).getcc();
						if(idcurrentinstrcc<=idcc){
							diffidcc=idcc-idcurrentinstrcc+1;
							if(ID_.get(instr).return_is_instruction_Load() || ID_.get(instr).return_is_instruction_Store()){
								ID_.get(instr).setcc(idcurrentinstrcc+diffidcc);
								EX_.get(instr).setcc(excurrentinstrcc+diffidcc);
								MEM_.get(instr).setcc(memcurrentinstrcc+diffidcc);
								WB_.get(instr).setcc(wbcurrentinstrcc+diffidcc);
							}
							else if(ID_.get(instr).return_is_instruction_Multiply()){
								m1currentinstrcc=M1_.get(instr).getcc();
								m2currentinstrcc=M2_.get(instr).getcc();
								m3currentinstrcc=M3_.get(instr).getcc();
								m4currentinstrcc=M4_.get(instr).getcc();
								ID_.get(instr).setcc(idcurrentinstrcc+diffidcc);
								M1_.get(instr).setcc(excurrentinstrcc+diffidcc);
								M2_.get(instr).setcc(m1currentinstrcc+diffidcc);
								M3_.get(instr).setcc(m2currentinstrcc+diffidcc);
								M4_.get(instr).setcc(m3currentinstrcc+diffidcc);
								MEM_.get(instr).setcc(m4currentinstrcc+diffidcc);
								WB_.get(instr).setcc(wbcurrentinstrcc+diffidcc);
							}
							else if(ID_.get(instr).return_is_instruction_Add()){
								a1currentinstrcc=A1_.get(instr).getcc();
								a2currentinstrcc=A2_.get(instr).getcc();
								ID_.get(instr).setcc(idcurrentinstrcc+diffidcc);
								A1_.get(instr).setcc(a1currentinstrcc+diffidcc);
								A2_.get(instr).setcc(a2currentinstrcc+diffidcc);
								MEM_.get(instr).setcc(memcurrentinstrcc+diffidcc);
								WB_.get(instr).setcc(wbcurrentinstrcc+diffidcc);
							}
							if(instr == line_input.size()-1){
								maxcc=wbcurrentinstrcc+diffidcc;
							}
						}
						IF_.get(instr).setcc(idcc);
					}*/
	}

	public static void print_Instruction_Sequence(PrintWriter fw) {
		ArrayList<IsIFWBDone> IfWb = new ArrayList<IsIFWBDone>();
		/*System.out.println("\n" + "INSTRUCTION-SEQUENCE");
		for (int i = 1; i <= clock_cycle; i++)
			System.out.printf("%-10d", i);
		System.out.println();
		for (int i = 1; i < line_input.size(); i++) {
			for (int j = 1; j < i; j++)
				System.out.printf("%-10s", "----      ");
			System.out.println(Instruction_Sequence.get(i));
		}
		for (int i = 0; i < 32; i++) {
			System.out.println("F" + i + " " + F_Register.get(i));
			System.out.println("M" + i + " " + Memory_Content.get(i));
		}*/
		
		//printing output in required format

		for (int i = 0; i <= maxcc; i++) {
			IfWb.add(i, new IsIFWBDone(false, false));
		}
		for (int ins = 1; ins <= line_input.size(); ins++) {
			if (ins == 1){
				fw.printf("%-10s"," ");
			}
			else{
				fw.printf("%-10s","I#"+(ins-1));
			}
		}
		fw.println();
		fw.println();
		for (int clkcycle = 1; clkcycle <= maxcc; clkcycle++){
			//System.out.format("%6s",clkcycle+"   ");
			fw.printf("%-10s","c#"+clkcycle);
			for (int ins = 1; ins < line_input.size(); ins++) {
				if(IF_.get(ins).getcc()==clkcycle){
					//System.out.printf("%-10s","IF      ");
					fw.printf("%-10s","IF      ");
					IfWb.get(clkcycle).setIfDone(true);
				}
				else if(ID_.get(ins).getcc()==clkcycle)
					//System.out.printf("%-10s","ID      ");
					fw.printf("%-10s","ID      ");
				else if(M1_.get(ins).getcc()==clkcycle)
					//System.out.printf("%-10s","M1      ");
					fw.printf("%-10s","M1      ");
				else if(M2_.get(ins).getcc()==clkcycle)
					//System.out.printf("%-10s","M2      ");
					fw.printf("%-10s","M2      ");
				else if(M3_.get(ins).getcc()==clkcycle)
					//System.out.printf("%-10s","M3      ");
					fw.printf("%-10s","M3      ");
				else if(M4_.get(ins).getcc()==clkcycle)
					//System.out.printf("%-10s","M4      ");
					fw.printf("%-10s","M4      ");
				else if(M5_.get(ins).getcc()==clkcycle)
					//System.out.printf("%-10s","M5      ");
					fw.printf("%-10s","M5      ");
				else if(M6_.get(ins).getcc()==clkcycle)
					//System.out.printf("%-10s","M6      ");
					fw.printf("%-10s","M6      ");
				else if(M7_.get(ins).getcc()==clkcycle)
					//System.out.printf("%-10s","M7      ");
					fw.printf("%-10s","M7      ");
				else if(A1_.get(ins).getcc()==clkcycle)
					//System.out.printf("%-10s","A1      ");
					fw.printf("%-10s","A1      ");
				else if(A2_.get(ins).getcc()==clkcycle)
					//System.out.printf("%-10s","A2      ");
					fw.printf("%-10s","A2      ");
				else if(A3_.get(ins).getcc()==clkcycle)
					//System.out.printf("%-10s","A3      ");
					fw.printf("%-10s","A3      ");
				else if(A4_.get(ins).getcc()==clkcycle)
					//System.out.printf("%-10s","A4      ");
					fw.printf("%-10s","A4      ");
				else if(EX_.get(ins).getcc()==clkcycle)
					//System.out.printf("%-10s","EX      ");
					fw.printf("%-10s","EX      ");
				else if(MEM_.get(ins).getcc()==clkcycle)
					//System.out.printf("%-10s","MEM     ");
					fw.printf("%-10s","MEM      ");
				else if(WB_.get(ins).getcc()==clkcycle){
					//System.out.printf("%-10s","WB      ");
					fw.printf("%-10s","WB      ");
					IfWb.get(ins).setWbDone(true);
				}
				else if(IfWb.get(clkcycle).isIfDone() || (IF_.get(ins).getcc() > clkcycle))
					//System.out.printf("%-10s", "----      ");
					fw.printf("%-10s", "----      ");
				else if(IfWb.get(ins).isWbDone())
					//System.out.printf("%-10s", "----      ");
					fw.printf("%-10s", "----      ");
				else
					//System.out.printf("%-10s","stall   ");
					fw.printf("%-10s","stall   ");
				
			}
			//System.out.print("\n\n");
			fw.print("\n\n");
		}
		
	}

	public static String get_instruction_word(int line_number_reference) {
		return ID_.get(line_number_reference).return_instruction_word();
	}

	public static String return_operand(int line_number_reference,
			boolean true_if_operand2_false_if_operand3) {
		String x;
		if (true_if_operand2_false_if_operand3)
			x = ID_.get(line_number_reference).return_operand2();
		else
			x = ID_.get(line_number_reference).return_operand3();
		return x;
	}

	public static void set_ID_values_of_operand1(int line_number_reference,
			float float_value_of_operand) {
		ID_.get(line_number_reference).set_value1(float_value_of_operand);
	}

	public static void set_ID_values_of_operand2(int line_number_reference,
			float float_value_of_operand) {
		ID_.get(line_number_reference).set_value2(float_value_of_operand);
	}

	public static void set_ID_values_of_operand3(int line_number_reference,
			float float_value_of_operand) {
		ID_.get(line_number_reference).set_value3(float_value_of_operand);
	}

	public static String get_ID_operand1(int line_number_reference) {
		return ID_.get(line_number_reference).return_operand1();
	}

	public String get_ID_operand2(int line_number_reference) {
		return ID_.get(line_number_reference).return_operand2();
	}

	public String get_ID_operand3(int line_number_reference) {
		return ID_.get(line_number_reference).return_operand3();
	}

	public float get_ID_value1(int line_number_reference) {
		return ID_.get(line_number_reference).return_value1();
	}

	public float get_ID_value2(int line_number_reference) {
		return ID_.get(line_number_reference).return_value2();
	}

	public float get_ID_value3(int line_number_reference) {
		return ID_.get(line_number_reference).return_value3();
	}

	public static boolean get_is_offset_positive(int line_number_reference) {
		return ID_.get(line_number_reference).return_has_a_negative_offset();
	}

	public static float get_value_of_FP_Register(int index_value) {
		return F_Register.get(index_value);
	}

	public static String Find_RAW(String operand_reference,
			int line_number_reference) {
		if (line_number_reference == 8)
			System.out.println(operand_reference);
		String Reg_Imm = null;
		int noOfDepLines=1;
		boolean is_it_not_ready_to_read = false;
		boolean break_the_loop = false;
		boolean waitforoneclockcycle = false;
		//setting the number of previous lines on which dependency needs to be checked
		/*if(ID_.get(line_number_reference).return_is_instruction_Add()){
			for(int f = 1;f<=max_line_finished;f++){
				if(WB_.get(f).getcc()>=A1_.get(line_number_reference).getcc()){
					noOfDepLines=f;
					break;
				}
			}
		}
		else if(ID_.get(line_number_reference).return_is_instruction_Multiply()){
			for(int f = 1;f<=max_line_finished;f++){
				if(WB_.get(f).getcc()>=M1_.get(line_number_reference).getcc()){
					noOfDepLines=f;
					break;
				}
			}
		}
		else if(ID_.get(line_number_reference).return_is_instruction_Load() || ID_.get(line_number_reference).return_is_instruction_Store()){
			for(int f = 1;f<=max_line_finished;f++){
				if(WB_.get(f).getcc()>=MEM_.get(line_number_reference).getcc()){
					noOfDepLines=f;
					break;
				}
			}
		}*/
		
		for (int i = line_number_reference - 1; i >= noOfDepLines && !break_the_loop && !is_it_not_ready_to_read; i--) {

			if (operand_reference.equals(ID_.get(i).return_operand1()) && !ID_.get(i).return_is_instruction_Store()) {
				//setting RAW conditions for ld-add,mul,sd conditions
				if ((ID_.get(i).return_is_instruction_Load()) && (ID_.get(line_number_reference).return_is_instruction_Add() || ID_.get(line_number_reference).return_is_instruction_Subtraction())) {
					if ((A1_.get(line_number_reference).getcc() <= MEM_.get(i).getcc())) {
						waitforoneclockcycle = true;
					} 
					else {
						waitforoneclockcycle = false;
					}
				}
				else if ((ID_.get(i).return_is_instruction_Load()) && ID_.get(line_number_reference).return_is_instruction_Multiply()) {
					if ((M1_.get(line_number_reference).getcc() <= MEM_.get(i).getcc())) {
						waitforoneclockcycle = true;
					} 
					else {
						waitforoneclockcycle = false;
					}
				}
				else if ((ID_.get(i).return_is_instruction_Load()) && ID_.get(line_number_reference).return_is_instruction_Store()) {
					if ((MEM_.get(line_number_reference).getcc() <= MEM_.get(i).getcc())) {
						waitforoneclockcycle = true;
					} 
					else {
						waitforoneclockcycle = false;
					}
				}
				//setting RAW conditions for add-add mul sd conditions
				else if((ID_.get(i).return_is_instruction_Add() || ID_.get(i).return_is_instruction_Subtraction()) && (ID_.get(line_number_reference).return_is_instruction_Add() || ID_.get(line_number_reference).return_is_instruction_Subtraction())){
					if ((A1_.get(line_number_reference).getcc() <= A4_.get(i).getcc())) {
						waitforoneclockcycle = true;
					} 
					else {
						waitforoneclockcycle = false;
					}
				}
				else if((ID_.get(i).return_is_instruction_Add() || ID_.get(i).return_is_instruction_Subtraction()) && ID_.get(line_number_reference).return_is_instruction_Multiply()){
					if ((M1_.get(line_number_reference).getcc() <= A4_.get(i).getcc())) {
						waitforoneclockcycle = true;
					} 
					else {
						waitforoneclockcycle = false;
					}
				}
				else if((ID_.get(i).return_is_instruction_Add() || ID_.get(i).return_is_instruction_Subtraction()) && ID_.get(line_number_reference).return_is_instruction_Store()){
					if ((MEM_.get(line_number_reference).getcc() <= A4_.get(i).getcc())) {
						waitforoneclockcycle = true;
					} 
					else {
						waitforoneclockcycle = false;
					}
				}
				//setting RAW conditions for mul-add mul sd conditions
				else if(ID_.get(i).return_is_instruction_Multiply() && (ID_.get(line_number_reference).return_is_instruction_Add() || ID_.get(line_number_reference).return_is_instruction_Subtraction())){
					if ((A1_.get(line_number_reference).getcc() <= M7_.get(i).getcc())) {
						waitforoneclockcycle = true;
					} 
					else {
						waitforoneclockcycle = false;
					}
				}
				else if(ID_.get(i).return_is_instruction_Multiply() && ID_.get(line_number_reference).return_is_instruction_Multiply()){
					if ((M1_.get(line_number_reference).getcc() <= M7_.get(i).getcc())) {
						waitforoneclockcycle = true;
					} 
					else {
						waitforoneclockcycle = false;
					}
				}
				else if(ID_.get(i).return_is_instruction_Multiply() && ID_.get(line_number_reference).return_is_instruction_Store()){
					if ((MEM_.get(line_number_reference).getcc() <= M7_.get(i).getcc())) {
						waitforoneclockcycle = true;
					} 
					else {
						waitforoneclockcycle = false;
					}
				}
				if ((ID_.get(i).return_is_instruction_Load()&& MEM_.get(i).return_has_finished_execution() && !waitforoneclockcycle)
						|| ((ID_.get(i).return_is_instruction_Add() || ID_.get(i).return_is_instruction_Subtraction()) && (!waitforoneclockcycle || MEM_.get(i).getcc()<=MEM_.get(line_number_reference).getcc()))
						|| (ID_.get(i).return_is_instruction_Multiply() && (!waitforoneclockcycle || MEM_.get(i).getcc()<=MEM_.get(line_number_reference).getcc()))) {
					if (line_number_reference == 8)
						System.out.print("\nRAW operand_reference:"
								+ operand_reference + " ");
					if (line_number_reference == 8)
						System.out.print(" " + line_number_reference + " -->"
								+ i + " ");
					break_the_loop = true;
					if (ID_.get(i).return_is_instruction_Load())
						Reg_Imm = String
								.valueOf(MEM_
										.get(i)
										.get_reg_immediate_holds_value_of_contents_to_written_to_writeback_to_register_or_the_value_of_a_register());
					if (ID_.get(i).return_is_instruction_Add() || ID_.get(i).return_is_instruction_Subtraction())
						Reg_Imm = String.valueOf(A4_.get(i).return_Reg_imm());
					if (ID_.get(i).return_is_instruction_Multiply())
						Reg_Imm = String.valueOf(M7_.get(i).return_Reg_imm());
				} else {
					if (line_number_reference == 8)
						System.out.print("\nRAW operand_reference:"
								+ operand_reference + " ");
					if (line_number_reference == 8)
						System.out.print(" " + line_number_reference + " -->"
								+ i + " ");
					is_it_not_ready_to_read = true;
				}
			} 
		}
		return is_it_not_ready_to_read + " " + break_the_loop + " " + Reg_Imm;
	}

	public static boolean Check_WAR(int line_number_reference) {
		boolean waitforoneclockcycle=false;
		String operand_reference = get_ID_operand1(line_number_reference);
		boolean is_it_not_ready_to_write = false;
		for (int i = line_number_reference - 1; i >= 1
				&& !is_it_not_ready_to_write; i--) {
			boolean x = operand_reference.equals(ID_.get(i).return_operand2()) && !ID_.get(i).return_is_instruction_Store();
			boolean y = operand_reference.equals(ID_.get(i).return_operand3());
			if ((x || y) && !ID_.get(i).return_is_instruction_Load()) {
				//setting WAW conditions for Add-add,mul,ld conditions
				if ((ID_.get(i).return_is_instruction_Add() || ID_.get(i).return_is_instruction_Subtraction()) 
						&& (ID_.get(line_number_reference).return_is_instruction_Add() || ID_.get(line_number_reference).return_is_instruction_Subtraction() || ID_.get(line_number_reference).return_is_instruction_Multiply())) {
					if ((WB_.get(line_number_reference).getcc()<=A1_.get(i).getcc())) {
						waitforoneclockcycle = true;
					} 
					else {
						waitforoneclockcycle = false;
					}
				}
				else if ((ID_.get(i).return_is_instruction_Add() || ID_.get(i).return_is_instruction_Subtraction()) && ID_.get(line_number_reference).return_is_instruction_Load()) {
					if ((MEM_.get(line_number_reference).getcc()<=A1_.get(i).getcc())) {
						waitforoneclockcycle = true;
					} 
					else {
						waitforoneclockcycle = false;
					}
				}
				//setting WAW conditions for Mul-add,mul,ld conditions
				else if ((ID_.get(i).return_is_instruction_Multiply())
						&& (ID_.get(line_number_reference).return_is_instruction_Add() || ID_.get(line_number_reference).return_is_instruction_Subtraction() || ID_.get(line_number_reference).return_is_instruction_Multiply())) {
					if ((WB_.get(line_number_reference).getcc()<=M1_.get(i).getcc())) {
						waitforoneclockcycle = true;
					} 
					else {
						waitforoneclockcycle = false;
					}
				}else if ((ID_.get(i).return_is_instruction_Multiply()) && ID_.get(line_number_reference).return_is_instruction_Load()) {
					if ((MEM_.get(line_number_reference).getcc()<=M1_.get(i).getcc())) {
						waitforoneclockcycle = true;
					} 
					else {
						waitforoneclockcycle = false;
					}
				}
				//setting WAW conditions for Sd-add,mul,ld conditions
				else if ((ID_.get(i).return_is_instruction_Store())
						&& (ID_.get(line_number_reference).return_is_instruction_Add() || ID_.get(line_number_reference).return_is_instruction_Subtraction() || ID_.get(line_number_reference).return_is_instruction_Multiply())) {
					if ((WB_.get(line_number_reference).getcc()<=MEM_.get(i).getcc())) {
						waitforoneclockcycle = true;
					} 
					else {
						waitforoneclockcycle = false;
					}
				}else if ((ID_.get(i).return_is_instruction_Store()) && ID_.get(line_number_reference).return_is_instruction_Load()) {
					if ((MEM_.get(line_number_reference).getcc()<=MEM_.get(i).getcc())) {
						waitforoneclockcycle = true;
					} 
					else {
						waitforoneclockcycle = false;
					}
				}
				if ((ID_.get(i).return_is_instruction_Store() && MEM_.get(i).return_has_finished_execution() && (!waitforoneclockcycle || MEM_.get(i).getcc()<MEM_.get(line_number_reference).getcc()))
						|| ((ID_.get(i).return_is_instruction_Add() || ID_.get(i).return_is_instruction_Subtraction()) && A1_.get(i).return_is_this_stage_completed() && (!waitforoneclockcycle || MEM_.get(i).getcc()<MEM_.get(line_number_reference).getcc()))
						|| (ID_.get(i).return_is_instruction_Multiply() && M1_.get(i).return_is_this_stage_completed() && (!waitforoneclockcycle || MEM_.get(i).getcc()<MEM_.get(line_number_reference).getcc()))) {

				} else {
					is_it_not_ready_to_write = true;
					System.out.print("\nWAR operand_reference:"
							+ operand_reference + " ");
					System.out.print(" " + line_number_reference + " -->" + i
							+ " ");
				}
			}
		}
		return is_it_not_ready_to_write;
	}

	public static boolean Check_WAW(int line_number_reference) {
		String operand_reference = get_ID_operand1(line_number_reference);
		boolean is_it_not_ready_to_write = false;
		boolean waitforoneclockcycle = false;
		boolean writingonsamecycle=false;
		//Checking condition so that no two instructions can write at same time as there is only one port to memory, registers
		for(int instr=1;instr<line_number_reference;instr++){
			if(!ID_.get(instr).return_is_instruction_Store()){
				if(WB_.get(instr).getcc() == WB_.get(line_number_reference).getcc()){
					writingonsamecycle=true;
				is_it_not_ready_to_write = true;
				}
			}
		}
		for (int i = line_number_reference - 1; i >= 1
				&& !is_it_not_ready_to_write; i--) {
			if (operand_reference.equals(ID_.get(i).return_operand1()) && !ID_.get(i).return_is_instruction_Store()) {
					if ((WB_.get(line_number_reference).getcc() <= WB_.get(i).getcc())) {
						waitforoneclockcycle = true;
					} 
					else {
						waitforoneclockcycle = false;
					}
				if (WB_.get(i).return_has_written() && !waitforoneclockcycle && !writingonsamecycle) {

				} else {
					is_it_not_ready_to_write = true;
					System.out.print("\nWAW references are:"
							+ operand_reference);
					System.out.print(" " + line_number_reference + " -->" + i
							+ " ");
				}
			}
		}
		return is_it_not_ready_to_write;
	}

}
