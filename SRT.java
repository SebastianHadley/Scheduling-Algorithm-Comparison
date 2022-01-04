//Sebastian Hadley
//c3349742
//Implements the Shortest Remaining Time First algorithm
import java.util.*;
import java.util.ArrayList;
public class SRT
{
  //Private Attributes
  private int SortTime;
  private double TotalWTime;
  private double TotalTATime;
  private double AvgWTime;
  private int SwitchCount;
  private double AvgTATime;
  private int dispatch;
  private int lastswitch;
  private ArrayList<Process> Processes;
  //Constructor
  public SRT()
  {
    TotalWTime = 0;
    TotalTATime = 0;
    SwitchCount = 0;
    AvgWTime = 0;
    AvgTATime = 0;
    dispatch = 0;
    SortTime = -1;
    Processes = new ArrayList<Process>();
    lastswitch = 0;
  }

  //Copies the processes from the main file in the SRT file for computing.
  public void CreateProcesses(ArrayList<Process> Og, int d)
  {
    for(int i = 0; i < Og.size(); i++)
    {
      Processes.add(Og.get(i));
    }
    dispatch = d;
  }

  //Sorts the Processes and gets the related data.
  public ArrayList<Process> Sort(ArrayList<Process> p)
  {
    //P1 is used to ensure that a process runs for at least 1ms before switching processes.
    int p1 = 0;
    //LowestRemindex is used to store the index of the Process with the lowest remaining time.
    Integer LowestRemIndex = 0;
    //SortCheck Stores the number of processes completed.
    int SortCheck = 0;
    //Swap records if the dispatcher has swapped in the current time slot.
    Integer Swap = 0;
    //ArrivalTimes[] Stores the arrival times of the processes by their indexes for easier access.
    int ArrivalTimes[];
    ArrivalTimes = new int[p.size()];
    for(int i = 0; i < p.size(); i++)
    {
      ArrivalTimes[i] = p.get(i).getATime();
    }

    while(SortCheck < p.size())
    {
      SortTime++;
      //Checks for any processes that arrive at 0 and find the one with the lowest execution time.
      if(SortTime == 0)
      {
        for(int i = 0; i < p.size(); i++)
        {
          if(ArrivalTimes[i] == 0)
          {
            if(p.get(i).getETime() < p.get(LowestRemIndex).getETime())
            {
              LowestRemIndex = i;
              Swap = 1;
              p1 = 1;
            }
            Swap = 1;
          }
        }
        if(Swap == 1)
        {
          SortTime = SortTime + dispatch;
          p.get(LowestRemIndex).addStartTimes(SortTime);
          SwitchCount++;
        }
      }
      //runs one time slot of a process if it was dispatched on the previous timeslot.
      else if(p1 == 1)
      {
        p1 = 0;
        int temp = 0;
        temp = p.get(LowestRemIndex).getETimeRem();
        temp = temp--;
        p.get(LowestRemIndex).setETimeRem(p.get(LowestRemIndex).getETimeRem() - 1);
        if(p.get(LowestRemIndex).getETimeRem() == 0)
        {
          p.get(LowestRemIndex).setCTime(SortTime);
          p.get(LowestRemIndex).setTATime(p.get(LowestRemIndex).getCTime() - p.get(LowestRemIndex).getATime());
          p.get(LowestRemIndex).setWTime((p.get(LowestRemIndex).getTATime()) - p.get(LowestRemIndex).getETime());
          SortCheck ++;
        }
      }
      //Determines whether to continue running process or switch to a new process and does whichever is applicable.
      else
      {
        Swap = 0;
        //Determines whether their is a process that has less remaining time then the one currently being executed.
        for(int i = 0; i < p.size(); i++)
        {
          if(p.get(i).getETimeRem() > 0)
          {
            if(p.get(i).getETimeRem() < p.get(LowestRemIndex).getETimeRem() || p.get(LowestRemIndex).getETimeRem() == 0)
            {
              if(ArrivalTimes[i] < SortTime)
              {
                LowestRemIndex = i;
                Swap = 1;
                p1 = 1;
              }
            }
          }
        }
        //Does calculations for if the current process is still the one with shortest time remaining.
        if(Swap == 0)
        {
          int temp = 0;
          temp = p.get(LowestRemIndex).getETimeRem();
          temp = temp--;
          p.get(LowestRemIndex).setETimeRem(p.get(LowestRemIndex).getETimeRem() - 1);
          if(p.get(LowestRemIndex).getETimeRem() == 0)
          {
            p.get(LowestRemIndex).setCTime(SortTime);
            p.get(LowestRemIndex).setTATime(p.get(LowestRemIndex).getCTime() - p.get(LowestRemIndex).getATime());
            p.get(LowestRemIndex).setWTime((p.get(LowestRemIndex).getTATime()) - p.get(LowestRemIndex).getETime());
            SortCheck ++;
          }
        }
        //Adds dispatch time and records the time at which a process was switched to.
        else
        {
          p.get(LowestRemIndex).addStartTimes(SortTime);
          SortTime = SortTime - 1;
          SortTime = SortTime + dispatch;
          SwitchCount++;
        }
      }
    }
    lastswitch = SortTime;
    return p;
  }
  //Calculates the Total and AVG TurnAround time for the Algorithm.
  public void TurnAround()
  {
    int avg = 0;
    for(int i = 0; i < Processes.size(); i++)
    {
      avg = Processes.get(i).getTATime() + avg;
    }
    System.out.println(avg);
    TotalTATime = avg;
    AvgTATime = TotalTATime/Processes.size();
  }
  //Calculates the Total and AVG Waiting time for the Algorithm.
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
  //returns average turn around time.
  public double getTurnAround()
  {
    return AvgTATime;
  }
  //Returns average wait time.
  public double getWait()
  {
    return AvgWTime;
  }
  //Resets the processes for the next algorithm.
  public void reset()
  {
    for(int i = 0; i < Processes.size(); i++)
    {
      Processes.get(i).setETimeRem(Processes.get(i).getETime());
      Processes.get(i).resetStarts();
    }
  }
  //Outputs the results into the console with the chosen format.
  public void Output()
  {

    //Sorts the Process Switched by the time they occured and pairs them to the process switched to.
    //Arrays are used to store the data.
    int times[];
    times = new int[SwitchCount];
    String strings[];
    strings = new String[SwitchCount];
    //Variabes are used to store for comparing within the loop.9
    String singleid = "";
    int x = lastswitch;
    System.out.println("SRT");
    for(int i = 0; i < SwitchCount; i++)
    {
      for(int a = 0; a < Processes.size(); a++)
      {
        for(int b = 0; b < Processes.get(a).getStartCount(); b++)
        {
          if(i == 0 || Processes.get(a).getStartTimes(b) > times[i-1])
          {
            if(Processes.get(a).getStartTimes(b) < x)
            {
              x = Processes.get(a).getStartTimes(b);
              singleid = Processes.get(a).getProcessID();
            }
          }
        }
      }
      times[i] = x;
      strings[i] = singleid;
      if(x == lastswitch)
      {
        break;
      }
      x = lastswitch;
    }
    for(int i = 0; i < SwitchCount; i++)
    {
      System.out.println("T"+times[i]+": "+ strings[i]);
    }
    System.out.println();
    System.out.println("Process" + "\t" + "Turnaround Time" + "\t" +  "Waiting Time");
    for (int i = 0; i < Processes.size(); i++)
    {
      System.out.println(Processes.get(i).getProcessID() + "\t" + Processes.get(i).getTATime() + "\t\t"+ Processes.get(i).getWTime());
    }
    System.out.println();
  }
  public void main()
  {
    Processes = Sort(Processes);
    Wait();
    TurnAround();
    Output();
    reset();
  }
}
