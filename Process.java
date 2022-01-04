///COmmentin
import java.util.ArrayList;
public class Process
{
  //Attributes
  private String ProcessID;
  private int ATime;
  private int ETime;
  private int Tickets;
  private int PNumber;
  private int TATime;
  private int WTime;
  private int CTime;
  private int ETimeRem;
  private ArrayList<Integer> StartTimes;
  private int StartCount;
  //Standard Constructor.
  public Process()
  {
    ProcessID = "";
    ATime = 0;
    ETime = 0;
    Tickets = 0;
    PNumber = 0;
    TATime = 0;
    WTime = 0;
    CTime = 0;
    ETimeRem = 0;
    StartTimes = new ArrayList<Integer>();
    StartCount = 0;
  }
  //Specific Constructor
  public Process(String id, int a, int e, int t)
  {
    ProcessID = id;
    ATime = a;
    ETime = e;
    Tickets = t;
    PNumber = ProcessID.charAt(1);
    TATime = 0;
    WTime = 0;
    CTime = 0;
    StartCount = 0;
    ETimeRem = e;
    StartTimes = new ArrayList<Integer>();
  }
  //------Setters------//
  public void setAll(String id, int a, int e, int t)
  {
    ProcessID = id;
    ATime = a;
    ETime = e;
    Tickets = t;
  }
  public void setATime(int x)
  {
    ATime = x;
  }
  public void setETime(int x)
  {
    ETime = x;
  }
  public void setTickets(int x)
  {
    Tickets = x;
  }
  public void setTATime(int x)
  {
    TATime = x;
  }
  public void setWTime(int x)
  {
    WTime = x;
  }
  public void setProcessID(String x)
  {
    ProcessID = x;
  }
  public void setCTime(int x)
  {
    CTime = x;
  }
  public void setETimeRem(int x)
  {
    ETimeRem = x;
  }
  public void reduceETimeRem()
  {
    ETimeRem = ETimeRem - 1;
  }
  public void addStartTimes(int time)
  {
    StartTimes.add(time);
    StartCount++;
  }
  public void resetStarts()
  {
    StartTimes.clear();
    StartCount = 0;
  }
  //------Getters------//
  public int getATime()
  {
    return ATime;
  }
  public int getETime()
  {
    return ETime;
  }
  public int getTickets()
  {
    return Tickets;
  }
  public String getProcessID()
  {
    return ProcessID;
  }
  public int getTATime()
  {
    return TATime;
  }
  public int getWTime()
  {
    return WTime;
  }
  public int getCTime()
  {
    return CTime;
  }
  public int getETimeRem()
  {
    return ETimeRem;
  }
  public int getStartTimes(int index)
  {
    return StartTimes.get(index);
  }
  public int getStartCount()
  {
    return StartCount;
  }
}
