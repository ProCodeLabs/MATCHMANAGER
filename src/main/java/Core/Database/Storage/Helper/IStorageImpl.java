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

	CompletableFuture<List<Player>> getAllPlayers();


	CompletableFuture<Team> addTeam( String teamName );

	CompletableFuture<Team> getTeam( String teamName );

	CompletableFuture<Team> getTeam( long id );

	CompletableFuture<Void> removeTeam( String teamName );

	CompletableFuture<Void> updateTeam( String teamName, Team team );

	CompletableFuture<List<Team>> getAllTeams();


	CompletableFuture<Match> addMatch( Team teamA, Team teamB, Team resultTeam );

	CompletableFuture<Void> updateMatch( Match match );

	CompletableFuture<Match> getMatch( long id );

	CompletableFuture<Integer> getMatchCount();

	CompletableFuture<List<Match>> getAllMatches();
}
