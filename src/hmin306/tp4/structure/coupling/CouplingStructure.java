package hmin306.tp4.structure.coupling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import hmin306.tp4.structure.tree.ClassTree;
import hmin306.tp4.structure.tree.InvocationTree;
import hmin306.tp4.structure.tree.MethodTree;

public class CouplingStructure
{
	public List<CouplingNode> couplingNodes = new ArrayList<CouplingNode>();
	
	public int totalReferences = 0;
	
	public CouplingStructure(ClassTree classTree)
	{
		for(Map.Entry<String, MethodTree> classEntry : classTree.classTree.entrySet())
			for(Map.Entry<String, InvocationTree> methodDeclarationEntry : classEntry.getValue().methodTree
				.entrySet())
				for(Map.Entry<String, Collection<String>> methodInvocationEntry : methodDeclarationEntry
					.getValue().invocationTree.entrySet())
					if(!classEntry.getKey().equals(methodInvocationEntry.getKey()))
					{
						totalReferences += methodInvocationEntry.getValue().size();
						addCouplingNode(new CouplingNode(classEntry.getKey(), methodInvocationEntry.getKey(), methodInvocationEntry.getValue().size()));
					}
	}
	
	public void addFigure(String className, Object classFigure)
	{
		for(CouplingNode couplingNode : couplingNodes)
			if(couplingNode.classNameA.equals(className))
				couplingNode.classFigureA = classFigure;		
			else if(couplingNode.classNameB.equals(className))
				couplingNode.classFigureB = classFigure;
	}
	
	private void addCouplingNode(CouplingNode couplingNodeToAdd)
	{
		if(couplingNodes.contains(couplingNodeToAdd))
			couplingNodes.get(couplingNodes.indexOf(couplingNodeToAdd)).counter += couplingNodeToAdd.counter;
		else
			couplingNodes.add(couplingNodeToAdd);
	}
}	
