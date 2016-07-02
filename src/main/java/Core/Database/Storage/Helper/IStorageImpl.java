package Core.Database.Storage.Helper;

import Core.Data.Match;
import Core.Data.Player;
import Core.Data.Team;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IStorageImpl
{
	CompletableFuture<Player> addPlayer( String teamName, Player player );

	CompletableFuture<Void> updatePlayer( long id, Player newPlayer );

	CompletableFuture<Void> removePlayer( long id );

	CompletableFuture<Player> getPlayer( long id );

	CompletableFuture<List<Player>> getAllTeamPlayers( String teamName );


	CompletableFuture<Team> addTeam( String teamName );

	CompletableFuture<Team> getTeam( String teamName );

	CompletableFuture<Void> removeTeam( String teamName );

	CompletableFuture<Void> updateTeam( String teamName, Team team );


	CompletableFuture<Match> addMatch( Team teamA, Team teamB );

	CompletableFuture<Void> endMatch( Match match, Team winner );

	CompletableFuture<Void> updateMatch( Match match );

	CompletableFuture<Match> getMatch( long id );


	CompletableFuture<List<Player>> getAllPlayers();

	CompletableFuture<List<Team>> getAllTeams();

	CompletableFuture<List<Match>> getAllMatches();
}
