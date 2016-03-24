package com.memorymodel;


/*
 * a class that will be used by multipl threads. hence we should be careful on publishing it properly before it is used.
 */
public class SafePublication {

	private int var1;
	private String st1;

	public SafePublication(){
		startpopulatingVar1();
		startpopulatingStr1();
	}

	private void startpopulatingVar1() {
		var1=10;

	}

	private void startpopulatingStr1() {
		st1="strrr";

	}

}



/**
 * this simple looking class may not publish the resource SafePublication to a thread completely as JMM does not guarantee that assigning reference to rs ,
 * populating all variables on rs and returning it from method happens in order.
 * Lets say thread A calls getResource(), since rs is null, a new SafePublication() will be assigned
 * At that time Thread B calls getResource(), it sees rs is not null, hence the rs is returned. But there is no guarantee that Thread B has got fully populated
 * SafePublication object.
 * @author user
 *
 */

class NotThreadSafePublication{
	SafePublication rs=null;
	public SafePublication getResource(){
		if(rs==null){
			rs=new SafePublication();
		}
		return rs;
	}

}

/**
 *
 * sol1
 *
 * since we are using static variable , it will be pouplated after class loading, before it is used by any thread.
 * We dont need extra synchronization here
 * @author user
 *
 */
class EagerSafeInitialisation{
 private static SafePublication rs=new SafePublication();
 public SafePublication getResource(){
	 return rs;
 }
}


/**
 * sol2 : SafePublication is private, not static and not eager loaded. But publish method getResource() is guarder by synchronized
 *
 * @author user
 *
 */
class LazySafeInitialization{
	private  SafePublication rs=null;

	public synchronized SafePublication getResource(){
        if(rs==null){rs=new SafePublication();}
        return rs;
	}
}


/**
 * sol 3:
 * we can combine the above sol1 and sol 2 to publish a object with lazy initalisation passing it to a helper class
 * we dont need extra synchronization here
 * @author user
 *
 */
class LazySafeInitializationWithStaticHelper{
	  private static class InitializerHelper{
           public static SafePublication rs=new SafePublication();
	  }

	  public SafePublication getResource(){
		  return InitializerHelper.rs;
	  }
}


