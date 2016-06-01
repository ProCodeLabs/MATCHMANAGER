package Database.CoreClasses;

import java.util.ArrayList;

public interface Tournament
{
	ArrayList<Team> _Teams = new ArrayList<>();
	ArrayList<Match> _Matches = new ArrayList<>();
	ArrayList<Player> _Players = new ArrayList<>();

	void addMatch();
	void addTeam();
	void addPlayer();
}
