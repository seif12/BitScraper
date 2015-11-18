package websocks;

import javax.websocket.OnMessage;

public class WSClient 
{
	 
	private static Object waitLock = new Object();
	 
	 @OnMessage
	 public void onMessage(String message) 
	 {
	
	        System.out.println("Received msg: "+message);        
	 }
	
	 private static void  wait4TerminateSignal()
	  {
	    synchronized(waitLock)
	    	{
	    		try {
	    				waitLock.wait();
	    			} 
	    		catch (InterruptedException e) 
	    			{    
	    				e.printStackTrace();
	    			}
	    	}
	    }
}
