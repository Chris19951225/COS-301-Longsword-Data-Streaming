/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.concurrent.ConcurrentLinkedQueue; 
/**
 *
 * @author Hristian Vitrychenko
 */
public class ConcQueue //Only an example class, real queue for data module implemented elsewhere 
{    
    private ConcurrentLinkedQueue<String> dataQueue; 
    
    public ConcQueue()
    {
        dataQueue = new ConcurrentLinkedQueue<>(); 
    }
    
    public boolean enqueue(String macAddress)
    {
        boolean inserted = false; 
        
        while(!inserted)//keep trying to insert the 'mac address' until successful (equivalent to buffering but doubtful that it would take long)
        {
            inserted = dataQueue.add("This is an example (should be mac address in string format"); 

            if(inserted)
            {
                System.out.println("Item inserted successfully"); 
            }
            else
            {
                System.out.println("Something went wrong, try again");//Every time this shows up, indicates increase in wait time  
            }
            
            //Time-out for trying to insert? 
        }
        
        return inserted; 
    }
    
    public String dequeue()//Always try to dequeue front as it has been waiting longest 
    {
        String str = ""; 
        boolean removed = false; 
        
        while(!removed)
        {
            if(!dataQueue.isEmpty())//Always have to check if queue is empty before deciding to remove (process 'mac address') item 
            {
                str = dataQueue.poll(); 
            }

            if(!"".equals(str))
            {
                System.out.println("Item successfully dequeued"); 
                //If item successfully removed, should validation occur before processing item? 
                //Or are we confident all data entered into queue has already been validated? 
            }
            else
            {
                System.out.println("No items in the queue");//Every time this message pops it simulates our module resting since queue is empty
            }
            
            //Should we have a timer which decides how long to wait before giving up on removing? 
        }
        
        return str; 
    }
    
    public static void main(String[] args)
    {
        ConcQueue q = new ConcQueue(); 
        boolean opWorked; 
        
        opWorked = q.enqueue("Simple mac address");//Attempt to insert a new request 
        
        if(opWorked)
        {
            //Continue with program knowing a new value was successfully inserted
        }
        else
        {
            //make provisions to attemp inserting mac address again
        }
        
        String ret = q.dequeue(); 
        
        if(!"".equals(ret))
        {
            //Successfully got item to be processed from queue. Validation done again? 
        }
        else
        {
            //Make provisions to attempt dequeue again the moment queue is no longer empty. 
        }
    }
}
