//Sebastian Hadley
//c3349742
//Implements the Multi Level variable Queue algorithm.
import java.util.*;
public class FBV
{
  //Private Attributes
  private int time;
  private int lastswitch;
  private int winner;
  private int dispatch;
  private ArrayList<Process> Processes;
  private ArrayList<Integer> LotNum;
  private double TotalWTime;
  private double TotalTATime;
  private double AvgWTime;
  private double AvgTATime;
  private Queue<Process> Priority1;
  private Queue<Process> Priority2;
  private Queue<Process> Priority3;
  private Queue<Process> Priority4;
  private ArrayList<Queue<Process>> Queues;
  public FBV()
  {
    time = 0;
    TotalWTime = 0;
    TotalTATime =0;
    lastswitch = 0;
    AvgWTime = 0;
    AvgTATime = 0;
    dispatch = 0;
    Priority1 = new LinkedList<Process>();
    Priority2 = new LinkedList<Process>();
    Priority3 = new LinkedList<Process>();
    Priority4 = new LinkedList<Process>();
    Queues = new ArrayList<Queue<Process>>();
    Processes = new ArrayList<Process>();
  }
  //Fills ArrayList with Processes for the classes use.
  public void CreateProcesses(ArrayList<Process> Og,int d)
  {
    for(int i = 0; i < Og.size(); i++)
    {
      Processes.add(Og.get(i));
    }
    dispatch = d;
  }
  //Generates output for the command line.
  public void Output()
  {
    //Determines total number of switches.
    int SwitchCount = 0;
    for(int i = 0; i < Processes.size(); i++)
    {
      SwitchCount = SwitchCount + Processes.get(i).getStartCount();
    }
    //Sorts the Process Switched by the time they occured and pairs them to the process switched to.
    //Arrays are used to store the data.
    int times[];
    times = new int[SwitchCount];
    String strings[];
    strings = new String[SwitchCount];
    //Variabes are used to store for comparing within the loop.
    String singleid = "";
    int x = time+1;
    System.out.println("FBV");
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
  //Sorts the processes and stores results.
  public ArrayList<Process> sort(ArrayList<Process> p)
  {
    //initialises variables used for sorting the processes.
    int priority = 0;
    dispatch = 1;
    ArrayList<Integer> templist;
    templist = new ArrayList<Integer>();
    Process temp;
    int timecost = 0;

    int done = 0;
    Queues.add(Priority1);
    Queues.add(Priority2);
    Queues.add(Priority3);
    Queues.add(Priority4);
    //Loops until all processes have completed.
    while(done < p.size())
    {
      int timecheck = 0;
      //Adds processes to the queue if the time they have arrived.
      for(int i = 0; i < p.size(); i++)
      {
        if(p.get(i).getATime() <= time)
        {
          if(!templist.contains(i))
          {
            Queues.get(0).add(p.get(i));
            templist.add(i);
          }
        }
      }
      for(int i = 0; i < p.size(); i++)
      {
        int processreset = 0;
        if(Queues.get(3).contains(p.get(i)))
        {
        if(processreset < p.get(i).getStartTimes(p.get(i).getStartCount()-1))
        {
          processreset = p.get(i).getStartTimes(p.get(i).getStartCount()-1);
          if((time - 32) > p.get(i).getStartTimes(p.get(i).getStartCount()-1))
          {
            Queues.get(3).remove(p.get(i));
            Queues.get(0).add(p.get(i));

          }
        }
        }
      }
      //loops through the process using the queues.
      for(int i = 0; i < Queues.size(); i++)
      {
        if(Queues.get(i).size() > 0)
        {
            timecheck++;
            timecost = priority + 1;
            if(priority > 1)
            {
              timecost = 4;
            }

            time = time + dispatch;
            temp = Queues.get(i).peek();
            temp.addStartTimes(time);
            temp.setETimeRem(temp.getETimeRem() - timecost);
            Queues.get(i).remove(temp);
            time = time + timecost;
            if(temp.getETimeRem() <= 0)
            {
              time = time + temp.getETimeRem();
              temp.setCTime(time);
              temp.setTATime(temp.getCTime() - temp.getATime());
              temp.setWTime(temp.getTATime() - temp.getETime());
              done++;
              if(Queues.get(i).size() == 0)
              {
                priority++;
                if(priority == 4)
                {
                  priority = 0;
                }
                break;
              }
              break;
            }
            for(int m = 0; m < p.size(); m++)
            {
              if(p.get(m).getATime() <= time)
              {
                if(!templist.contains(m))
                {
                  Queues.get(0).add(p.get(m));
                  templist.add(m);
                  priority = 0;
                }
              }
            }
            if(Queues.get(priority).size() == 0)
            {
              if(priority ==  0 && Queues.get(1).size() == 0 && Queues.get(2).size() == 0 && Queues.get(3).size() == 0)
              {
                if(temp.getETimeRem() > 0 )
                {
                  if(time != 11)
                  {
                  Queues.get(0).add(temp);
                }
                }
              }
              if(priority == 3)
              {
                if(time != 11)
                {
                priority = 0;
                }
              }
              else
              {
                priority++;
                if(temp.getETimeRem() > 0)
                {
                  Queues.get(priority).add(temp);
                }
              }
            }
            else
            {
              Queues.get(i+1).add(temp);
            }
            break;
        }
      }
      if(timecheck == 0)
      {
        time++;
      }
    }
    lastswitch = time - timecost;
    return p;
  }
  //Returns the average turnaround time for the arraylist
  public double getTurnAround()
  {
    return AvgTATime;
  }
  //Returns the average wait time for the ArrayList
  public double getWait()
  {
    return AvgWTime;
  }
  //Calculates the average TurnAround time for the ArrayList
  public void TurnAround()
  {
    int avg = 0;
    for(int i = 0; i < Processes.size(); i++)
    {
      avg = avg + Processes.get(i).getTATime();
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
      avg = avg + Processes.get(i).getWTime();
    }
    TotalWTime = avg;
    AvgWTime = TotalWTime/ Processes.size();
  }
  //Resets the Processes for the next sorting algorithm
  public void reset()
  {
    for(int i = 0; i < Processes.size(); i++)
    {
      Processes.get(i).setETimeRem(Processes.get(i).getETime());
      Processes.get(i).resetStarts();
    }
  }
  public void main()
  {
    Processes = sort(Processes);
    Output();
    TurnAround();
    Wait();
    reset();
    }
}
