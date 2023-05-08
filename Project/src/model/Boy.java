package model;

import java.util.ArrayList;
import java.util.List;

public class Boy extends Person
{

	private List<Girl> girlsList = new ArrayList<Girl>();
	private Girl girlFriend;

	public Boy(String name, Girl girlFriend)
	{
		super(name);
		this.girlFriend = girlFriend;
	}

	public List<Girl> getGirlsList()
	{
		return girlsList;
	}

	public void setGirlsList(List<Girl> girlsList)
	{
		this.girlsList = girlsList;
	}

	public Girl getGirlFriend()
	{
		return girlFriend;
	}

	public void setGirlFriend(Girl girlFriend)
	{
		this.girlFriend = girlFriend;
	}
}
