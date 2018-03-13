import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CollectIDFromScanner {

  public static void main(String[] args) {
    Map<String, String> sysconfig = System.getenv();

    for (String keys : sysconfig.keySet()) {
      System.out.println("Key: " + keys + "------------>" + "value: " + sysconfig.get(keys));
    }

    collectTheIds collect = new collectTheIds();

    Thread t1 = new Thread(new ThreadRunnerForScanner(collect));
    t1.setName("1");
    Thread t2 = new Thread(new ThreadRunnerForScanner(collect));
    t2.setName("2");
    Thread t3 = new Thread(new ThreadRunnerForScanner(collect));
    t3.setName("3");
    Thread t4 = new Thread(new ThreadRunnerForScanner(collect));
    t4.setName("4");

    t1.start();
    t2.start();
    t3.start();
    t4.start();

    collect.displayAllIds();

  }
}

class collectTheIds {

  List<String> allFetchedIDs = new LinkedList<>(); //This list will contain a list of all collected IDs from
  // all the scanners
  final Object lock = new Object();
  private int mutex = 0;

  /**
   * This method will collect Ids from different scanners using different threads thereby reducing
   * the execution time and increasing efficiency as one thread can collect inputs from a scanner as
   * other might be waiting for the input
   */
  public void collectIDs() {
    switch (Thread.currentThread().getName()) {
      case "1": {
        List<String> IdsFromScannerOne = new LinkedList<>();
        IdsFromScannerOne.add("ID1");
        consoliadteAllIDs(IdsFromScannerOne);
        synchronized (lock) {
          mutex++;
        }
      }
      break;
      case "2": {
        List<String> IdsFromScannerTwo = new LinkedList<>();
        IdsFromScannerTwo.add("ID2");
//        try {
//          Thread.sleep(4000);
//          System.out.println(Thread.currentThread().getName() + " is sleeping..zzzz");
//        } catch (InterruptedException exe) {
//          System.out.println("who the fuck woke me up");
//        }
        consoliadteAllIDs(IdsFromScannerTwo);
        synchronized (lock) {
          mutex++;
        }
      }
      break;
      case "3": {
        List<String> IdsFromScannerThree = new LinkedList<>();
        IdsFromScannerThree.add("ID3");
        consoliadteAllIDs(IdsFromScannerThree);
        synchronized (lock) {
          mutex++;
        }
      }
      break;
      case "4": {
        List<String> IdsFromScannerFour = new LinkedList<>();
        IdsFromScannerFour.add("ID4");
        consoliadteAllIDs(IdsFromScannerFour);
        synchronized (lock) {
          mutex++;
        }
      }
      default:
        // do nothin....
    }
  }

  // This will store the ID s obtained from the first scanner

  /**
   * This method is used to collect all the IDs generated from the scanners using multiple threads
   * and puts them together
   */
  private synchronized void consoliadteAllIDs(List<String> myList) {

    System.out.println(
        "Thread " + Thread.currentThread().getName() + "is adding IDs from its own list");
    //System.out.println("is adding its results");
    this.allFetchedIDs.addAll(myList);
  }

  /**
   * This nethod is used to display the entire ID set collected from all the scanners As the results
   * are to be executed in the end and we are using threads so a simple mutex is added that is to be
   * filled up by all the threads so that the list of IDs are displayed only at the end
   */
  public void displayAllIds() {
      while (mutex != 4) {
        System.out.println("going in loop again"+mutex);
    }

    System.out.println("wow now  I can write");
    System.out.println("List of All Ids");
    for (String str : allFetchedIDs) {
      System.out.println(str);
    }
  }
}

class ThreadRunnerForScanner implements Runnable {

  private collectTheIds collect;

  public ThreadRunnerForScanner(collectTheIds receivedCollector) {
    this.collect = receivedCollector;
  }

  @Override
  public void run() {
    collect.collectIDs();
  }

}

