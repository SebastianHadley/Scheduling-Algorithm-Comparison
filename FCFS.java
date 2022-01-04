//Sebastian Hadley
//c3349742
//Implements the First Come First serve algorithm and returns the results from the algorithm.
import java.util.*;
import java.util.ArrayList;
public class FCFS
{
	private double TotalWTime;
	private double TotalTATime;
	private double AvgWTime;
	private double AvgTATime;
	private int dispatch;
	private ArrayList<Process> Processes;
	//Constructor
	public FCFS()
	{
		TotalWTime = 0;
		TotalTATime = 0;
		AvgWTime = 0;
		AvgTATime = 0;
		dispatch = 0;
		Processes = new ArrayList<Process>();
	}
	//Creates the processes from the main file in the FCFS file for copmuting.
	public void CreateProcesses(ArrayList<Process> Original, int d)
	{
		for (int i = 0; i < Original.size(); i++)
		{
			Processes.add(Original.get(i));
		}
		dispatch = d;
	}
	//Implements the algorithm.
	public ArrayList<Process> Sort(ArrayList<Process> p)
	{
		//Loops through processes and completes their execution.
		for (int i = 0; i < p.size(); i++)
		{
			if(i == 0)
			{
				p.get(i).setWTime(dispatch + p.get(i).getATime());
				p.get(i).setTATime(dispatch + p.get(i).getETime());
				p.get(i).setCTime(p.get(i).getTATime());
			}
			else
			p.get(i).setCTime(p.get(i-1).getCTime() + dispatch + p.get(i).getETime());
			p.get(i).setTATime(p.get(i).getCTime() - p.get(i).getATime());
			p.get(i).setWTime(p.get(i).getTATime() - p.get(i).getETime());
		}
		return p;
	}
	public void Output()
	{
		System.out.println("FCFS:");
		for(int i = 0; i < Processes.size(); i++)
		{
			System.out.println("T"+(Processes.get(i).getCTime() - Processes.get(i).getETime())+": "+Processes.get(i).getProcessID());
		}
		System.out.println();
		System.out.println("Process" + "\t" + "Turnaround Time" + "\t" +  "Waiting Time");
		for (int i = 0; i < Processes.size(); i++)
		{
			System.out.println(Processes.get(i).getProcessID() + "\t" + Processes.get(i).getTATime() + "\t\t"+ Processes.get(i).getWTime());
		}
			System.out.println();
	}
	//Calculates the average tutrn around time for the algorithm.
	public void TurnAround()
	{
		int avg = 0;
		for(int i = 0; i < Processes.size(); i++)
		{
			avg = Processes.get(i).getTATime() + avg;
		}
		TotalTATime = avg;
		AvgTATime = TotalTATime/Processes.size();

	}
	//Caclulates the average Wait time for the algorithm.
	public void Wait()
	{
		int avg = 0;
		for(int i = 0; i < Processes.size(); i++)
		{
			avg = Processes.get(i).getWTime() + avg;
		}
		TotalWTime = avg;
		AvgWTime = TotalWTime/ Processes.size();
	}
  //Returns the average turn around time.
	public double getTurnAround()
	{
		return AvgTATime;
	}
	//Returns the average wait time.
	public double getWait()
	{
		return AvgWTime;
	}

	public void main()
	{
		//Sorts the data and records the values for the results.9
		Processes = Sort(Processes);
		TurnAround();
		Wait();
		Output();
	}

}
