package it.polito.tdp.PremierLeague.model;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private Graph<Match, DefaultWeightedEdge> grafo;
	private PremierLeagueDAO dao;
	private Map<Integer, Match> map;
	
	

	public Model() {
		
		this.dao = new PremierLeagueDAO() ;
	}



	public void creaGrafo(Month mese, int minuti) {
		this.grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		// aggiungo i vertici
		Graphs.addAllVertices(this.grafo, dao.getVertici(mese));
		this.map= new HashMap<Integer, Match>();
		for(Match m: dao.getVertici(mese))
		{
			map.put(m.getMatchID(),m);
		}
		
		for(Adiacenza a : dao.getArchi(map, mese,  minuti))
		{
			Graphs.addEdgeWithVertices(this.grafo, a.getM1(), a.getM2(), a.getPeso());
		}
		
		
	}
	
	 public int nVertivi()
		{
			return this.grafo.vertexSet().size();
		}
		
		public int nArchi ()
		{
			return this.grafo.edgeSet().size();
		}
		
		
	
		public Graph<Match, DefaultWeightedEdge> getGrafo() {
			return grafo;
		}



		public void setGrafo(Graph<Match, DefaultWeightedEdge> grafo) {
			this.grafo = grafo;
		}



		public List<Adiacenza> getCnnessioneMax()
		{
			List <Adiacenza> maggiore = new ArrayList <Adiacenza>();
			int max= Integer.MIN_VALUE;
			for(DefaultWeightedEdge e: this.grafo.edgeSet())
			{
				if(this.grafo.getEdgeWeight(e)>max)
				{
					max=(int) this.grafo.getEdgeWeight(e);
				
				}
				
			}
			for(DefaultWeightedEdge e: this.grafo.edgeSet())
			{
				if(this.grafo.getEdgeWeight(e)==max)
				{
					
					maggiore.add(new Adiacenza(this.grafo.getEdgeSource(e), this.grafo.getEdgeTarget(e), (int)this.grafo.getEdgeWeight(e)));

				}
				
			}
			
			
			return maggiore;
		}
		
}
