import java.io.File;

import java.util.Scanner;


public class Shortest_path {
	 int[][] cost;
     int[] d;
     int []p;
     int []deleted;
    int total_nodes;
    Shortest_path()
    {
    	total_nodes=0;
    }
    void create_topology() 
    {
    	try
    	{
    		
    	Scanner sc = new Scanner(new File("input.txt")); // Scanner for input
    	while(sc.hasNextLine()){
    		
    		sc.nextLine();
    		total_nodes++;	
    	}
    	
    	cost= new int[total_nodes][total_nodes];
    	d=new int[total_nodes];
    	p=new int[total_nodes];
    	deleted=new int[total_nodes];
    	sc.close();
    	sc = new Scanner(new File("./input.txt"));
    	System.out.println("Review orignal Topology matrix:");
    	 for(int i=0;i<total_nodes;i++)
    	 {
    		 System.out.print("\n");
    		 for(int j=0;j<total_nodes;j++) //Extract cost matrix
    		 {
    			cost[i][j]= sc.nextInt();
    			System.out.print(cost[i][j]+" ");
    			if(cost[i][j]==-1)
    				cost[i][j]=999;
    		 }
    		 deleted[i]=0; //Initialize delete and path 
    		 p[i]=-1;
    	}
    		System.out.println("\n The network has "+total_nodes+" nodes");
    	 sc.close();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    void build_table(int rt){
    		int x;
    		System.out.println(" Connection table for Router "+rt);
    		System.out.println(" Destination->Interface");
    		System.out.println("----------------------------------");
    	for(int i=0;i<total_nodes;i++)
    	{
    		x=i+1;
    		 if(i==rt-1)	// If same source and destination
    		 {
    			 
    			 System.out.println(x+ "->"+"-");
    		 }
    		 else
    		 {		int dt;
    			 
    			 	if(deleted[i]==0 ) //If node not deleted
    			 	{
    			 		dt=shortestPath(rt-1,i);
    			 	if(dt==(rt-1))
    			 	{  
    			 		dt=i+1;
    			 	
    			 		System.out.println(x+"-> "+dt);
    			 	}
    			 	else
    			 	{
    			 		dt=dt+1;
    			 		System.out.println(x+"->"+dt);
    			 	}
    			 }
    		 }
    			
    	}
    	
    	
    }
    	void findis(int src,int des)
    	{
    		int x;
    		x= shortestPath(src-1,des-1);
    		
    		System.out.println("\n Distance from "+src+" to "+des+" is "+d[des-1]);
    		System.out.println("Path taken is");
    		int c=0,i,z,temp=-1,t[];
   	     	t=new int[total_nodes];
   	     	i=des-1;
   	     	
   	     	z=0;
   	     	while(i!=src-1) // store the path in the array
   	     	{
   	     		temp=p[i];
   	     		t[z]=temp+1;
   	     		i=temp;
   	     		
   	     		z++;
   	     		c++;
   	     		
   	     	}
   	     	System.out.print(" "+ src +"-> ");
   	     		for(i=c-2;i>=0;i--) // Print path
   	     		System.out.print(" "+t[i]+"-> ");
   	     System.out.print(" "+ des+"\n" );
    	}
    	void findp(int src,int des)
    	{
    		int x;
    		x= shortestPath(src-1,des-1);
    		
    		System.out.println("\n Distance from "+src+" to "+des+" is "+d[des-1]);
    		System.out.println("The packet flows from source " + src + "to destination "+ des +":" );
    		build_table(src);
    		int c=0,i,z,temp=-1,t[];
   	     	t=new int[total_nodes];
   	     	i=des-1;
   	     	
   	     	z=0;
   	     	while(i!=src-1) // store the path in the array
   	     	{
   	     		temp=p[i];
   	     		t[z]=temp+1;
   	     		i=temp;
   	     		
   	     		z++;
   	     		c++;
   	     		
   	     	}
   	     	// System.out.print(" "+ src +"-> ");
   	     		for(i=c-2;i>=0;i--) // Print path
   	     		{
   	     				temp=t[i];
   	     			System.out.println(" We get next hop from table is "+ temp + " to destination"+ des+":" );
   	     			build_table(temp);
   	     		
   	     		}
   	     System.out.print("Reached Destination:"+ des+"\n" );//
    	}
    int shortestPath(int src, int dest) {
    	
    	
    	int s[]=new int[total_nodes];
    	int u,min;
    	   for(int i=0;i<total_nodes;i++)
    	    {	
    		   	if(cost[src][i]!= -1 && deleted[i]==0 )
    		   	{
    	        d[i] = cost[src][i];  //initialize d to distance b/w source and vertices
    	        s[i] = 0; //no node is visited initially
    	        p[i] = src; //path from source
    		   	}
    		   	else
    		   	{
    		   		d[i]= 999;
    		   		s[i]=0;
    		   		p[i]=-1;
    		   	}
    	    }
    	    s[src] = 1; //source is visited first

    		/* find u and d[u] such that d[u] is minimum */

    	    while(s[dest]!=1)
    	    {
    	        u = -1;
    	        min = 999;
    	         u=findmin(s,d);
    	           //System.out.println("Here U is"+u);

    	        if(u==-1)
    	        {	System.out.println("Exit here");
    	        	return -1;
    	        }
    	        s[u] = 1; //add u to visited vertices

    	         /* find d[v] and p[v] for every v in v-s */

    	        for(int v=0;v<total_nodes;v++)
    	        {
    	            if(s[v]==0 && deleted[v]==0)
    	            {
    	            	if( (d[u]+cost[u][v]) < d[v])	
    	            	{	
    	            		
    	            		d[v] = d[u]+cost[u][v];
    	            	
    	            			p[v] = u;
    	            	}
    	            }
    	        }
    	    }
    	     int c=0,i,z,temp=-1,t[];
    	     t=new int[total_nodes];
    	     	i=dest;
    	     	
    	     	z=0;
    	     	while(i!=src)  // Path from source to destination is stored in t[]
    	     	{
    	     		temp=p[i];
    	     		t[z]=temp;
    	     		i=temp;
    	     		//System.out.println("I is "+i);
    	     		z++;
    	     		c++;
    	     		
    	     	}
    	     	int y;
    	     	for(i=0;i<c;i++) // return next hop
    	     	{
    	     			
    	     		if(t[i]==src)
    	     		{
    	     			if(i==0)
    	     			{	y=t[i];
    	     				return y;
    	     			}
    	     			else
    	     			{
    	     				y=t[i-1];
    	     				return y;
    	     			}
    	     		}
    	     	}
    	     	
    	     	return -1;
    	     	
    }
		int findmin(int[] s,int[] d)
		{
			int min=999,index=-1;
			
			for(int i=0;i<total_nodes;i++)
			{
				if(s[i]==0 && deleted[i]==0) // check if node not selected or deleted
				{
					if(d[i]<min)
					{
						min=d[i];
						index=i;
					}
				}
			}
			return index; //Return node with minimum distance 
		}
		
	public static void main(String[] args) {
		
		int choice,flag=0;
		Shortest_path s= new Shortest_path();
		int rt=-1,dt=-1;
		Scanner reader = new Scanner(System.in);
		int first =0;
		do
		{ 
			
		System.out.println("CS542 Link State Routing Simulator");
		System.out.println("1. Create a Network Topology ");
		System.out.println("2. Build a Connection Table");
		System.out.println("3. Shortest Path to Destination Router ");
		System.out.println("4. Modify a Topology");
		System.out.println("5. Print All routing Tables");
		System.out.println("6. Packet Travel from source to destination");
		System.out.println("7. Exit");
		System.out.println("Enter Choice:");
		  
		//System.out.println("Enter a number: ");
		choice = reader.nextInt();
		switch(choice)
		{
		case 1: s.create_topology();
					first=1;
					break;
		case 2: 
					flag=0;
	      			if( first==1)
	      			{
	      			flag=0;
	      			System.out.println("Enter a source router");
	      			rt=reader.nextInt();
	      			if(s.deleted[rt-1]!=0 && rt > s.total_nodes && rt < 1)
	      			{
					System.out.println("\n Deleted or Invalid node! Please enter another node");
					rt=-1;
					flag=1;
	      			}
	      			
	      			if(flag==0)
				s.build_table(rt);
	      			}
	      			else
	      			{
	      				System.out.println("No topolgy exists");
	      			}
					break;
					
		case 3:	 flag=0;
					if(first==1)
					{	
				if(rt==-1 )
				{
			     
					System.out.println("Enter a source router");
						rt=reader.nextInt();
						if(s.deleted[rt-1]!=0 || rt > s.total_nodes || rt < 1)
						{
							System.out.println("\n Deleted or Invalid node! Please enter another node");
							rt=-1;
							flag=1;
						}
						
				}	
						
					if(flag==0)
					{
						System.out.println("Enter a destination router:");
						dt=reader.nextInt();
						if(s.deleted[dt-1]!=0 || dt > s.total_nodes || dt < 1)
						{
							System.out.println("\n Deleted or Invalid node! Please enter another node");
							dt=-1;
							flag=1;
						}
						else
						{
								if(flag==0)
								s.findis(rt,dt);
						}
					}
					}
					else
					{
						System.out.println("No topolgy exists");
					}
						break;
		case 4: 	
			flag=0;
					if(first==1)
					{
						
					System.out.println("Enter a  router that you want to delete:");
						int del=reader.nextInt();
						if(s.deleted[del-1]!=0 || del > s.total_nodes  || del < 1)
						{
							System.out.println("\n Deleted node! Please enter another node");
							rt=-1;
							flag=1;
						}
						else
						{
							 if(del==rt)
								 rt=-1;
							 if(del==dt)
								 dt=-1;
						s.deleted[del-1]=1;
						}
						System.out.println("Node Deleted ");
						
						
						if((rt==-1 &&  flag==0))
						{
					     
							System.out.println("Enter a source router");
								rt=reader.nextInt();
								if(s.deleted[rt-1]!=0 || rt > s.total_nodes || rt < 1)
								{
									System.out.println("\n Deleted or Invalid node! Please enter another node");
									rt=-1;
									flag=1;
								}
								
						}
						
						if((flag==0 && dt==-1) )
						{
							System.out.println("Enter a destination router:");
							dt=reader.nextInt();
							if(s.deleted[dt-1]!=0 || dt > s.total_nodes || dt < 1)
							{
								System.out.println("\n Deleted or Invalid node! Please enter another node");
								dt=-1;
								flag=1;
							}
							
						}
						if(flag==0 && s.deleted[rt-1]==0 && s.deleted[dt-1]==0)
						{
							s.build_table(rt);
							s.findis(rt,dt);
						}
					}
					else
					{
						System.out.println("No topolgy exists");
					}
					break;
		case 5:	    
					if(first==1)
					{
					for(int i=0;i<s.total_nodes;i++)
					{
						if(s.deleted[i]==0)
							s.build_table(i+1);
					}
					}
					else
					{
						System.out.println("No topolgy exists");
					}
					break;
					
		case 6: flag=0;
			if(first==1)
				{	
				if(rt==-1 )
					{
     
						System.out.println("Enter a source router");
							rt=reader.nextInt();
							if(s.deleted[rt-1]!=0 || rt > s.total_nodes || rt < 1)
							{
								System.out.println("\n Deleted or Invalid node! Please enter another node");
								rt=-1;
								flag=1;
							}
			
					}	
			
				if(flag==0 && dt==-1)
				{
					System.out.println("Enter a destination router:");
					dt=reader.nextInt();
					if(s.deleted[dt-1]!=0 || dt > s.total_nodes || dt < 1)
					{
						System.out.println("\n Deleted or Invalid node! Please enter another node");
						dt=-1;
						flag=1;
					}
				}
				
					if(flag==0)
					s.findp(rt,dt);
					
		
				}
			else
			{
				System.out.println("No topolgy exists");
			}
			break;
					
		}
	}while(choice!=7);
		reader.close();
	}

}
