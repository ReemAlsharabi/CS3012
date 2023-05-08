package model;

import java.util.ArrayList;
import java.util.List;

public class Girl extends Person
{

	private List<Boy> boysList = new ArrayList<Boy>();
	private Boy boyFriend;

	public Girl(String name, Boy boyFriend)
	{
		super(name);
		this.boyFriend = boyFriend;
	}

	public List<Boy> getBoysList()
	{
		return boysList;
	}

	public void setBoysList(List<Boy> boyList)
	{
		this.boysList = boyList;
	}

	public Boy getBoyFriend()
	{
		return boyFriend;
	}

	public void setBoyFriend(Boy boyFriend)
	{
		this.boyFriend = boyFriend;
	}

}
