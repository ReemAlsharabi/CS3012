package model;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI
{

	private Solution solution;

	public GUI()
	{
	}

	public List<JTextField> enter_names(int amountOfNames) throws Exception
	{

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2, 8, 8));

		List<JTextField> namesList = names(panel, amountOfNames);

		int result = JOptionPane.showConfirmDialog(null, panel, "Stable Matching", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if (result != JOptionPane.OK_OPTION)
			throw new Exception("Operation canceled");

		return namesList;
	}

	public void pick_options(List<Person> people) throws Exception
	{

		String[] girlsNames = new String[people.size() / 2];
		for (int i = 0; i < people.size() / 2; i++)
		{
			girlsNames[i] = people.get(i).getName();
		}

		String[] boysNames = new String[people.size() / 2];
		for (int j = 0, i = people.size() / 2; i < people.size(); i++, j++)
		{
			boysNames[j] = people.get(i).getName();
		}

		JPanel panel = new JPanel(new GridLayout(people.size(), 2, 8, 8));

		List<JComboBox> elections = getElectionsFromPeople(panel, people, girlsNames, boysNames);

		int result = JOptionPane.showConfirmDialog(null, panel, "Stable Matching", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if (result != JOptionPane.OK_OPTION)
			throw new Exception("Operation canceled");

		List<Person> auxElections = adjustElections(elections, people);

		people = electionsToPeopleList(people, auxElections);

		List<Boy> boys = new ArrayList<Boy>();
		List<Girl> girls = new ArrayList<Girl>();

		for (Person person : people)
		{
			if (person instanceof Boy)
			{
				boys.add((Boy) person);

			} else if (person instanceof Girl)
			{
				girls.add((Girl) person);
			}
		}

		printResults(solution.stable_matching(boys, girls, people));

	}

	/* Other methods */
	public List<JTextField> names(JPanel panel, int quantityOfNames)
	{
		List<JTextField> namesList = new ArrayList<JTextField>();
		for (int i = 0; i < 2 * quantityOfNames; i++)
		{
			JTextField boy = new JTextField("");
			if (i < quantityOfNames)
			{
				JTextField girl = new JTextField("");
				panel.add(new JLabel("Name of Student number " + (i + 1) + " GroupA: "));
				panel.add(girl);
				namesList.add(girl);
			} else
			{
				panel.add(new JLabel("Name of Student number " + (i - quantityOfNames + 1) + " GroupB: "));
				panel.add(boy);
				namesList.add(boy);
			}
		}
		return namesList;
	}

	public List<Person> electionsToPeopleList(List<Person> people, List<Person> electionsAux)
	{
		for (int i = 0; i < Math.sqrt(electionsAux.size() / 2); i++)
		{
			for (int j = 0; j < people.size() / 2; j++)
			{
				if (people.get(j) instanceof Girl && electionsAux.get(j + (i * people.size() / 2)) instanceof Boy)
				{
					((Girl) people.get(j)).getBoysList().add((Boy) electionsAux.get(j + (i * people.size() / 2)));
				}
			}
			for (int k = people.size() / 2; k < people.size(); k++)
			{
				if (people.get(k) instanceof Boy && electionsAux.get((int) (k + Math.pow(people.size() / 2, 2)
						- (people.size() / 2) + (i * people.size() / 2))) instanceof Girl)
				{
					((Boy) people.get(k)).getGirlsList().add((Girl) electionsAux.get((int) (k
							+ Math.pow(people.size() / 2, 2) - (people.size() / 2) + (i * people.size() / 2))));
				}
			}
		}
		return people;
	}

	public List<Person> adjustElections(List<JComboBox> elections, List<Person> people)
	{
		List<Person> peopleAux = new ArrayList<Person>();
		for (JComboBox election : elections)
		{
			for (Person p : people)
			{
				if (p.getName().equals(election.getSelectedItem().toString()))
				{
					peopleAux.add(p);
				}
			}
		}
		Collections.reverse(peopleAux);
		List<Person> aux1 = peopleAux.subList(0, peopleAux.size() / 2);
		Collections.reverse(aux1);
		List<Person> aux2 = peopleAux.subList(peopleAux.size() / 2, peopleAux.size());
		Collections.reverse(aux2);
		List<Person> electionsAux = new ArrayList<Person>();
		electionsAux.addAll(aux1);
		electionsAux.addAll(aux2);
		return electionsAux;
	}

	public List<JComboBox> getElectionsFromPeople(JPanel panel, List<Person> people, String[] girlOptions,
			String[] boyOptions)
	{

		List<JComboBox> elections = new ArrayList<JComboBox>();

		int option = 0;

		for (int i = 0; i < people.size(); i++)
		{
			option++;
			if (option == (1 + people.size() / 2))
			{
				option = 1;
			}
			if (i < people.size() / 2)
			{
				for (int j = people.size() / 2; j < people.size(); j++)
				{
					panel.add(new JLabel("Enter the " + option + "° option of " + people.get(j).getName() + ":"));
					JComboBox girlCombo = new JComboBox(girlOptions);
					panel.add(girlCombo);
					elections.add(girlCombo);
				}
			} else
			{
				for (int j = 0; j < people.size() / 2; j++)
				{
					panel.add(new JLabel("Enter the " + option + "° option of " + people.get(j).getName() + ":"));
					JComboBox boyCombo = new JComboBox(boyOptions);
					panel.add(boyCombo);
					elections.add(boyCombo);
				}
			}

		}
		return elections;
	}

	public List<Person> createPeople(List<JTextField> names)
	{

		List<Person> people = new ArrayList<Person>();

		for (int i = 0; i < names.size(); i++)
		{
			if (i < (names.size() / 2))
			{
				String text = names.get(i).getText();
				Girl girl = new Girl(text, null);
				people.add(girl);
			} else
			{
				String text = names.get(i).getText();
				Boy boy = new Boy(text, null);
				people.add(boy);
			}
		}

		return people;
	}

	public void printResults(List<Boy> boys)
	{

		JPanel panel = new JPanel(new GridLayout(1, 0, 8, 8));

		for (Boy b : boys)
		{
			panel.add(new JLabel("Teammate of " + b.getName() + ": " + b.getGirlFriend().getName()));
		}

		int result = JOptionPane.showConfirmDialog(null, panel, "Stable Matching", JOptionPane.PLAIN_MESSAGE,
				JOptionPane.PLAIN_MESSAGE);
	}

	public Solution getSolution()
	{
		return solution;
	}

	public void setSolution(Solution solution)
	{
		this.solution = solution;
	}

}
