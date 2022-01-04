//Assignment 1 For operating systems//
//Sebastian Hadley
//c3349742
//Used to Get the processes from external file and create isntances of classses.
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Scanner;
class A1{
	public static void main(String[] args) throws IOException
	{
		//Declare variables
		ArrayList<Integer> RandomNumbers = new ArrayList<Integer>();
		ArrayList<Process> MainProcesses = new ArrayList<Process>();
		String ProcessID = "NA";
		int ATime = 0;
		int ETime = 0;
		int Tickets = 0;
		Scanner file = new Scanner(new File(args[0]));
		int test = 0;
		//String to store inputs from scanner.
		String text = "";
		int Dispatch = 0;
		//Scan for values in file.
		try
		{
			while(file.hasNextLine())
			{
				text = file.next();
				if(text.equals("BEGIN"))
				{
					text = file.next();
					if(text.equals("DISP:"))
					{
						text = file.next();
						Dispatch = Integer.parseInt(text);
					}
				}
				else if(text.equals("END"))
				{
					text = file.next();
					if(text.equals("ID:"))
					{
						text = file.next();
						ProcessID = text;
					}
				}
				else if (text.equals("EOF"))
				{
					break;
				}
				else if(text.equals("BEGINRANDOM"))
				{
					text = file.next();
					while(!text.equals("ENDRANDOM"))
					{
						RandomNumbers.add(Integer.parseInt(text));
						text = file.next();
					}
					break;
				}
				//Else for calculating values for each attribute of each process
				else
				{
					if(text.equals("ID:"))
					{
						text = file.next();
						ProcessID = text;
					}
					text = file.next();
					if(text.equals("Arrive:"))
					{
						text = file.next();
						ATime = Integer.parseInt(text);
					}
					text = file.next();
					if(text.equals("ExecSize:"))
					{
						text = file.next();
						ETime = Integer.parseInt(text);
					}
					text = file.next();
					if(text.equals("Tickets:"))
					{
						text = file.next();
						Tickets = Integer.parseInt(text);
						text = file.next();
					}
					Process ProcessData = new Process(ProcessID, ATime, ETime, Tickets);
					MainProcesses.add(ProcessData);
					//Process FCFSData = new Process(ProcessID, ATime, ETime, Tickets);
				}
			}
		}
		catch(Exception fail)
		{
			System.out.println("Error with file format");
		}
		file.close();
		 //Generate Each ALgorithm and Results.
		 	//First come first serve
			FCFS FirstCome = new FCFS();
			FirstCome.CreateProcesses(MainProcesses, Dispatch);
	 	  FirstCome.main();
		 //Shortest time remaining
		 SRT ShortestRem = new SRT();
	   ShortestRem.CreateProcesses(MainProcesses, Dispatch);
		 ShortestRem.main();

		 FBV PriorityQueue = new FBV();
		 PriorityQueue.CreateProcesses(MainProcesses, Dispatch);
		 PriorityQueue.main();

		  //Lottery
			LTR Lottery = new LTR();
			Lottery.CreateProcesses(MainProcesses,RandomNumbers,Dispatch);
		  Lottery.main();


			System.out.println("Summary");
			System.out.println("Algorithm"+" Average Turnaround Time "+"Waiting Time");
			System.out.println("FCFS"+"\t"+"  "+FirstCome.getTurnAround()+"\t\t\t"+"  "+FirstCome.getWait());
			System.out.println("SRT"+"\t"+"  "+ShortestRem.getTurnAround()+"\t\t\t"+"  "+ShortestRem.getWait());
			System.out.println("FBV"+"\t"+"  "+PriorityQueue.getTurnAround()+"\t\t\t"+"  "+PriorityQueue.getWait());
			System.out.println("LTR"+"\t"+"  "+Lottery.getTurnAround()+"\t\t\t"+"  "+Lottery.getWait());
	}
}
