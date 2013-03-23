package by.bsuir.iit.abramov.ppvis.grapheditor_new;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import by.bsuir.iit.abramov.ppvis.grapheditor_new.util.Pair;

public class Matrix {
	private int size = 6;
	private Map<String, Integer> IDtoNum; 
	private List matrix;/*[][] =    {  
								{0, 4, 4, 5, 0},
								{4, 0, 2, 0, 3},
								{4, 2, 0, 3, 1},
								{5, 0, 3, 0, 4},
								{0, 3, 1, 4, 0}
								};*/
	
	public Matrix()
	{
		matrix = new ArrayList<ArrayList<Integer>>();
		size = 1;
	}
	
	//add
	
	public void addNode(String ID)
	{
		List list;
		for (int i = 0; i < matrix.size(); ++i)
		{
			list = (List)matrix.get(i);
			list.add(new Integer(0));
		} 
		matrix.add(new ArrayList<Integer>());
		list = (List)matrix.get(matrix.size() - 1);
		for (int i = 0; i < matrix.size(); ++i)
		{
			list.add(new Integer(0));
		}
	}
	
	public void addEdge(int ID1, int ID2)
	{
		
	}
	
	public void print()
	{
		List list;
		for (int i = 0; i < matrix.size(); ++i)
		{
			list = (List)matrix.get(i);
			for (int j = 0; j < list.size(); ++j)
				System.out.print(list.get(j) + " ");
			System.out.println();
		}
	}
	
	
	public int[][] getMatrix()
	{
		int mas[][] =   {  
				{0, 4, 4, 5, 99, 99},
				{4, 0, 8, 99, 3, 99},
				{4, 8, 0, 3, 10, 99},
				{5, 99, 3, 0, 4, 99},
				{99, 3, 10, 4, 0, 1},
				{99, 99, 99, 99, 1, 0}
				};
		int max[] = new int[size];
		int k, i ,j;
		for (i = 0; i < size; ++i)
			max[i] = 0;
		for (k = 0; k < size; ++k)
			for (i = 0; i < size; ++i)
				for (j = 0; j < size; ++j)
					mas[i][j] = Math.min(mas[i][j], mas[i][k] + mas[k][j]);
		for (i = 0; i < size ; ++i)
			for (j = 0 ; j < size ; ++j)
			{
				if (mas[i][j] > max[i])
					max[i] = mas[i][j];
			}
		for (i = 0; i < size; ++i)
			System.out.print(max[i] + " ");
		System.out.println("");
		int min = max[0];
		List<Integer> num = new ArrayList<Integer>();
		for (i = 0; i < size; ++i)
		{
			if (min > max[i])
			{
				num.clear();
				min = max[i];
				num.add(i);
			}
			else
				if (min == max[i])
				{
					num.add(i);
				}
		}
		System.out.println("min = " + min);
		System.out.println(num);
		List<Pair<Integer, Integer>> pair = new ArrayList<Pair<Integer, Integer>>();
		int sum;
		for (Integer in : num)
		{
			sum = 0;
			for (i = 0; i < size; ++i)
			{
				sum += mas[in][i];
			}
			pair.add(new Pair(in, sum));
			System.out.println("sum " + in +" = " + sum);
		}
		
		min = pair.get(0).getSecond();
		int min_num = pair.get(0).getFirst();
		
		for (Pair<Integer, Integer> p : pair)
		{
			if (min > p.getSecond())
			{
				min = p.getSecond();
				min_num = p.getFirst();
			}
		}
		
		System.out.println(min_num);
		
		return mas;
	}
	
	
}
