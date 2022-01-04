//Sebastian hadley
//c3349742
//Used to implement the lottery algorithm and return the results.
import java.util.*;
public class LTR
{
  //Private Attributes
  private int time;
  private int SwitchCount;
  private int tottic;
  private int winner;
  private int dispatch;
  private ArrayList<Process> Processes;
  private ArrayList<Integer> LotNum;
  private double TotalWTime;
  private double TotalTATime;
  private double AvgWTime;
  private double AvgTATime;
  //Constructor
  public LTR()
  {
    tottic = 0;
    SwitchCount = 0;
    winner = 0;
    time = 0;
    Processes = new ArrayList<Process>();
    LotNum = new ArrayList<Integer>();
    TotalWTime = 0;
    TotalTATime =0;
    AvgWTime = 0;
    AvgTATime = 0;
    dispatch = 0;
  }
  //Creates processes from A1.
  public void CreateProcesses(ArrayList<Process> Og, ArrayList<Integer> Numbers ,int d)
  {
    for(int i = 0; i < Og.size(); i++)
    {
      Processes.add(Og.get(i));
    }
    for(int i = 0; i < Numbers.size(); i++)
    {
      LotNum.add(Numbers.get(i));
    }
    dispatch = d;
  }
  //Outputs the results from the algorithm.
  public void Output()
  {
    //Sorts the Process Switched by the time they occured and pairs them to the process switched to.
    //Arrays are used to store the data.
    int times[];
    times = new int[SwitchCount];
    String strings[];
    strings = new String[SwitchCount];
    //Variabes are used to store for comparing within the loop.
    String singleid = "";
    int x = time+1;
    System.out.println("LTR");
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
      if(x == time)
      {
        break;
      }
      x = time;
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

  //Calculates the turn around time for the process.9
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
  //Calculates the average wait time for the process.
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

  public double getTurnAround()
  {
    return AvgTATime;
  }
  public double getWait()
  {
    return AvgWTime;
  }
  //Implements the algorithm.9
  public ArrayList<Process> sort(ArrayList<Process> p)
  {
    int finished = 0;
    int timerem = 0;
    int temp = 0;
      for(int m = 0; m < LotNum.size(); m++)
      {
        int tottic = 0;
        int qcount = 0;
        int reset = p.size() + 1;
        //Checks if all processes have been executed.
        if(finished == p.size())
        {
          break;
        }
        //Determines total tickets in current queue.
        for(int i = 0; i < p.size(); i++)
        {
          if(p.get(i).getETimeRem() > 0)
          {
            if(p.get(i).getATime() <= time)
            {

              tottic = tottic + p.get(i).getTickets();
            }
          }
        }
        //Allows loop to continue cycle and adds one to time, while keeping the loop at same position.
        if(tottic == 0)
        {
          time++;
          m--;
          continue;
        }
        winner = LotNum.get(m)%tottic;
        temp = winner;
        //Determines who the winner is if there is no remainder and simulates a process being at the back of queue if has been previously executed.
        for(int i = 0; i < p.size(); i++)
          {
            if(p.get(i).getETimeRem() > 0)
            {
              if(p.get(i).getATime() <= time)
              {

                winner = i;
                qcount++;
                if(p.get(i).getETimeRem() < p.get(i).getETime())
                {
                  reset = i;
                }
              }
            }
          }
          //Loops through array checking which index's tickets contain the winning number.
        for(int i = 0; i < p.size(); i++)
        {
          if(p.get(i).getETimeRem() > 0)
          {
            if(p.get(i).getATime() <= time)
            {
                if(temp == 0)
                {
                  i = winner;
                }
                //If the index is meant to be at the back of the queue due to previously running will skip its loop and continue through the loop.
                if(i == reset && qcount > i+1)
                {
                  continue;
                }

                temp = temp - p.get(i).getTickets();
                //Calculates data for the process.
                if(temp <=  0)
                {
                  SwitchCount++;
                  timerem = p.get(i).getETimeRem();
                  timerem = timerem - 4;
                  time = time + dispatch;
                  p.get(i).addStartTimes(time);
                  time = time + 4;
                  p.get(i).setETimeRem(timerem);
                  if(p.get(i).getETimeRem() <= 0)
                  {
                   time = time + p.get(i).getETimeRem();
                   p.get(i).setCTime(time);
                   p.get(i).setTATime(p.get(i).getCTime() - p.get(i).getATime());
                   p.get(i).setWTime(p.get(i).getTATime() - p.get(i).getETime());
                   finished++;
                   break;
                 }
                 break;
                }
              }
            }
          }
        }
    return p;
  }


  public void main()
  {
    Processes = sort(Processes);
    TurnAround();
    Wait();
    Output();
    return;
  }
}
