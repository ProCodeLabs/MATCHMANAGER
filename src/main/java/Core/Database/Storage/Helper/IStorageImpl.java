package Core.Database.Storage.Helper;

import Core.Data.Match;
import Core.Data.Player;
import Core.Data.Team;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IStorageImpl
{
	CompletableFuture<Player> addPlayer( String teamName, Player player );

	CompletableFuture<Void> updatePlayer( int id, Player newPlayer );

	CompletableFuture<Player> getPlayer( int id );

	CompletableFuture<List<Player>> getAllTeamPlayers( String teamName );


	CompletableFuture<Team> addTeam( String teamName );

	CompletableFuture<Void> addPlayerToTeam( Match match, Player user );

	CompletableFuture<Team> getTeam( String teamName );

	CompletableFuture<Void> updateTeam( Team targetTeam, Team newTeam );


	CompletableFuture<Match> addMatch( Team teamA, Team teamB );

	CompletableFuture<Void> endMatch( Team winner );

	CompletableFuture<Void> updateMatch( Match match );

	CompletableFuture<Match> getMatch( int id );


	CompletableFuture<List<Player>> getAllPlayers();

	CompletableFuture<List<Team>> getAllTeams();

	CompletableFuture<List<Match>> getAllMatches();
}
