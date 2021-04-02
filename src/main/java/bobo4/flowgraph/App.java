package bobo4.flowgraph;

public class App 
{
    public static void main( String[] args )
    {
    	Graph hung = new Graph();
    	hung.print();
    	
    	FindPath dog = new FindPath(hung.getGraph());
    	dog.print();
    }
}
