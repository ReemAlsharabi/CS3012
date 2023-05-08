package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution
{

	public Solution()
	{
	}

	public List<Boy> stable_matching(List<Boy> boys, List<Girl> girls, List<Person> people)
	{

		boolean flag = false;
		int i = 0;

		while (!flag)
		{
			// while at least one girl doesn't have a boyfriend
			flag = checkFlag(girls);

			if (boys.get(i).getGirlFriend() == null)
			{
				getGirlfriendForBoy(boys.get(i));
			}

			if (i < boys.size() - 1)
			{
				i++;
			} else
			{
				i = 0;
			}

		}

		return boys;
	}

	public boolean checkFlag(List<Girl> girls)
	{
		boolean flag = true;
		for (Girl g : girls)
		{
			if (g.getBoyFriend() == null)
			{
				flag = false;
			}
		}
		return flag;
	}

	public void getGirlfriendForBoy(Boy currentBoy)
	{
		int pos = 0;

		while (currentBoy.getGirlFriend() == null && pos < currentBoy.getGirlsList().size())
		{

			if (currentBoy.getGirlsList().get(pos).getBoyFriend() == null)
			{
				currentBoy.setGirlFriend(currentBoy.getGirlsList().get(pos));
				currentBoy.getGirlsList().get(pos).setBoyFriend(currentBoy);
			} else
			{
				checkIfBestOption(currentBoy, pos);
			}
			pos++;
		}

	}

	public Map<String, Integer> mapCurrentGirlElections(Boy currentBoy, int pos)
	{

		Map<String, Integer> map = new HashMap<String, Integer>();
		List<Boy> currentGirlList = currentBoy.getGirlsList().get(pos).getBoysList();

		for (int auxPos = 0; auxPos < currentGirlList.size(); auxPos++)
		{
			Boy aux = currentBoy.getGirlsList().get(pos).getBoysList().get(auxPos);
			map.put(aux.getName(), auxPos + 1);
		}

		return map;
	}

	public void checkIfBestOption(Boy currentBoy, int pos)
	{

		Map<String, Integer> map = mapCurrentGirlElections(currentBoy, pos);

		if (map.get(currentBoy.getName()) < map.get(currentBoy.getGirlsList().get(pos).getBoyFriend().getName()))
		{
			for (Boy b : currentBoy.getGirlsList().get(pos).getBoysList())
			{
				if (b.getGirlFriend() != null)
				{
					if (b.getGirlFriend().equals(currentBoy.getGirlsList().get(pos)))
					{
						b.setGirlFriend(null);
					}
				}
			}
			currentBoy.getGirlsList().get(pos).setBoyFriend(currentBoy);
			currentBoy.setGirlFriend(currentBoy.getGirlsList().get(pos));
		}
	}

}
